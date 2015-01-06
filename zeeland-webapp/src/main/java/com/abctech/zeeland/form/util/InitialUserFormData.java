package com.abctech.zeeland.form.util;


import com.abctech.zeeland.form.data.ExtendedUser;
import com.abctech.zeeland.wsAuthentication.WebserviceAuthentication;
import com.abctech.zeeland.wsObject.transform.userobject.ExtendedUserObjectTransformer;
import no.zett.model.enums.UserAccessRights;
import no.zett.model.enums.UserStatus;
import no.zett.service.facade.ServiceException_Exception;
import no.zett.service.facade.UserResponse;
import no.zett.service.facade.UserService;
import no.zett.service.facade.ZettUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InitialUserFormData {

    private final static Logger log = LoggerFactory.getLogger(InitialUserFormData.class);

    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm";

    @Resource(name = "webserviceAuthentication")
    private WebserviceAuthentication webserviceAuthentication;

    @Autowired
    private UserService userServicePortType;

    @Autowired
    private ExtendedUserObjectTransformer extendedUserObjectTransformer;

    public ModelAndView initialUserFormData(Integer userId, ExtendedUser extendedUserParm) throws ServiceException_Exception {

        ExtendedUser extendedUser = extendedUserParm;

        ModelAndView modelAndView = new ModelAndView();
        List<String> userAccessList = new ArrayList<String>();
        for (UserAccessRights right : UserAccessRights.values()) {
            userAccessList.add(right.toString());
        }
        modelAndView.addObject("userAccessList", userAccessList);

        Map<Integer, String> userAccessMap = new HashMap<Integer, String>();
        for (UserAccessRights right : UserAccessRights.values()) {
            userAccessMap.put(right.toInteger(), right.toString());
        }
        modelAndView.addObject("userAccessMap", userAccessMap);

        if (userId != null) {

            webserviceAuthentication.authentication(userServicePortType);
            UserResponse userResponse = userServicePortType.loadUser(userId);
            if (userResponse.getZettUser() == null) {
                log.debug("User is null");
                //  modelAndView.addObject("deleteMessage", "showcompany.warning.delete.contact");
                modelAndView.addObject("noresult", "showuser.message.warning.noresult.id");
                modelAndView.addObject("errorinput", userId);
                modelAndView.setViewName("searchuser");
                return modelAndView;
            }
            ZettUser zettUser = userResponse.getZettUser();

            modelAndView.addObject("zettUser", zettUser);
            modelAndView.addObject("zeelandUrl", "showad.html?adId=");
            modelAndView.addObject("zettUrl", "http://www.zett.no/bil__til_salgs.html?objectId=");
            modelAndView.addObject("rowUserAds", zettUser.getUserAds().size());
            //modelAndView.addObject("type", UserType.fromInteger(zettUser.getUserType()));
            modelAndView.addObject("userStatus", UserStatus.fromInteger(zettUser.getStatus()));

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT);
            modelAndView.addObject("registerTime", simpleDateFormat.format(zettUser.getRegisteredTime().toGregorianCalendar().getTime()));
            modelAndView.addObject("modifiedTime", simpleDateFormat.format(zettUser.getModifiedTime().toGregorianCalendar().getTime()));

            if (extendedUser == null) {
                extendedUser = extendedUserObjectTransformer.transform(zettUser);
            }
            extendedUser.setUserId(zettUser.getUserId());
        }
        modelAndView.addObject("extendedUserForm", extendedUser);
        modelAndView.setViewName("showuser");
        return modelAndView;
    }

}

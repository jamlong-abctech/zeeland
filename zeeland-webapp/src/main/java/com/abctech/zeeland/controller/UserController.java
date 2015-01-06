package com.abctech.zeeland.controller;

import com.abctech.zeeland.form.data.ExtendedUser;
import com.abctech.zeeland.form.util.InitialUserFormData;
import com.abctech.zeeland.form.validation.SaveUserValidation;
import com.abctech.zeeland.form.validation.SearchUserValidation;
import com.abctech.zeeland.wsAuthentication.WebserviceAuthentication;
import com.abctech.zeeland.wsObject.update.UserUpdate;
import no.zett.crypto.MD5Generator;
import no.zett.service.facade.ServiceException_Exception;
import no.zett.service.facade.UserResponse;
import no.zett.service.facade.UserService;
import no.zett.service.facade.ZettUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.xml.ws.soap.SOAPFaultException;
import java.io.IOException;

@Controller
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private InitialUserFormData initialUserFormData;

    @Autowired
    private UserUpdate userUpdate;

    @Autowired
    @Qualifier(value = "saveUserValidation")
    private SaveUserValidation saveUserValidation;

    @Autowired
    @Qualifier(value = "searchUserValidation")
    private SearchUserValidation searchUserValidation;

    @Resource(name = "webserviceAuthentication")
    private WebserviceAuthentication webserviceAuthentication;

    @Autowired
    private UserService userServicePortType;

    @RequestMapping(value = "searchuser.html")
    public ModelAndView searchUser(@ModelAttribute("extendedUser") ExtendedUser extendedUser,
                                   BindingResult result) throws IOException, ServiceException_Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("searchuser");
        return modelAndView;
    }

    @RequestMapping(value = "showuser.html")
    public ModelAndView showSingleUser(@ModelAttribute("extendedUser") ExtendedUser extendedUser
            , BindingResult result) throws ServiceException_Exception {

        ModelAndView modelAndView = new ModelAndView();
        searchUserValidation.validate(extendedUser, result);
        if (result.hasErrors()) {
            modelAndView.setViewName("searchuser");
            return modelAndView;
        } else {
            webserviceAuthentication.authentication(userServicePortType);
            Integer userId = null;
            UserResponse userResponse = null;
            if (extendedUser != null) {
                if (extendedUser.getUserId() != null) {
                    userId = extendedUser.getUserId();
                    userResponse = userServicePortType.loadUser(userId);

                } else {
                    if (extendedUser.getEmail() != null && !extendedUser.getEmail().isEmpty()) {
                        String email = extendedUser.getEmail();
                        userResponse = userServicePortType.loadUserByEmail(extendedUser.getEmail());
                        if (userResponse.getZettUser() != null && userResponse.getZettUser() != null) {
                            ZettUser zettUser = userResponse.getZettUser();
                            userId = zettUser.getUserId();
                        } else {
                             modelAndView.addObject("noresult", "showuser.message.warning.noresult.email");
                             modelAndView.addObject("errorinput" , email);
                             modelAndView.setViewName("searchuser");
                            return modelAndView;

                        }

                    }
                }

            }
            modelAndView = initialUserFormData.initialUserFormData(userId, null);
            return modelAndView;
        }
    }

    @RequestMapping(value = "showuserbyemail.html")
    public ModelAndView searchUserByEmail(@RequestParam(value = "email", required = true) String email) throws IOException, ServiceException_Exception {
        webserviceAuthentication.authentication(userServicePortType);
        UserResponse userResponse = userServicePortType.loadUserByEmail(email);
        ZettUser zettUser = userResponse.getZettUser();
        ModelAndView modelAndView = initialUserFormData.initialUserFormData(zettUser.getUserId(), null);
        return modelAndView;
    }

    @RequestMapping(value = "savepassword.html")
    public ModelAndView savePassword(@RequestParam(value = "userId", required = true) Integer userId
            , @RequestParam(value = "password", required = true) String password) throws ServiceException_Exception {

        UserResponse userResponse = userServicePortType.loadUser(userId);

        ZettUser zettUser = userResponse.getZettUser();
        zettUser.setPassword(MD5Generator.encrypt(password));
        userServicePortType.saveUser(zettUser);

        ModelAndView modelAndView = initialUserFormData.initialUserFormData(userId, null);
        modelAndView.addObject("updateMessage", "showuser.message.updated.password");
        return modelAndView;
    }

    @RequestMapping(value = "adduser.html")
    public ModelAndView addNewUser() throws ServiceException_Exception{
        ExtendedUser extendedUser = new ExtendedUser();
        return initialUserFormData.initialUserFormData(null, extendedUser);
    }



    @RequestMapping(value = "saveuser.html")
    public ModelAndView saveUser(@ModelAttribute("extendedUserForm") ExtendedUser extendedUser,
                                 BindingResult result) throws ServiceException_Exception {

        Integer userId = extendedUser.getUserId();
        saveUserValidation.validate(extendedUser, result);
        String updateMessage = null;

        if (!result.hasErrors()) {
            try {
                if (extendedUser.getUserId() != null) {
                    //update user
                    /*ZettUser zettUser = */ userUpdate.update(extendedUser);
                    updateMessage = "showuser.message.updated";

                } else {
                    //add new user
                    ZettUser zettUser = userUpdate.add(extendedUser);
                    updateMessage = "showuser.message.added";
                    userId = zettUser.getUserId();
                }
            } catch (SOAPFaultException err) {
                log.trace(err.getMessage());
                result.addError(generateErrorWithException(err));
            }
        }

        ModelAndView modelAndView = initialUserFormData.initialUserFormData(userId, extendedUser);
        if (updateMessage != null) {
            modelAndView.addObject("updateMessage", updateMessage);
        }

        return modelAndView;
    }

    @RequestMapping(value = "deleteuser.html")
    public ModelAndView deleteUser( @ModelAttribute("extendedUser") ExtendedUser extendedUser,
                                    @RequestParam(value ="delid" , required = true) Integer userId) throws ServiceException_Exception {
        String updatemessage = null ;
        log.debug("++++ Delete User ++++++");
        UserResponse userResponse = userServicePortType.loadUser(userId);
        ZettUser zettUser = userResponse.getZettUser() ;
        userServicePortType.deleteUser(zettUser) ;
        updatemessage = "showuser.message.deleted" ;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("updateMessage",updatemessage);
        modelAndView.setViewName("searchuser");
        return modelAndView;

    }

    private ObjectError generateErrorWithException(Exception err) {
        if (err.getMessage().contains("duplicate") && err.getMessage().contains("users_email_idx")) {
            return new ObjectError("email", new String[]{"validation.email.duplicate"}, null, null);
        }
        return new ObjectError("*", err.getMessage());
    }

}

package com.abctech.zeeland.wsObject.update;

import com.abctech.mockland.runner.Mockland;
import com.abctech.zeeland.form.validation.SaveUserValidation;
import com.abctech.zeeland.form.validation.SearchUserValidation;
import com.abctech.zeeland.security.Token;
import com.abctech.zeeland.wsAuthentication.WebserviceAuthentication;
import no.zett.service.facade.AdService;
import no.zett.service.facade.ServiceException_Exception;
import no.zett.service.facade.UserService;
import no.zett.service.facade.ZettAdObject;
import no.zett.service.facade.ZettUser;
import no.zett.service.facade.ZettUserAdObject;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


public class SetAdOwner {
    private final static Logger log = org.slf4j.LoggerFactory.getLogger(SetAdOwner.class);

    private Mockland mockland;

    @Autowired
    private AdService adService;

    @Autowired
    private UserUpdate userUpdater;

    @Autowired
    @Qualifier(value = "tokenObject")
    private Token tokenObject;

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


    /*@Before
    public void setUp(){
        mockland = Mockland.createMockland();
    }
    @After
    public void tearDown(){                                                 //2154347   "an-romb@online.no"
        mockland.stop();
    }*/


    public ZettUser setAdOwner(ZettUser zettUser, ZettAdObject zettAdObject) throws ServiceException_Exception {
/*
        ObjectFactory objectFactory = new ObjectFactory();
        AdObjectResponse adObjectResponse = adService.loadObject(adObjectId);


        ZettAdObject zettAdObject = adObjectResponse.getZettAdObject().getValue() ;

         zettUser = userResponse.getZettUser().getValue();


        JAXBElement<ArrayOfZettUserAdObject> arrayOfZettAdObject = userResponse.getZettUser().getValue().getUserAds() ;
        List<ZettUserAdObject> zettUserAdObjectList = arrayOfZettAdObject.getValue().getZettUserAdObject() ;
*/
        List<ZettUserAdObject> zettUserAdObjectList = zettUser.getUserAds();

        for (ZettUserAdObject zettUserAdObject : zettUserAdObjectList) {
            if (zettUserAdObject.getAdObjectId() != zettAdObject.getObjectId()) {
                ZettUserAdObject zettUserToAdd = new ZettUserAdObject();

                zettUserToAdd.setAdObjectId(zettAdObject.getObjectId());
                zettUserToAdd.setTitle(zettAdObject.getTitle());
                zettUserToAdd.setObjectId(zettAdObject.getCompany().getObjectId());

                zettUserAdObjectList.add(zettUserToAdd);
            }
        }

        for(ZettUserAdObject zettUserAdObject:zettUserAdObjectList){
            zettUser.getUserAds().add(zettUserAdObject);
        }

        return zettUser;

    }

    @Test
    public void testSetAdOwner() throws ServiceException_Exception {

        ZettAdObject zettAdObject = new ZettAdObject();

        zettAdObject.setObjectId(2154347);
        zettAdObject.setTitle("Title");
        // zettAdObject.getCompany().getValue().setObjectId(0001);

        ZettUser zettUser = new ZettUser();
       List<ZettUserAdObject> arrayOfZettUserAdObject = zettUser.getUserAds();

        List<ZettUserAdObject> zettUserAdObjectList = new ArrayList<ZettUserAdObject>();
        ZettUserAdObject zettUserAdObject = new ZettUserAdObject();

        zettUserAdObject.setAdObjectId(0001);
        zettUserAdObject.setEditUrl("EditTitle");
        zettUserAdObject.setObjectId(01);
        zettUserAdObject.setTitle("XXXX");

        zettUserAdObjectList.add(zettUserAdObject);

        arrayOfZettUserAdObject.add(zettUserAdObject) ;
        for(ZettUserAdObject zettUserAdObject1:arrayOfZettUserAdObject){
            zettUser.getUserAds().add(zettUserAdObject1);
        }

        ZettAdObject adObjectToAdd = new ZettAdObject();
        adObjectToAdd.setObjectId(30001);
        adObjectToAdd.setTitle("AddTitle");
        adObjectToAdd.setObjectId(4000);

        Assert.assertEquals("Expect 1 before add", 1, zettUserAdObjectList.size());
        zettUser = setAdOwner(zettUser, adObjectToAdd);

       // Assert.assertEquals("Expect 1 before add", 2, zettUserAdObjectList.size());


    }


}
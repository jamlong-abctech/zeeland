package com.abctech.zeeland.wsObject.update;

import com.abctech.mockland.runner.Mockland;
import com.abctech.zeeland.form.data.ExtendedAdObject;
import com.abctech.zeeland.wsObject.transform.adobject.ExtendedAdObjectTransformer;
import com.thoughtworks.xstream.XStream;
import no.zett.model.base.ObjectMedia;
import no.zett.service.facade.ZettAdObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImageUpdateTest {

    private final static Logger log = LoggerFactory.getLogger(ImageUpdateTest.class);

    private Mockland mockland;

    @Before
    public void setUp() {
        mockland = Mockland.createMockland();
        mockland.start();
    }

    @After
    public void tearDown() throws Exception {
        mockland.stop();
    }
    
    public List<ObjectMedia> setNewMainImage(List<ObjectMedia> objectMediaList,int mediaId){

        List<ObjectMedia> newObjectMedia = new ArrayList<ObjectMedia>();
        for(ObjectMedia objectMedia:objectMediaList){
            if(objectMedia.getMediaId().equals(mediaId)){
                objectMedia.setOrder(0);
                newObjectMedia.add(objectMedia);
                objectMediaList.remove(objectMedia);
            }
        }

        for(int i = 0;i<objectMediaList.size();i++){
            ObjectMedia objectMedia = objectMediaList.get(i);
            objectMedia.setOrder(i+1);
            newObjectMedia.add(objectMedia);
        }

        return newObjectMedia;
    }

    @Test
    public void imageUpdateTest() throws Exception{
        String mockUrl = mockland.getBaseURI() +  "xml/zeeland/ad/zettadobject.xml";
        XStream xstream = new XStream();
        ZettAdObject zettAdObject = (ZettAdObject) xstream.fromXML(new URL(mockUrl).openStream());
        ExtendedAdObjectTransformer extendedCompanyObjectTransformer = new ExtendedAdObjectTransformer();
        ExtendedAdObject extendedAdObject = extendedCompanyObjectTransformer.transform(zettAdObject);

        for(ObjectMedia objectMedia:extendedAdObject.getMedia()){
            log.debug("media id = "+objectMedia.getMediaId());
        }
        
        List<ObjectMedia> objectMediaList = setNewMainImage(extendedAdObject.getMedia(),60759717);
        Assert.assertEquals((objectMediaList.get(0).getMediaId()).toString(),"60759717");
        Assert.assertEquals((objectMediaList.get(0).getOrder()).toString(),"0");
        for(ObjectMedia objectMedia : objectMediaList){
            log.debug("after = "+objectMedia.getMediaId() + " order = "+objectMedia.getOrder());
        }

    }
}

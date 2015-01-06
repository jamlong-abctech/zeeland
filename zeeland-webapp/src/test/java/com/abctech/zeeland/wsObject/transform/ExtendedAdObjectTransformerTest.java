package com.abctech.zeeland.wsObject.transform;

import com.abctech.mockland.runner.Mockland;
import com.abctech.zeeland.form.data.ExtendedAdObject;
import com.abctech.zeeland.wsObject.transform.adobject.ExtendedAdObjectTransformer;
import com.thoughtworks.xstream.XStream;
import junit.framework.Assert;
import no.zett.service.facade.ZettAdObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class ExtendedAdObjectTransformerTest {

    private final static Logger log = LoggerFactory.getLogger(ExtendedAdObjectTransformerTest.class);

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

    @Test
    public void CompanyTransformerTest() throws Exception{
        String mockUrl = mockland.getBaseURI() +  "xml/zeeland/ad/zettadobject.xml";
        XStream xstream = new XStream();
        ZettAdObject zettAdObject = (ZettAdObject) xstream.fromXML(new URL(mockUrl).openStream());
        ExtendedAdObjectTransformer extendedCompanyObjectTransformer = new ExtendedAdObjectTransformer();
        ExtendedAdObject extendedAdObject = extendedCompanyObjectTransformer.transform(zettAdObject);
        Assert.assertNotNull(extendedAdObject);
        ZettAdObject updateZettAdObject = extendedCompanyObjectTransformer.transform(extendedAdObject);
        Assert.assertNotNull(updateZettAdObject);
    }
}

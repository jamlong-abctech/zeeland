package com.abctech.zeeland.wsObject.transform;


import com.abctech.mockland.runner.Mockland;
import com.abctech.zeeland.form.data.ExtendedCompanyObject;
import com.abctech.zeeland.wsObject.transform.companyobject.ExtendedCompanyObjectTransformer;
import com.thoughtworks.xstream.XStream;
import junit.framework.Assert;
import no.zett.service.facade.ZettCompanyObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class ExtendedCompanyObjectTransformerTest {

    private final static Logger log = LoggerFactory.getLogger(ExtendedCompanyObjectTransformerTest.class);

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
        String mockUrl = mockland.getBaseURI() +  "xml/zeeland/company/zettcompanyobject.xml";
        XStream xstream = new XStream();
        ZettCompanyObject zettCompanyObject = (ZettCompanyObject) xstream.fromXML(new URL(mockUrl).openStream());
        ExtendedCompanyObjectTransformer extendedCompanyObjectTransformer = new ExtendedCompanyObjectTransformer();
        ExtendedCompanyObject extendedCompanyObject = extendedCompanyObjectTransformer.transform(zettCompanyObject);
        Assert.assertNotNull(extendedCompanyObject);
        ZettCompanyObject updateZettCompanyObject = extendedCompanyObjectTransformer.transform(extendedCompanyObject);
        Assert.assertNotNull(updateZettCompanyObject);
    }
}

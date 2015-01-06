package com.abctech.zeeland.wsObject.transform.companyobject;

import com.abctech.zeeland.form.data.ExtendedCompanyObject;
import com.abctech.zeeland.wsObject.transform.base.ObjectMediaListTransformer;
import no.zett.model.CompanyObject;
import no.zett.model.base.ObjectMedia;
import no.zett.service.facade.ZettCompanyObject;
import no.zett.service.facade.ZettObjectMedia;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtendedCompanyObjectTransformer{
//    private final static Logger log = LoggerFactory.getLogger(ExtendedCompanyObjectTransformer.class);
    public ZettCompanyObject transform(ExtendedCompanyObject extendedCompanyObject){
        CompanyObjectTransformer companyObjectTransformer = new CompanyObjectTransformer();
        ZettCompanyObject zettCompanyObject = companyObjectTransformer.transform(extendedCompanyObject);
        return zettCompanyObject;
    }

    public ExtendedCompanyObject transform(ZettCompanyObject zettCompanyObject){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        CompanyObjectTransformer companyObjectTransformer = new CompanyObjectTransformer();
        CompanyObject companyObject = companyObjectTransformer.transform(zettCompanyObject);
        ExtendedCompanyObject extendedCompanyObject = new ExtendedCompanyObject();

        extendedCompanyObject.setObjectId(companyObject.getObjectId());
        extendedCompanyObject.setDataTable(companyObject.getDataTable());
        extendedCompanyObject.setStatus(companyObject.getStatus());
        extendedCompanyObject.setStatusValue(companyObject.getStatusValue());
        extendedCompanyObject.setAdminStatus(companyObject.getAdminStatus());
        extendedCompanyObject.setAdminStatusValue(companyObject.getAdminStatusValue());
        extendedCompanyObject.setCategory(companyObject.getCategory());
        extendedCompanyObject.setTitle(companyObject.getTitle());
        extendedCompanyObject.setShortDescription(companyObject.getShortDescription());
        extendedCompanyObject.setPublishToTime(companyObject.getPublishToTime());
        extendedCompanyObject.setPublishFromTime(companyObject.getPublishToTime());
        extendedCompanyObject.setCreatedBy(companyObject.getCreatedBy());
        extendedCompanyObject.setCreatedTime(companyObject.getCreatedTime());
        if(companyObject.getCreatedTime() != null){
            Date date = companyObject.getCreatedTime();
            extendedCompanyObject.setCreatedTimeString(simpleDateFormat.format(date));
        }
        extendedCompanyObject.setDeletedBy(companyObject.getDeletedBy());
        extendedCompanyObject.setDeletedTime(companyObject.getDeletedTime());
        extendedCompanyObject.setContacts(companyObject.getContacts());
        extendedCompanyObject.setAttributes(companyObject.getAttributes());
        extendedCompanyObject.setMedia(companyObject.getMedia());
        extendedCompanyObject.setParentId(companyObject.getParentId());
        extendedCompanyObject.setBillingCompanyId(companyObject.getBillingCompanyId());
        extendedCompanyObject.setAddress(companyObject.getAddress());
        extendedCompanyObject.setPackagedeal(companyObject.getPackagedeal());
        extendedCompanyObject.setModifiedBy(companyObject.getModifiedBy());
        extendedCompanyObject.setModifiedTime(companyObject.getModifiedTime());
        if(companyObject.getModifiedTime() != null){
            Date date = companyObject.getModifiedTime();
            extendedCompanyObject.setModifiedTimeString(simpleDateFormat.format(date));
        }
        extendedCompanyObject.setSystemModifiedTime(companyObject.getSystemModifiedTime());

        if(companyObject.getSystemModifiedTime() != null){
            Date date = companyObject.getSystemModifiedTime();
            extendedCompanyObject.setSystemModifiedTimeString(simpleDateFormat.format(date));
        }
        if(zettCompanyObject.getPackageDeal()!=null){
            extendedCompanyObject.setPackageDealTypeValue(zettCompanyObject.getPackageDeal().getPackageDealtype());
        }

        ZettObjectMedia[] zettObjectMedias = zettCompanyObject.getMedia().toArray(
                new ZettObjectMedia[zettCompanyObject.getMedia().size()]);
        List<ObjectMedia> objectMediaList = ObjectMediaListTransformer.transform(zettObjectMedias);
        extendedCompanyObject.setObjectMediaList(objectMediaList);

        return extendedCompanyObject;
    }
}

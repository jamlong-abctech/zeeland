package com.abctech.zeeland.form.util;

import com.abctech.zeeland.wsAuthentication.WebserviceAuthentication;
import no.zett.model.base.AdObjectCategory;
import no.zett.service.facade.CategoryService;
import no.zett.service.facade.ZettAdObjectCategory;
import no.zett.service.facade.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CategoryBuilder {

    //private final static Logger log = LoggerFactory.getLogger(InitialUserFormData.class);

    @Resource(name = "webserviceAuthentication")
    private WebserviceAuthentication webserviceAuthentication;

    @Autowired
    private CategoryService categoryServicePortType;

    private Map<Integer,String> categoryMap;
    private Map<Integer,AdObjectCategory> categoryObjectMap;
    private List<AdObjectCategory> adObjectCategoryList;

    public void generate(){
        if(categoryMap == null){
            categoryMap = new HashMap<Integer, String>();
            categoryObjectMap = new HashMap<Integer, AdObjectCategory>();
            adObjectCategoryList = new ArrayList<AdObjectCategory>();

            webserviceAuthentication.authentication(categoryServicePortType);
            CategoryResponse categoryResponse = categoryServicePortType.getCategoryTreeSortedById();
            //ZettAdObjectCategory[] arrayOfZettAdObjectCategory = categoryResponse.getZettcategories();

            for (ZettAdObjectCategory zettAdObjectCategory : categoryResponse.getZettcategories()) {
                AdObjectCategory adObjectCategory = convert(zettAdObjectCategory);
                categoryMap.put(adObjectCategory.getCategoryId(),adObjectCategory.getName());
                categoryObjectMap.put(adObjectCategory.getCategoryId(),adObjectCategory);
                adObjectCategoryList.add(adObjectCategory);
            }
        }
    }

    private AdObjectCategory convert(ZettAdObjectCategory zettAdObjectCategory){
        AdObjectCategory adObjectCategory = new AdObjectCategory();
        adObjectCategory.setCategoryId(zettAdObjectCategory.getCategoryId());
        adObjectCategory.setFullname(zettAdObjectCategory.getFullname());
        adObjectCategory.setName(zettAdObjectCategory.getName());
        adObjectCategory.setLink(zettAdObjectCategory.getLink());
        adObjectCategory.setObject_id(zettAdObjectCategory.getObjectId());
        adObjectCategory.setParent(zettAdObjectCategory.getParent());
        adObjectCategory.setSeq_id(zettAdObjectCategory.getSeqId());
        adObjectCategory.setType(zettAdObjectCategory.getType());
        return adObjectCategory;
    }

    public List<AdObjectCategory> getChildCategory(int categoryId){
        List<AdObjectCategory> categoryList = new ArrayList<AdObjectCategory>();
        for(AdObjectCategory adObjectCategory:adObjectCategoryList){
            if(adObjectCategory.getParent().equals(categoryId)){
                categoryList.add(adObjectCategory);
            }
        }
        return categoryList;
    }

    public AdObjectCategory getCategoryById(int categoryId){
        return categoryObjectMap.get(categoryId);
    }

    public Map<Integer,String> getCategoryMap(){
        if(categoryMap == null){
            generate();
        }
        return categoryMap;
    }
}

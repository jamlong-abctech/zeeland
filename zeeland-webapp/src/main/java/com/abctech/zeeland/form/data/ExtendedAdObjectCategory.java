package com.abctech.zeeland.form.data;

import no.zett.model.base.AdObjectCategory;

import java.util.List;

public class ExtendedAdObjectCategory extends AdObjectCategory{

    private List<ExtendedAdObjectCategory> child;

    public List<ExtendedAdObjectCategory> getChild() {
        return child;
    }

    public void setChild(List<ExtendedAdObjectCategory> child) {
        this.child = child;
    }
}

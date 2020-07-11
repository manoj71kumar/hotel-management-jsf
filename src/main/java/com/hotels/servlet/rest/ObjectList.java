package com.hotels.servlet.rest;

import com.hotels.domain.AbstractEntity;

import java.util.List;

public class ObjectList<T extends AbstractEntity<Long>> {

    private List<T> data;

    public ObjectList(List<T> data){
        this.data=data;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}

package com.eterna.yofi.demoyofi.dto;

import com.google.gson.Gson;

import java.io.Serializable;

public class BaseObject implements Serializable {

    private static final long serialVersionUID = 1L;

    public BaseObject() {
        //nothing to do
    };

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}

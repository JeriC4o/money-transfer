package com.yetanotherbank.api.web.model;

import java.io.Serializable;

public class DefaultResponse<T extends Serializable> implements Serializable {
    protected T data;

    public DefaultResponse() {
    }

    public DefaultResponse(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

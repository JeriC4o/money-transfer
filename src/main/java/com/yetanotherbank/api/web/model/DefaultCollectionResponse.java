package com.yetanotherbank.api.web.model;

import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

public class DefaultCollectionResponse<T extends Serializable> implements Serializable {
    protected Collection<T> data;
    protected int size;

    public DefaultCollectionResponse() {
    }

    public DefaultCollectionResponse(Collection<T> data) {
        this.data = ObjectUtils.defaultIfNull(data, Collections.EMPTY_LIST);
        size = data.size();
    }

    public Collection<T> getData() {
        return data;
    }
}

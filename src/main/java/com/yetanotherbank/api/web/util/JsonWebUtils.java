package com.yetanotherbank.api.web.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Filter;
import spark.Request;
import spark.ResponseTransformer;
import spark.Route;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class JsonWebUtils {

    public static final String JSON_TYPE = "application/json; charset=utf-8";

    private ObjectMapper mapper;

    @Inject
    public JsonWebUtils(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Route response(Route requestAction) {
        return (req, res) -> {
            Object resut = requestAction.handle(req, res);
            res.type(JSON_TYPE);
            if (resut != null) {
                return mapper.writeValueAsString(resut);
            }
            return "{}";
        };
    }

    public <T> T extractFromBody(Request request, Class<? extends T> type) {
        try {
            return mapper.readValue(request.body(), type);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Filter filter() {
        return  (req, res) -> {
            res.type(JSON_TYPE);
        };
    }

    public ResponseTransformer responseTransformer() {
        return o -> mapper.writeValueAsString(o);
    }
}

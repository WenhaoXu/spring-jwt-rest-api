package com.tw.apistackbase.api.customer.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by jxzhong. on 20/08/2017.
 */
public class ResourceWithUrl<T> extends ResourceSupport {

    private final T content;

    @JsonCreator
    public ResourceWithUrl(@JsonProperty("content") T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }
}

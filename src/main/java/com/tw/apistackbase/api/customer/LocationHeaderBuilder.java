package com.tw.apistackbase.api.customer;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Created by jxzhong. on 20/08/2017.
 */
public class LocationHeaderBuilder {
    public static URI buildCustomerAddressLocation(int id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }
}

package com.tw.apistackbase.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by jxzhong. on 20/08/2017.
 */
public class AddressDto {

    private Long id;
    @NotNull
    private String city;

    public AddressDto() {
    }

    public AddressDto(long id, String city) {
        this.id = id;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

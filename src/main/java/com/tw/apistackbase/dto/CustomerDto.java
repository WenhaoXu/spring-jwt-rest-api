package com.tw.apistackbase.dto;

import com.tw.apistackbase.domian.customer.model.Customer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by jxzhong. on 18/08/2017.
 */
public class CustomerDto {

    private Long id;
    @Size(min = 8, max = 20)
    private String firstName;
    @Size(min = 8, max = 20)
    private String lastName;

    public CustomerDto() {
    }

    public CustomerDto(Long id, @Size(min = 8, max = 20) String firstName, @Size(min = 8, max = 20) String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public CustomerDto(String firstName, String lastName
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

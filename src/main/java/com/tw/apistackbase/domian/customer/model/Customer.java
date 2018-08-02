package com.tw.apistackbase.domian.customer.model;

/**
 * Created by jxzhong on 2017/7/17.
 */

import com.tw.apistackbase.domian.address.model.Address;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(min = 5, max = 20)
    private String firstName;
    @Size(min = 3, max = 10)
    private String lastName;

    @OneToMany
    @JoinTable(name = "customer_address", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "address_id"))
    private Set<Address> addressSet;

    protected Customer() {
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Customer(Long id, String firstName, String lastName) {
        this.id = id;
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

    public Set<Address> getAddressSet() {
        return addressSet;
    }

    public void setAddressSet(Set<Address> addressSet) {
        this.addressSet = addressSet;
    }

    @Override
    public String toString() {
        return String.format(
                "CustomerDto[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }

    public Customer merge(Customer newCustomer) {
        return new Customer(id,
                nonNull(newCustomer.firstName, firstName),
                nonNull(newCustomer.lastName, lastName));
    }

    private <T> T nonNull(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }


}

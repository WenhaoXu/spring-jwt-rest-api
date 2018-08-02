package com.tw.apistackbase.api.customer;

import com.tw.apistackbase.domian.address.model.Address;
import com.tw.apistackbase.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by jxzhong. on 21/08/2017.
 */
@RestController
@RequestMapping("/addresses")
public class AddressResource {

    private final AddressService addressService;

    @Autowired
    public AddressResource(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public HttpEntity<List<Address>> getAddresses() {
        return new ResponseEntity<>(addressService.getAddresses(), HttpStatus.OK);
    }
}

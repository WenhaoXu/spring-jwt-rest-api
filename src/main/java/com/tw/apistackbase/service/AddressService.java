package com.tw.apistackbase.service;

import com.tw.apistackbase.service.Translator.CustomerTranslator;
import com.tw.apistackbase.dto.AddressDto;
import com.tw.apistackbase.domian.address.AddressRepository;
import com.tw.apistackbase.domian.address.model.Address;
import com.tw.apistackbase.domian.customer.CustomerRepository;
import com.tw.apistackbase.domian.customer.model.Customer;
import com.tw.apistackbase.service.resourceValidator.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jxzhong. on 20/08/2017.
 */
@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    private final CustomerValidator customerValidator;

    @Autowired
    public AddressService(AddressRepository addressRepository, CustomerRepository customerRepository, CustomerValidator customerValidator) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
        this.customerValidator = customerValidator;
    }

    public AddressDto getByCustomerIdAndAddressId(Long customerId, Long addressId){
        Address address = customerValidator.validateCustomerAndAddressExistAndReturn(customerId, addressId);
        return CustomerTranslator.translateAddressModelToDto(address);
    }

    public boolean addAddress(Long customerId, Address address) {
        Customer customer = customerValidator.validateCustomerExistAndReturn(customerId);
        Address addressSave = addressRepository.save(address);
        customer.getAddressSet().add(addressSave);
        customerRepository.save(customer);
        return true;
    }

    public boolean modifyAddressByCustomerIdAndAddressId(Long customerId, Long addressId, Address address){
        Address oldAddress = customerValidator.validateCustomerAndAddressExistAndReturn(customerId, addressId);
        addressRepository.save(oldAddress.merge(address));
        return true;
    }

    public boolean deleteAddressByCustomerIdAndAddressId(Long customerId, Long addressId) {
        customerValidator.validateCustomerAndAddressExistAndReturn(customerId, addressId);
        addressRepository.deleteById(addressId);
        return true;
    }

    public List<Address> getAddresses(){
        return addressRepository.findAll();
    }
}

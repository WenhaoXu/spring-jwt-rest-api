package com.tw.apistackbase.service;

import com.tw.apistackbase.service.Translator.CustomerTranslator;
import com.tw.apistackbase.dto.AddressDto;
import com.tw.apistackbase.dto.CustomerDto;
import com.tw.apistackbase.domian.customer.CustomerRepository;
import com.tw.apistackbase.domian.customer.model.Customer;
import com.tw.apistackbase.service.resourceValidator.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jxzhong. on 20/08/2017.
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerValidator customerValidator;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerValidator customerValidator) {
        this.customerRepository = customerRepository;
        this.customerValidator = customerValidator;
    }

    public List<CustomerDto> getByLastNameContaining(String lastName, Pageable page) {
        return customerRepository.findByLastNameContaining(lastName, page)
                .stream()
                .map(CustomerTranslator::translateCustomerModelToDto)
                .collect(Collectors.toList());
    }

    public List<CustomerDto> getAll() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerTranslator::translateCustomerModelToDto)
                .collect(Collectors.toList());
    }

    public List<CustomerDto> getCustomerByPage(Pageable page) {
        return customerRepository.findAll(page)
                .stream()
                .map(CustomerTranslator::translateCustomerModelToDto)
                .collect(Collectors.toList());
    }

    public boolean save(Customer customer) {
        Customer save = customerRepository.save(customer);
        return save != null;
    }

    public boolean updateById(Long id, Customer customer) {
        Customer customerById = customerValidator.validateCustomerExistAndReturn(id);
        Customer customerMerged = customerById.merge(customer);
        customerRepository.save(customerMerged);
        return true;
    }

    public boolean deleteById(Long id) {
        customerValidator.validateCustomerExistAndReturn(id);
        int deleteCustomerById = customerRepository.deleteCustomerById(id);
        return deleteCustomerById != 0;
    }


    public List<AddressDto> getAddressesByCustomerId(Long id){
        Customer customerById = customerValidator.validateCustomerExistAndReturn(id);
        return CustomerTranslator.translateAddressesModelToDto(customerById.getAddressSet());
    }

    public CustomerDto getById(Long id) {
        Customer customerById = customerValidator.validateCustomerExistAndReturn(id);
        return CustomerTranslator.translateCustomerModelToDto(customerById);
    }
}

package com.tw.apistackbase.service.resourceValidator;

import com.tw.apistackbase.domian.address.model.Address;
import com.tw.apistackbase.domian.customer.CustomerRepository;
import com.tw.apistackbase.domian.customer.model.Customer;
import com.tw.apistackbase.exception.ResourceNotExitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by jxzhong. on 21/08/2017.
 */
@Component
public class CustomerValidator {

    private final CustomerRepository customerRepository;
    private static String CustomerNotExitExceptionMsg = "Customer with id=%s does not exist";
    private static String AddressNotExitExceptionMsg = "Address with id=%s does not exist";

    @Autowired
    public CustomerValidator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer validateCustomerExistAndReturn(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (!customer.isPresent()) {
            throw new ResourceNotExitException(String.format(CustomerNotExitExceptionMsg, customerId));
        }
        return customer.get();
    }

    public Address validateCustomerAndAddressExistAndReturn(Long customerId, Long addressId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (!customer.isPresent()) {
            throw new ResourceNotExitException(String.format(CustomerNotExitExceptionMsg, customerId));
        }
        List<Address> addresses = customer.get().getAddressSet()
                .stream()
                .filter(addressOne -> addressOne.getId().equals(addressId))
                .collect(Collectors.toList());
        if (addresses.size() == 0) {
            throw new ResourceNotExitException(String.format(AddressNotExitExceptionMsg, addressId));
        }
        return addresses.get(0);
    }
}

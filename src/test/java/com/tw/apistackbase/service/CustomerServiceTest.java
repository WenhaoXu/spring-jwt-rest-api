package com.tw.apistackbase.service;

import com.tw.apistackbase.domian.customer.CustomerRepository;
import com.tw.apistackbase.domian.customer.model.Customer;
import com.tw.apistackbase.dto.CustomerDto;
import com.tw.apistackbase.service.resourceValidator.CustomerValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    CustomerValidator customerValidator;


    @Test
    void getByLastNameContaining() {

        //given
        CustomerService customerService = new CustomerService(customerRepository, customerValidator);
        Page<Customer> customers = new PageImpl<>(Arrays.asList(new Customer(1L, "Jason", "Zhong")));
        Pageable unpaged = Pageable.unpaged();
        String lastName = "jason";
        when(customerRepository.findByLastNameContaining(lastName, unpaged)).thenReturn(customers);
        //when
        List<CustomerDto> customerDtos = customerService.getByLastNameContaining(lastName, unpaged);

        //then
        assertThat(customerDtos.size(), is(1));
    }
}
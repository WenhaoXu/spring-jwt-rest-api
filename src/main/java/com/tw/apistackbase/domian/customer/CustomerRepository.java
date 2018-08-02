package com.tw.apistackbase.domian.customer;

/**
 * Created by jxzhong on 2017/7/17.
 */

import com.tw.apistackbase.domian.customer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);

    List<Customer> findByLastNameContaining(String lastName);

    Page<Customer> findByLastNameContaining(String lastName, Pageable pageable);

    Page<Customer> findAll(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Customer customer SET customer.lastName = :lastName WHERE customer.id = :id")
    @Transactional
    int changeLastNameById(@Param("id") Long id, @Param("lastName") String lastName);

    @Transactional
    int deleteCustomerById(@Param("id") Long id);
}

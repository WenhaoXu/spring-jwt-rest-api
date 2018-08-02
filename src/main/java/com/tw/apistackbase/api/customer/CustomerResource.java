package com.tw.apistackbase.api.customer;

import com.tw.apistackbase.api.customer.response.ResourceWithUrl;
import com.tw.apistackbase.domian.address.model.Address;
import com.tw.apistackbase.domian.customer.model.Customer;
import com.tw.apistackbase.dto.AddressDto;
import com.tw.apistackbase.dto.CustomerDto;
import com.tw.apistackbase.exception.ResourceNotExitException;
import com.tw.apistackbase.service.AddressService;
import com.tw.apistackbase.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by jxzhong on 18/08/2017.
 */
@RestController
@RequestMapping("/customers")
public class CustomerResource {

    private final CustomerService customerService;
    private final AddressService addressService;
    private final Logger log = Logger.getLogger(this.getClass().getName());

    @Autowired
    public CustomerResource(CustomerService customerService, AddressService addressService) {
        this.customerService = customerService;
        this.addressService = addressService;
    }

    @GetMapping(produces = {"application/json"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ResourceWithUrl<CustomerDto>>> getAll(
            @RequestParam(required = false, defaultValue = "") String lastName,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "20", required = false) Integer size
    ) {
//        @PageableDefault() Pageable page
        List<CustomerDto> customerDtos = lastName.isEmpty() ? customerService.getCustomerByPage(PageRequest.of(page, size))
                : customerService.getByLastNameContaining(lastName, PageRequest.of(page, size));
        List<ResourceWithUrl<CustomerDto>> resourceWithUrls;
        if (customerDtos.size() == 0) {
            throw new ResourceNotExitException("this page is not exist!");
        }
        resourceWithUrls = customerDtos.stream()
                .map(this::getCustomerWithUrl)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resourceWithUrls);
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity getById(@PathVariable("customer-id") Long id) {
        CustomerDto customerDto = customerService.getById(id);
        if (customerDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(getCustomerWithUrl(customerDto));
    }

    @PostMapping(consumes = {"application/json"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity save(@Valid @RequestBody Customer customer) {
        if (customerService.save(customer)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/{customer-id}")
    public ResponseEntity update(@PathVariable("customer-id") Long id, @RequestBody Customer customer) {
        if (customerService.updateById(id, customer)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{customer-id}")
    public ResponseEntity delete(@PathVariable("customer-id") Long id) {
        if (customerService.deleteById(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{customer-id}/addresses")
    public ResponseEntity getAddressesByCustomerId(@PathVariable("customer-id") Long id) {
        List<AddressDto> addressesByCustomerId = customerService.getAddressesByCustomerId(id);
        if (addressesByCustomerId != null) {
            List<ResourceWithUrl<AddressDto>> resourceWithUrls = addressesByCustomerId
                    .stream()
                    .map(addressDto -> getAddressWithUrl(id, addressDto))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(resourceWithUrls);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{customer-id}/addresses/{address-id}")
    public ResponseEntity getAddressByCustomerIdAndAddressId(@PathVariable("customer-id") Long customerId,
                                                             @PathVariable("address-id") Long addressId) {
        AddressDto addressDto = addressService.getByCustomerIdAndAddressId(customerId, addressId);
        if (addressDto != null) {
            return ResponseEntity.ok(getAddressWithUrl(customerId, addressDto));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/{customer-id}/addresses")
    public ResponseEntity addAddress(@PathVariable("customer-id") Long customerId, @RequestBody Address address) {
        if (addressService.addAddress(customerId, address)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PatchMapping("/{customer-id}/addresses/{address-id}")
    public ResponseEntity modifyAddressByCustomerIdAndAddressId(@PathVariable("customer-id") Long customerId,
                                                                @PathVariable("address-id") Long addressId,
                                                                @RequestBody Address address) {
        if (addressService.modifyAddressByCustomerIdAndAddressId(customerId, addressId, address)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{customer-id}/addresses/{address-id}")
    public ResponseEntity deleteAddressByCustomerIdAndAddressId(@PathVariable("customer-id") Long customerId,
                                                                @PathVariable("address-id") Long addressId) {
        if (addressService.deleteAddressByCustomerIdAndAddressId(customerId, addressId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private ResourceWithUrl<CustomerDto> getCustomerWithUrl(CustomerDto customerDto) {
        ResourceWithUrl<CustomerDto> resourceWithUrl = new ResourceWithUrl<>(customerDto);
        resourceWithUrl.add(linkTo(methodOn(this.getClass()).getById(customerDto.getId())).withSelfRel());
        resourceWithUrl.add(linkTo(methodOn(this.getClass()).getAddressesByCustomerId(customerDto.getId())).withRel("addresses"));
        return resourceWithUrl;
    }

    private ResourceWithUrl<AddressDto> getAddressWithUrl(Long CustomerId, AddressDto addressDto) {
        ResourceWithUrl<AddressDto> resourceWithUrl = new ResourceWithUrl<>(addressDto);
        resourceWithUrl.add(linkTo(methodOn(this.getClass()).
                getAddressByCustomerIdAndAddressId(CustomerId, addressDto.getId())).withSelfRel());
        return resourceWithUrl;
    }
}

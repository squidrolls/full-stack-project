package com.elaine.customer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("api/v1/customer")
    public List<Customer> getCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("api/v1/customer/{id}")
    public Customer getCustomersById(@PathVariable("id") Integer id) {
        return customerService.getCustomerById(id);
    }
}

package com.elaine.customer;

import com.elaine.exception.ResourceNotFound;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers(){
        return customerDao.selectAllCustomers();
    }

    public Customer getCustomerById(Integer id){
        return customerDao.selectCustomerById(id)
                .orElseThrow(
                        () -> new ResourceNotFound("customer with id %s not found".formatted(id)));
    }
}

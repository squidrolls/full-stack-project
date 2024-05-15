package com.elaine.customer;

import com.elaine.exception.DuplicateResourceException;
import com.elaine.exception.RequestValidationException;
import com.elaine.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jpa") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers(){
        return customerDao.selectAllCustomers();
    }

    public Customer getCustomerById(Integer id){
        return customerDao.selectCustomerById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("customer with id %s not found".formatted(id)));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        //check if email exists
        if(customerDao.existsPersonWithEmail(customerRegistrationRequest.email())){
            throw new DuplicateResourceException("email already taken");
        }
        //add
        customerDao.insertCustomer(
                new Customer(customerRegistrationRequest.name(),
                        customerRegistrationRequest.email(),
                        customerRegistrationRequest.age())
        );

    }

    public void deleteCustomer(Integer id) {
        if(!customerDao.existsPersonWithId(id)){
            throw new ResourceNotFoundException("customer with id %s not found". formatted(id));
        }
        customerDao.deleteCustomer(id);
    }


    public void updateCustomer(Integer id, CustomerRegistrationRequest updateRequest) {
        Customer customer = customerDao.selectCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(id)
                ));

        boolean changes = false;

        if (updateRequest.name() != null && !updateRequest.name().equals(customer.getName())) {
            customer.setName(updateRequest.name());
            changes = true;
        }

        if (updateRequest.age() != null && !updateRequest.age().equals(customer.getAge())) {
            customer.setAge(updateRequest.age());
            changes = true;
        }

        if (updateRequest.email() != null && !updateRequest.email().equals(customer.getEmail())) {
            if (customerDao.existsPersonWithEmail(updateRequest.email())) {
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }
            customer.setEmail(updateRequest.email());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("no data changes found");
        }

        customerDao.updateCustomer(customer);


    }
}

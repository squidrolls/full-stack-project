package com.elaine.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;

    @Mock private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        //initialize the mock itself
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        //When
        underTest.selectAllCustomers();
        //Then
        verify(customerRepository)
                .findAll();
    }

    @Test
    void selectCustomerById() {
        //Given
        Long id = 1L;
        //When
        underTest.selectCustomerById(id);
        //Then
        verify(customerRepository).findById(id);
    }

    @Test
    void insertCustomer() {
        //Given
        Customer customer = new Customer(
                1L, "Elaine", "Elaine@gmail.com",20
        );
        //When
        underTest.insertCustomer(customer);
        //Then
        verify(customerRepository).save(customer);
    }

    @Test
    void existsPersonWithEmail() {
        //Given
        String email = "Elaine@gmail.com";
        //When
        underTest.existsPersonWithEmail(email);
        //Then
        verify(customerRepository).existsCustomerByEmail(email);
    }

    @Test
    void deleteCustomer() {
        Long id = 1L;
        //When
        underTest.deleteCustomer(id);
        //Then
        verify(customerRepository).deleteById(id);
    }

    @Test
    void existsPersonWithId() {
        Long id = 1L;
        //When
        underTest.existsPersonWithId(id);
        //Then
        verify(customerRepository).existsCustomerById(id);
    }

    @Test
    void updateCustomer() {
        //Given
        Customer customer = new Customer(
                1L, "Elaine", "Elaine@gmail.com",20
        );
        //When
        underTest.updateCustomer(customer);
        //Then
        verify(customerRepository).save(customer);
    }
}
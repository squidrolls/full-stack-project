package com.elaine.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao{

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT id, name, email, age
                FROM customer
                """;
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        var sql = """
                SELECT id, name, email, age
                FROM customer
                WHERE id = ?
                """;
        return jdbcTemplate.query(sql, customerRowMapper, id).stream().findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer(name, email, age)
                VALUES(?, ?, ?)
                """;
        int result = jdbcTemplate.update(
                sql,
                customer.getName(),
                customer.getEmail(),
                customer.getAge());
        //return 1
        //one row inserted into database
        System.out.println("jdbcTemplate.update= " + result);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        var sql = """
                SELECT id, name, email, age
                FROM customer
                WHERE email = ?
                """;
        return jdbcTemplate.query(sql, customerRowMapper, email).stream().findFirst().isPresent();
    }

    @Override
    public void deleteCustomer(Long id) {
        var sql = """
                DELETE FROM customer
                WHERE id = ?
                """;
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsPersonWithId(Long id) {
        var sql = """
                SELECT id, name, email, age
                FROM customer
                WHERE id = ?
                """;
        return jdbcTemplate.query(sql, customerRowMapper, id).stream().findFirst().isPresent();
    }

    @Override
    public void updateCustomer(Customer update) {
        if (update.getName() != null) {
            var sql = """
                UPDATE customer
                SET name = ?
                WHERE id = ?
                """;
            jdbcTemplate.update(
                    sql,
                    update.getName(),
                    update.getId());
        }
        if (update.getEmail() != null) {
            var sql = """
                UPDATE customer
                SET email = ?
                WHERE id = ?
                """;
            jdbcTemplate.update(
                    sql,
                    update.getEmail(),
                    update.getId());
        }
        if (update.getAge() != null) {
            var sql = """
                UPDATE customer
                SET age = ?
                WHERE id = ?
                """;
            jdbcTemplate.update(
                    sql,
                    update.getAge(),
                    update.getId());
        }
   }
}

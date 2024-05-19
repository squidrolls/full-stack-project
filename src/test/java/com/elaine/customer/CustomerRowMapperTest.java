package com.elaine.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerRowMapperTest {

    private CustomerRowMapper underTest;
    @Mock private ResultSet resultSet;

    @BeforeEach
    void setUp() {
        underTest = new CustomerRowMapper();
    }

    @Test
    void mapRow() throws SQLException {
        //Given
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getInt("age")).thenReturn(20);
        when(resultSet.getString("name")).thenReturn("Elaine");
        when(resultSet.getString("email")).thenReturn("elaine@gmail.com");


        //When
        Customer actual = underTest.mapRow(resultSet, 1);

        //Then
        Customer expected = new Customer(
                1L, "Elaine", "elaine@gmail.com",20);
        assertThat(actual).isEqualTo(expected);
    }
}
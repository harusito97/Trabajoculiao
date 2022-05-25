package com.pichulacorp.integracion;


import com.pichulacorp.integracion.Entity.Customer;
import com.pichulacorp.integracion.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProvider implements UserDetailsService {


    @Autowired
    private CustomerRepository repository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = repository.getCustomerByRut(username);
        return new CustomerDetails(customer);
    }

}

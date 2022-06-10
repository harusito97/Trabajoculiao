package com.pichulacorp.integracion;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pichulacorp.integracion.Entity.Customer;
import com.pichulacorp.integracion.Repository.CustomerRepository;

public class UserProvider implements UserDetailsService {


    @Autowired
    private CustomerRepository repository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = repository.getCustomerByRut(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username does not exist: " + username));
        return new CustomerDetails(customer);
    }
}

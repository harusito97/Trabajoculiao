package com.pichulacorp.integracion.Service;

import com.pichulacorp.integracion.Entity.Customer;
import com.pichulacorp.integracion.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public Customer saveUser(Customer customer){
        return repository.save(customer);
    }

    public Customer getUserById(int id){
        return repository.findById(id).orElse(null);
    }

    public Customer getUserByRut(String rut){
        return repository.findByRut(rut).orElse(null);
    }

    public List<Customer> getUsers(){
        return repository.findAll();
    }

    public String deleteUser(int id){
        repository.deleteById(id);
        return "user removed !!"+id;
    }

    public Customer updateUser(Customer customer){
        Customer actualCustomer =repository.findById(customer.getId()).orElse(customer);
        actualCustomer.setName(customer.getName());
        actualCustomer.setLastname(customer.getLastname());
        actualCustomer.setPwd(customer.getPwd());
        actualCustomer.setRut(customer.getRut());
        actualCustomer.setEmail(customer.getEmail());
        actualCustomer.setPhone(customer.getPhone());
        return repository.save(actualCustomer);
    }





}
package com.pichulacorp.integracion.Repository;

import com.pichulacorp.integracion.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByRut(String rut);

}

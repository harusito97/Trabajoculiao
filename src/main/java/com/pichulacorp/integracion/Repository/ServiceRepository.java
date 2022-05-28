package com.pichulacorp.integracion.Repository;

import com.pichulacorp.integracion.Entity.Customer;
import com.pichulacorp.integracion.Entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {

    List<Service> findByOwnerRut(String rut);

    List<Service> findAllMyServicesByOwner(Customer customer);
}

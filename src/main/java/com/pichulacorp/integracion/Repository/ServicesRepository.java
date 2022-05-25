package com.pichulacorp.integracion.Repository;

import com.pichulacorp.integracion.Entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicesRepository extends JpaRepository<Service, Integer> {

    List<Service> findByOwnerRut(String rut);

}

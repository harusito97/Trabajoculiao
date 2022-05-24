package com.pichulacorp.integracion.Repository;

import com.pichulacorp.integracion.Entity.Services;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicesRepository extends CrudRepository<Services, Integer> {
}

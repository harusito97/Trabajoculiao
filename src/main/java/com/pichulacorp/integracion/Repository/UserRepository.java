package com.pichulacorp.integracion.Repository;

import com.pichulacorp.integracion.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByRut(String rut);

}

package com.pichulacorp.integracion.Repository;

import com.pichulacorp.integracion.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}

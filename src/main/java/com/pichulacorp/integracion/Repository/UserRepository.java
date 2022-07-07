package com.pichulacorp.integracion.Repository;

import com.pichulacorp.integracion.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Integer> {

}

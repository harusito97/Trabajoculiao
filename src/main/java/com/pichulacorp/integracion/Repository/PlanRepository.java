package com.pichulacorp.integracion.Repository;

import com.pichulacorp.integracion.Entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Integer> {

    List<Plan> findAllByServiceId(int id);

    Plan findPlanById(int id);
}

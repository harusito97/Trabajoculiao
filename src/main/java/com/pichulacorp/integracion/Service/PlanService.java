package com.pichulacorp.integracion.Service;

import com.pichulacorp.integracion.Entity.Plan;
import com.pichulacorp.integracion.Repository.PlanRepository;
import com.pichulacorp.integracion.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlanService {

    @Autowired
    private PlanRepository repository;

    @Autowired
    private ServiceRepository serviceRepository;

    public Plan savePlan(Plan plan, com.pichulacorp.integracion.Entity.Service parentService){
        plan.setService(serviceRepository.getById(parentService.getId()));
        return repository.save(plan);
    }


    public Plan getPlanById(int id) {
        return repository.findPlanById(id);
    }

    public Plan updatePlan(Plan plan) {
        Plan actualPlan =  repository.findPlanById(plan.getId());
        actualPlan.setName(plan.getName());
        actualPlan.setPrice(plan.getPrice());
        actualPlan.setDescription(plan.getDescription());
        actualPlan.setAvailableMonday(plan.getAvailableMonday());
        actualPlan.setAvailableTuesday(plan.getAvailableTuesday());
        actualPlan.setAvailableWednesday(plan.getAvailableWednesday());
        actualPlan.setAvailableThursday(plan.getAvailableThursday());
        actualPlan.setAvailableFriday(plan.getAvailableFriday());
        actualPlan.setAvailableSaturday(plan.getAvailableSaturday());
        actualPlan.setAvailableSunday(plan.getAvailableSunday());
        return repository.save(actualPlan);
    }

    public void deletePlan(Plan plan) {
        repository.delete(plan);
    }
}

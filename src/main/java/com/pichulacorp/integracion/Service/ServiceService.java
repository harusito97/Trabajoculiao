package com.pichulacorp.integracion.Service;


import com.pichulacorp.integracion.CustomerDetails;
import com.pichulacorp.integracion.Entity.Customer;
import com.pichulacorp.integracion.Entity.Plan;
import com.pichulacorp.integracion.Entity.Service;
import com.pichulacorp.integracion.Repository.CustomerRepository;
import com.pichulacorp.integracion.Repository.PlanRepository;
import com.pichulacorp.integracion.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    private ServiceRepository repository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Service saveService(Service service){
        return repository.save(service);
    }

    public Service updateService(Service service, CustomerDetails owner){
        Service actualService = repository.findById(service.getId()).orElse(service);
        actualService.setName(service.getName());
        actualService.setEmail(service.getEmail());
        actualService.setAddress(service.getAddress());
        actualService.setPhone(service.getPhone());
        actualService.setOwner(owner.getCustomer());
        return repository.save(actualService);
    }

    public List<Service> getServiceByOwnerRut(String rut){
        return repository.findByOwnerRut(rut);
    }

    public List<Service> getServices(){
        return repository.findAll();
    }

    public Service getServiceById(int id){
        return repository.findServiceById(id);
    }

    public List<Service> getAllMyServices(Customer customer) {
        return repository.findAllMyServicesByOwner(customer);
    }

    public void deleteService(Service service) {
        repository.delete(repository.findServiceById(service.getId()));
    }

    public List<Plan> getServicePlans(int id) {
        return planRepository.findAllByServiceId(id);
    }

}

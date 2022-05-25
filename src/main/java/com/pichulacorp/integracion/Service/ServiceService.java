package com.pichulacorp.integracion.Service;


import com.pichulacorp.integracion.Entity.Service;
import com.pichulacorp.integracion.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    private ServiceRepository repository;

    public Service saveService(Service service){
        return repository.save(service);
    }

    public List<Service> getServiceByOwnerRut(String rut){
        return repository.findByOwnerRut(rut);
    }

    public List<Service> getServices(){
        return repository.findAll();
    }

}

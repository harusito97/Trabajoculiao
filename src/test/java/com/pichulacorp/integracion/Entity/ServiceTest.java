package com.pichulacorp.integracion.Entity;

import com.pichulacorp.integracion.Service.ServiceService;
import com.pichulacorp.integracion.Service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ServiceTest {

    @Autowired
    public CustomerService traealweon;
    @Autowired
    public ServiceService traeelservicio;


    @Test
    public void probarEntidadServicio() {

        Customer usuarioTest = new Customer();
        usuarioTest.setName("Alejandro");
        usuarioTest.setLastname("Jarpa");
        usuarioTest.setPwd("123123");
        usuarioTest.setRut("19534382-9");
        usuarioTest.setEmail("conchetuamre@gmail.com");
        usuarioTest.setPhone("123123123");
        traealweon.saveCustomer(usuarioTest);

        Service service = new Service();
        service.setName("Aweonao");
        service.setAddress("micasa");
        service.setPhone("123123123");
        service.setEmail("micasa@gmail.com");
        Customer customer = traealweon.getUserByRut("19534382-9");
        service.setOwner(customer);
        traeelservicio.saveService(service);

//        System.out.println(user);
//        System.out.println(traealweon.getUserByRut("19534382-9"));
        List<Service> serviceByOwnerRut = traeelservicio.getServiceByOwnerRut("19534382-9");
        System.out.println(serviceByOwnerRut);
        System.out.println(serviceByOwnerRut.get(0).getOwner());
        System.out.println(traeelservicio.getAllMyServices(usuarioTest));

    }
}
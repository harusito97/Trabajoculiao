package com.pichulacorp.integracion.Entity;

import com.pichulacorp.integracion.Service.ServicesService;
import com.pichulacorp.integracion.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest
class ServiceTest {

    @Autowired
    public UserService traealweon;
    @Autowired
    public ServicesService traeelservicio;


    //@Test
    public void probarEntidadServicio() {

        User usuarioTest = new User();
        usuarioTest.setName("Alejandro");
        usuarioTest.setLastname("Jarpa");
        usuarioTest.setPwd("123123");
        usuarioTest.setRut("19534382-9");
        usuarioTest.setEmail("conchetuamre@gmail.com");
        usuarioTest.setTelefono("123123123");
        traealweon.saveUser(usuarioTest);

        Service service = new Service();
        service.setName("Aweonao");
        service.setDirection("micasa");
        service.setTelefono("123123123");
        User user = traealweon.getUserByRut("19534382-9");
        service.setOwner(user);
        traeelservicio.saveService(service);

//        System.out.println(user);
//        System.out.println(traealweon.getUserByRut("19534382-9"));
        List<Service> serviceByOwnerRut = traeelservicio.getServiceByOwnerRut("19534382-9");
        System.out.println(serviceByOwnerRut);
        System.out.println(serviceByOwnerRut.get(0).getOwner());

    }
}
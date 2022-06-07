package com.pichulacorp.integracion.Security;

import com.pichulacorp.integracion.Entity.Customer;
import com.pichulacorp.integracion.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if(customerRepository.getCustomerByRole(Roles.Admin).isEmpty()){
            Customer admin = new Customer();
            admin.setName("Admin");
            admin.setLastname("UwU");
            admin.setPwd(bCryptPasswordEncoder.encode("123123"));
            admin.setRut("19534382-9");
            admin.setEmail("alejandrojarpa97@gmail.com");
            admin.setPhone("978870550");
            admin.setRole(Roles.Admin);
            customerRepository.save(admin);
        }
        alreadySetup = true;
    }
}
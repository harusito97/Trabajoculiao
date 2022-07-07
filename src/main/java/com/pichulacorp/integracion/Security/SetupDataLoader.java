package com.pichulacorp.integracion.Security;

import com.pichulacorp.integracion.Entity.Customer;
import com.pichulacorp.integracion.Entity.Service;
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

        if(customerRepository.getFirstCustomerByRole(Roles.Admin).isEmpty()){
            Customer admin = new Customer();
            admin.setName("Admin");
            admin.setLastname("UwU");
            admin.setPwd(bCryptPasswordEncoder.encode("123123"));
            admin.setRut("19534382-9");
            admin.setEmail("alejandrojarpa97@gmail.com");
            admin.setPhone("978870550");
            admin.setRole(Roles.Admin);
            customerRepository.save(admin);

            for (int i=0; i<6;i++){
                Customer newcustomer = new Customer();
                newcustomer.setName("Customer "+i);
                newcustomer.setLastname("Test");
                newcustomer.setPwd(bCryptPasswordEncoder.encode("123123"));
                newcustomer.setRut("12345678-"+i);
                newcustomer.setEmail("ejemplo@ejemplo.cl");
                newcustomer.setPhone("978870550");
                newcustomer.setRole(Roles.Full);
                customerRepository.save(newcustomer);

                for (int j = 0; j<10; j++){
                    Service nwservice = new Service();
                    nwservice.setOwner(newcustomer);
                    nwservice.setName("Test Service "+j+" de "+newcustomer.getName());
                    nwservice.setAddress("Direccion de ejemplo "+j);
                    nwservice.setPhone("978870550");
                    nwservice.setDescription("descripcion de ejemplo");
                    nwservice.setEmail("ejemplo@ejemplo.cl");



                }

            }


        }
        alreadySetup = true;
    }
}
package com.pichulacorp.integracion.Security;

import com.pichulacorp.integracion.Entity.*;
import com.pichulacorp.integracion.Repository.CustomerRepository;
import com.pichulacorp.integracion.Repository.PlanRepository;
import com.pichulacorp.integracion.Repository.ServiceRepository;
import com.pichulacorp.integracion.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private UserRepository userRepository;


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
                User newuser = new User();
                newuser.setName("Usuario "+i);
                newuser.setLastname("Testeo");
                newuser.setPwd(bCryptPasswordEncoder.encode("123123"));
                newuser.setRut("12312312-"+i);
                newuser.setEmail("ejemplo@ejemplo.cl");
                newuser.setPhone("978870550");
                userRepository.save(newuser);

                for (int j = 0; j<10; j++){
                    Service nwservice = new Service();
                    nwservice.setOwner(newcustomer);
                    nwservice.setName("Test Service "+j);
                    nwservice.setAddress("Direccion de ejemplo "+j);
                    nwservice.setPhone("978870550");
                    nwservice.setDescription("descripcion de ejemplo");
                    nwservice.setEmail("ejemplo@ejemplo.cl");
                    serviceRepository.save(nwservice);

                        for (int k=0;k<3;k++){
                            Plan nwplan = new Plan();
                            nwplan.setName("Plan de Testeo "+k);
                            nwplan.setService(nwservice);
                            nwplan.setPrice(25000L);
                            nwplan.setDescription("descripcion de testeo");
                            nwplan.setAvailableMonday(true);
                            nwplan.setAvailableThursday(true);
                            nwplan.setAvailableWednesday(true);
                            nwplan.setAvailableTuesday(true);
                            nwplan.setAvailableFriday(true);
                            nwplan.setAvailableSaturday(true);
                            nwplan.setAvailableSunday(true);
                            planRepository.save(nwplan);

                            for (int q=0; q<10; q++){
                                Reservation nwreservation = new Reservation();
                                nwreservation.setService(nwservice);
                                nwreservation.setPlan(nwplan);
                                nwreservation.setUserrut(newuser);
                                nwreservation.setDate(ZonedDateTime.now());
                                nwreservation.setStartdate(ZonedDateTime.now().plus(1,ChronoUnit.DAYS));
                                nwreservation.setEnddate(nwreservation.getStartdate().plus(5, ChronoUnit.DAYS));
                            }
                        }
                }
            }
        }
        alreadySetup = true;
    }
}
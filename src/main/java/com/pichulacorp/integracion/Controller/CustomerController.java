package com.pichulacorp.integracion.Controller;


import com.pichulacorp.integracion.Entity.Customer;
import com.pichulacorp.integracion.Service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomerController {


    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerService service;

    @PostMapping( "/Register")
    public ModelAndView addUser(Customer customer){
        try {
            service.saveUser(customer);
            return new ModelAndView("redirect:/Login");
        }catch(DataAccessException e){
            logger.error("Se fue a la chucha", e);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("Error");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }


    }

}
package com.pichulacorp.integracion.Controller;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import com.pichulacorp.integracion.Entity.Customer;
import com.pichulacorp.integracion.Service.CustomerService;

@Controller
public class CustomerController {


    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerService service;

    @PostMapping( "/Register")
    public String addCustomer(Model model, @Valid Customer customer, BindingResult result){
        if (result.hasErrors()) {
            model.addAttribute("Active Page", "Register");
            model.addAttribute("customer", customer);
            return "Register";
        }
        try {
            service.saveCustomer(customer);
            return "redirect:/Login";
        }catch(DataAccessException e){
            logger.error("Se fue a la chucha", e);
            model.addAttribute("error", e.getMessage());
            return "Error";
        }
    }

//    @PostMapping("/Login")
//    public ModelAndView loginCustomer(String account, String pwd){
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        try {
//            Customer customer = service.getUserByRut(account);
//            if (bCryptPasswordEncoder.matches(pwd, customer.getPwd())) return new ModelAndView("redirect:/");
//            else return new ModelAndView("redirect:/Login");
//        }catch (DataAccessException e){
//            logger.error("Se fue a la chucha", e);
//            ModelAndView modelAndView = new ModelAndView();
//            modelAndView.setViewName("Error");
//            modelAndView.addObject("error", e.getMessage());
//            return modelAndView;
//        }
//    }


}

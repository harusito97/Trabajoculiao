package com.pichulacorp.integracion.Controller;


import javax.validation.Valid;

import com.pichulacorp.integracion.CustomerDetails;
import com.pichulacorp.integracion.CustomerForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.pichulacorp.integracion.Entity.Customer;
import com.pichulacorp.integracion.Service.CustomerService;

@Controller
public class CustomerController {


    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerService service;

    @PostMapping( "/Register")
    public String addCustomer(Model model, @Valid CustomerForm customerForm, BindingResult result) {
        model.addAttribute("customerForm", customerForm);
        if (result.hasErrors()) {
            model.addAttribute("Active Page", "Register");
            return "Register";
        }
        try {
            service.saveCustomer(customerForm);
            return "redirect:/Login";
        }catch(DataAccessException e){
            logger.error("Se fue a la chucha", e);
            model.addAttribute("error", e.getMessage());
            return "Error";
        }
    }

    @GetMapping("/EditProfile")
    public String editCustomerProfile(Model model, @AuthenticationPrincipal CustomerDetails scustomer){
        model.addAttribute("customer", scustomer.getCustomer());
        model.addAttribute("customerForm", scustomer.getCustomer());
        return "EditProfile";
    }

    @PostMapping("/EditProfile")
    public String saveCustomerProfile(Model model, @Valid CustomerForm customerForm, BindingResult result, @AuthenticationPrincipal CustomerDetails customer){
        model.addAttribute("customerForm", customerForm);
        if (result.hasErrors()) {
            model.addAttribute("customer", customer.getCustomer());
            model.addAttribute("activePage", "EditProfile");
            return "EditProfile";
        }
        try {
            service.updateCustomer(customerForm);
            return "redirect:/CustomerProfile";
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

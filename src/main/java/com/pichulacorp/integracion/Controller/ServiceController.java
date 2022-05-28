package com.pichulacorp.integracion.Controller;

import com.pichulacorp.integracion.CustomerDetails;
import com.pichulacorp.integracion.Entity.Customer;
import com.pichulacorp.integracion.Entity.Service;
import com.pichulacorp.integracion.Service.ServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ServiceController {

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private ServiceService myservice;

    @GetMapping("/AddService")
    public String getAddService(Model model, @AuthenticationPrincipal CustomerDetails customer, Service service){
        model.addAttribute("activePage","AddService");
        model.addAttribute("customer",customer.getCustomer());
        return "AddService";
    }

    @PostMapping("/AddService")
    public ModelAndView addService(Service service, @AuthenticationPrincipal CustomerDetails owner){
        try {
            service.setOwner(owner.getCustomer());
            myservice.saveService(service);
            return new ModelAndView("redirect:/CustomerServices");
        }catch (DataAccessException e){
            logger.error("Se fue a la chucha", e);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("Error");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }
}

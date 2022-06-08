package com.pichulacorp.integracion.Controller;

import com.pichulacorp.integracion.CustomerDetails;
import com.pichulacorp.integracion.Entity.Customer;
import com.pichulacorp.integracion.Entity.Service;
import com.pichulacorp.integracion.Service.ServiceService;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ServiceController {

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private ServiceService myservice;

    @GetMapping("/AddService")
    public String getAddService(Model model, @AuthenticationPrincipal CustomerDetails customer){
        model.addAttribute("activePage","AddService");
        model.addAttribute("customer",customer.getCustomer());
        model.addAttribute("service", new Service());
        return "AddService";
    }

    @PostMapping("/AddService")
    public ModelAndView addService(Service service, @AuthenticationPrincipal CustomerDetails owner){
        try {
            service.setOwner(owner.getCustomer());
            Service newservice = myservice.saveService(service);
            return new ModelAndView("redirect:/ServiceDetails/"+newservice.getId());
        }catch (DataAccessException e){
            logger.error("Se fue a la chucha", e);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("Error");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/EditService/{id}")
    public String editService(Model model,@AuthenticationPrincipal CustomerDetails customer,Service service){
        model.addAttribute("service",myservice.getServiceById(service.getId()));
        model.addAttribute("customer",customer.getCustomer());
        return "EditService";
    }

    @PostMapping("/EditService/{id}")
    public String editServiceSave(Model model, @AuthenticationPrincipal CustomerDetails customer, Service service){
        model.addAttribute("customer",customer.getCustomer());
        Service newservice = myservice.updateService(service, customer);
        return "redirect:/ServiceDetails/"+newservice.getId();
    }

    @GetMapping("/DeleteService/{id}")
    public String deleteService(Model model, Service service, @AuthenticationPrincipal CustomerDetails customer){
        model.addAttribute("customer",customer.getCustomer());
        myservice.deleteService(service);
        return "redirect:/CustomerServices";
    }

    @GetMapping("/ServiceDetails/{id}")
    public String serviceDetails(Model model, Service service, @AuthenticationPrincipal CustomerDetails customer) {
        model.addAttribute("customer",customer.getCustomer());
        model.addAttribute("service",myservice.getServiceById(service.getId()));
        model.addAttribute("plans",myservice.getServicePlans(service.getId()));
        return "ServiceDetails";
    }

}

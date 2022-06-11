package com.pichulacorp.integracion.Controller;


import com.pichulacorp.integracion.CustomerDetails;
import com.pichulacorp.integracion.Entity.Plan;
import com.pichulacorp.integracion.Entity.Service;
import com.pichulacorp.integracion.Service.PlanService;
import com.pichulacorp.integracion.Service.ServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class PlanController {

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private PlanService service;

    @Autowired
    private ServiceService serviceService;

    @GetMapping("/AddPlan/{id}")
    public String addPlan(Model model, @AuthenticationPrincipal CustomerDetails customer, Service service){
        model.addAttribute("serviceid", service.getId());
        model.addAttribute("customer", customer.getCustomer());
        model.addAttribute("activePage", "AddPlan");
        model.addAttribute("plan", new Plan());
        return "AddPlan";
    }

    @PostMapping("/AddPlan/{id}")
    public String savePlan(Model model, @Valid Plan plan, Service parentService){
        service.savePlan(plan, parentService);
        return "redirect:/CustomerServices";
    }

    @GetMapping("/EditPlan/{id}")
    public String editPlan(Model model, Plan plan, @AuthenticationPrincipal CustomerDetails customerDetails){
        model.addAttribute("customer", customerDetails.getCustomer());
        model.addAttribute("plan", service.getPlanById(plan.getId()));
        return "EditPlan";
    }

    @PostMapping("/EditPlan/{id}")
    public String editPlanSave(Model model, @Valid Plan plan, @AuthenticationPrincipal CustomerDetails customerDetails){
        model.addAttribute("customer", customerDetails.getCustomer());
        Plan newplan = service.updatePlan(plan);
        return "redirect:/ServiceDetails/"+newplan.getService().getId();
    }

    @GetMapping("/DeletePlan/{id}")
    public String deletePlan(Model model, Plan plan, @AuthenticationPrincipal CustomerDetails customerDetails){
        model.addAttribute("customer", customerDetails.getCustomer());
        service.deletePlan(plan);
        return "redirect:/CustomerServices";
    }


}

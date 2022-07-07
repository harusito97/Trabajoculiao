package com.pichulacorp.integracion.Controller;


import com.pichulacorp.integracion.CustomerDetails;
import com.pichulacorp.integracion.Entity.Plan;
import com.pichulacorp.integracion.Entity.Service;
import com.pichulacorp.integracion.NavigationController;
import com.pichulacorp.integracion.Service.PlanService;
import com.pichulacorp.integracion.Service.ServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.util.Pair;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PlanController {


    @Autowired
    private NavigationController navController;

    @ModelAttribute("pages")
    public List<Pair<String, String>> pages(@AuthenticationPrincipal CustomerDetails customer) {
        return navController.pages(customer);
    }
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
        return "/AddPlan";
    }

    @PostMapping("/AddPlan/{id}")
    public String savePlan(Model model, @Valid Plan plan, BindingResult result, Service parentService, @AuthenticationPrincipal CustomerDetails customer){
        model.addAttribute("customer",customer.getCustomer());
        if(result.hasErrors()){
            model.addAttribute("serviceid", parentService.getId());
            model.addAttribute("activePage", "AddPlan");
            model.addAttribute("plan", plan);
            return "/AddPlan";
        }
        try{
            service.savePlan(plan, parentService);
            return "redirect:/ServiceDetails/"+parentService.getId();
        }catch (DataAccessException e){
            logger.error("Se fue a la chucha", e);
            model.addAttribute("error", e.getMessage());
            return "Error";
        }

    }

    @GetMapping("/EditPlan/{id}")
    public String editPlan(Model model, Plan plan, @AuthenticationPrincipal CustomerDetails customerDetails){
        model.addAttribute("customer", customerDetails.getCustomer());
        model.addAttribute("plan", service.getPlanById(plan.getId()));
        return "EditPlan";
    }

    @PostMapping("/EditPlan/{id}")
    public String editPlanSave(Model model, @Valid Plan plan, BindingResult result, @AuthenticationPrincipal CustomerDetails customer){
        model.addAttribute("customer", customer.getCustomer());
        if(result.hasErrors()){
            model.addAttribute("activePage","EditPlan");
            model.addAttribute("plan", plan);
            return "EditPlan";
        }
        try{
            Plan newplan = service.updatePlan(plan);
            return "redirect:/ServiceDetails/"+newplan.getService().getId();
        }catch (DataAccessException e){
            logger.error("Se fue a la chucha", e);
            model.addAttribute("error", e.getMessage());
            return "Error";
        }
    }

    @GetMapping("/DeletePlan/{id}")
    public String deletePlan(Model model, Plan plan, @AuthenticationPrincipal CustomerDetails customerDetails){
        model.addAttribute("customer", customerDetails.getCustomer());
        service.deletePlan(plan);
        return "redirect:/CustomerServices";
    }


}

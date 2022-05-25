package com.pichulacorp.integracion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CustomerSessionNavigationController {

    @GetMapping("/CustomerIndex")
    public String getCustomerIndex(Model model){
        model.addAttribute("Active Page","CustomerIndex");
        return "CustomerIndex";
    }

    @GetMapping("/CustomerServices")
    public String getCustomerServices(Model model){
        model.addAttribute("Active Page","CustomerServices");
        return "CustomerServices";
    }

    @GetMapping("/CustomerReports")
    public String getCustomerReports(Model model){
        model.addAttribute("Active Page","CustomerReports");
        return "CustomerReports";
    }

    @GetMapping("/CustomerProfile")
    public String getCustomerProfile(Model model){
        model.addAttribute("Active Page","CustomerProfile");
        return "CustomerProfile";
    }
}

package com.pichulacorp.integracion;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CustomerSessionNavigationController {

    @GetMapping("/CustomerIndex")
    public String getCustomerIndex(Model model, @AuthenticationPrincipal CustomerDetails customer){
        model.addAttribute("customer",customer.getCustomer());
        model.addAttribute("activePage","CustomerIndex");
        return "CustomerIndex";
    }

    @GetMapping("/CustomerServices")
    public String getCustomerServices(Model model, @AuthenticationPrincipal CustomerDetails customer){
        model.addAttribute("customer",customer.getCustomer());
        model.addAttribute("activePage","CustomerServices");
        return "CustomerServices";
    }

    @GetMapping("/CustomerReports")
    public String getCustomerReports(Model model, @AuthenticationPrincipal CustomerDetails customer){
        model.addAttribute("customer",customer.getCustomer());
        model.addAttribute("activePage","CustomerReports");
        return "CustomerReports";
    }

    @GetMapping("/CustomerProfile")
    public String getCustomerProfile(Model model, @AuthenticationPrincipal CustomerDetails customer){
        model.addAttribute("customer",customer.getCustomer());
        model.addAttribute("activePage","CustomerProfile");
        return "CustomerProfile";
    }
}

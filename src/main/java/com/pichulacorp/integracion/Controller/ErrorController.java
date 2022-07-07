package com.pichulacorp.integracion.Controller;

import com.pichulacorp.integracion.CustomerDetails;
import com.pichulacorp.integracion.NavigationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.util.Pair;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public class ErrorController {

    @Autowired
    private NavigationController navController;

    @ModelAttribute("pages")
    public List<Pair<String, String>> pages(@AuthenticationPrincipal CustomerDetails customer) {
        return navController.pages(customer);
    }

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @RequestMapping("/error")
    public ModelAndView showError(DataAccessException e){
        logger.error("Se fue a la chucha", e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Error");
        modelAndView.addObject("error", e.getMessage());
        return modelAndView;
    }



}

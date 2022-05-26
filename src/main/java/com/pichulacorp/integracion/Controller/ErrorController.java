package com.pichulacorp.integracion.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

public class ErrorController {

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

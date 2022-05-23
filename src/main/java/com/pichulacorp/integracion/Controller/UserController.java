package com.pichulacorp.integracion.Controller;


import com.pichulacorp.integracion.Entity.User;
import com.pichulacorp.integracion.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {


    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService service;

    @PostMapping( "/Register")
    public ModelAndView addUser(User user){
        try {
            service.saveUser(user);
            return new ModelAndView("redirect:/Login");
        }catch(DataAccessException e){
            logger.error("Se fue a la chucha", e);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("Error");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }


    }

}

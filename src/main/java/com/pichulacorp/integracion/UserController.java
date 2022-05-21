package com.pichulacorp.integracion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/Register")
    public ModelAndView save(@ModelAttribute User user){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedpw = bCryptPasswordEncoder.encode(user.getPassword());
        String insert_sql = "INSERT INTO Cliente(nombre, pwd, email, rut, telefono) VALUES (?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insert_sql, user.getNombre(), encodedpw, user.getEmail(), user.getRut(), user.getTelefono());
            return new ModelAndView("redirect:/Login");
        } catch (DataAccessException e) {
            logger.error("Se fue a la chucha", e);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("Error");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }

    }

}

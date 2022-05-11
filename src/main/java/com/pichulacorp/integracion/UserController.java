package com.pichulacorp.integracion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/Register")
    public String save(@ModelAttribute User user){
        String insert_sql = "INSERT INTO Usuario(nombre, email, rut, telefono) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insert_sql, user.getNombre(), user.getEmail(), user.getRut(), user.getTelefono());
        } catch (DataAccessException e) {
            logger.error("Se fue a la chucha", e);
        }
        return "redirect:/";

    }

}

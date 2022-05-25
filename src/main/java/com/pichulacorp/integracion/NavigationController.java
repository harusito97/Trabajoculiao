package com.pichulacorp.integracion;

import com.pichulacorp.integracion.Entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;




@Controller
public class NavigationController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(NavigationController.class);

    @GetMapping("/")
    public String getIndex(Model model){
        model.addAttribute("Active Page","Index");
//        String sql = "INSERT INTO Usuario (nombre, email, rut, telefono) VALUES (?, ?, ?, ?)";
//        jdbcTemplate.update(sql, "Juanito", "juanito@juanito.juanito", "12345678-9", "1111111");
        return "/Index";
    }

    @GetMapping("/Login")
    public String getLogin(Model model){
        model.addAttribute("Active Page","Login");

        return "Login";
    }

    @GetMapping("/Register")
    public String getRegister(Model model){
        model.addAttribute("Active Page", "Register");
        model.addAttribute("user", new Customer());
        return "Register";
    }

    @GetMapping("/Contact")
    public String getContact(Model model){
        model.addAttribute("Active Page", "Contact");
        return "Contact";
    }

    @GetMapping("/Aboutus")
    public String getAboutus(Model model){
        model.addAttribute("Active Page","Aboutus");
        return "Aboutus";
    }
}

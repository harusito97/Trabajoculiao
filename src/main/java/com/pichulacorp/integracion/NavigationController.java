package com.pichulacorp.integracion;

import com.pichulacorp.integracion.Entity.Customer;
import com.pichulacorp.integracion.Security.Privileges;
import com.pichulacorp.integracion.Security.Roles;
import com.pichulacorp.integracion.Service.ServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.List;


@Controller
public class NavigationController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(NavigationController.class);

    @Autowired
    private ServiceService myservice;

    @ModelAttribute("pages")
    public List<Pair<String, String>> pages(@AuthenticationPrincipal CustomerDetails customer) {
        if (customer == null) {
            return Arrays.asList(
                    Pair.of("Inicio", "/"),
                    Pair.of("Iniciar Sesion", "/Login"),
                    Pair.of("Sobre Nosotros", "/Aboutus"),
                    Pair.of("Contacto", "/Contact"));
        } else if (customer.getCustomer().getRole().equals(Roles.Admin)) {
            return Arrays.asList(
                    Pair.of("Inicio", "/"),
                    Pair.of("Resumen", "/CustomerIndex"),
                    Pair.of("Servicios", "/CustomerServices"),
                    Pair.of("Reportes", "/CustomerReports"),
                    Pair.of("Perfil", "/CustomerProfile"),
                    Pair.of("Cerrar Sesion", "/Logout"),
                    Pair.of("Sobre Nosotros", "/Aboutus"),
                    Pair.of("Contacto", "/Contact"),
                    Pair.of("Admin", "/Register"));
        } else {
            return Arrays.asList(
                    Pair.of("Inicio", "/"),
                    Pair.of("Resumen", "/CustomerIndex"),
                    Pair.of("Servicios", "/CustomerServices"),
                    Pair.of("Reportes", "/CustomerReports"),
                    Pair.of("Perfil", "/CustomerProfile"),
                    Pair.of("Cerrar Sesion", "/Logout"),
                    Pair.of("Sobre Nosotros", "/Aboutus"),
                    Pair.of("Contacto", "/Contact"));
        }
    }

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("Active Page", "Index");
//        String sql = "INSERT INTO Usuario (nombre, email, rut, telefono) VALUES (?, ?, ?, ?)";
//        jdbcTemplate.update(sql, "Juanito", "juanito@juanito.juanito", "12345678-9", "1111111");
        return "Index";
    }

    @GetMapping("/Login")
    public String getLogin(Model model) {
        model.addAttribute("Active Page", "Login");
        return "Login";
    }

    @GetMapping("/Register")
    public String getRegister(Model model) {
        model.addAttribute("Active Page", "Register");
        model.addAttribute("customerForm", new CustomerForm());
        return "Register";
    }

    @GetMapping("/Contact")
    public String getContact(Model model) {
        model.addAttribute("Active Page", "Contact");
        return "Contact";
    }

    @GetMapping("/Aboutus")
    public String getAboutus(Model model) {
        model.addAttribute("Active Page", "Aboutus");
        return "Aboutus";
    }

    @GetMapping("/CustomerIndex")
    public String getCustomerIndex(Model model, @AuthenticationPrincipal CustomerDetails customer){
        model.addAttribute("customer",customer.getCustomer());
        model.addAttribute("activePage","CustomerIndex");
        model.addAttribute("myservices", myservice.getAllMyServices(customer.getCustomer()));
        return "CustomerIndex";
    }

    @GetMapping("/CustomerServices")
    public String getCustomerServices(Model model, @AuthenticationPrincipal CustomerDetails customer){
        model.addAttribute("customer",customer.getCustomer());
        model.addAttribute("myservices", myservice.getAllMyServices(customer.getCustomer()));
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

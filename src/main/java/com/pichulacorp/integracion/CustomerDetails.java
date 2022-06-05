package com.pichulacorp.integracion;

import com.pichulacorp.integracion.Entity.Customer;
import com.pichulacorp.integracion.Security.Privileges;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;



public class CustomerDetails implements UserDetails {

    @Getter
    private Customer customer;

    public CustomerDetails(Customer customer){
        this.customer = customer;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> lista = new ArrayList<>();
        for (Privileges p : this.customer.getRole().privilegesList) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(p.toString());
            lista.add(simpleGrantedAuthority);
        }
        return lista;
    }

    @Override
    public String getPassword() {
        return customer.getPwd();
    }

    @Override
    public String getUsername() {
        return customer.getRut();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

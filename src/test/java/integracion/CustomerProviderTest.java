package com.pichulacorp.integracion;

import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.gson.GsonProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;


class CustomerProviderTest {

    @Test
    public void probarUnaWea(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String unaweaencodea = bCryptPasswordEncoder.encode("unawea");
        System.out.println(unaweaencodea);

    }
}
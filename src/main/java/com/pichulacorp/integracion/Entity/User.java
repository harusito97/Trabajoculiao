package com.pichulacorp.integracion.Entity;


import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Type;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.imageio.spi.ServiceRegistry;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Cliente")
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 25)
    private String name;

    @NotNull
    @NotBlank
    private String lastname;

    @NotNull
    @NotBlank
    private String pwd;

    @NotNull
    @NotBlank
    @Size(min = 9, max = 12)
    @Pattern(regexp = "^([0-9]{1,2})+(\\.{0,1})+([0-9]{3})+(\\.{0,1})+([0-9]{3})+(\\-{0,1})+([0-9kK]{1})$")
    private String rut;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
    message = "Email Invalido")
    private String email;

    @NotNull
    @NotBlank
    private String telefono;

    @OneToMany(mappedBy = "User", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Services> service = new ArrayList<>();

    public void setService(List<Services> service) {
        this.service = service;
    }
}

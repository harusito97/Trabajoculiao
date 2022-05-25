package com.pichulacorp.integracion.Entity;


import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString(exclude = {"services"})
@Table(name = "Customer")
public class Customer {

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
    @Column(unique = true)
//    @Pattern(regexp = "^([0-9]{1,2})+(\\.{0,1})+([0-9]{3})+(\\.{0,1})+([0-9]{3})+(\\-{0,1})+([0-9kK]{1})$")
    private String rut;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Email Invalido")
    private String email;

    @NotNull
    @NotBlank
    private String phone;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Service> services = new ArrayList<>();

    public void setService(List<Service> service) {
        this.services = service;
    }
}

package com.pichulacorp.integracion.Entity;


import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString(exclude = {"owner"})
@Table(name = "Service")
public class Service {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String address;

    @NotBlank
    @NotNull
    private String phone;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Email Invalido")
    private String email;

    @ManyToOne
    @JoinColumn(name = "ownerid", nullable = false)
    private Customer owner; //id del dueño

    @OneToMany(mappedBy = "reservedservice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservation = new ArrayList<>();

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Plan> plan = new ArrayList<>();

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> review = new ArrayList<>();
}

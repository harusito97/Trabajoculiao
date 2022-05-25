package com.pichulacorp.integracion.Entity;


import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Servicio")
@Getter
@Setter
@ToString(exclude = {"owner"})
public class Service {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String direction;

    @NotBlank
    @NotNull
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "ownerrut", nullable = false)
    private User owner; //RUT DEL DUEÑO

    public Service(String name, String direction, String telefono, User owner) {
        this.name = name;
        this.direction = direction;
        this.telefono = telefono;
        this.owner = owner;
    }

    public Service() {

    }
}

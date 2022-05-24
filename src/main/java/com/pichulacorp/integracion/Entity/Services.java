package com.pichulacorp.integracion.Entity;


import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Data;
import lombok.Generated;
import net.bytebuddy.description.modifier.Ownership;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Table(name = "Servicio")
public class Services {

    @Id
    @GeneratedValue
    @NotBlank
    @NotNull
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

    public Services(String name, String direction, String telefono, User owner) {
        this.name = name;
        this.direction = direction;
        this.telefono = telefono;
        this.owner = owner;
    }
}

package com.pichulacorp.integracion.Entity;


import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Reservation")
public class Reservation {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank
    @NotNull
    private String date; // fecha en que se realizo la reserva

    @NotBlank
    @NotNull
    private String startdate; // fecha comienzo de servicio

    @NotBlank
    @NotNull
    private String enddate; // fecha termino de servicio

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    //

    @ManyToOne
    @JoinColumn(name = "userrut", nullable = false)
    private User userrut; // usuario que hizo la reserva

    @ManyToOne
    @JoinColumn(name = "service", nullable = false)
    private Service reservedservice; // servicio contratado

    @ManyToOne
    @JoinColumn(name = "usedplan", nullable = false)
    private Plan plan; // El plan del servicio el cual fue contratado

    //


}

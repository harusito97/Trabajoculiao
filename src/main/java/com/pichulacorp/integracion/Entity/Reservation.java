package com.pichulacorp.integracion.Entity;


import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
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

    @CreatedDate
    private ZonedDateTime creationdate;

    @LastModifiedDate
    private ZonedDateTime lastmodifydate;

    @NotBlank
    @NotNull
    private ZonedDateTime date; // fecha en que se realizo la reserva

    @NotBlank
    @NotNull
    private ZonedDateTime startdate; // fecha comienzo de servicio

    @NotBlank
    @NotNull
    private ZonedDateTime enddate; // fecha termino de servicio

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    //

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "userrut", nullable = false)
    private User userrut; // usuario que hizo la reserva

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "service", nullable = false)
    private Service service; // servicio contratado

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "plan", nullable = false)
    private Plan plan; // El plan del servicio el cual fue contratado

    //


}

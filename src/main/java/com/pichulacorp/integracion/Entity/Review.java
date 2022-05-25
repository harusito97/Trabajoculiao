package com.pichulacorp.integracion.Entity;


import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Review")
public class Review {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank
    @NotNull
    private String description;

    @NotBlank
    @NotNull
    @Min(value = 1, message = "las valoraciones son de 1 a 5")
    @Max(value = 5, message = "las valoraciones son de 1 a 5")
    private int valoration; // 1 - 5

    @ManyToOne
    @JoinColumn(name = "atreservation", nullable = false)
    private Reservation reservation; // reserva en cuestion

    @ManyToOne
    @JoinColumn(name = "atservice", nullable = false)
    private Service service; // servicio en cuestion





}

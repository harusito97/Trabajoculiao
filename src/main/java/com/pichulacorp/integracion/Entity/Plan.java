package com.pichulacorp.integracion.Entity;


import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Plan")
public class Plan {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank
    @NotNull
    private float price;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String description;

    @NotBlank
    @NotNull
    private Boolean availableMonday = true;

    @NotBlank
    @NotNull
    private Boolean availableTuesday = true;

    @NotBlank
    @NotNull
    private Boolean availableWednesday = true;

    @NotBlank
    @NotNull
    private Boolean availableThursday = true;

    @NotBlank
    @NotNull
    private Boolean availableFriday = true;

    @NotBlank
    @NotNull
    private Boolean availableSaturday = true;

    @NotBlank
    @NotNull
    private Boolean availableSunday = true;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "service", nullable = false)
    private Service service;
}

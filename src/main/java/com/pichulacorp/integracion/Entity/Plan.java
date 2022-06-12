package com.pichulacorp.integracion.Entity;


import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import javax.validation.groups.ConvertGroup;
import java.time.ZonedDateTime;
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

    @NotNull
    @NotBlank(message = "No puede estar en blanco")
    @Size(min = 2, max = 25, message = "Debe tener entre 2 a 25 caracteres")
    private String name;

    @javax.validation.constraints.NotNull(message = "No puede estar en blanco")
    @Min(value = 0, message = "El valor no puede ser menor a 0")
    private Long price;

    @NotNull
    @NotBlank(message = "Agrega una descripcion")
    @Size(max = 512, message = "No pueden ser mas de 512 caracteres")
    private String description;

    @CreatedDate
    private ZonedDateTime creationdate;

    @LastModifiedDate
    private ZonedDateTime lastmodifydate;

    @NotNull
    private Boolean availableMonday = true;

    @NotNull
    private Boolean availableTuesday = true;

    @NotNull
    private Boolean availableWednesday = true;

    @NotNull
    private Boolean availableThursday = true;

    @NotNull
    private Boolean availableFriday = true;

    @NotNull
    private Boolean availableSaturday = true;

    @NotNull
    private Boolean availableSunday = true;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "service", nullable = false)
    private Service service;
}

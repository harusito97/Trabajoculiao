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
import javax.validation.constraints.NotBlank;
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
    private float price = 0;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
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

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service", nullable = false)
    private Service service;
}

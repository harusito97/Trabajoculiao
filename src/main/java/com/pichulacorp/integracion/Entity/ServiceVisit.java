package com.pichulacorp.integracion.Entity;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
public class ServiceVisit {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Service service;

    @CreatedDate
    private ZonedDateTime visitTimestamp;

    @NotNull
    @NotBlank
    private String visitorIP;
}

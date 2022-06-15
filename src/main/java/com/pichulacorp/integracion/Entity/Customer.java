package com.pichulacorp.integracion.Entity;


import com.pichulacorp.integracion.Security.Roles;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString(exclude = {"services"})
@Table(name = "Customer", uniqueConstraints = @UniqueConstraint(columnNames = "rut"))
@DynamicUpdate
public class Customer {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String lastname;

    private String pwd;

    @Column(unique = true)
    private String rut;

    private String email;

    private String phone;

    @CreationTimestamp // si funciona
    private ZonedDateTime creationdate;

    @UpdateTimestamp
    private ZonedDateTime lastmodifydate;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Service> services = new ArrayList<>();

    public void setService(List<Service> service) {
        this.services = service;
    }
}

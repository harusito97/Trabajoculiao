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
import javax.validation.constraints.Pattern;
import java.time.ZonedDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String lastname;

    @NotNull
    @NotBlank
    private String pwd;

    @NotNull
    @NotBlank
    private String rut;

    @NotNull
    @NotBlank
    private String phone;

    @CreatedDate
    private ZonedDateTime creationdate;

    @LastModifiedDate
    private ZonedDateTime lastmodifydate;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Email Invalido")
    private String email;

    @OneToMany(mappedBy = "userrut", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;

}

package com.pichulacorp.integracion.Entity;


import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString(exclude = {"owner"})
@Table(name = "Service")
public class Service {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @NotBlank(message = "No puede estar en blanco")
    @Size(min = 2, max = 25, message = "Debe tener entre 2 a 25 caracteres")
    private String name;

    @NotNull
    @NotBlank(message = "No puede estar en blanco")
    @Size(max = 512, message = "No pueden ser mas de 512 caracteres")
    private String description;

    @NotNull
    @NotBlank(message = "Ingresa una direccion")
    @Size(max = 75, message = "No pueden ser mas de 75 caracteres")
    private String address;

    @NotNull
    @NotBlank(message = "No puede estar en blanco")
    @Pattern(regexp = "^(9[0-9]{8}|[0-9]{8}$)", message = "Telefono invalido, ej: 12345678 o 912345678")
    @Size(max = 9, message = "No puede tener mas de 9 caracteres")
    private String phone;

    @CreationTimestamp
    private ZonedDateTime creationdate;

    @UpdateTimestamp
    private ZonedDateTime lastmodifydate;

    @NotNull
    @NotBlank(message = "No puede estar en blanco")
    @Email(message = "Email invalido, ej: ejemplo@gmail.com")
    @Size(max = 128, message = "No puede tener mas de 128 caracteres")
    private String email;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "ownerid", nullable = false)
    private Customer owner; //id del dueño

    @OneToMany(mappedBy = "service", cascade = CascadeType.REMOVE)
    private List<Reservation> reservation = new ArrayList<>();

    @OneToMany(mappedBy = "service", cascade = CascadeType.REMOVE)
    private List<Plan> plan = new ArrayList<>();

    @OneToMany(mappedBy = "service", cascade = CascadeType.REMOVE)
    private List<Review> review = new ArrayList<>();

    @OneToMany(mappedBy = "service", cascade = CascadeType.REMOVE)
    private List<ServiceVisit> serviceVisits = new ArrayList<>();
}

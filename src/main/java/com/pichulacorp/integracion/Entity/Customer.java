package com.pichulacorp.integracion.Entity;


import com.pichulacorp.integracion.Security.Roles;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
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
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @NotBlank(message = "No puede estar en blanco")
    @Size(min = 2, max = 25, message = "Debe tener entre 2 a 25 caracteres")
    private String name;

    @NotNull
    @NotBlank(message = "No puede estar en blanco")
    @Size(min = 2, max = 25, message = "Debe tener entre 2 a 25 caracteres")
    private String lastname;

    @Valid
    @NotNull
    @NotBlank(message = "No puede estar en blanco")
    @Size(min = 6, max = 25, message = "Debe tener entre 6 a 25 caracteres")
    private String pwd;

    @NotNull
    @NotBlank(message = "No puede estar en blanco")
    @Size(min = 9,max = 12, message = "Debe tener entre 9 a 12 caracteres")
    @Column(unique = true)
    @Pattern(regexp = "^[0-9]{1,2}(\\.?[0-9]{3}){2}\\-[0-9kK]$", message = "rut Invalido, ej: 12345678-9")
    private String rut;

    @NotNull
    @NotBlank(message = "No puede estar en blanco")
    @Email(message = "Email invalido, ej: ejemplo@gmail.com")
    @Size(max = 128, message = "No puede tener mas de 128 caracteres")
    private String email;

    @NotNull
    @NotBlank(message = "No puede estar en blanco")
    @Pattern(regexp = "^(9[0-9]{8}|[0-9]{8}$)", message = "Telefono invalido, ej: 12345678 o 912345678")
    @Size(max = 9, message = "No puede tener mas de 9 caracteres")
    private String phone;

    @CreationTimestamp
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

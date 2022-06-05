package com.pichulacorp.integracion.Entity;


import com.pichulacorp.integracion.Security.Roles;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
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
    @NotBlank
    @Size(min = 2, max = 25)
    private String name;

    @NotNull
    @NotBlank
    private String lastname;

    @NotNull
    @NotBlank
    private String pwd;

    @NotNull
    @NotBlank
    @Size(min = 9, max = 12)
    @Column(unique = true)
//    @Pattern(regexp = "^([0-9]{1,2})+(\\.{0,1})+([0-9]{3})+(\\.{0,1})+([0-9]{3})+(\\-{0,1})+([0-9kK]{1})$")
    private String rut;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Email Invalido")
    private String email;

    @NotNull
    @NotBlank
    private String phone;

    @CreationTimestamp
    private ZonedDateTime creationdate;

    @UpdateTimestamp
    private ZonedDateTime lastmodifydate;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Service> services = new ArrayList<>();

    public void setService(List<Service> service) {
        this.services = service;
    }
}

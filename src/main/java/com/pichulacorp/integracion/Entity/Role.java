package com.pichulacorp.integracion.Entity;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Role")
public class Role {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @NotBlank
    private String name;
}

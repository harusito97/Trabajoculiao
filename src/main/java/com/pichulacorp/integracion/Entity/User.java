package com.pichulacorp.integracion.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Cliente")
public class User {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String lastname;
    private String pwd;
    private String rut;
    private String email;
    private String telefono;


}

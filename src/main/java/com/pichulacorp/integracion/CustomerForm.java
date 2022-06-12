package com.pichulacorp.integracion;


import com.pichulacorp.integracion.Security.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerForm {
    @NotNull
    @NotBlank(message = "No puede estar en blanco")
    @Size(min = 2, max = 25, message = "Debe tener entre 2 a 25 caracteres")
    private String name;

    @NotNull
    @NotBlank(message = "No puede estar en blanco")
    @Size(min = 2, max = 25, message = "Debe tener entre 2 a 25 caracteres")
    private String lastname;

    @NotNull
    @NotBlank(message = "No puede estar en blanco")
    @Size(min = 6, max = 25, message = "Debe tener entre 6 a 25 caracteres")
    private String pwd;

    @NotNull
    @NotBlank(message = "No puede estar en blanco")
    @Size(min = 9,max = 12, message = "Debe tener entre 9 a 12 caracteres")
    @Column(unique = true)
    @Pattern(regexp = "^\\d{1,2}(\\.?\\d{3}){2}-[\\dkK]$", message = "rut Invalido, ej: 12345678-9")
    private String rut;

    @NotNull
    @NotBlank(message = "No puede estar en blanco")
    @Email(message = "Email invalido, ej: ejemplo@gmail.com")
    @Size(max = 128, message = "No puede tener mas de 128 caracteres")
    private String email;

    @NotNull
    @NotBlank(message = "No puede estar en blanco")
    @Pattern(regexp = "^(9?\\d{8}$)", message = "Telefono invalido, ej: 12345678 o 912345678")
    @Size(max = 9, message = "No puede tener mas de 9 caracteres")
    private String phone;

    @Enumerated(EnumType.STRING)
    private Roles role;
}

package com.goddrinksjava.prep.model.pojo.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {

    private Integer id;
    private String nombre;
    private String calle;
    private String colonia;
    private String numeroCasa;
    private String municipio;
    private String codigoPostal;
    private String telefono;
    private String email;
    private String password;
    private Boolean enabled;

}

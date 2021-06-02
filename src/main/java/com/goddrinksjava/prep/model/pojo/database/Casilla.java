package com.goddrinksjava.prep.model.pojo.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Casilla {

    private Integer id;
    private Integer distrito;
    private Integer seccion;
    private Integer fkTipoCasilla;

}

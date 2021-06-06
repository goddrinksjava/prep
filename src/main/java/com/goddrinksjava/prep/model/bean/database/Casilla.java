package com.goddrinksjava.prep.model.bean.database;

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
    private Integer fkSeccional;
    private Integer fkTipoCasilla;

}

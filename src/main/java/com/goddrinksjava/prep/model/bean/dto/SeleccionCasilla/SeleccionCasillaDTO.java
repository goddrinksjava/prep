package com.goddrinksjava.prep.model.bean.dto.SeleccionCasilla;

import com.goddrinksjava.prep.validation.Digits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeleccionCasillaDTO {
    @NotNull
    @Digits
    private String casilla;
}

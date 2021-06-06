package com.goddrinksjava.prep.model.bean.dto.SeleccionCasilla;

import com.goddrinksjava.prep.model.bean.database.Seccional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistritoDTO {
    private Integer id;
    private List<SeccionalDTO> seccionalDTOList;
}

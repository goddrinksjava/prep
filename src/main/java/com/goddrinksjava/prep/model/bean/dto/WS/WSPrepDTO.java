package com.goddrinksjava.prep.model.bean.dto.WS;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WSPrepDTO {
    private List<WSCasillaDTO> casillas;
}

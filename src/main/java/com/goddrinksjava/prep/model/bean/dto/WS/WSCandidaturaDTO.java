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
public
class WSCandidaturaDTO {
    private String nombre;
    private List<WSCandidatoDTO> candidatos;
}
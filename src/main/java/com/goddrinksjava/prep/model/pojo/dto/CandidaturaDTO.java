package com.goddrinksjava.prep.model.pojo.dto;

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
class CandidaturaDTO {
    private String nombre;
    private List<CandidatoDTO> candidatos;
}
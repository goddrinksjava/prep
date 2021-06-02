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
public class CasillaDTO {
    private int id;
    private List<CandidaturaDTO> candidaturas;
}

package com.goddrinksjava.prep.model.pojo.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CapturaDTO {
    Integer id;
    Integer votos;
    String nombre;
    String partido;
}

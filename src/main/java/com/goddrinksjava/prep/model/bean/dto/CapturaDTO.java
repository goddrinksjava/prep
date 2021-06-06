package com.goddrinksjava.prep.model.bean.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CapturaDTO {
    Integer id;
    Integer votos;
    String partido;
}

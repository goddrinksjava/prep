package com.goddrinksjava.prep.model.bean.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seccional {
  private Integer id;
  private Integer fkDistrito;
}

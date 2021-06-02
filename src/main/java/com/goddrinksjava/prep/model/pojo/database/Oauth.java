package com.goddrinksjava.prep.model.pojo.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Oauth {

    private Integer id;
    private String sub;
    private Integer fkUsuario;
    private Integer fkOauthApplication;

}

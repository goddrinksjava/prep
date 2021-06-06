package com.goddrinksjava.prep.model.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OauthSignupDTO {
    private String name;
    private String email;
}

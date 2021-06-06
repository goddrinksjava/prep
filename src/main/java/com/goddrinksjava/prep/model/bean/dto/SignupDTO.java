package com.goddrinksjava.prep.model.bean.dto;

import com.goddrinksjava.prep.validation.Alpha;
import com.goddrinksjava.prep.validation.NoWhitespace;
import com.goddrinksjava.prep.validation.PhoneNumber;
import com.goddrinksjava.prep.validation.Trimmed;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupDTO {
    @NotNull
    @Alpha
    @Size(min = 1, max = 60)
    @Trimmed
    private String nombre;

    @NotNull
    @Alpha
    @Size(min = 1, max = 30)
    @Trimmed
    private String calle;

    @NotNull
    @Alpha
    @Size(min = 1, max = 30)
    @Trimmed
    private String colonia;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 5)
    @Trimmed
    private String numeroCasa;

    @NotNull
    @Alpha
    @Size(min = 1, max = 30)
    @Trimmed
    private String municipio;

    @NotNull
    @Size(min = 5, max = 5)
    @Trimmed
    private String codigoPostal;

    @NotNull
    @PhoneNumber
    private String telefono;

    @NotNull
    @Email
    private String email;

    @NotNull
    @NoWhitespace
    @Size(min = 5, max = 64)
    private String password;
}

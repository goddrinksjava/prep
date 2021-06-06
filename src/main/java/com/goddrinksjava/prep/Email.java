package com.goddrinksjava.prep;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Email {
    private String subject;
    private String message;
    private String fromAddress;
    private String toAdress;
}

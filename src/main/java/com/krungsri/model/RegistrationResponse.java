package com.krungsri.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponse {
    private Long id;
    private String username;
    private String phone;
    private String address;
    private Integer salary;
    private String referenceCode;
    private String memberType;
}

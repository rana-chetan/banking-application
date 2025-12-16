package com.example.banking.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtAuthResponse {

    private String jwtToken;
    private String username;
}
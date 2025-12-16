package com.example.banking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int userId;

    @NotBlank
    @Size(min = 4, message = "Username must be minimum of 4 characters")
    private String username;

    @NotNull
    @Size(min = 3, max = 10, message = "Password must be between 3 to 10 characters")
    private String password;

    private Set<RoleDto> roles = new HashSet<>();

}

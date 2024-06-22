package com.Api.ecommerce.Model.Dto.Security;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ForgotPasswordRequest {

    @NotEmpty(message = "Email cannot be empty")
    private String email;
}

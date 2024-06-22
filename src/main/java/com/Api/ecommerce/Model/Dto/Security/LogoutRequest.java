package com.Api.ecommerce.Model.Dto.Security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LogoutRequest {

    private String accessToken ;
    private String refreshToken ;

}

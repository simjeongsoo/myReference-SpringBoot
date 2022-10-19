package com.sim.jwt.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    //--Token 정보를 Response할 때 사용할 Dto--//

    private String token;
}

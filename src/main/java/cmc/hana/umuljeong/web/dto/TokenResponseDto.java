package cmc.hana.umuljeong.web.dto;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenResponseDto {
    private String accessToken;
    // todo : refreshToken
}

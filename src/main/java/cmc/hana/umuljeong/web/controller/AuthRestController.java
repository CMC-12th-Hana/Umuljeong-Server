package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.auth.filter.JwtFilter;
import cmc.hana.umuljeong.auth.provider.TokenProvider;
import cmc.hana.umuljeong.converter.AuthConverter;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.service.MemberService;
import cmc.hana.umuljeong.web.dto.AuthRequestDto;
import cmc.hana.umuljeong.web.dto.AuthResponseDto;
import cmc.hana.umuljeong.web.dto.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthRestController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final MemberService memberService;

    private String createTokenByPhoneNumberAndPassword(String phoneNumber, String password) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(phoneNumber, password);

        //authenticationManagerBuilder가 호출되면서 CustomUserDetailService가 로드됨.
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.createToken(authentication);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto.LoginDto> login(@RequestBody AuthRequestDto.LoginDto loginDto) {

        String accessToken = createTokenByPhoneNumberAndPassword(loginDto.getPhoneNumber(), loginDto.getPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(AuthConverter.toLoginDto(accessToken));
    }

    @PostMapping("/join")
    public ResponseEntity<AuthResponseDto.JoinDto> join(@RequestBody AuthRequestDto.JoinDto joinDto) {
        // todo : 전화번호(ID) 중복 및 인증, 이메일 중복 처리 방안
        /*
            1. 신규 사용자 디비에 저장
            2. 해당 정보로 토큰 발급
         */
        Member member = memberService.join(joinDto);

        String accessToken = createTokenByPhoneNumberAndPassword(member.getPhoneNumber(), member.getPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(AuthConverter.toJoinDto(member, accessToken));
    }
}

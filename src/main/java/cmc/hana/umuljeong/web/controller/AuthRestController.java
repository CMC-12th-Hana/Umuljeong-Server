package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.auth.annotation.AuthUser;
import cmc.hana.umuljeong.auth.filter.JwtFilter;
import cmc.hana.umuljeong.auth.provider.TokenProvider;
import cmc.hana.umuljeong.converter.AuthConverter;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.service.MemberService;
import cmc.hana.umuljeong.web.dto.AuthRequestDto;
import cmc.hana.umuljeong.web.dto.AuthResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth API", description = "로그인, 회원가입")
@Validated
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

    @Operation(summary = "[001_01]", description = "로그인")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto.LoginDto> login(@RequestBody AuthRequestDto.LoginDto loginDto) {

        String accessToken = createTokenByPhoneNumberAndPassword(loginDto.getPhoneNumber(), loginDto.getPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(AuthConverter.toLoginDto(accessToken));
    }

    @Operation(summary = "[001_02]", description = "회원가입")
    @PostMapping("/join")
    public ResponseEntity<AuthResponseDto.JoinDto> join(@RequestBody AuthRequestDto.JoinDto joinDto) {
        // todo : 전화번호(ID) 중복 및 인증, 이메일 중복 처리 방안
        /*
            1. 신규 사용자 디비에 저장 or 갱신
                - 회사에서 만들어둔 계정이 있으면(등록된 전화번호가 있으면) 개인정보를 업데이트해주고
                - 없으면 그냥 새로 만들어주기
            2. 해당 정보로 토큰 발급
         */
        Member member = memberService.join(joinDto);

        String accessToken = createTokenByPhoneNumberAndPassword(member.getPhoneNumber(), joinDto.getPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(AuthConverter.toJoinDto(member, accessToken));
    }

    @PatchMapping("/company/join")
    public ResponseEntity<AuthResponseDto.JoinCompanyDto> joinCompany(@AuthUser Member member) {
        Member joinedMember = memberService.joinCompany(member);
        return ResponseEntity.ok(AuthConverter.toJoinCompanyDto(joinedMember));
    }
}

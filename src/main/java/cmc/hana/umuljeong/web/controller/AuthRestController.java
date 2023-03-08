package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.auth.annotation.AuthUser;
import cmc.hana.umuljeong.auth.filter.JwtFilter;
import cmc.hana.umuljeong.auth.provider.TokenProvider;
import cmc.hana.umuljeong.converter.AuthConverter;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.enums.VerifyMessageStatus;
import cmc.hana.umuljeong.exception.common.ApiErrorResult;
import cmc.hana.umuljeong.service.MemberService;
import cmc.hana.umuljeong.service.MessageService;
import cmc.hana.umuljeong.web.dto.AuthRequestDto;
import cmc.hana.umuljeong.web.dto.AuthResponseDto;

import cmc.hana.umuljeong.web.dto.TaskCategoryResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import javax.validation.Valid;

@Tag(name = "Auth API", description = "로그인, 회원가입")
@Validated
@RestController
@RequiredArgsConstructor
public class AuthRestController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final MemberService memberService;

    private final MessageService messageService;

    private String createTokenByPhoneNumberAndPassword(String phoneNumber, String password) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(phoneNumber, password);

        //authenticationManagerBuilder가 호출되면서 CustomUserDetailService가 로드됨.
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.createToken(authentication);
    }

    @Operation(summary = "[001_01]", description = "로그인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = AuthResponseDto.LoginDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST : 요청 데이터의 값이 형식에 맞지 않은 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto.LoginDto> login(@RequestBody @Valid AuthRequestDto.LoginDto loginDto) {

        String accessToken = createTokenByPhoneNumberAndPassword(loginDto.getPhoneNumber(), loginDto.getPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(AuthConverter.toLoginDto(accessToken));
    }

    @Operation(summary = "[001_02]", description = "회원가입")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = AuthResponseDto.JoinDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST : 요청 데이터의 값이 형식에 맞지 않은 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
    })
    @PostMapping("/join")
    public ResponseEntity<AuthResponseDto.JoinDto> join(@RequestBody @Valid AuthRequestDto.JoinDto joinDto) {
        // todo : 전화번호(ID) 중복 및 인증
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

    @Operation(summary = "[001_04]", description = "회사 합류")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = AuthResponseDto.JoinCompanyDto.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 사원권한이 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
    })
    @PatchMapping("/company/join")
    public ResponseEntity<AuthResponseDto.JoinCompanyDto> joinCompany(@AuthUser Member member) {
        Member joinedMember = memberService.joinCompany(member);
        return ResponseEntity.ok(AuthConverter.toJoinCompanyDto(joinedMember));
    }

    @PostMapping("/send/message")
    public ResponseEntity<AuthResponseDto.SendMessageDto> sendMessage(@RequestBody @Valid AuthRequestDto.SendMessageDto request) {
        // todo : 예외처리, 중복
        messageService.sendMessage(request.getPhoneNumber());
        return ResponseEntity.ok(AuthConverter.toSendMessageDto());
    }

    @PostMapping("/verify/message")
    public ResponseEntity<AuthResponseDto.VerifyMessageDto> authenticateMessage(@RequestBody @Valid AuthRequestDto.VerifyMessageDto request) {
         VerifyMessageStatus verifyMessageStatus = messageService.verifyMessage(request);
         return ResponseEntity.ok(AuthConverter.toVerifyMessageDto(verifyMessageStatus));
    }
}

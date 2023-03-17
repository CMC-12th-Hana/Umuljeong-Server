package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.auth.annotation.AuthUser;
import cmc.hana.umuljeong.auth.filter.JwtFilter;
import cmc.hana.umuljeong.auth.provider.TokenProvider;
import cmc.hana.umuljeong.auth.service.CustomUserDetailsService;
import cmc.hana.umuljeong.converter.AuthConverter;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.enums.VerifyMessageStatus;
import cmc.hana.umuljeong.exception.AuthException;
import cmc.hana.umuljeong.exception.CompanyException;
import cmc.hana.umuljeong.exception.JwtAuthenticationException;
import cmc.hana.umuljeong.exception.MemberException;
import cmc.hana.umuljeong.exception.common.ApiErrorResult;
import cmc.hana.umuljeong.exception.common.ErrorCode;
import cmc.hana.umuljeong.service.MemberService;
import cmc.hana.umuljeong.service.MessageService;
import cmc.hana.umuljeong.validation.validator.AuthValidator;
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

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Tag(name = "Auth API", description = "로그인, 회원가입")
@Validated
@RestController
@RequiredArgsConstructor
public class AuthRestController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final CustomUserDetailsService customUserDetailsService;
    private final MemberService memberService;
    private final MessageService messageService;

    private UsernamePasswordAuthenticationToken createUsernamePasswordAuthenticationToken(String phoneNumber, String password) {
        return new UsernamePasswordAuthenticationToken(phoneNumber, password);
    }

    private UsernamePasswordAuthenticationToken createUsernamePasswordAuthenticationToken(Member member) {
        return new UsernamePasswordAuthenticationToken(member.getPhoneNumber(), null, List.of(new SimpleGrantedAuthority(member.getMemberRole().getAuthority())));
    }

    private String createRefreshTokenByAuthenticationToken(UsernamePasswordAuthenticationToken authenticationToken) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationToken.getName());
        return tokenProvider.createRefreshToken(
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities())
        );
    }

    private String createAccessTokenByPhoneNumberAndPassword(UsernamePasswordAuthenticationToken authenticationToken) {
        //authenticationManagerBuilder가 호출되면서 CustomUserDetailService가 로드됨.
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.createAccessToken(authentication);
    }

    private String createReissueAccessTokenByPhoneNumberAndPassword(UsernamePasswordAuthenticationToken authenticationToken) {
        //authenticationManagerBuilder가 호출되면서 CustomUserDetailService가 로드됨.
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationToken.getName());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        return tokenProvider.createAccessToken(usernamePasswordAuthenticationToken);
    }



    @Operation(summary = "[001_01]", description = "로그인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = AuthResponseDto.LoginDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST : 요청 데이터의 값이 형식에 맞지 않은 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto.LoginDto> login(@RequestBody @Valid AuthRequestDto.LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken = createUsernamePasswordAuthenticationToken(loginDto.getPhoneNumber(), loginDto.getPassword());
        String accessToken = createAccessTokenByPhoneNumberAndPassword(authenticationToken);
        String refreshToken = createRefreshTokenByAuthenticationToken(authenticationToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(TokenProvider.AUTHORIZATION_HEADER, "Bearer " + accessToken);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(AuthConverter.toLoginDto(accessToken, refreshToken));
    }


    @Operation(summary = "액세스 토큰 만료시 재발급을 위한 API", description = "토큰 재발급 with RefreshToken")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = AuthResponseDto.ReissueDto.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 리프레시 토큰 또한 만료된 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
    })
    @PostMapping("/reissue")
    public ResponseEntity<AuthResponseDto.ReissueDto> refresh(@RequestBody AuthRequestDto.RefreshDto request) {
        String originRefreshToken = request.getRefreshToken();
        boolean isValid = tokenProvider.validateToken(originRefreshToken, TokenProvider.TokenType.REFRESH);
        if(isValid) {
            Authentication authentication = tokenProvider.getAuthentication(originRefreshToken);

            Member member = memberService.findByPhoneNumber(authentication.getName()).orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = createUsernamePasswordAuthenticationToken(member);
            String newAccessToken = createReissueAccessTokenByPhoneNumberAndPassword(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            return ResponseEntity.ok(AuthConverter.toReissueDto(newAccessToken));
        }

        throw new JwtAuthenticationException(ErrorCode.JWT_REFRESH_TOKEN_EXPIRED);
    }


    @Operation(summary = "[001_02]", description = "회원가입")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = AuthResponseDto.JoinDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST : 요청 데이터의 값이 형식에 맞지 않은 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
    })
    @PostMapping("/join")
    public ResponseEntity<AuthResponseDto.JoinDto> join(@RequestBody @Valid AuthRequestDto.JoinDto joinDto) {
        /*
            1. 신규 사용자 디비에 저장 or 갱신
                - 회사에서 만들어둔 계정이 있으면(등록된 전화번호가 있으면) 개인정보를 업데이트해주고
                - 없으면 그냥 새로 만들어주기
            2. 해당 정보로 토큰 발급
         */

        if(!AuthValidator.isVerified(joinDto.getPhoneNumber())) throw new AuthException(ErrorCode.UNVERIFIED_PHONE_NUMBER);

        Member member = memberService.join(joinDto);

        UsernamePasswordAuthenticationToken authenticationToken = createUsernamePasswordAuthenticationToken(member.getPhoneNumber(), joinDto.getPassword());
        String accessToken = createAccessTokenByPhoneNumberAndPassword(authenticationToken);
        String refreshToken = createRefreshTokenByAuthenticationToken(authenticationToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(TokenProvider.AUTHORIZATION_HEADER, "Bearer " + accessToken);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(AuthConverter.toJoinDto(member, accessToken, refreshToken));
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
        if(member.getCompany() == null) throw new CompanyException(ErrorCode.COMPANY_NOT_FOUND);
        Member joinedMember = memberService.joinCompany(member);
        return ResponseEntity.ok(AuthConverter.toJoinCompanyDto(joinedMember));
    }

    @PostMapping("/message/send")
    public ResponseEntity<AuthResponseDto.SendMessageDto> sendMessage(@RequestBody @Valid AuthRequestDto.SendMessageDto request) {
        messageService.sendMessage(request);
        return ResponseEntity.ok(AuthConverter.toSendMessageDto());
    }

    @PostMapping("/message/verify")
    public ResponseEntity<AuthResponseDto.VerifyMessageDto> authenticateMessage(@RequestBody @Valid AuthRequestDto.VerifyMessageDto request) {
         VerifyMessageStatus verifyMessageStatus = messageService.verifyMessage(request);
         if(request.getMessageType() == AuthRequestDto.MessageType.JOIN) return ResponseEntity.ok(AuthConverter.toJoinVerifyMessageDto(verifyMessageStatus));
         else {
             messageService.sendTempPassword(request.getPhoneNumber());
             return ResponseEntity.ok(AuthConverter.toJoinVerifyMessageDto(verifyMessageStatus));
         }
    }


}

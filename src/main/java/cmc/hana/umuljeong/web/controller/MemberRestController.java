package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.auth.annotation.AuthUser;
import cmc.hana.umuljeong.converter.MemberConverter;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.exception.CompanyException;
import cmc.hana.umuljeong.exception.common.ApiErrorResult;
import cmc.hana.umuljeong.exception.common.ErrorCode;
import cmc.hana.umuljeong.service.MemberService;
import cmc.hana.umuljeong.validation.annotation.ExistCompany;
import cmc.hana.umuljeong.web.dto.ClientCompanyResponseDto;
import cmc.hana.umuljeong.web.dto.MemberRequestDto;
import cmc.hana.umuljeong.web.dto.MemberResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Member API", description = "사원 조회, 추가")
@Validated
@RestController
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;

    @Operation(summary = "[005_02.1]", description = "사원 추가")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = MemberResponseDto.ProfileListDto.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사가 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : companyId에 해당하는 회사가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @GetMapping("/company/{companyId}/members")
    public ResponseEntity<MemberResponseDto.ProfileListDto> getMemberList(@PathVariable(name = "companyId") @ExistCompany Long companyId, @AuthUser Member member) {
        if(companyId != member.getCompany().getId()) throw new CompanyException(ErrorCode.COMPANY_ACCESS_DENIED);
        List<Member> memberList = memberService.findByCompany(companyId);
        return ResponseEntity.ok(MemberConverter.toMemberProfileListDto(memberList));
    }

    @Operation(summary = "[005_03]", description = "사원 프로필 조회")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = MemberResponseDto.ProfileDto.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @GetMapping("/company/member/profile")
    public ResponseEntity<MemberResponseDto.ProfileDto> getProfile(@AuthUser Member member) {
        return ResponseEntity.ok(MemberConverter.toProfileDto(member));
    }

    @Operation(summary = "[005_03]", description = "사원 프로필 수정")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = MemberResponseDto.UpdateProfileDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST : 요청 데이터의 값이 형식에 맞지 않은 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @PatchMapping("/company/member/profile")
    public ResponseEntity<MemberResponseDto.UpdateProfileDto> updateProfile(@RequestBody @Valid MemberRequestDto.UpdateProfileDto request, @AuthUser Member member) {
        Member updatedMember = memberService.updateProfile(member, request);
        return ResponseEntity.ok(MemberConverter.toUpdateProfileDto(updatedMember));
    }

    @Operation(summary = "[005_02.1]", description = "사원 추가")
    @Parameters({
            @Parameter(name = "leader", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = MemberResponseDto.CreateDto.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사가 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : companyId에 해당하는 회사가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @PostMapping("/company/{companyId}/member")
    public ResponseEntity<MemberResponseDto.CreateDto> create(@PathVariable(name = "companyId") @ExistCompany Long companyId, @RequestBody @Valid MemberRequestDto.CreateDto request, @AuthUser Member leader) {
        if(companyId != leader.getCompany().getId()) throw new CompanyException(ErrorCode.COMPANY_ACCESS_DENIED);
        Member createdMember = memberService.create(companyId, request);
        return ResponseEntity.ok(MemberConverter.toCreateDto(createdMember));
    }
}

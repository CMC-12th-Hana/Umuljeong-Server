package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.auth.annotation.AuthUser;
import cmc.hana.umuljeong.converter.MemberConverter;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.exception.CompanyException;
import cmc.hana.umuljeong.exception.MemberException;
import cmc.hana.umuljeong.exception.common.ApiErrorResult;
import cmc.hana.umuljeong.exception.common.ErrorCode;
import cmc.hana.umuljeong.service.MemberService;
import cmc.hana.umuljeong.validation.annotation.ExistCompany;
import cmc.hana.umuljeong.validation.annotation.ExistMember;
import cmc.hana.umuljeong.validation.validator.CompanyValidator;
import cmc.hana.umuljeong.validation.validator.MemberValidator;
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

    @Operation(summary = "[005_02.1]", description = "사원 목록 조회")
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
    public ResponseEntity<MemberResponseDto.ProfileListDto> getMemberList(@PathVariable(name = "companyId") @ExistCompany Long companyId,
                                                                          @RequestParam(name = "name", required = false) String name, @AuthUser Member member) {
        if(!CompanyValidator.isAccessible(member, companyId)) throw new CompanyException(ErrorCode.COMPANY_ACCESS_DENIED);
        List<Member> memberList = memberService.findByCompanyAndName(companyId, name);
        return ResponseEntity.ok(MemberConverter.toMemberProfileListDto(memberList));
    }

    @Operation(summary = "[005_01]", description = "다른 사원 프로필 조회")
    @Parameters({
            @Parameter(name = "loginMember", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = MemberResponseDto.ProfileDto.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사의 구성원이 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : memberId에 해당하는 회사가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @GetMapping("/company/member/{memberId}/profile")
    public ResponseEntity<MemberResponseDto.ProfileDto> getMember(@PathVariable(name = "memberId") @ExistMember Long memberId, @AuthUser Member loginMember) {
        if(!MemberValidator.isAccessible(loginMember, memberId))
            throw new MemberException(ErrorCode.MEMBER_ACCESS_DENIED);
        Member member = memberService.findById(memberId);
        return ResponseEntity.ok(MemberConverter.toProfileDto(member));
    }

    @Operation(summary = "[005_03]", description = "내 프로필 조회")
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

    @Operation(summary = "[005_03]", description = "내 프로필 수정")
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

    @Operation(summary = "[005_03.3]", description = "다른 사원 프로필 수정, 리더는 본인 프로필 수정")
    @Parameters({
            @Parameter(name = "leader", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = MemberResponseDto.UpdateProfileDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST : 요청 데이터의 값이 형식에 맞지 않은 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사의 구성원이 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @PatchMapping("/company/member/{memberId}/profile")
    public ResponseEntity<MemberResponseDto.UpdateProfileDto> updateMemberProfile(@PathVariable(name = "memberId") @ExistMember Long memberId, @RequestBody @Valid MemberRequestDto.UpdateMemberProfileDto request, @AuthUser Member leader) {
        Member updatedMember;
        if(MemberValidator.isSameMember(leader, memberId)) {
            updatedMember = memberService.updateLeaderProfile(leader, request);
        }
        if(!MemberValidator.isAccessible(leader, memberId))
            throw new MemberException(ErrorCode.MEMBER_ACCESS_DENIED);

        updatedMember = memberService.updateMemberProfile(memberId, request);
        return ResponseEntity.ok(MemberConverter.toUpdateProfileDto(updatedMember));
    }

    @Operation(summary = "[176985]", description = "사원 권한 변경 (일반 사원을 리더로)")
    @Parameters({
            @Parameter(name = "leader", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = MemberResponseDto.UpdateProfileDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST : 요청 데이터의 값이 형식에 맞지 않은 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사의 구성원이 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @PatchMapping("/company/member/{memberId}/role")
    public ResponseEntity<MemberResponseDto.UpdateRoleDto> updateRole(@PathVariable(name = "memberId") @ExistMember Long memberId, @AuthUser Member leader) {
        if(!MemberValidator.isAccessible(leader, memberId))
            throw new MemberException(ErrorCode.MEMBER_ACCESS_DENIED);

        Member updatedMember = memberService.updateMemberRole(leader, memberId);
        return ResponseEntity.ok(MemberConverter.toUpdateRoleDto(updatedMember));
    }

    @Operation(summary = "[005_03]", description = "내 비밀번호 재설정")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = MemberResponseDto.UpdatePasswordDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST : 요청 데이터의 값이 형식에 맞지 않은 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @PatchMapping("/company/member/password")
    public ResponseEntity<MemberResponseDto.UpdatePasswordDto> updatePassword(@RequestBody @Valid MemberRequestDto.UpdatePasswordDto request, @AuthUser Member member) {
        Member updatedMember = memberService.updatePassword(member, request);
        return ResponseEntity.ok(MemberConverter.toUpdatePasswordDto(updatedMember));
    }

    @Operation(summary = "[005_02.1]", description = "사원 추가")
    @Parameters({
            @Parameter(name = "leader", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = MemberResponseDto.CreateDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST : 요청 데이터의 값이 형식에 맞지 않은 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사가 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : companyId에 해당하는 회사가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @PostMapping("/company/{companyId}/member")
    public ResponseEntity<MemberResponseDto.CreateDto> create(@PathVariable(name = "companyId") @ExistCompany Long companyId, @RequestBody @Valid MemberRequestDto.CreateDto request, @AuthUser Member leader) {
        if(!CompanyValidator.isAccessible(leader, companyId)) throw new CompanyException(ErrorCode.COMPANY_ACCESS_DENIED);
        Member createdMember = memberService.create(companyId, request);
        return ResponseEntity.ok(MemberConverter.toCreateDto(createdMember));
    }

    @Operation(summary = "[005_03.2]", description = "사원 삭제")
    @Parameters({
            @Parameter(name = "leader", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = MemberResponseDto.DeleteDto.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사의 구성원이 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : memberId 해당하는 구성원이 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @DeleteMapping("/company/member/{memberId}")
    public ResponseEntity<MemberResponseDto.DeleteDto> delete(@PathVariable(name = "memberId") @ExistMember Long memberId, @AuthUser Member leader) {
        if(!MemberValidator.isAccessible(leader, memberId))
            throw new MemberException(ErrorCode.MEMBER_ACCESS_DENIED);
        memberService.delete(memberId);
        return ResponseEntity.ok(MemberConverter.toDeleteDto());
    }

    @Operation(summary = "[]", description = "회원 탈퇴")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = MemberResponseDto.DeleteDto.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사의 구성원이 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
    })
    @DeleteMapping("/company/member")
    public ResponseEntity<MemberResponseDto.DeleteDto> exit(@AuthUser Member member) {
        memberService.delete(member.getId());
        return ResponseEntity.ok(MemberConverter.toDeleteDto());
    }
}

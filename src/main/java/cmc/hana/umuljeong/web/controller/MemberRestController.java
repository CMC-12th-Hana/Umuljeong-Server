package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.auth.annotation.AuthUser;
import cmc.hana.umuljeong.converter.MemberConverter;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.service.MemberService;
import cmc.hana.umuljeong.validation.annotation.ExistCompany;
import cmc.hana.umuljeong.web.dto.MemberRequestDto;
import cmc.hana.umuljeong.web.dto.MemberResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
    @GetMapping("/company/{companyId}/members")
    public ResponseEntity<MemberResponseDto.ProfileListDto> getMemberList(@PathVariable(name = "companyId") @ExistCompany Long companyId, @AuthUser Member member) {
        // todo : 회사랑 멤버가 일치하는지
        List<Member> memberList = memberService.findByCompany(companyId);
        return ResponseEntity.ok(MemberConverter.toMemberProfileListDto(memberList));
    }

    @Operation(summary = "[005_03]", description = "사원 프로필 조회")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @GetMapping("/company/member/profile")
    public ResponseEntity<MemberResponseDto.ProfileDto> getProfile(@AuthUser Member member) {
        return ResponseEntity.ok(MemberConverter.toProfileDto(member));
    }

    @Operation(summary = "[005_03]", description = "사원 프로필 수정")
    @Parameters({
            @Parameter(name = "member", hidden = true)
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
    @PostMapping("/company/{companyId}/member")
    public ResponseEntity<MemberResponseDto.CreateDto> create(@PathVariable(name = "companyId") @ExistCompany Long companyId, @RequestBody @Valid MemberRequestDto.CreateDto request, @AuthUser Member leader) {
        // todo : 리더권한 & 회사랑 멤버가 일치하는지
        Member createdMember = memberService.create(companyId, request);
        return ResponseEntity.ok(MemberConverter.toCreateDto(createdMember));
    }
}

package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.auth.annotation.AuthUser;
import cmc.hana.umuljeong.converter.MemberConverter;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.service.MemberService;
import cmc.hana.umuljeong.web.dto.MemberRequestDto;
import cmc.hana.umuljeong.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;

    @GetMapping("/company/{companyId}/members")
    public ResponseEntity<MemberResponseDto.ProfileListDto> getMemberList(@PathVariable(name = "companyId") Long companyId, @AuthUser Member member) {
        // todo : 회사랑 멤버가 일치하는지
        List<Member> memberList = memberService.findByCompany(companyId);
        return ResponseEntity.ok(MemberConverter.toMemberProfileListDto(memberList));
    }

    @GetMapping("/company/member/profile")
    public ResponseEntity<MemberResponseDto.ProfileDto> getProfile(@AuthUser Member member) {
        return ResponseEntity.ok(MemberConverter.toProfileDto(member));
    }

    @PostMapping("/company/{companyId}/member")
    public ResponseEntity<MemberResponseDto.CreateDto> create(@PathVariable(name = "companyId") Long companyId, @RequestBody MemberRequestDto.CreateDto request, @AuthUser Member leader) {
        // todo : 리더권한 & 회사랑 멤버가 일치하는지
        Member createdMember = memberService.create(companyId, request);
        return ResponseEntity.ok(MemberConverter.toCreateDto(createdMember));
    }
}

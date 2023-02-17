package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.auth.annotation.AuthUser;
import cmc.hana.umuljeong.converter.MemberConverter;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.service.MemberService;
import cmc.hana.umuljeong.web.dto.MemberRequestDto;
import cmc.hana.umuljeong.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;

    @GetMapping("/company/members")
    public ResponseEntity<MemberResponseDto.ProfileListDto> getMemberList(@AuthUser Member member) {
        List<Member> memberList = memberService.findByCompany(member.getCompany());
        return ResponseEntity.ok(MemberConverter.toMemberProfileListDto(memberList));
    }

    @GetMapping("/company/member/profile")
    public ResponseEntity<MemberResponseDto.ProfileDto> getProfile(@AuthUser Member member) {
        return ResponseEntity.ok(MemberConverter.toProfileDto(member));
    }

    @PostMapping("/company/member")
    public ResponseEntity<MemberResponseDto.CreateDto> create(@RequestBody MemberRequestDto.CreateDto request, @AuthUser Member leader) {
        Member createdMember = memberService.create(leader, request);
        return ResponseEntity.ok(MemberConverter.toCreateDto(createdMember));
    }
}

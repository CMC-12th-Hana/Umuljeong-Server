package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.enums.JoinCompanyStatus;
import cmc.hana.umuljeong.domain.enums.MemberRole;
import cmc.hana.umuljeong.exception.common.ErrorCode;
import cmc.hana.umuljeong.exception.MemberException;
import cmc.hana.umuljeong.repository.MemberRepository;
import cmc.hana.umuljeong.web.dto.AuthRequestDto;
import cmc.hana.umuljeong.web.dto.MemberRequestDto;
import cmc.hana.umuljeong.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MemberConverter {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    private static PasswordEncoder staticPasswordEncoder;
    private static MemberRepository staticMemberRepository;

    public static MemberResponseDto.UpdateProfileDto toUpdateProfileDto(Member updatedMember) {
        return MemberResponseDto.UpdateProfileDto.builder()
                .memberId(updatedMember.getId())
                .updatedAt(updatedMember.getUpdatedAt())
                .build();
    }

    public static MemberResponseDto.DeleteDto toDeleteDto() {
        return MemberResponseDto.DeleteDto.builder()
                .deletedAt(LocalDateTime.now())
                .build();
    }

    public static MemberResponseDto.UpdatePasswordDto toUpdatePasswordDto(Member updatedMember) {
        return MemberResponseDto.UpdatePasswordDto.builder()
                .memberId(updatedMember.getId())
                .updatedAt(updatedMember.getUpdatedAt())
                .build();
    }

    public static MemberResponseDto.UpdateRoleDto toUpdateRoleDto(Member updatedMember) {
        return MemberResponseDto.UpdateRoleDto.builder()
                .memberId(updatedMember.getId())
                .role(updatedMember.getMemberRole().getDescription())
                .updatedAt(updatedMember.getUpdatedAt())
                .build();
    }

    @PostConstruct
    public void init() {
        staticPasswordEncoder = this.passwordEncoder;
        staticMemberRepository = this.memberRepository;
    }

    public static Member toMember(AuthRequestDto.JoinDto joinDto) {
        return Member.builder()
                .company(null)
                .memberRole(MemberRole.STAFF)
                .name(joinDto.getName())
                .phoneNumber(joinDto.getPhoneNumber())
                .password(staticPasswordEncoder.encode(joinDto.getPassword()))
                .isEnabled(Boolean.TRUE)
                .joinCompanyStatus(JoinCompanyStatus.PENDING)
                .build();
    }

    public static Member toMember(MemberRequestDto.CreateDto createDto) {
        return Member.builder()
                .company(null)
                .memberRole(MemberRole.STAFF)
                .name(createDto.getName())
                .phoneNumber(createDto.getPhoneNumber())
                .password(null)
                .staffRank(createDto.getStaffRank())
                .staffNumber(createDto.getStaffNumber())
                .isEnabled(Boolean.FALSE)
                .joinCompanyStatus(JoinCompanyStatus.PENDING)
                .build();
    }

    public static Member toMember(String phoneNumber) {
        return staticMemberRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public static MemberResponseDto.ProfileDto toProfileDto(Member member) {
        return MemberResponseDto.ProfileDto.builder()
                .companyId(member.getCompany() != null ? member.getCompany().getId() : -1)
                .memberId(member.getId())
                .name(member.getName())
                .role(member.getMemberRole().getDescription())
                .companyName(member.getCompany() != null ? member.getCompany().getName() : "")
                .staffRank(member.getStaffRank() != null ? member.getStaffRank() : "")
                .phoneNumber(member.getPhoneNumber())
                .staffNumber(member.getStaffNumber() != null ? member.getStaffNumber() : "")
                .build();
    }

    public static MemberResponseDto.ProfileListDto toMemberProfileListDto(List<Member> memberList) {
        List<MemberResponseDto.ProfileDto> profileDtoList =
                memberList.stream()
                .map(member -> toProfileDto(member))
                .collect(Collectors.toList());

        return MemberResponseDto.ProfileListDto.builder()
                .profileDtoList(profileDtoList)
                .build();
    }


    public static MemberResponseDto.CreateDto toCreateDto(Member createdMember) {
        return MemberResponseDto.CreateDto.builder()
                .memberId(createdMember.getId())
                .memberName(createdMember.getName())
                .companyName(createdMember.getCompany().getName())
                .createdAt(createdMember.getCreatedAt())
                .build();
    }

}

package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.enums.MemberRole;
import cmc.hana.umuljeong.web.dto.AuthRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class MemberConverter {

    private final PasswordEncoder passwordEncoder;

    private static PasswordEncoder staticPasswordEncoder;

    @PostConstruct
    public void init() {
        staticPasswordEncoder = this.passwordEncoder;
    }

    public static Member toMember(AuthRequestDto.JoinDto joinDto) {
        return Member.builder()
                .company(null)
                .memberRole(MemberRole.STAFF)
                .name(joinDto.getName())
                .email(joinDto.getEmail())
                .phoneNumber(joinDto.getPhoneNumber())
                .password(staticPasswordEncoder.encode(joinDto.getPassword())) // todo : 암호화 필요
                .isEnabled(Boolean.TRUE)
                .build();
    }
}

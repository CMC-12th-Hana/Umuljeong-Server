package cmc.hana.umuljeong.auth.service;

import cmc.hana.umuljeong.auth.common.CustomUserDetails;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        return memberRepository.findByPhoneNumber(phoneNumber)
                .map(member -> createUser(member))
                .orElseThrow(() -> new UsernameNotFoundException(phoneNumber + "로 가입된 계정이 없습니다."));
    }

    /**Security User 정보를 생성한다. */
    private CustomUserDetails createUser(Member member) {
        if(!member.getIsEnabled()){
            throw new BadCredentialsException("계정이 비활성화되어있습니다.");
        }
        List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(member.getMemberRole().getAuthority()));

        // todo : CustomUserDetails로 변경? 이메일 인증 && 휴대폰 인증 등 복잡한 경우 생기면..
        return CustomUserDetails.builder()
                .phoneNumber(member.getPhoneNumber())
                .password(member.getPassword())
                .authorities(grantedAuthorities)
                .enabled(member.getIsEnabled())
                .build();
    }
}

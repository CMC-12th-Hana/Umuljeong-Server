package cmc.hana.umuljeong.auth.config;

import cmc.hana.umuljeong.auth.handler.JwtAccessDeniedHandler;
import cmc.hana.umuljeong.auth.handler.JwtAuthenticationEntryPoint;
import cmc.hana.umuljeong.auth.provider.TokenProvider;
import cmc.hana.umuljeong.domain.enums.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAtuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final String STAFF = MemberRole.STAFF.name();
    private final String LEADER = MemberRole.LEADER.name();

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring()
                .antMatchers(
                        "/favicon.ico",
                        "/join",
                        "/login",
                        "/health",
                        "/");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()

                /**401, 403 Exception 핸들링 */
                .exceptionHandling()
                .authenticationEntryPoint(jwtAtuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                /**세션 사용하지 않음*/
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                /** HttpServletRequest를 사용하는 요청들에 대한 접근 제한 설정*/
                .and()
                .authorizeHttpRequests()
                // Auth API
                .antMatchers(
                        HttpMethod.PATCH, "/company/join"
                ).hasRole(STAFF)

                // ClientCompany API
                .antMatchers(
                        HttpMethod.POST, "/company/{companyId}/client"
                ).hasAnyRole(STAFF, LEADER)
                .antMatchers(
                        HttpMethod.GET, "/company/client/{clientId}", "/company/{companyId}/clients"
                ).hasAnyRole(STAFF, LEADER)
                .antMatchers(
                        HttpMethod.PATCH, "/company/client/{clientId}"
                ).hasAnyRole(STAFF, LEADER)

                // TaskCategory API
                .antMatchers(
                        HttpMethod.POST, "/company/{companyId}/client/business/task/category"
                ).hasRole(LEADER)
                .antMatchers(
                        HttpMethod.PATCH, "/company/client/business/task/category/{categoryId}"
                ).hasRole(LEADER)
                .antMatchers(
                        HttpMethod.DELETE, "/company/client/business/task/categories"
                ).hasRole(LEADER)
                .antMatchers(
                        HttpMethod.GET, "/company/{companyId}/client/business/task/categories"
                ).hasAnyRole(STAFF, LEADER)

                // TASK API

                // Company API
                .antMatchers(
                        HttpMethod.POST, "/company"
                ).hasAnyRole(STAFF, LEADER)

                // Business API
                .antMatchers(
                        HttpMethod.POST, "/company/client/{clientId}/business"
                ).hasAnyRole(STAFF, LEADER)
                .antMatchers(
                        HttpMethod.GET, "/company/client/business/{businessId}"
                ).hasAnyRole(STAFF, LEADER)
                .antMatchers(
                        HttpMethod.PATCH, "/company/client/business/{businessId}"
                ).hasAnyRole(STAFF, LEADER)

                // Member API
                .antMatchers(
                        HttpMethod.POST, "/company/{companyId}/member"
                ).hasRole(LEADER)
                .antMatchers(
                        HttpMethod.GET, "/company/member/profile"
                ).hasAnyRole(STAFF, LEADER)
                .antMatchers(
                        HttpMethod.PATCH, "/company/member/profile"
                ).hasAnyRole(STAFF, LEADER)
                .antMatchers(
                        HttpMethod.GET, "/company/{companyId}/members"
                ).hasAnyRole(STAFF, LEADER)

                /**JwtSecurityConfig 적용 */
                .and()
                .apply(new JwtSecurityConfig(tokenProvider))

                .and().build();
    }

}

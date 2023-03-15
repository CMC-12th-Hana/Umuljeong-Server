package cmc.hana.umuljeong.auth.provider;

import cmc.hana.umuljeong.exception.JwtAuthenticationException;
import cmc.hana.umuljeong.exception.common.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;


@Component
public class TokenProvider implements InitializingBean {

    private final Logger LOGGER = LoggerFactory.getLogger(TokenProvider.class);

    private final String AUTHORITIES_KEY;
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final String secret;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;
    private Key key;

    public enum TokenType {
        ACCESS, REFRESH;
    }

    public TokenProvider(@Value("${jwt.secret}") String secret,
                         @Value("${jwt.authorities-key}") String authoritiesKey,
                         @Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidityInMilliseconds,
                         @Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInMilliseconds){
        this.secret = secret;
        this.AUTHORITIES_KEY = authoritiesKey;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**token 생성 algorithm */
    public String createAccessToken(Authentication authentication){
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.accessTokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public String createRefreshToken(Authentication authentication){
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.refreshTokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    /**token 생성 algorithm */
    public String createAccessToken(String name, Collection<? extends GrantedAuthority> authorities){

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.accessTokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(name)
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public String createRefreshToken(String name, Collection<? extends GrantedAuthority> authorities) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.refreshTokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(name)
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    /**인증 정보 조회 */
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    /**token 유효성 검증 */
    public boolean validateToken(String token, TokenType type) throws JwtAuthenticationException {
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            throw new JwtAuthenticationException(ErrorCode.JWT_BAD_REQUEST);
        }catch(ExpiredJwtException e){
            if(type == TokenType.ACCESS) throw new JwtAuthenticationException(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED);
            else throw new JwtAuthenticationException(ErrorCode.JWT_REFRESH_TOKEN_EXPIRED);
        }catch(UnsupportedJwtException e){
            throw new JwtAuthenticationException(ErrorCode.JWT_UNSUPPORTED_TOKEN);
        }catch(IllegalArgumentException e){
            throw new JwtAuthenticationException(ErrorCode.JWT_BAD_REQUEST);
        }
    }

    /**토큰 정보 추출 */
    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}

package mystudy.study.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

// 로그인 필터  // 로그인은 form 로그인 방식으로 JWT 를 발급하기 위해서
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        // 요청에서 username, password 추출
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(token);
    }

    // 로그인 성공시 응답
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException, ServletException {
        // == 다중 토큰 == //
        log.info("loginFilter successfulAuthentication 중");

        // 사용자 정보
        String username = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority(); // 권한

        // 토큰 생성
        String access = jwtUtil.createJWT("access", username, role, 15 * 60 * 1000L); // 15분
        String refresh = jwtUtil.createJWT("refresh", username, role, 24 * 60 * 60 * 1000L); // 24시간
        
        // 응답 설정
        response.setHeader("access", access); // access token 헤더에 추가
        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());
        response.sendRedirect("/");
    }

    // 로그인 실패시 응답
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
    }


    // == 쿠키 생성 ==
    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value); // 쿠키 만들기
        cookie.setMaxAge(24*60*60); // 쿠키 유효 기간 설정 (초 단위)
        //cookie.setSecure(true); // HTTPS 의 보안 상태에서만 쿠키 유효 설정
        //cookie.setPath("/");  // 애플리케이션내의 모든 경로에서 쿠키가 유효하게 설정
        cookie.setHttpOnly(true); // HttpOnly 쿠키가 클라이언트 측 스크립트에서 접근할 수 없게 된다 (XSS) 공격 보호 설정

        return cookie;
    }
}

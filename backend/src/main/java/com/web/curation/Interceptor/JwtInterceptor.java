package com.web.curation.Interceptor;

import com.web.curation.member.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    private static final String HEADER_AUTH = "Authorization";
    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String token = request.getHeader("access-token");
        if (StringUtils.equals(request.getMethod(), "OPTIONS")) {
            log.debug("if request options method is options, return true");
            return true;
        }
        if(token != null && jwtService.isUsable(token)){
            System.out.println("인증롼료");
            return true;
        }else return false;
    }
}
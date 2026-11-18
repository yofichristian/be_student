package com.eterna.yofi.demoyofi.security;

import com.eterna.yofi.demoyofi.constant.SystemConstant;
import com.eterna.yofi.demoyofi.util.UUIDUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final ApplicationContext applicationContext;
    private final JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        try {
            String authHeader = request.getHeader(SystemConstant.AUTHORIZATION_HEADER);
            String requestId = request.getHeader(SystemConstant.X_REQUEST_ID);
//            String username = request.getHeader(SystemConstant.USERNAME);
//
//            String userid = request.getHeader(SystemConstant.USERID);
//            String fullname = request.getHeader(SystemConstant.FULLNAME);
//            String realIp = request.getHeader(SystemConstant.X_REAL_IP);
//            String host = request.getHeader(SystemConstant.HOST);
//            String userAgent = request.getHeader(SystemConstant.HEADER_USER_AGENT);
//            String browser = request.getHeader(SystemConstant.BROWSER);
//            String platform = request.getHeader(SystemConstant.PLATFORM);
//            String osname = request.getHeader(SystemConstant.OSNAME);
//
//            String menu = request.getHeader(SystemConstant.MENU);
//            String device = request.getHeader(SystemConstant.DEVICE);
//            String services = request.getHeader(SystemConstant.SERVICES);
//
            requestId = StringUtils.isEmpty(requestId) ? UUIDUtil.getUuid() : requestId;
            MDC.put(SystemConstant.X_REQUEST_ID, requestId);

//            MDC.put(SystemConstant.USERNAME, username);
//            MDC.put(SystemConstant.AUTHORIZATION_HEADER, authHeader);
//
//            MDC.put(SystemConstant.USERID, userid);
//            MDC.put(SystemConstant.FULLNAME, fullname);
//            MDC.put(SystemConstant.X_REAL_IP, realIp);
//            MDC.put(SystemConstant.HOST, host);
//            MDC.put(SystemConstant.HEADER_USER_AGENT, userAgent);
//            MDC.put(SystemConstant.BROWSER, browser);
//            MDC.put(SystemConstant.PLATFORM, platform);
//            MDC.put(SystemConstant.OSNAME, osname);
//
//            MDC.put(SystemConstant.MENU, menu);
//            MDC.put(SystemConstant.DEVICE, device);
//            MDC.put(SystemConstant.SERVICES, services);

            log.info("Check token {} ", request.getRequestURL());

            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                String token = "";
                String username = "";

                if (authHeader != null && authHeader.startsWith(SystemConstant.BEARER)) {
                    token = authHeader.substring(7); // Extract token
                    username = jwtUtil.extractUsername(token);
                    MDC.put(SystemConstant.USERNAME, username);
                }

                if (StringUtils.isNotEmpty(username)){
                    JwtUserDetails userDetails = applicationContext.getBean(JwtUserDetailsService.class)
                            .loadUserByUsername(username);

                    boolean tokenValid = jwtUtil.validateToken(token, userDetails);

                    if (!tokenValid) throw new RuntimeException("invalid token");

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                }

            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

}

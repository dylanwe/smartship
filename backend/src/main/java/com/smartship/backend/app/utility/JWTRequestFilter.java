package com.smartship.backend.app.utility;

import com.smartship.backend.app.config.GlobalConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private final GlobalConfig globalConfig;
    private final JWTokenUtil jwTokenUtil;

    @Autowired
    public JWTRequestFilter(GlobalConfig globalConfig, JWTokenUtil jwTokenUtil) {
        this.globalConfig = globalConfig;
        this.jwTokenUtil = jwTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {
        String servletPath = request.getServletPath();

        // OPTIONS request and non-secured area should pass through without check
        if (HttpMethod.OPTIONS.matches(request.getMethod()) || globalConfig.SECURED_PATHS.stream().noneMatch(servletPath::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        String encryptedToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Check if we can find the authorization header
        if (encryptedToken == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No token provided. You need to login first.");
            return;
        }

        // Decode the token
        try {
            JWTokenInfo jwTokenInfo = jwTokenUtil.decode(encryptedToken.replace("Bearer ", ""),
                    this.globalConfig.getPassphrase());
            request.setAttribute(JWTokenInfo.KEY, jwTokenInfo);

            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage() + " You need to login first");
        }

    }
}

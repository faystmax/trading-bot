package com.faystmax.tradingbot.config.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Slf4j
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException exc) throws IOException {
        log.error("Unauthorized error = '{}', url = '{}'", exc.getMessage(), request.getRequestURI(), exc);
        response.sendError(SC_UNAUTHORIZED, "Error: Unauthorized");
    }
}

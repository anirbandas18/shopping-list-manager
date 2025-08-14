package com.teenthofabud.codingchallenge.ecommerce.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teenthofabud.codingchallenge.ecommerce.model.ErrorVo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;

@Component
@Slf4j
public class AuthenticationErrorHandler implements AccessDeniedHandler, AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    public AuthenticationErrorHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("Access denied error occurred", accessDeniedException);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ErrorVo vo = ErrorVo.builder().domain("Accessibility").code(HttpStatus.FORBIDDEN.name()).message(accessDeniedException.getMessage()).build();
        response.getWriter().write(objectMapper.writeValueAsString(vo));
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        log.error("Authentication failure error occurred", authenticationException);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ErrorVo vo = ErrorVo.builder().domain("Accessibility").code(HttpStatus.UNAUTHORIZED.name()).message(authenticationException.getMessage()).build();
        response.getWriter().write(objectMapper.writeValueAsString(vo));
    }
}

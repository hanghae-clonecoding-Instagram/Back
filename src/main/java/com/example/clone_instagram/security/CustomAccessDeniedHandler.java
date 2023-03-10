//package com.example.clone_instagram.security;
//
//import com.example.clone_instagram.dto.SecurityExceptionDto;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.OutputStream;
//
//@Component
//public class CustomAccessDeniedHandler implements AccessDeniedHandler {
//    private static final SecurityExceptionDto exceptionDto =
//            new SecurityExceptionDto("접근 권한이 없습니다.", HttpStatus.FORBIDDEN.value());
//
//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response,
//                       AccessDeniedException accessDeniedException) throws IOException {
//
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setStatus(HttpStatus.FORBIDDEN.value());
//
//        try (OutputStream os = response.getOutputStream()) {
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.writeValue(os, exceptionDto);
//            os.flush();
//        }
//    }
//}

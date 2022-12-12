package com.py.projects.trello.filter;

import com.py.projects.trello.dto.ErrorResponse;
import com.py.projects.trello.exceptions.InvalidatedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);

        } catch (InvalidatedJwtException e) {
            logger.info("Here in invalid jwt ");
            setErrorResponse(UNAUTHORIZED, response, e);
            e.printStackTrace();
        } catch (RuntimeException e) {
            logger.info("Here in Run time jwt ");
            e.printStackTrace();
            setErrorResponse(INTERNAL_SERVER_ERROR, response, e);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex){
        response.setStatus(status.value());
        response.setContentType("application/json");
        // A class used for errors
        ErrorResponse error = ErrorResponse.builder()
                .errorCode(UNAUTHORIZED.value())
                .message("Session Expired.").build();
        try {
            String json = error.convertToJson();
            System.out.println(json);
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
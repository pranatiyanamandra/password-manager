package com.py.projects.trello.controllers;

import com.py.projects.trello.dto.LoginRequest;
import com.py.projects.trello.dto.LoginResponse;
//import com.py.projects.trello.service.JwtUserDetailsService;
import com.py.projects.trello.dto.RegisterRequest;
import com.py.projects.trello.dto.StatusResponse;
import com.py.projects.trello.service.JwtUserDetailsService;
import com.py.projects.trello.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
public class AuthenticationController {
    @Autowired
    private InvalidatedTokenCollection invalidatedTokenCollection;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest loginRequest) throws Exception {

        authenticate(loginRequest.getEmailAddress(), loginRequest.getPassword());

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(loginRequest.getEmailAddress());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping(value = "/users/register")
    public StatusResponse registerUser(@RequestBody RegisterRequest registerRequest) {
        jwtUserDetailsService.addUser(registerRequest);
        return new StatusResponse(200, "Registered Successfully");
    }

    @PostMapping(value = "/check")
    public StatusResponse checkUser(@RequestBody RegisterRequest registerRequest) {
        String response = jwtUserDetailsService.checkUser(registerRequest);
        return new StatusResponse(200, response);
    }

    @PostMapping(value = "/users/logout")
    public StatusResponse logout(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        String token = httpServletRequest.getHeader("Authorization").replace("Bearer ","");
        if(jwtTokenUtil.isTokenExpired(token)){
            httpServletResponse.setHeader("Authorization","");
            return new StatusResponse(200,"Logout successful");
        }
        invalidatedTokenCollection.addToken(token);
        httpServletResponse.setHeader("Authorization","");
        return new StatusResponse(200,"Logout successful");

    }

    @PostMapping(value = "/abc")
    public StatusResponse abc(@RequestBody RegisterRequest registerRequest) {
        String response = jwtUserDetailsService.checkUser(registerRequest);
        return new StatusResponse(200, response);
    }
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
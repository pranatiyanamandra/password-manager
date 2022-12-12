package com.py.projects.trello.controllers;

import com.py.projects.trello.dto.ProfileResponse;
import com.py.projects.trello.dto.StatusResponse;
import com.py.projects.trello.service.UserDataService;
import com.py.projects.trello.util.JwtTokenUtil;
import io.jsonwebtoken.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class UserDataController {
    @Autowired
    private UserDataService userDataService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/users/{id}/data")
    public ProfileResponse getUserData(@PathVariable String id) {
        return userDataService.getUserData(id);
    }

    @PutMapping("/users/{id}/data")
    public StatusResponse editUserData(@PathVariable String id, @RequestBody ProfileResponse profileResponse) {
        String response = userDataService.editUserData(id,profileResponse);
        return new StatusResponse(200, response);
    }

    @PostMapping("/users/{id}/passwords")
    public StatusResponse addField(@PathVariable String id, @RequestBody String field){
        String response = userDataService.addField(id,field);
        return new StatusResponse(200, response);
    }

    @GetMapping("/users/{id}/passwords")
    public String getField(@PathVariable String id){
        return userDataService.getFields(id);
    }

    @PutMapping("/users/{id}/passwords")
    public StatusResponse updateFields(@PathVariable String id, @RequestBody String updatedFields){
        userDataService.updateFields(id,updatedFields);
        return new StatusResponse(200,"update successful");

    }

    @DeleteMapping("/users/{id}/passwords/{index}")
    public StatusResponse deleteFields(@PathVariable String id,@PathVariable Integer index,HttpServletRequest httpServletRequest){
        log.info("Integer of index in this why is it unauthorized"+String.valueOf(index));
        String response = userDataService.deleteField(id,index);
        return new StatusResponse(200,response);
    }
}

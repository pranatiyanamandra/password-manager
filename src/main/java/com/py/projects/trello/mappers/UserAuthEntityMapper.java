package com.py.projects.trello.mappers;

import com.py.projects.trello.dto.RegisterRequest;
import com.py.projects.trello.model.StoredPasswords;
import com.py.projects.trello.model.UserAuthEntity;
import com.py.projects.trello.model.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserAuthEntityMapper {
    public UserAuthEntity toUserAuthEntity(RegisterRequest registerRequest){
        return new UserAuthEntity(toUserEntity(registerRequest),registerRequest.getPassword(),new StoredPasswords(""));
    }

    private UserEntity toUserEntity(RegisterRequest registerRequest){
        return new UserEntity(registerRequest.getFirstName(),registerRequest.getLastName(),registerRequest.getEmailAddress());
    }
}

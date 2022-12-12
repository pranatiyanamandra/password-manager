package com.py.projects.trello.mappers;

import com.py.projects.trello.dto.ProfileResponse;
import com.py.projects.trello.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {
    public UserEntity toUserEntity(ProfileResponse profileResponse){
        return new UserEntity(profileResponse.getFirstName(), profileResponse.getFirstName(), profileResponse.getEmailAddress());
    }

    public ProfileResponse toProfileResponse(UserEntity userEntity){
        return new ProfileResponse(userEntity.getFirstName(), userEntity.getFirstName(), userEntity.getEmailAddress());
    }
}

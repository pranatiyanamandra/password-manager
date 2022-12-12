package com.py.projects.trello.service;

import com.py.projects.trello.dto.ProfileResponse;
import com.py.projects.trello.mappers.UserEntityMapper;
import com.py.projects.trello.model.UserAuthEntity;
import com.py.projects.trello.model.UserEntity;
import com.py.projects.trello.repos.UserAuthRepository;
import com.py.projects.trello.repos.UserRepository;
import com.py.projects.trello.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class UserDataService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;



    @Transactional
    public ProfileResponse getUserData(String base64EmailAddress) {
        String emailAddress = new String(Base64.getDecoder().decode(base64EmailAddress));
        return userEntityMapper.toProfileResponse(userRepository.findByEmailAddress(emailAddress).get());
    }
    @Transactional
    public String editUserData(String base64EmailAddress, ProfileResponse profileResponse) {
        String emailAddress = new String(Base64.getDecoder().decode(base64EmailAddress));
        Optional<UserEntity> userEntityOptional = userRepository.findByEmailAddress(emailAddress);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity1 = userEntityOptional.get();
            userEntity1.setFirstName(profileResponse.getFirstName());
            userEntity1.setLastName(profileResponse.getLastName());
            userRepository.save(userEntity1);
            return "Edit successful";
        }
        return "Edit Failed";

    }
    @Transactional
    public String addField(String base64EmailAddress, String field) {
        String emailAddress = new String(Base64.getDecoder().decode(base64EmailAddress));
        String existingFields = getFields(base64EmailAddress);
        int length = existingFields.length();
        if(existingFields.equalsIgnoreCase("[]")){
            existingFields = "["+field+"]";

        }
        else{
            existingFields = existingFields.substring(0,length-1)+","+field+"]";
        }
        UserEntity userEntity = userRepository.findByEmailAddress(emailAddress).get();
        UserAuthEntity userAuthEntity = userAuthRepository.findByUserEntity(userEntity).get();
        userAuthEntity.getStoredPasswords().setSecretRecords(existingFields);
        userAuthRepository.save(userAuthEntity);
        return "Added Field successfully";

    }
    @Transactional
    public void updateFields(String base64EmailAddress,String updatedFields) {
        String emailAddress = new String(Base64.getDecoder().decode(base64EmailAddress));
        UserEntity userEntity = userRepository.findByEmailAddress(emailAddress).get();
        UserAuthEntity userAuthEntity = userAuthRepository.findByUserEntity(userEntity).get();
        userAuthEntity.getStoredPasswords().setSecretRecords(updatedFields);
        userAuthRepository.save(userAuthEntity);
    }



    @Transactional
    public String getFields(String base64EmailAddress) {
        String emailAddress = new String(Base64.getDecoder().decode(base64EmailAddress));
        UserEntity userEntity = userRepository.findByEmailAddress(emailAddress).get();
        UserAuthEntity userAuthEntity = userAuthRepository.findByUserEntity(userEntity).get();
        if (isNull(userAuthEntity.getStoredPasswords())) {
            return "[]";
        }
        return userAuthEntity.getStoredPasswords().getSecretRecords();
    }


    @Transactional
    public String deleteField(String base64EmailAddress, Integer index) {
        String emailAddress = new String(Base64.getDecoder().decode(base64EmailAddress));
        UserEntity userEntity = userRepository.findByEmailAddress(emailAddress).get();
        UserAuthEntity userAuthEntity = userAuthRepository.findByUserEntity(userEntity).get();
        if (isNull(userAuthEntity.getStoredPasswords())) {
            return "No records found to delete.";
        }
        String existingFields = getFields(base64EmailAddress);
        int length = existingFields.split("}").length-1;
        if(index>=length){
            return "No records found to delete.";
        }
        int startIndex = StringUtils.ordinalIndexOf(existingFields,"{",index+1);
        int endIndex = StringUtils.ordinalIndexOf(existingFields,"}",index+1);
        if(length == 1){
            existingFields = "[]";
        }
        else if(index == length-1){
            existingFields = existingFields.substring(0,startIndex-1)+existingFields.substring(endIndex+1);
        }
        else{
            existingFields = existingFields.substring(0,startIndex)+existingFields.substring(endIndex+2);
        }
        userAuthEntity.getStoredPasswords().setSecretRecords(existingFields);
        userAuthRepository.save(userAuthEntity);
        return "Deletion successful";
    }


}

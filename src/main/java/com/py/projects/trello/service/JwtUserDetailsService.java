package com.py.projects.trello.service;

import com.py.projects.trello.dto.RegisterRequest;
import com.py.projects.trello.mappers.UserAuthEntityMapper;
import com.py.projects.trello.model.UserAuthEntity;
import com.py.projects.trello.model.UserEntity;
import com.py.projects.trello.repos.UserAuthRepository;
import com.py.projects.trello.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private UserAuthEntityMapper mapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmailAddress(username);
        if (userEntityOptional.isPresent()) {
                UserEntity userEntity = userEntityOptional.get();
                UserAuthEntity userAuthEntity = userAuthRepository.findByUserEntity(userEntity).get();
                return new User(userEntity.getEmailAddress(), userAuthEntity.getPassword(), new ArrayList<>());
        }
        throw new UsernameNotFoundException("User not found with email address: " + username);
    }

    public void addUser(RegisterRequest registerRequest){
        UserAuthEntity userAuthEntity = mapper.toUserAuthEntity(registerRequest);
        userAuthRepository.save(userAuthEntity);
    }
    @Transactional
    public String checkUser(RegisterRequest registerRequest) {
        if(userRepository.findByEmailAddress(registerRequest.getEmailAddress()).isPresent()){
            return "User Found";
        }
        return "User Not Found";
    }
}
package com.py.projects.trello.repos;

import com.py.projects.trello.model.UserAuthEntity;
import com.py.projects.trello.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserAuthRepository extends CrudRepository<UserAuthEntity,Integer> {
    abstract Optional<UserAuthEntity> findByUserEntity(UserEntity userEntity);
}

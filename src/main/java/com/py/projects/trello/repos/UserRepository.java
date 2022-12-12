package com.py.projects.trello.repos;

import com.py.projects.trello.model.UserAuthEntity;
import com.py.projects.trello.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity,Integer> {
    abstract Optional<UserEntity> findByEmailAddress(String emailAddress);

}

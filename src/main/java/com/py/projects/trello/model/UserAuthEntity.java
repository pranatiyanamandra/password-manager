package com.py.projects.trello.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "UserAuthEntity")
public class UserAuthEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_auth_id")
    private Integer userAuthId;

    @OneToOne
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column(name = "password")
    private String password;

    @OneToOne
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "stored_passwords_id")
    private StoredPasswords storedPasswords;

    public UserAuthEntity() {
    }


    public UserAuthEntity(UserEntity userEntity, String password, StoredPasswords storedPasswords) {
        this.userEntity = userEntity;
        this.password = password;
        this.storedPasswords = storedPasswords;
    }

    public Integer getUserAuthId() {
        return userAuthId;
    }

    public void setUserAuthId(Integer userAuthId) {
        this.userAuthId = userAuthId;
    }

    public StoredPasswords getStoredPasswords() {
        return storedPasswords;
    }

    public void setStoredPasswords(StoredPasswords storedPasswords) {
        this.storedPasswords = storedPasswords;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

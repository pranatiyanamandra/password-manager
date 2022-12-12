package com.py.projects.trello.model;



import javax.persistence.*;

@Entity
public class StoredPasswords {
    @Id
    @GeneratedValue
    @Column(name = "stored_passwords_id")
    private int storedPasswordsId;

    @Column(name = "secret_records")
    @Lob
    private String secretRecords ;

    public StoredPasswords() {
    }

    public StoredPasswords(String secretRecords) {
        this.secretRecords = secretRecords;
    }

    public int getStoredPasswordsId() {
        return storedPasswordsId;
    }

    public void setStoredPasswordsId(int storedPasswordsId) {
        this.storedPasswordsId = storedPasswordsId;
    }

    public String getSecretRecords() {
        return secretRecords;
    }

    public void setSecretRecords(String secretRecords) {
        this.secretRecords = secretRecords;
    }

    public void addField(String secretRecord){
        this.secretRecords+=secretRecord;
    }
}

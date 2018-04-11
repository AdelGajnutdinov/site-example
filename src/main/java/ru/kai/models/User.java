package ru.kai.models;

import java.time.LocalDate;

public class User {
    private Integer id;
    private String name;
    private String passwordHash;
    private LocalDate birthDate;

    public User(String name, String passwordHash) {
        this.name = name;
        this.passwordHash = passwordHash;
    }

    public User(String name, String passwordHash, LocalDate birthDate) {
        this.id = null;
        this.name = name;
        this.passwordHash = passwordHash;
        this.birthDate = birthDate;
    }

    public User(Integer id, String name, String passwordHash, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.passwordHash = passwordHash;
        this.birthDate = birthDate;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof User)) return false;
        User otherUser = (User) obj;
        return name.equals(otherUser.getName())
                && passwordHash.equals(otherUser.getPasswordHash())
                && birthDate.isEqual(otherUser.getBirthDate());
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}

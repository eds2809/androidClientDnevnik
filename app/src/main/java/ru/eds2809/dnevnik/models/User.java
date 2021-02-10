package ru.eds2809.dnevnik.models;


import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class User implements Serializable {
    private long id;
    private String name;
    private String login;
    private String password;
    private UserRole role;

    private ClassRoom classRoom;

    private List<Subject> subjects;

    List<ClassRoom> classRooms;

    public User() {
    }

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString()  {
        return name;
    }
}

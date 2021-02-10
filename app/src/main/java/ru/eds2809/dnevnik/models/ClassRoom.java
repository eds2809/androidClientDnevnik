package ru.eds2809.dnevnik.models;


import java.io.Serializable;

import lombok.Data;

@Data
public class ClassRoom implements Serializable {
    private Long id;
    private String name;
    /*@OneToOne
    private User classroomSupervisor;
    @OneToMany
    @BatchSize(size = 100)
    private List<User> users;*/
}

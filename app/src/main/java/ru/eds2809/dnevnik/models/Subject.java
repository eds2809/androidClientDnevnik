package ru.eds2809.dnevnik.models;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Subject implements Serializable {
    private long id;
    private String name;

    private List<Appraisal> appraisals;
}

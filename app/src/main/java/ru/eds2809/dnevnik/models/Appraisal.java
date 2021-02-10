package ru.eds2809.dnevnik.models;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Appraisal implements Serializable {
    private Long id;
    private Long subjectId;
    private Long userId;
    private Long score;
    private String evaluationDateString;
    private Date updateDate;
}

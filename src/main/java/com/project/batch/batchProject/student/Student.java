package com.project.batch.batchProject.student;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Student {

    @Id
    @GeneratedValue
    private int id;
    private String first_name;
    private String last_name;
    private int age;

}

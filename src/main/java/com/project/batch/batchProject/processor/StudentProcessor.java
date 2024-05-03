package com.project.batch.batchProject.processor;

import com.project.batch.batchProject.student.Student;
import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemProcessor;

public class StudentProcessor implements ItemProcessor<Student, Student> {


    @Override
    public Student process(Student student) throws Exception {

        //all business logic here
        return student;
    }
}

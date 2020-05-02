package com.security.securityservice.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private static final List<Student> students = Arrays.asList(
            new Student(1, "john Smith"),
            new Student(2, "Mike Jenson"),
            new Student(3, "Dennish Riche")
    );


    @GetMapping(value = "/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable("id") int id) {

        Student student = students.stream().filter(stu -> stu.getId() == id).findFirst().
                orElseThrow(() -> new IllegalArgumentException("Student Id " + id + "does not exists"));
        return ResponseEntity.ok(student);

    }
}

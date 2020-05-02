package com.security.securityservice.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/management/api/student")
public class StudentManagementController {


    public static List<Student> students = Arrays.asList(new Student(1, "john Smith"),
            new Student(2, "Mike Jenson"),
            new Student(3, "Dennish Riche"));

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_ADMIN','ROLE_ADMIN_TRAINEE')")
    public List<Student> getAllStudents() {
        return students;
    }

    @DeleteMapping(path = "/{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("studentId") int studentId) {
        System.out.println("studentId = [" + studentId + "]");
    }

    @PutMapping(path = "/{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public Student updateStudent(@PathVariable("studentId") int studentId, @RequestBody Student student) {
        System.out.println("studentId = [" + studentId + "], student = [" + student + "]");
        return student;
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('student:write')")
    public int registerNewStudent(@RequestBody Student student) {
        return student.getId();
    }
}

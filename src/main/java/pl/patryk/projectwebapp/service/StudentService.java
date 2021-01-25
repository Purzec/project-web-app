package pl.patryk.projectwebapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.patryk.projectwebapp.model.Student;

import java.util.Optional;

public interface StudentService {

    Optional<Student> getStudent(Integer studentId);
    Student setStudent(Student student);
    void deleteStudent(Integer studentId);
    Page<Student> getStudents(Pageable pageable);
    Page<Student> searchByNazwisko(String surname, Pageable pageable);

}

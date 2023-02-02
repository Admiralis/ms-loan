package fr.omg.admiralis.msloan.student;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StudentService {
    private final StudentRestRepository studentRestRepository;

    public StudentService(StudentRestRepository studentRestRepository) {
        this.studentRestRepository = studentRestRepository;
    }

    public Student findById(String id) {
        return studentRestRepository.findById(id);
    }

    public Student save(Student student) {
        return studentRestRepository.save(student);
    }

    public void deleteById(String id) {
        studentRestRepository.deleteById(id);
    }

    public Student update(String id, Student student) {
        return studentRestRepository.update(id, student);
    }

    public Student[] findAll() {
        return studentRestRepository.findAll();
    }

    public Student findOrCreateStudent(Student student) {
        if (student.getId() != null) {
            try {
                student = findById(student.getId());
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
            }
        } else if (student.getFirstName() != null && student.getLastName() != null) {
            try {
                student = save(student);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'élève doit avoir un nom et un prénom");
            }
        }
        return student;
    }
}

package fr.omg.admiralis.msloan.student;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class StudentRestRepository {
    String studentUrl;
    private final RestTemplate restTemplate;

    public StudentRestRepository(RestTemplate restTemplate, @Value("${ms.student.url}") String studentUrl) {
        this.restTemplate = restTemplate;
        this.studentUrl = studentUrl;
    }

    public Student save(Student student) {
        return restTemplate.postForObject(studentUrl, student, Student.class);
    }

    public Student findById(String id) {
        return restTemplate.getForObject(studentUrl + "/" + id, Student.class);
    }


    public void deleteById(String id) {
        restTemplate.delete(studentUrl + "/" + id);
    }

    public Student update(String id, Student student) {
        return restTemplate.patchForObject(studentUrl + "/" + id, student, Student.class);
    }

    public Student[] findAll() {
        return restTemplate.getForObject(studentUrl, Student[].class);
    }
}

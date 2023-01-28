package fr.omg.admiralis.msloan.course;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Repository
public class CourseRestRepository {

    private final String courseUrl;

    private final RestTemplate restTemplate;

    public CourseRestRepository(RestTemplate restTemplate, @Value("${ms.course.url}") String courseUrl) {
        this.restTemplate = restTemplate;
        this.courseUrl = courseUrl;
    }

    /**
     * Récupère les formations depuis le microservice course
     * @return la liste des formations
     */
    public List<Course> findAll() {
        Course[] courses = restTemplate.getForObject(courseUrl, Course[].class);
        return courses == null ? Collections.emptyList() : List.of(courses);
    }

    public Course save(Course course) {
        return restTemplate.postForObject(courseUrl, course, Course.class);
    }

    public Course findById(String id) {
        return restTemplate.getForObject(courseUrl + "/" + id, Course.class);
    }

    public void deleteById(String id) {
        restTemplate.delete(courseUrl + "/" + id);
    }
}

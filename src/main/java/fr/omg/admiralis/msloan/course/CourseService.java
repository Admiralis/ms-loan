package fr.omg.admiralis.msloan.course;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRestRepository courseRepository;

    public CourseService(CourseRestRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public Course findById(String id) {
        return courseRepository.findById(id);
    }

    public void deleteById(String id) {
        courseRepository.deleteById(id);
    }
}

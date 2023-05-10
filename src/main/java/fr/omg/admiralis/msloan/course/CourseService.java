package fr.omg.admiralis.msloan.course;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    /**
     * Recherche un cours par son ID.
     * Si le cours n'a pas d'ID, mais a un label, alors il est créé
     * @param course      le cours à rechercher
     * @return le cours trouvé ou créé sous forme d'objet ne disposant que d'un ID.
     */
    public Course findOrCreateCourse(Course course) {
        if (course.getId() != null) {
            try {
                course = findById(course.getId());
            } catch (ResponseStatusException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
            }
        } else if (course.getLabel() != null) {
            try {
                course = save(course);
            } catch (ResponseStatusException e) {
                course = null;
            }
        }
        return course;
    }
}

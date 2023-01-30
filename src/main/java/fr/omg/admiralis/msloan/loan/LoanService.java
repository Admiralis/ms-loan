package fr.omg.admiralis.msloan.loan;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.omg.admiralis.msloan.course.Course;
import fr.omg.admiralis.msloan.course.CourseService;
import fr.omg.admiralis.msloan.course.courseDto.CourseIdDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final CourseService courseService;

    private final ObjectMapper objectMapper;

    public LoanService(LoanRepository loanRepository, CourseService courseService, ObjectMapper objectMapper) {
        this.loanRepository = loanRepository;
        this.courseService = courseService;
        this.objectMapper = objectMapper;
    }

    public List<Loan> findAll() {
        List<Loan> loans = loanRepository.findAll();
        loans.forEach(this::populateCourse);
        return loans;
    }

    public Loan findById(String id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (loan.getCourse() != null) {
            populateCourse(loan);
        }
        return loan;
    }

    /**
     * Peuple le contenu de l'objet course d'un prêt.
     * @param loan
     */
    private void populateCourse(Loan loan) {
        Course course;
        try {
            course = courseService.findById(loan.getCourse().getId());
        } catch (ResponseStatusException e) {
            course = null;
        }
        loan.setCourse(course);
    }

    public Loan save(Loan newLoan) {
        if (newLoan.getCourse() != null) {
            newLoan.setCourse(findOrCreateCourse(newLoan.getCourse()));
            loanRepository.save(newLoan);
            populateCourse(newLoan);
            return newLoan;
        }
        return loanRepository.save(newLoan);
    }

    /**
     * Recherche un cours par son ID.
     * Si le cours n'a pas d'ID, mais a un label, alors il est créé
     *
     * @param course le cours à rechercher
     * @return le cours trouvé ou créé sous forme d'objet ne disposant que d'un ID.
     */
    private Course findOrCreateCourse(Course course) {
        if (course.getId() != null) {
            try {
                course = courseService.findById(course.getId());
            } catch (ResponseStatusException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
            }
        } else if (course.getLabel() != null) {
            try {
                course = courseService.save(course);
            } catch (ResponseStatusException e) {
                course = null;
            }
        }
        return extractCourseId(course);
    }

    /**
     * Convertit un objet Course en objet CourseIdDto, puis en objet Course.
     * L'objectif est de ne conserver que l'ID du cours dans l'objet Loan.
     *
     * @param course le cours à convertir
     * @return le cours converti
     */
    private Course extractCourseId(Course course) {
        CourseIdDto courseId = objectMapper.convertValue(course, CourseIdDto.class);
        return objectMapper.convertValue(courseId, Course.class);
    }

    public Loan replace(String id, Loan updateLoan) {
        Loan loan = findById(id);
        if (loan != null) {
            loan.setStartDate(updateLoan.getStartDate());
            loan.setEndDate(updateLoan.getEndDate());
            loan.setDepositState(updateLoan.getDepositState());
            loan.setLoanType(updateLoan.getLoanType());
            loan.setCourse(findOrCreateCourse(updateLoan.getCourse()));
            loanRepository.save(loan);
        }
        return loan;
    }

    public void deleteById(String id) {
        loanRepository.deleteById(id);
    }
}

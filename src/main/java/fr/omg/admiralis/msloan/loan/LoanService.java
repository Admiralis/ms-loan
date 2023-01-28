package fr.omg.admiralis.msloan.loan;

import fr.omg.admiralis.msloan.course.Course;
import fr.omg.admiralis.msloan.course.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final CourseService courseService;

    public LoanService(LoanRepository loanRepository, CourseService courseService) {
        this.loanRepository = loanRepository;
        this.courseService = courseService;
    }

    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    public Loan findById(String id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (loan.getCourse() != null) {
            populateCourse(loan);
        }
        return loan;
    }

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
        loanRepository.save(newLoan);
        if (newLoan.getCourse() != null) {
            newLoan.setCourse(findOrCreateCourse(newLoan.getCourse()));
        }
        return newLoan;
    }

    private Course findOrCreateCourse(Course course) {
        if (course.getId() != null) {
            try {
                course = courseService.findById(course.getId());
            } catch (ResponseStatusException e) {
                if (course.getLabel() != null) {
                    return courseService.save(course);
                } else {
                    course = null;
                }
            }
        }
        return course;
    }

    public Loan update(String id, Loan updateLoan) {
        Loan loan = findById(id);
        if (loan != null) {
            loan.setStart(updateLoan.getStart());
            loan.setEnd(updateLoan.getEnd());
            loan.setDepositState(updateLoan.getDepositState());
            loan.setLoanType(updateLoan.getLoanType());
            loanRepository.save(loan);
        }
        return loan;
    }

    public void deleteById(String id) {
        loanRepository.deleteById(id);
    }
}

package fr.omg.admiralis.msloan.loan;

import com.fasterxml.jackson.databind.ObjectMapper;
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



    public LoanService(LoanRepository loanRepository, CourseService courseService, ObjectMapper objectMapper) {
        this.loanRepository = loanRepository;
        this.courseService = courseService;
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
            newLoan.setCourse(courseService.findOrCreateCourse(newLoan.getCourse()));
            loanRepository.save(newLoan);
            populateCourse(newLoan);
            return newLoan;
        }
        return loanRepository.save(newLoan);
    }

    public Loan replace(String id, Loan updateLoan) {
        Loan loan = findById(id);
        if (loan != null) {
            loan.setStartDate(updateLoan.getStartDate());
            loan.setEndDate(updateLoan.getEndDate());
            loan.setDepositState(updateLoan.getDepositState());
            loan.setLoanType(updateLoan.getLoanType());
            loan.setCourse(courseService.findOrCreateCourse(updateLoan.getCourse()));
            loanRepository.save(loan);
        }
        return loan;
    }

    public void deleteById(String id) {
        loanRepository.deleteById(id);
    }
}

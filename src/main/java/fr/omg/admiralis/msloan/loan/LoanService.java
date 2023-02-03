package fr.omg.admiralis.msloan.loan;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.omg.admiralis.msloan.computer.Computer;
import fr.omg.admiralis.msloan.computer.ComputerService;
import fr.omg.admiralis.msloan.computer.ComputerStatus;
import fr.omg.admiralis.msloan.course.Course;
import fr.omg.admiralis.msloan.course.CourseService;
import fr.omg.admiralis.msloan.loan.model.Loan;
import fr.omg.admiralis.msloan.loan.model.LoanStatus;
import fr.omg.admiralis.msloan.student.Student;
import fr.omg.admiralis.msloan.student.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final CourseService courseService;
    private final ComputerService computerService;

    private final StudentService studentService;


    public LoanService(LoanRepository loanRepository, CourseService courseService, ComputerService computerService, StudentService studentService) {
        this.loanRepository = loanRepository;
        this.courseService = courseService;
        this.computerService = computerService;
        this.studentService = studentService;
    }

    public List<Loan> findAll() {
        List<Loan> loans = loanRepository.findAll();
        loans.forEach(this::populateCourse);
        loans.forEach(this::populateComputer);
        return loans;
    }

    public Loan findById(String id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (loan.getCourse() != null) {
            populateCourse(loan);
        }
        if (loan.getStudent() != null) {
            populateStudent(loan);
        }
        populateComputer(loan);
        return loan;
    }

    private void populateStudent(Loan loan) {
        Student student;
        try {
            student = studentService.findById(loan.getStudent().getId());
        } catch (ResponseStatusException e) {
            student = null;
        }
        loan.setStudent(student);
    }

    /**
     * Peuple le contenu de l'objet course d'un prêt.
     *
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

    /**
     * Peuple le contenu de l'objet course d'un prêt.
     *
     * @param loan
     */
    private void populateComputer(Loan loan) {
        Computer computer;
        try {
            computer = computerService.findById(loan.getComputer().getId());
            loan.setComputer(computer);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Computer not found");
        }
    }

    public Loan save(Loan newLoan) {
        Course course;
        Student student;
        if (newLoan.getComputer() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le prêt doit contenir un ordinateur");
        } else {
            try {
                Computer computer = computerService.findById(newLoan.getComputer().getId());
                computer.setComputerStatus(ComputerStatus.IN_USE);
                computerService.replace(computer);
            } catch (ResponseStatusException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ordinateur non trouvé");
            }
        }
        if (newLoan.getCourse() != null) {
            course = courseService.findOrCreateCourse(newLoan.getCourse());
            newLoan.setCourse(course);
        }
        if (newLoan.getLoanStatus() == null) {
            newLoan.setLoanStatus(LoanStatus.IN_PROGRESS);
        }

        if (newLoan.getCourse() == null && newLoan.getStudent() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le cours doit contenir un étudiant ou un cours");
        }

        if (newLoan.getStudent() != null) {
            newLoan.setCourse(null);
            student = studentService.findOrCreateStudent(newLoan.getStudent());
            newLoan.setStudent(student);
        }

        if (newLoan.getStartDate() == null) {
            newLoan.setStartDate(LocalDate.now());
        }

        loanRepository.save(newLoan);
        return findById(newLoan.getId());
    }

    public Loan replace(String id, Loan updateLoan) {
        Loan loan = findById(id);
        if (loan != null) {
            loan.setStartDate(updateLoan.getStartDate());
            loan.setEndDate(updateLoan.getEndDate());
            loan.setDepositState(updateLoan.getDepositState());
            loan.setLoanType(updateLoan.getLoanType());
            loan.setCourse(courseService.findOrCreateCourse(updateLoan.getCourse()));
            loan.setComputer(computerService.findById(updateLoan.getComputer().getId()));
            loanRepository.save(loan);
        }
        return loan;
    }

    public Loan update(String id, Loan updateLoan) {
        Loan loan = findById(id);
        if (loan != null) {
            loan.setStartDate(updateLoan.getStartDate());
            if (updateLoan.getEndDate() != null) {
                loan.setEndDate(updateLoan.getEndDate());
            }
            if (updateLoan.getDepositState() != null) {
                loan.setDepositState(updateLoan.getDepositState());
            }
            if (updateLoan.getLoanType() != null) {
                loan.setLoanType(updateLoan.getLoanType());
            }
            if (updateLoan.getCourse() != null) {
                loan.setCourse(courseService.findOrCreateCourse(updateLoan.getCourse()));
            }
            loan.setComputer(computerService.findById(updateLoan.getComputer().getId()));
            loanRepository.save(loan);
        }
        return loan;
    }

    public Loan deleteById(String id) {
        Loan loan = findById(id);
        if (loan != null) {
            Computer computer = computerService.findById(loan.getComputer().getId());
            computer.setComputerStatus(ComputerStatus.AVAILABLE);
            computerService.replace(computer);
            if (loan.getEndDate().isAfter(LocalDate.now())) {
                loan.setLoanStatus(LoanStatus.CANCELED);
            } else {
                loan.setLoanStatus(LoanStatus.FINISHED);
            }
            loan.setEndDate(LocalDate.now());
            loanRepository.save(loan);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Prêt non trouvé");
        }
        return findById(id);
    }
}

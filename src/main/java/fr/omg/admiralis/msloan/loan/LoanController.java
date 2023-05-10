package fr.omg.admiralis.msloan.loan;

import fr.omg.admiralis.msloan.loan.model.Loan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public List<Loan> findAll() {
        return loanService.findAll();
    }

    @GetMapping("{id}")
    public Loan findById(@PathVariable String id) {
        return loanService.findById(id);
    }

    @PostMapping
    public Loan save(@RequestBody Loan newLoan) {
        return loanService.save(newLoan);
    }

    @PutMapping("{id}")
    public Loan update(@PathVariable String id, @RequestBody Loan newLoan) {
        return loanService.replace(id, newLoan);
    }

    @PatchMapping("{id}")
    public Loan patch(@PathVariable String id, @RequestBody Loan newLoan) {
        return loanService.update(id, newLoan);
    }

    @DeleteMapping("{id}")
    public Loan deleteById(@PathVariable String id) {
        return loanService.deleteById(id);
    }

    @GetMapping("/student/{studentId}")
    public List<Loan> findByStudentId(@PathVariable String studentId) {
        return loanService.findByStudentId(studentId);
    }

    @GetMapping("/course/{courseId}")
    public List<Loan> findByCourseId(@PathVariable String courseId) {
        return loanService.findByCourseId(courseId);
    }

    @GetMapping("/computer/{computerId}")
    public List<Loan> findByComputerId(@PathVariable String computerId) {
        return loanService.findByComputerId(computerId);
    }

    @GetMapping("/course/{courseId}/in-progress")
    public Loan findByCourseIdAndLoanStatus(@PathVariable String courseId) {
        return loanService.findByCourseIdAndInProgressStatus(courseId);
    }

    @GetMapping("/computer/{computerId}/in-progress")
    public Loan findByComputerIdAndLoanStatus(@PathVariable String computerId) {
        return loanService.findByComputerIdAndInProgressStatus(computerId);
    }


}

package fr.omg.admiralis.msloan.loan;

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

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable String id) {
        loanService.deleteById(id);
    }
}

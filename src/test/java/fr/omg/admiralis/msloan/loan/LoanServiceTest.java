package fr.omg.admiralis.msloan.loan;

import fr.omg.admiralis.msloan.loan.dto.DepositState;
import fr.omg.admiralis.msloan.loan.dto.LoanType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LoanServiceTest {

    List<Loan> loans = new ArrayList<>();
    LoanService loanService;

    @MockBean
    private LoanRepository loanRepository;

    @BeforeEach
    public void setUp() {
        loanService = new LoanService(loanRepository);
        Loan loan1 = new Loan("1", LocalDate.now(), LocalDate.now(), DepositState.PAID, LoanType.INDIVIDUAL);
        loans.add(loan1);
        Loan loan2 = new Loan("2", LocalDate.now(), LocalDate.now(), DepositState.PAID, LoanType.INDIVIDUAL);
        loans.add(loan2);
        when(loanRepository.findAll()).thenReturn(loans);
        when(loanRepository.findById("1")).thenReturn(java.util.Optional.of(loans.get(0)));
        when(loanRepository.findById("2")).thenReturn(java.util.Optional.of(loans.get(1)));
    }

    @Test
    public void recupererTousLesEmprunts() {
        List<Loan> loans = loanService.findAll();
        assert loans.size() == 2;
    }

    @Test
    public void recupererUnEmprunt() {
        Loan loan = loanService.findById("1");
        assert loan.getId().equals("1");
    }

    @Test
    public void recupererUnEmpruntInexistant() {
        assertThrows(ResponseStatusException.class, () -> loanService.findById("3"));
    }

}

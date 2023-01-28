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

    Loan newLoan;

    @MockBean
    private LoanRepository loanRepository;

    @BeforeEach
    public void setUp() {
        setUpValues();
        setUpLoanRepositoryMock();
    }

    private void setUpLoanRepositoryMock() {
        loanService = new LoanService(loanRepository);
        when(loanRepository.findAll()).thenReturn(loans);
        when(loanRepository.findById("1")).thenReturn(java.util.Optional.of(loans.get(0)));
        when(loanRepository.findById("2")).thenReturn(java.util.Optional.of(loans.get(1)));
        when(loanRepository.save(newLoan)).thenAnswer(i -> i.getArguments()[0]);
    }

    private void setUpValues() {
        Loan loan1 = new Loan("1", LocalDate.now(), LocalDate.now(), DepositState.PAID, LoanType.INDIVIDUAL);
        Loan loan2 = new Loan("2", LocalDate.now(), LocalDate.now(), DepositState.PAID, LoanType.INDIVIDUAL);
        loans.add(loan1);
        loans.add(loan2);
        newLoan = new Loan("3", LocalDate.now(), LocalDate.now(), DepositState.UNNECESSARY, LoanType.COLLECTIVE);
    }

    @Test
    public void lorsquilYADeuxEmpreintsLaListeEstDeDeux() {
        List<Loan> loans = loanService.findAll();
        assert loans.size() == 2;
    }

    @Test
    public void LIDEstCorrectLorsqueJeRecupereUnEmprunt() {
        Loan loan = loanService.findById("1");
        assert loan.getId().equals("1");
    }

    @Test
    public void ilYAUneErreurLorsqueLEmpruntNexistePas() {
        assertThrows(ResponseStatusException.class, () -> loanService.findById("3"));
    }

    @Test
    public void LEmpruntEstCreeCorrectement() {
        Loan loan = loanService.save(newLoan);
        assert loan.getId().equals("3");
    }

    @Test
    public void LEmpruntEstMisAJourCorrectement() {
        Loan loan = loanService.update("1", newLoan);
        assert loan.getId().equals("1");
        assert loan.getDepositState().equals(DepositState.UNNECESSARY);
        assert loan.getLoanType().equals(LoanType.COLLECTIVE);
    }

    @Test
    public void uneErreurEstLanceeLorsqueLEmpruntNexistePas() {
        assertThrows(ResponseStatusException.class, () -> loanService.update("3", newLoan));
    }

}

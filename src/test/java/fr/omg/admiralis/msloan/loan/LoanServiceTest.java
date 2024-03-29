package fr.omg.admiralis.msloan.loan;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.omg.admiralis.msloan.computer.Computer;
import fr.omg.admiralis.msloan.computer.ComputerService;
import fr.omg.admiralis.msloan.course.CourseService;
import fr.omg.admiralis.msloan.loan.model.DepositState;
import fr.omg.admiralis.msloan.loan.model.LoanStatus;
import fr.omg.admiralis.msloan.loan.model.LoanType;
import fr.omg.admiralis.msloan.loan.model.Loan;
import fr.omg.admiralis.msloan.student.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LoanServiceTest {

    List<Loan> loans = new ArrayList<>();
    LoanService loanService;

    Loan newLoan;

    @MockBean
    private LoanRepository loanRepository;

    @MockBean
    private CourseService courseService;

    @MockBean
    private ComputerService computerService;

    @MockBean
    private StudentService studentService;

    @Mock
    private Computer computer;


    @BeforeEach
    public void setUp() {
        setUpValues();
        setUpLoanRepositoryMock();
    }

    private void setUpLoanRepositoryMock() {
        loanService = new LoanService(loanRepository, courseService, computerService, studentService);
        when(loanRepository.findAll()).thenReturn(loans);
        when(loanRepository.findById("1")).thenReturn(java.util.Optional.of(loans.get(0)));
        when(loanRepository.findById("2")).thenReturn(java.util.Optional.of(loans.get(1)));
        when(loanRepository.save(newLoan)).thenAnswer(i -> i.getArguments()[0]);
        doNothing().when(loanRepository).deleteById(anyString());
    }

    private void setUpValues() {
        Loan loan1 = new Loan("1", LocalDate.now(), LocalDate.now(), DepositState.PAID, LoanType.INDIVIDUAL, LoanStatus.IN_PROGRESS, null, computer, null);
        Loan loan2 = new Loan("2", LocalDate.now(), LocalDate.now(), DepositState.PAID, LoanType.INDIVIDUAL,LoanStatus.IN_PROGRESS, null, computer, null);
        loans.add(loan1);
        loans.add(loan2);
        newLoan = new Loan("3", LocalDate.now(), LocalDate.now(), DepositState.UNNECESSARY, LoanType.COLLECTIVE, LoanStatus.IN_PROGRESS,null, computer, null);
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
        Loan loan = loanService.replace("1", newLoan);
        assert loan.getId().equals("1");
        assert loan.getDepositState().equals(DepositState.UNNECESSARY);
        assert loan.getLoanType().equals(LoanType.COLLECTIVE);
    }

    @Test
    public void uneErreurEstLanceeLorsqueLEmpruntNexistePas() {
        assertThrows(ResponseStatusException.class, () -> loanService.replace("3", newLoan));
    }

    @Test
    public void LEmpruntEstSupprimeCorrectement() {
        loanService = spy(loanService);
        loanService.deleteById("1");
        verify(loanService, times(1)).deleteById("1");
    }

}

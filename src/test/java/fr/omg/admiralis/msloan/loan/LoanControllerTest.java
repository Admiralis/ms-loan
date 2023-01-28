package fr.omg.admiralis.msloan.loan;

import fr.omg.admiralis.msloan.loan.dto.DepositState;
import fr.omg.admiralis.msloan.loan.dto.LoanType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class LoanControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LoanService loanService;

    List<Loan> loans = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        loans.add(new Loan("1", LocalDate.now(), LocalDate.now(), DepositState.PAID, LoanType.INDIVIDUAL));
        loans.add(new Loan("1", LocalDate.now(), LocalDate.now(), DepositState.UNNECESSARY, LoanType.COLLECTIVE));
        when(loanService.findAll()).thenReturn(loans);
        when(loanService.findById("1")).thenReturn(loans.get(0));
        when(loanService.findById("2")).thenReturn(loans.get(1));
    }

    @Test
    public void leNombreDePretsRecuperesEstCorrect() throws Exception {
        mockMvc.perform(get("/api/loans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(loans.size()))).andDo(print());
    }

    @Test
    public void jeRecupereLeBonPret() throws Exception {
        mockMvc.perform(get("/api/loans/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1")).andDo(print());
    }
}

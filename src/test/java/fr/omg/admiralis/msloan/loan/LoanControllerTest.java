package fr.omg.admiralis.msloan.loan;

import fr.omg.admiralis.msloan.loan.dto.DepositState;
import fr.omg.admiralis.msloan.loan.dto.LoanType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        Loan loan1 = new Loan("1", LocalDate.now(), LocalDate.now(), DepositState.PAID, LoanType.INDIVIDUAL, null);
        loans.add(loan1);
        Loan loan2 = new Loan("2", LocalDate.now(), LocalDate.now(), DepositState.UNNECESSARY, LoanType.COLLECTIVE, null);
        loans.add(loan2);
        when(loanService.findAll()).thenReturn(loans);
        when(loanService.findById("1")).thenReturn(loans.get(0));
        when(loanService.findById("2")).thenReturn(loans.get(1));
        when(loanService.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    public void leNombreDePretsRecuperesEstCorrect() throws Exception {
        mockMvc.perform(get("/api/loans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(loans.size()))).andDo(print());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2"})
    public void jeRecupereLeBonPret(String id) throws Exception {
        mockMvc.perform(get("/api/loans/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id)).andDo(print());
    }

    @Test
    public void siJajouteUnPretIlEstBienAjoute() throws Exception {
        mockMvc.perform(post("/api/loans")
                .contentType("application/json")
                .content("{\"startDate\":\"2020-01-01\",\"endDate\":\"2020-01-01\",\"depositState\":\"PAID\",\"loanType\":\"INDIVIDUAL\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void siJeModifieUnPretIlEstBienMisAJour() throws Exception {
        mockMvc.perform(put("/api/loans/1")
                .contentType("application/json")
                .content("{\"startDate\":\"2020-01-01\",\"endDate\":\"2020-01-01\",\"depositState\":\"UNNECESSARY\",\"loanType\":\"COLLECTIVE\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void siJeSupprimeUnPretIlEstBienSupprime() throws Exception {
        mockMvc.perform(delete("/api/loans/1"))
                .andExpect(status().isOk());
    }
}

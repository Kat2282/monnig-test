package edu.tcu.cs.monnigtest.loans;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.monnigtest.system.StatusCode;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.mockito.Mockito;

@SpringBootTest
@AutoConfigureMockMvc
class LoansControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LoansService loansService;

    @Autowired
    ObjectMapper objectMapper;

    List<Loans> loans;

    @Value("/api/v1")
    String baseUrl;


    @BeforeEach
    void setUp() {
        this.loans = new ArrayList<>();

        Loans l1 = new Loans();
        l1.setId("00001");
        l1.setLoaneeName("loan 1");
        l1.setInstitution("TCU");
        l1.setEmail("johndoe@example.com");
        l1.setPhone("+1234567890");
        l1.setAddress("123 Main Street, City, Country");
        l1.setLoanStartDate("2024-04-01");
        l1.setLoanEndDate("2025-04-01");
        l1.setMonnigIds("250, 251, 252, 253");
        l1.setTrackingNumber(101);
        l1.setNotes("Customer requested a payment extension");
        l1.setExtraFile("loan-agreement-1.pdf");
        l1.setArchived(false);

        Loans l2 = new Loans();
        l2.setId("00002");
        l2.setLoaneeName("loan 2");
        l2.setInstitution("TCU 2");
        l2.setEmail("johndoe@example.com 2");
        l2.setPhone("+1234567890 2");
        l2.setAddress("123 Main Street, City, Country 2");
        l2.setLoanStartDate("2024-04-01 2");
        l2.setLoanEndDate("2025-04-01 2");
        l2.setMonnigIds("250, 251, 252, 253 2");
        l2.setTrackingNumber(102);
        l2.setNotes("Notes 2");
        l2.setExtraFile("loan-agreement-2.pdf");
        l2.setArchived(false);

        Loans l3 = new Loans();
        l3.setId("00003");
        l3.setLoaneeName("loan 3");
        l3.setInstitution("TCU 3");
        l3.setEmail("johndoe@example.com 3");
        l3.setPhone("+1234567890 3");
        l3.setAddress("123 Main Street, City, Country 3");
        l3.setLoanStartDate("2024-04-01 3");
        l3.setLoanEndDate("2025-04-01 3");
        l3.setMonnigIds("250, 251, 252, 253 3");
        l3.setTrackingNumber(103);
        l3.setNotes("Notes 3");
        l3.setExtraFile("loan-agreement-3.pdf");
        l3.setArchived(false);

        //DON'T FORGET TO ADD THEM
        this.loans.add(l1);
        this.loans.add(l2);
        this.loans.add(l3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindLoansByIdSuccess() throws Exception {
        // Given
        given(this.loansService.findById("00001")).willReturn(this.loans.get(0));

        // When and Then
        this.mockMvc.perform(get("/api/v1/loans/00001").accept(MediaType.APPLICATION_JSON))
                //client is accepting JSON as response
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value("00001"))
                .andExpect(jsonPath("$.data.loaneeName").value("loan 1"));
    }
    @Test
    void testFindLoansByIdNotFound() throws Exception {
        // Given
        given(this.loansService.findById("00001")).willThrow(new LoansNotFoundException("00001"));

        // When and Then
        this.mockMvc.perform(get("/api/v1/loans/00001").accept(MediaType.APPLICATION_JSON))
                //client is accepting JSON as response
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find loan with id 00001 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testFindAllLoansSuccess() throws Exception {
        // Given
        given(this.loansService.findAll()).willReturn(this.loans);

        // When and then
        this.mockMvc.perform(get("/api/v1/loans").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(this.loans.size())))
                .andExpect(jsonPath("$.data[0].id").value("00001"))
                .andExpect(jsonPath("$.data[0].loaneeName").value("loan 1"))
                .andExpect(jsonPath("$.data[1].id").value("00002"))
                .andExpect(jsonPath("$.data[1].loaneeName").value("loan 2"));
    }

    @Test
    void testAddLoanSuccess() throws Exception {
        // Given
        Loans loan = new Loans();
        loan.setId("11111");
        loan.setLoaneeName("new loanee 1");
        loan.setInstitution("new institution 1");
        loan.setEmail("new email 1");
        loan.setPhone("new phone 1");
        loan.setAddress("new address 1");
        loan.setLoanStartDate("new start date 1");
        loan.setLoanEndDate("new end date 1");
        loan.setMonnigIds("new monnig ids 1");
        loan.setTrackingNumber(11111);
        loan.setNotes("new notes 1");
        loan.setExtraFile("new file 1");
        loan.setArchived(false);

        // Mock the service's save method to return the loan object
        given(loansService.save(Mockito.any(Loans.class))).willReturn(loan);

        // Convert the loan object to JSON string
        String jsonRequest = objectMapper.writeValueAsString(loan);

        // When sending a POST request to add a loan
        mockMvc.perform(post(baseUrl + "/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .accept(MediaType.APPLICATION_JSON))
                // Then expect the following response
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.id").value(loan.getId()))
                .andExpect(jsonPath("$.data.loaneeName").value(loan.getLoaneeName()))
                .andExpect(jsonPath("$.data.institution").value(loan.getInstitution()))
                .andExpect(jsonPath("$.data.email").value(loan.getEmail()))
                .andExpect(jsonPath("$.data.phone").value(loan.getPhone()))
                .andExpect(jsonPath("$.data.address").value(loan.getAddress()))
                .andExpect(jsonPath("$.data.loanStartDate").value(loan.getLoanStartDate()))
                .andExpect(jsonPath("$.data.loanEndDate").value(loan.getLoanEndDate()))
                .andExpect(jsonPath("$.data.monnigIds").value(loan.getMonnigIds()))
                .andExpect(jsonPath("$.data.trackingNumber").value(loan.getTrackingNumber()))
                .andExpect(jsonPath("$.data.notes").value(loan.getNotes()))
                .andExpect(jsonPath("$.data.extraFile").value(loan.getExtraFile()))
                .andExpect(jsonPath("$.data.archived").value(loan.isArchived()));
    }

    @Test
    void testUpdateLoanSuccess() throws Exception {
        // Given
        Loans oldLoans = new Loans();
        oldLoans.setId("00001");
        oldLoans.setLoaneeName("loanee 2 service");
        oldLoans.setInstitution("institution 2 service");
        oldLoans.setEmail("email 2 service");
        oldLoans.setPhone("phone 2 service");
        oldLoans.setAddress("address 2 service");
        oldLoans.setLoanStartDate("start date 2 service");
        oldLoans.setLoanEndDate("end date 2 service");
        oldLoans.setMonnigIds("monnig ids 2 service");
        oldLoans.setTrackingNumber(11111);
        oldLoans.setNotes("Notes 2 service");
        oldLoans.setExtraFile("extra file 2 service");
        oldLoans.setArchived(false);

        String json = this.objectMapper.writeValueAsString(oldLoans);

        Loans update = new Loans();
        update.setId("00001");
        update.setLoaneeName("new loanee");
        update.setInstitution("new Instituion");
        update.setEmail("new email");
        update.setPhone("new phone");
        update.setAddress("new address");
        update.setLoanStartDate("new start date");
        update.setLoanEndDate("new end date");
        update.setMonnigIds("new monnig ids");
        update.setTrackingNumber(11112);
        update.setNotes("new notes");
        update.setExtraFile("new file");
        update.setArchived(false);

        given(this.loansService.update(eq("00001"), Mockito.any(Loans.class))).willReturn(update);

        // When and Then
        this.mockMvc.perform(put("/api/v1/loans/00001").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update Success"))
                .andExpect(jsonPath("$.data.id").value("00001"))
                .andExpect(jsonPath("$.data.loaneeName").value(update.getLoaneeName()))
                .andExpect(jsonPath("$.data.institution").value(update.getInstitution()))
                .andExpect(jsonPath("$.data.email").value(update.getEmail()));
    }
    @Test
    void testUpdateLoanErrorWithNonExistentId() throws Exception {
        // Given
        Loans updatedLoan = new Loans();
        updatedLoan.setId("00001");
        updatedLoan.setLoaneeName("New Loanee Name");
        updatedLoan.setInstitution("New Institution");
        updatedLoan.setEmail("newemail@example.com");
        updatedLoan.setPhone("+1234567890");
        updatedLoan.setAddress("New Address");
        updatedLoan.setLoanStartDate("2024-05-01");
        updatedLoan.setLoanEndDate("2025-05-01");
        updatedLoan.setMonnigIds("254, 255");
        updatedLoan.setTrackingNumber(103);
        updatedLoan.setNotes("Updated Notes");
        updatedLoan.setExtraFile("new-file.pdf");
        updatedLoan.setArchived(false);

        String json = this.objectMapper.writeValueAsString(updatedLoan);

        given(this.loansService.update(eq("00001"), Mockito.any(Loans.class))).willThrow(new LoansNotFoundException("00001"));

        // When and Then
        this.mockMvc.perform(put("/api/v1/loans/00001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find loan with id 00001 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testArchiveLoanSuccess() throws Exception {
        // Given
        Loans oldLoans = new Loans();
        oldLoans.setId("00001");
        oldLoans.setLoaneeName("loanee 2 service");
        oldLoans.setInstitution("institution 2 service");
        oldLoans.setEmail("email 2 service");
        oldLoans.setPhone("phone 2 service");
        oldLoans.setAddress("address 2 service");
        oldLoans.setLoanStartDate("start date 2 service");
        oldLoans.setLoanEndDate("end date 2 service");
        oldLoans.setMonnigIds("monnig ids 2 service");
        oldLoans.setTrackingNumber(11111);
        oldLoans.setNotes("Notes 2 service");
        oldLoans.setExtraFile("extra file 2 service");
        oldLoans.setArchived(false);

        Loans archivedLoans = new Loans();
        archivedLoans.setId("00001");
        archivedLoans.setLoaneeName("loanee 2 service");
        archivedLoans.setInstitution("institution 2 service");
        archivedLoans.setEmail("email 2 service");
        archivedLoans.setPhone("phone 2 service");
        archivedLoans.setAddress("address 2 service");
        archivedLoans.setLoanStartDate("start date 2 service");
        archivedLoans.setLoanEndDate("end date 2 service");
        archivedLoans.setMonnigIds("monnig ids 2 service");
        archivedLoans.setTrackingNumber(11111);
        archivedLoans.setNotes("Notes 2 service");
        archivedLoans.setExtraFile("extra file 2 service");
        archivedLoans.setArchived(true);

        given(this.loansService.archive(eq("00001"))).willReturn(archivedLoans);
        // When and Then
        this.mockMvc.perform(put("/api/v1/loans/{loanId}/archive", "00001")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Archive Success"))
                .andExpect(jsonPath("$.data.id").value("00001"))
                .andExpect(jsonPath("$.data.archived").value(true));
    }
    @Test
    void testArchiveLoansErrorWithNonExistentId() throws Exception {
        // Given
        given(this.loansService.archive("fakeId")).willThrow(new LoansNotFoundException("fakeId"));

        // When and Then
        this.mockMvc.perform(put("/api/v1/loans/{loanId}/archive", "fakeId")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find loan with id fakeId :("))
                .andExpect(jsonPath("$.data").doesNotExist());

    }
}
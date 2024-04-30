package edu.tcu.cs.monnigtest.loans;

import edu.tcu.cs.monnigtest.loans.utils.IdWorker;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoansServiceTest {

    @Mock
    LoansRepository loansRepository;

    @Mock
    IdWorker idWorker;

    @InjectMocks
    LoansService loansService;

    List<Loans> loans;


    @BeforeEach
    void setUp() {
        this.loans = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {
        // Given. arrange inputs and targets. prepares some fake data, define behavior of the mock.
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
        l1.setTrackingNumber(12345);
        l1.setNotes("Customer requested a payment extension");
        l1.setExtraFile("loan-agreement.pdf");
        l1.setArchived(false);

        given(loansRepository.findById("00001")).willReturn(Optional.of(l1)); //defines behavior of the mock object

        //When. where you call the method to be tested. act on the target behavior. when steps should cover the method to be tested
        Loans returnedLoans = loansService.findById("00001");

        //Then. assert expected outcomes.
        assertThat(returnedLoans.getId()).isEqualTo(l1.getId());
        assertThat(returnedLoans.getLoaneeName()).isEqualTo(l1.getLoaneeName());
        assertThat(returnedLoans.getInstitution()).isEqualTo(l1.getInstitution());
        assertThat(returnedLoans.getEmail()).isEqualTo(l1.getEmail());
        assertThat(returnedLoans.getPhone()).isEqualTo(l1.getPhone());
        assertThat(returnedLoans.getAddress()).isEqualTo(l1.getAddress());
        assertThat(returnedLoans.getLoanStartDate()).isEqualTo(l1.getLoanStartDate());
        assertThat(returnedLoans.getLoanEndDate()).isEqualTo(l1.getLoanEndDate());
        assertThat(returnedLoans.getMonnigIds()).isEqualTo(l1.getMonnigIds());
        assertThat(returnedLoans.getTrackingNumber()).isEqualTo(l1.getTrackingNumber());
        assertThat(returnedLoans.getNotes()).isEqualTo(l1.getNotes());
        assertThat(returnedLoans.getExtraFile()).isEqualTo(l1.getExtraFile());
        assertThat(returnedLoans.isArchived()).isEqualTo(l1.isArchived());

        verify(loansRepository, times(1)).findById("00001");
    }
    @Test
    void testFindByIdNotFound() {
        // Given
        given(loansRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> {
            Loans returnedLoans = loansService.findById("00001");
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(LoansNotFoundException.class)
                .hasMessage("Could not find loan with id 00001 :(");
        verify(loansRepository, times(1)).findById("00001");
    }

    @Test
    void testFindAllSuccess() {
        // Given
        given(loansRepository.findAll()).willReturn(this.loans);

        // When
        List<Loans> actualLoans = this.loansService.findAll();

        // Then
        assertThat(actualLoans.size()).isEqualTo(this.loans.size());
        verify(this.loansRepository, times(1)).findAll();
    }

    @Test
    void testSaveSuccess() {
        // Given
        Loans newLoans = new Loans();
        newLoans.setLoaneeName("new loanee 1 service");
        newLoans.setInstitution("new institution 1 service");
        newLoans.setEmail("new email 1 service");
        newLoans.setPhone("new phone 1 service");
        newLoans.setAddress("new address 1 service");
        newLoans.setLoanStartDate("new start date 1 service");
        newLoans.setLoanEndDate("new end date 1 service");
        newLoans.setMonnigIds("new monnig ids 1 service");
        newLoans.setTrackingNumber(11111);
        newLoans.setNotes("new Notes 1 service");
        newLoans.setExtraFile("new extra file 1 service");
        newLoans.setArchived(false);

        given(this.idWorker.nextId()).willReturn(123456L);
        given(this.loansRepository.save(newLoans)).willReturn(newLoans);

        // When
        Loans savedLoans = this.loansService.save(newLoans);

        // Then
//        assertThat(savedLoans.getId()).isNull();
        assertThat(savedLoans.getId()).isEqualTo("123456");
        assertThat(savedLoans.getInstitution()).isEqualTo(newLoans.getInstitution());
        assertThat(savedLoans.getEmail()).isEqualTo(newLoans.getEmail());
        assertThat(savedLoans.getPhone()).isEqualTo(newLoans.getPhone());
        assertThat(savedLoans.getAddress()).isEqualTo(newLoans.getAddress());
        assertThat(savedLoans.getLoanStartDate()).isEqualTo(newLoans.getLoanStartDate());
        assertThat(savedLoans.getLoanEndDate()).isEqualTo(newLoans.getLoanEndDate());
        assertThat(savedLoans.getMonnigIds()).isEqualTo(newLoans.getMonnigIds());
        assertThat(savedLoans.getTrackingNumber()).isEqualTo(newLoans.getTrackingNumber());
        assertThat(savedLoans.getNotes()).isEqualTo(newLoans.getNotes());
        assertThat(savedLoans.getExtraFile()).isEqualTo(newLoans.getExtraFile());
        assertThat(savedLoans.isArchived()).isEqualTo(newLoans.isArchived());
        verify(loansRepository, times(1)).save(newLoans);
    }

    @Test
    void testUpdateSuccess() {
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

        given(this.loansRepository.findById(oldLoans.getId())).willReturn(Optional.of(oldLoans));
        given(this.loansRepository.save(oldLoans)).willReturn(oldLoans);

        // When
        Loans updatedLoans = loansService.update("00001", update);

        // Then
        assertThat(updatedLoans.getId()).isEqualTo("00001");
        assertThat(updatedLoans.getLoaneeName()).isEqualTo(update.getLoaneeName());
        verify(this.loansRepository, times(1)).findById("00001");
        verify(this.loansRepository, times(1)).save(oldLoans);
    }
    @Test
    void testUpdateNotFound() {
        // Given
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

        given(this.loansRepository.findById("00001")).willReturn(Optional.empty());

        // When
        assertThrows(ObjectNotFoundException.class, () -> {
            this.loansService.update("00001", update);
        });

        // Then
        verify(this.loansRepository, times(1)).findById("00001");
    }

    @Test
    void testArchiveSuccess() {
        // Given
        Loans loanToArchive = new Loans();
        loanToArchive.setId("00001");
        loanToArchive.setLoaneeName("new loanee");
        loanToArchive.setArchived(false);

        given(this.loansRepository.findById("00001")).willReturn(Optional.of(loanToArchive));
        given(this.loansRepository.save(loanToArchive)).willReturn(loanToArchive);

        // When
        Loans archivedLoan = loansService.archive("00001");

        // Then
        assertThat(archivedLoan.getId()).isEqualTo("00001");
        assertThat(archivedLoan.isArchived()).isTrue();
        verify(this.loansRepository, times(1)).findById("00001");
        verify(this.loansRepository, times(1)).save(loanToArchive);
    }
    @Test
    void testArchiveNotFound() {
        // Given
         given(this.loansRepository.findById("00001")).willReturn(Optional.empty());

        // When
        assertThrows(LoansNotFoundException.class, () -> {
            this.loansService.archive("00001");
        });

        // Then
        verify(this.loansRepository, times(1)).findById("00001");
    }

}
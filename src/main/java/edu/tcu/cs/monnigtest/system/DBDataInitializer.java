package edu.tcu.cs.monnigtest.system;

import edu.tcu.cs.monnigtest.loans.Loans;
import edu.tcu.cs.monnigtest.loans.LoansRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBDataInitializer implements CommandLineRunner {


    private final LoansRepository loansRepository;
    //Could put one here for meteorites

    public DBDataInitializer(LoansRepository loansRepository) {
        this.loansRepository = loansRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        //gets called when spring boot is started

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
        l3.setArchived(true);

        loansRepository.save(l1);
        loansRepository.save(l2);
        loansRepository.save(l3);

    }

}

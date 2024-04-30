package edu.tcu.cs.monnigtest.loans;

import edu.tcu.cs.monnigtest.loans.utils.IdWorker;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LoansService {

    private final LoansRepository loansRepository;
    private final IdWorker idWorker;

    public LoansService(LoansRepository loansRepository, IdWorker idWorker) {
        this.loansRepository = loansRepository;
        this.idWorker = idWorker;
    }

    public Loans findById(String loansId) {
        return this.loansRepository.findById(loansId)
                .orElseThrow(() -> new LoansNotFoundException(loansId));
    }

    public List<Loans> findAll() {
        return this.loansRepository.findAll();
    }

    //save (for adding a loan)
    public Loans save(Loans newloans) {
        newloans.setId(idWorker.nextId() + "");
        return this.loansRepository.save(newloans);
    }

    public Loans update(String loansId, Loans update) {
        return this.loansRepository.findById(loansId)
                .map(oldLoans -> {
                    oldLoans.setLoaneeName(update.getLoaneeName());
                    oldLoans.setInstitution(update.getInstitution());
                    oldLoans.setEmail(update.getEmail());
                    oldLoans.setPhone(update.getPhone());
                    oldLoans.setAddress(update.getAddress());
                    oldLoans.setLoanStartDate(update.getLoanStartDate());
                    oldLoans.setLoanEndDate(update.getLoanEndDate());
                    oldLoans.setMonnigIds(update.getMonnigIds());
                    oldLoans.setTrackingNumber(update.getTrackingNumber());
                    oldLoans.setNotes(update.getNotes());
                    oldLoans.setExtraFile(update.getExtraFile());
                    oldLoans.setArchived(update.isArchived());
                    return this.loansRepository.save(oldLoans);
                })
                .orElseThrow(() -> new ObjectNotFoundException(Optional.of("loans"), loansId));
    }

    public Loans archive(String loansId) {
        Loans loanToArchive = findById(loansId);
        loanToArchive.setArchived(true);
        return this.loansRepository.save(loanToArchive);
    }

}

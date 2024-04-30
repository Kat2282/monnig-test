package edu.tcu.cs.monnigtest.loans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoansRepository extends JpaRepository<Loans, String> {
    //if the id becomes a string instead of integer, then change it to String
}

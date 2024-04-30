package edu.tcu.cs.monnigtest.loans;

public class LoansNotFoundException extends RuntimeException {

    public LoansNotFoundException(String id) {
        super("Could not find loan with id " + id + " :(");
    }

}

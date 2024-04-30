package edu.tcu.cs.monnigtest.system.Exception;

import edu.tcu.cs.monnigtest.loans.LoansNotFoundException;
import edu.tcu.cs.monnigtest.system.Result;
import edu.tcu.cs.monnigtest.system.StatusCode;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(LoansNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result handleLoansNotFoundException(LoansNotFoundException ex) {
        return new Result(false, StatusCode.NOT_FOUND, ex.getMessage());
    }

}

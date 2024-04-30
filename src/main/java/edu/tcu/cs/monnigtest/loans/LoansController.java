package edu.tcu.cs.monnigtest.loans;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import edu.tcu.cs.monnigtest.system.StatusCode;
import edu.tcu.cs.monnigtest.system.Result;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/loans")
public class LoansController {

    private final LoansService loansService;

    public LoansController(LoansService loansService) {
        this.loansService = loansService;
    }


    @GetMapping("{loansId}")
    public Result findLoansById(@PathVariable String loansId) {
       Loans foundLoans = this.loansService.findById(loansId);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", foundLoans);
        //behind the scenes, before springmvc returns the result,
        //it will automatically serialize the result into a json string and send that to the client
    }

    @GetMapping
    public Result findAllLoans() {
        List<Loans> foundLoans = this.loansService.findAll();
        return new Result(true, StatusCode.SUCCESS, "Find All Success", foundLoans);
        //don't need to convert the dto thing because there's no ownership
    }

    @PostMapping
    public Result addLoans(@Valid @RequestBody Loans loans) {
        Loans savedLoans = this.loansService.save(loans);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedLoans);
    }

    @PutMapping("/{loanId}")
    public Result updateLoans(@PathVariable String loanId, @Valid @RequestBody Loans loans) {
        Loans updatedLoans = this.loansService.update(loanId, loans);
        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedLoans);
    }

    @PutMapping("/{loanId}/archive")
    public Result archiveLoans(@PathVariable String loanId) {
        Loans archivedLoans = this.loansService.archive(loanId);
        return new Result(true, StatusCode.SUCCESS, "Archive Success", archivedLoans);
    }

    //add an un-archive?

}

package akash_national_bank.controller;

import akash_national_bank.account.BankAccount;
import akash_national_bank.service.BankAccountService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import akash_national_bank.dto.BankAccountResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

@RestController
@Validated
@RequestMapping("/api/accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

  @PostMapping
 public BankAccountResponse createAccount(
        @Valid @RequestBody BankAccount bankAccount){
    return bankAccountService.saveBankAccount(bankAccount);
}

    @PostMapping("/{accountId}/deposit")
    public BankAccountResponse deposit(
            @PathVariable Long accountId,
            @RequestParam @Positive(message = "Deposit amount must be greater than 0") double amount){

        return bankAccountService.deposit(accountId, amount);
    }

    @PostMapping("/{accountId}/withdraw")
    public BankAccountResponse withdraw(
            @PathVariable Long accountId,
            @RequestParam @Positive(message = "Withdrawal amount must be greater than 0") double amount) {

        return bankAccountService.withdraw(accountId, amount);
    }
    @GetMapping
public List<BankAccountResponse> getAllAccounts() {
    return bankAccountService.getAllAccounts();
}
}
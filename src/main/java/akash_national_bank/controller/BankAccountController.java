package akash_national_bank.controller;

import akash_national_bank.account.BankAccount;
import akash_national_bank.service.BankAccountService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import akash_national_bank.dto.BankAccountResponse;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping
    public BankAccount createAccount(@RequestBody BankAccount bankAccount) {
        return bankAccountService.saveBankAccount(bankAccount);
    }

    @PostMapping("/{accountId}/deposit")
    public BankAccountResponse deposit(
            @PathVariable Long accountId,
            @RequestParam double amount) {

        return bankAccountService.deposit(accountId, amount);
    }

    @PostMapping("/{accountId}/withdraw")
    public BankAccountResponse withdraw(
            @PathVariable Long accountId,
            @RequestParam double amount) {

        return bankAccountService.withdraw(accountId, amount);
    }
    @GetMapping
public List<BankAccountResponse> getAllAccounts() {
    return bankAccountService.getAllAccounts();
}
}
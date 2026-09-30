package akash_national_bank.controller;

import java.util.List;
import akash_national_bank.transaction.Transaction;
import akash_national_bank.service.TransactionService;
import org.springframework.web.bind.annotation.*;
import akash_national_bank.dto.TransactionResponse;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }
    @GetMapping("/account/{accountId}")
public List<TransactionResponse> getTransactionsByAccount(@PathVariable Long accountId) {
    return transactionService.getTransactionsByAccount(accountId);
}
}
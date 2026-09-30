package akash_national_bank.service;

import akash_national_bank.transaction.Transaction;
import akash_national_bank.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import akash_national_bank.dto.TransactionResponse;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
    public List<TransactionResponse> getTransactionsByAccount(Long accountId) {

    return transactionRepository.findByBankAccountId(accountId)
            .stream()
            .map(transaction -> new TransactionResponse(
                    transaction.getId(),
                    transaction.getAmount(),
                    transaction.getDate(),
                    transaction.getDescription(),
                    transaction.getStatus(),
                    transaction.getType(),
                    transaction.getBankAccount().getId()
            ))
            .toList();
}
}
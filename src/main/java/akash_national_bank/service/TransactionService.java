package akash_national_bank.service;

import akash_national_bank.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import akash_national_bank.dto.TransactionResponse;
import akash_national_bank.repository.BankAccountRepository;
import akash_national_bank.exception.ResourceNotFoundException;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;

    public TransactionService(
            TransactionRepository transactionRepository,
            BankAccountRepository bankAccountRepository) {

        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<TransactionResponse> getTransactionsByAccount(Long accountId) {

        bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank account not found"));

        return transactionRepository.findByBankAccountId(accountId)
                .stream()
                .map(transaction -> new TransactionResponse(
                        transaction.getId(),
                        transaction.getAmount(),
                        transaction.getDate(),
                        transaction.getDescription(),
                        transaction.getStatus(),
                        transaction.getType(),
                        transaction.getBankAccount().getId()))
                .toList();
    }
}
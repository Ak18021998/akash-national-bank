package akash_national_bank.repository;

import akash_national_bank.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByBankAccountId(Long bankAccountId);
}
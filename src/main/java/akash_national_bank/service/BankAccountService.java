package akash_national_bank.service;

import akash_national_bank.account.BankAccount;
import akash_national_bank.entity.Customer;
import akash_national_bank.repository.BankAccountRepository;
import akash_national_bank.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import akash_national_bank.repository.TransactionRepository;
import akash_national_bank.transaction.Transaction;
import java.util.List;
import akash_national_bank.dto.BankAccountResponse;
import org.springframework.transaction.annotation.Transactional;
import akash_national_bank.exception.BadRequestException;
import akash_national_bank.exception.DuplicateResourceException;
import akash_national_bank.exception.ResourceNotFoundException;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public BankAccountService(
            BankAccountRepository bankAccountRepository,
            CustomerRepository customerRepository,
            TransactionRepository transactionRepository) {

        this.bankAccountRepository = bankAccountRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

   public BankAccountResponse saveBankAccount(BankAccount bankAccount) {

        if (bankAccount.getAccountNumber() == null
                || bankAccount.getAccountNumber().isBlank()) {

            throw new BadRequestException("Account number is required");
        }

        if (bankAccount.getBalance() < 0) {

            throw new BadRequestException("Initial balance cannot be negative");
        }

        Customer customer = bankAccount.getCustomer();

        if (customer == null || customer.getId() == null) {

            throw new BadRequestException("Customer is required");
        }

        Customer existingCustomer = customerRepository.findById(customer.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        bankAccount.setCustomer(existingCustomer);

        if (bankAccountRepository.existsByAccountNumber(bankAccount.getAccountNumber())) {

            throw new DuplicateResourceException("Account number already exists");
        }

        BankAccount savedAccount = bankAccountRepository.save(bankAccount);

return new BankAccountResponse(
        savedAccount.getId(),
        savedAccount.getAccountNumber(),
        savedAccount.getAccountType(),
        savedAccount.getBalance(),
        savedAccount.getBranch(),
        savedAccount.getCustomer().getId(),
        savedAccount.getCustomer().getFullName()
);

    }

    @Transactional
    public BankAccountResponse deposit(Long accountId, double amount) {

        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank account not found"));

        if (amount <= 0) {
            throw new BadRequestException("Deposit amount must be greater than 0");
        }

        bankAccount.setBalance(bankAccount.getBalance() + amount);

        BankAccount savedAccount = bankAccountRepository.save(bankAccount);

        Transaction transaction = new Transaction();
        transaction.setType("Deposit");
        transaction.setAmount(amount);
        transaction.setDescription("Cash Deposit");
        transaction.setDate(java.time.LocalDate.now().toString());
        transaction.setStatus("Success");
        transaction.setBankAccount(savedAccount);

        transactionRepository.save(transaction);

        return new BankAccountResponse(
        savedAccount.getId(),
        savedAccount.getAccountNumber(),
        savedAccount.getAccountType(),
        savedAccount.getBalance(),
        savedAccount.getBranch(),
        savedAccount.getCustomer().getId(),
        savedAccount.getCustomer().getFullName()
);
    }

    public List<BankAccountResponse> getAllAccounts() {

        return bankAccountRepository.findAll()
                .stream()
                .map(account -> new BankAccountResponse(
                        account.getId(),
                        account.getAccountNumber(),
                        account.getAccountType(),
                        account.getBalance(),
                        account.getBranch(),
                        account.getCustomer().getId(),
                        account.getCustomer().getFullName()))
                .toList();
    }

    @Transactional
    public BankAccountResponse withdraw(Long accountId, double amount) {

        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank account not found"));

        if (amount <= 0) {
            throw new BadRequestException("Withdrawal amount must be greater than 0");
        }

        if (amount > bankAccount.getBalance()) {
            throw new BadRequestException("Insufficient balance");
        }

        bankAccount.setBalance(bankAccount.getBalance() - amount);

        BankAccount savedAccount = bankAccountRepository.save(bankAccount);

        Transaction transaction = new Transaction();
        transaction.setType("Withdraw");
        transaction.setAmount(amount);
        transaction.setDescription("Cash Withdrawal");
        transaction.setDate(java.time.LocalDate.now().toString());
        transaction.setStatus("Success");
        transaction.setBankAccount(savedAccount);

        transactionRepository.save(transaction);

        return new BankAccountResponse(
        savedAccount.getId(),
        savedAccount.getAccountNumber(),
        savedAccount.getAccountType(),
        savedAccount.getBalance(),
        savedAccount.getBranch(),
        savedAccount.getCustomer().getId(),
        savedAccount.getCustomer().getFullName()
);
    }
}
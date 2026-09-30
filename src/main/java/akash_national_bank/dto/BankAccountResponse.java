package akash_national_bank.dto;

public class BankAccountResponse {

    private Long id;
    private String accountNumber;
    private String accountType;
    private double balance;
    private String branch;
    private Long customerId;
    private String customerName;

    public BankAccountResponse() {
    }

    public BankAccountResponse(
            Long id,
            String accountNumber,
            String accountType,
            double balance,
            String branch,
            Long customerId,
            String customerName) {

        this.id = id;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.branch = branch;
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public Long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public String getBranch() {
        return branch;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }
}
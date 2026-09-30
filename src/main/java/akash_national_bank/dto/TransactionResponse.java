package akash_national_bank.dto;

public class TransactionResponse {

    private Long id;
    private double amount;
    private String date;
    private String description;
    private String status;
    private String type;
    private Long bankAccountId;

    public TransactionResponse() {
    }

    public TransactionResponse(
            Long id,
            double amount,
            String date,
            String description,
            String status,
            String type,
            Long bankAccountId) {

        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.status = status;
        this.type = type;
        this.bankAccountId = bankAccountId;
    }

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }
}

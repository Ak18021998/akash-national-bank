package akash_national_bank.dto;

public class CustomerResponse {

    private Long id;
    private String customerId;
    private String fullName;
    private String email;
    private String mobile;
    private String dob;

    public CustomerResponse() {
    }

    public CustomerResponse(
            Long id,
            String customerId,
            String fullName,
            String email,
            String mobile,
            String dob) {

        this.id = id;
        this.customerId = customerId;
        this.fullName = fullName;
        this.email = email;
        this.mobile = mobile;
        this.dob = dob;
    }

    public Long getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getDob() {
        return dob;
    }
}
package akash_national_bank.controller;

import akash_national_bank.entity.Customer;
import akash_national_bank.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import akash_national_bank.dto.CustomerResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // =========================
    // CREATE CUSTOMER
    // =========================
    @PostMapping
    public CustomerResponse createCustomer(
            @Valid @RequestBody Customer customer) {

        return customerService.saveCustomer(customer);
    }

    // =========================
    // GET ALL CUSTOMERS
    // =========================
    @GetMapping
    public List<CustomerResponse> getAllCustomers() {

        return customerService.getAllCustomers();
    }

    // =========================
    // LOGIN
    // =========================
    @PostMapping("/login")
    public CustomerResponse login(
            @RequestParam String customerId,
            @RequestParam String password) {

        return customerService.login(customerId, password);
    }

    // =========================
    // RESET PASSWORD
    // =========================
    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam String customerId,
            @RequestParam String newPassword) {

        customerService.resetPassword(customerId, newPassword);

        return "Password reset successfully";
    }
}
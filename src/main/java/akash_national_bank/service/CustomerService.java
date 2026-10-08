package akash_national_bank.service;

import akash_national_bank.entity.Customer;
import akash_national_bank.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import akash_national_bank.dto.CustomerResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import akash_national_bank.exception.BadRequestException;
import akash_national_bank.exception.DuplicateResourceException;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomerService(
            CustomerRepository customerRepository,
            BCryptPasswordEncoder passwordEncoder) {

        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // =========================
    // CREATE CUSTOMER
    // =========================
    public CustomerResponse saveCustomer(Customer customer) {

        if (customer.getCustomerId() == null
                || customer.getCustomerId().isBlank()) {

            throw new BadRequestException("Customer ID is required");
        }

        if (customerRepository.existsByCustomerId(customer.getCustomerId())) {

            throw new DuplicateResourceException("Customer ID already exists");
        }

        if (customer.getEmail() == null
                || customer.getEmail().isBlank()) {

            throw new BadRequestException("Email is required");
        }

        if (customerRepository.existsByEmail(customer.getEmail())) {

            throw new DuplicateResourceException("Email already exists");
        }

        if (customer.getMobile() == null
                || customer.getMobile().isBlank()) {

            throw new BadRequestException("Mobile number is required");
        }

        if (customerRepository.existsByMobile(customer.getMobile())) {

            throw new DuplicateResourceException("Mobile number already exists");
        }

        if (customer.getPassword() == null
                || customer.getPassword().isBlank()) {

            throw new BadRequestException("Password is required");
        }

        // Password ko BCrypt se encrypt/hash karna
        String hashedPassword = passwordEncoder.encode(customer.getPassword());

        customer.setPassword(hashedPassword);

        Customer savedCustomer = customerRepository.save(customer);

        return new CustomerResponse(
                savedCustomer.getId(),
                savedCustomer.getCustomerId(),
                savedCustomer.getFullName(),
                savedCustomer.getEmail(),
                savedCustomer.getMobile(),
                savedCustomer.getDob());
    }

    // =========================
    // GET ALL CUSTOMERS
    // =========================
    public List<CustomerResponse> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(customer -> new CustomerResponse(
                        customer.getId(),
                        customer.getCustomerId(),
                        customer.getFullName(),
                        customer.getEmail(),
                        customer.getMobile(),
                        customer.getDob()))
                .toList();
    }

    // =========================
    // LOGIN
    // =========================
    public CustomerResponse login(String customerId, String password) {

        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new BadRequestException(
                        "Invalid customer ID or password"));

        if (!passwordEncoder.matches(password, customer.getPassword())) {

            throw new BadRequestException(
                    "Invalid customer ID or password");
        }

        return new CustomerResponse(
                customer.getId(),
                customer.getCustomerId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getMobile(),
                customer.getDob());
    }

    // =========================
    // RESET PASSWORD
    // =========================
    public void resetPassword(String customerId, String newPassword) {

        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new BadRequestException("Customer not found"));

        if (newPassword == null || newPassword.isBlank()) {

            throw new BadRequestException("Password is required");
        }

        // New password ko BCrypt hash me convert karna
        String hashedPassword = passwordEncoder.encode(newPassword);

        customer.setPassword(hashedPassword);

        customerRepository.save(customer);
    }
}
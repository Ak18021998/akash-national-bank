package akash_national_bank.controller;

import akash_national_bank.entity.Customer;
import akash_national_bank.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import akash_national_bank.dto.CustomerResponse;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

   @PostMapping
public CustomerResponse createCustomer(@RequestBody Customer customer){
        return customerService.saveCustomer(customer);
    }

 @GetMapping
public List<CustomerResponse> getAllCustomers() {
    return customerService.getAllCustomers();
}
@PostMapping("/login")
public CustomerResponse login(
        @RequestParam String customerId,
        @RequestParam String password) {

    return customerService.login(customerId, password);
}
}
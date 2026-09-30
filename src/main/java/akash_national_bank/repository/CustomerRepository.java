package akash_national_bank.repository;

import akash_national_bank.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByCustomerId(String customerId);
    boolean existsByCustomerId(String customerId);
    boolean existsByEmail(String email);
    boolean existsByMobile(String mobile);
}
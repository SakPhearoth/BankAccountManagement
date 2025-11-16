package co.pactice.roth.bankaccountmanagement.repository;

import co.pactice.roth.bankaccountmanagement.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNo(String accountNo);
    List<Account> findByCustomerId(Long customerId);
    boolean existsByAccountNo(String accountNo);

    String accNo(UUID accNo);

    List<Account> accNo(UUID accNo);
}

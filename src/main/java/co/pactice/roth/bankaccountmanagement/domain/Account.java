package co.pactice.roth.bankaccountmanagement.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table (name = "accounts", uniqueConstraints = @UniqueConstraint(columnNames = "acc_no"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "act_no", nullable = false, unique = true, length = 50)
    private UUID accNo;

    @Column(name = "customerId",  nullable = false, unique = true, length = 50)
    private Long customerId;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(length = 50)
    private String accountType;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "createAt", nullable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt;

}

package co.pactice.roth.bankaccountmanagement.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponseDto {
    private Long id;
    private UUID accountNo;
    private Long customerId;
    private BigDecimal balance;
    private String accountType;
    private Boolean isDeleted;
    private Instant createdAt;
    private Instant updatedAt;
}

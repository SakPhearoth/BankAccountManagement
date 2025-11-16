package co.pactice.roth.bankaccountmanagement.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountDto {

    @NotBlank(message = "accNo is required")
    @Size(max =50, message = "accNo max length is 50")
    private String accNo;

    @NotNull(message = "customerId is required")
    @Positive(message = "customerId must be positive")
    private Long customerId;

    @NotNull(message = "balance is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "balance must be >= 0")
    private BigDecimal balance;

    @NotBlank(message = "accountType is required")
    private String accountType;


}

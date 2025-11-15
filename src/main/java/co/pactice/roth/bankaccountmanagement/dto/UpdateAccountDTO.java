package co.pactice.roth.bankaccountmanagement.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAccountDTO {

    // actNo is not updatable here (we update by actNo param)
    @NotNull (message = "customerId is required")
    @Positive (message = "customerId must be positive")
    private Long customerId;

    @NotNull(message = "balance is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "balance must be >= 0")
    private BigDecimal balance;

    @NotBlank(message = "accountType is required")
    private String accountType;
}

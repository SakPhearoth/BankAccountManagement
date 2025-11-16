package co.pactice.roth.bankaccountmanagement.mapper;

import co.pactice.roth.bankaccountmanagement.domain.Account;
import co.pactice.roth.bankaccountmanagement.dto.AccountResponseDto;
import co.pactice.roth.bankaccountmanagement.dto.CreateAccountDto;
import co.pactice.roth.bankaccountmanagement.dto.UpdateAccountDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface AccountMapper {

    Account toEntity(CreateAccountDto dto);

    AccountResponseDto toDto(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UpdateAccountDto dto, @MappingTarget Account entity);
}

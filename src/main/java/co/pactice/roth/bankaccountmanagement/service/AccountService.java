package co.pactice.roth.bankaccountmanagement.service;

import co.pactice.roth.bankaccountmanagement.dto.AccountResponseDto;
import co.pactice.roth.bankaccountmanagement.dto.CreateAccountDto;
import co.pactice.roth.bankaccountmanagement.dto.PagedResponse;
import co.pactice.roth.bankaccountmanagement.dto.UpdateAccountDto;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    AccountResponseDto create(CreateAccountDto createAccountDto);

    List<AccountResponseDto> findAll();

    AccountResponseDto findByAccNo(UUID accNo);

    List<AccountResponseDto> findByCustomerId(Long customerId);

    void deleteByAccNo(UUID accNo);

    AccountResponseDto updateByAccNo(UUID accNo, UpdateAccountDto updateAccountDto);

    AccountResponseDto disableByAccNo(UUID accNo);

    PagedResponse<AccountResponseDto> findAllPaged(int page, int size, String sortBy, String direction);
}

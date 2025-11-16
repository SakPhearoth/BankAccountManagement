package co.pactice.roth.bankaccountmanagement.service;

import co.pactice.roth.bankaccountmanagement.dto.AccountResponseDto;
import co.pactice.roth.bankaccountmanagement.dto.CreateAccountDto;
import co.pactice.roth.bankaccountmanagement.dto.UpdateAccountDto;

import java.util.List;

public interface AccountService {
    AccountResponseDto create(CreateAccountDto createAccountDto);

    List<AccountResponseDto> findAll();

    AccountResponseDto findByAccNo(String accNo);

    List<AccountResponseDto> findByCustomerId(Long customerId);

    void deleteByAccNo(String accNo);

    AccountResponseDto updateByAccNo(String accNo, UpdateAccountDto updateAccountDto);

    AccountResponseDto disableByAccNo(String accNo);
}

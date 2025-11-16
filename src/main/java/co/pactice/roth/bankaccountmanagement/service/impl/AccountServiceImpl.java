package co.pactice.roth.bankaccountmanagement.service.impl;

import co.pactice.roth.bankaccountmanagement.domain.Account;
import co.pactice.roth.bankaccountmanagement.dto.AccountResponseDto;
import co.pactice.roth.bankaccountmanagement.dto.CreateAccountDto;
import co.pactice.roth.bankaccountmanagement.dto.UpdateAccountDto;
import co.pactice.roth.bankaccountmanagement.exception.ResourceNotFoundException;
import co.pactice.roth.bankaccountmanagement.mapper.AccountMapper;
import co.pactice.roth.bankaccountmanagement.repository.AccountRepository;
import co.pactice.roth.bankaccountmanagement.service.AccountService;
import jakarta.annotation.Resources;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository  accountRepository;
    private final AccountMapper accountMapper;

    // Create account
    @Override
    @Transactional
    public AccountResponseDto create(CreateAccountDto createAccountDto) {
        if (accountRepository.existsByAccountNo(createAccountDto.getAccNo())) {
            throw new IllegalArgumentException("AccNo already exists");
        }
        Account entity = accountMapper.toEntity(createAccountDto);
        entity.setCreatedAt(Instant.now());
        entity.setIsDeleted(false);
        Account savedAccount = accountRepository.save(entity);
        return accountMapper.toDto(savedAccount);
    }

    // Find all accounts
    @Override
    @Transactional(readOnly = true)
    public List<AccountResponseDto> findAll(){
        return  accountRepository.findAll().stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    // Find by account number
    @Override
    @Transactional(readOnly = true)
    public AccountResponseDto findByAccNo(String accNo){
        Account account = accountRepository.findByAccountNo(accNo)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with accNo: " + accNo));
        return accountMapper.toDto(account);
    }

    // Find by customer Id
    @Override
    @Transactional(readOnly = true)
    public List<AccountResponseDto> findByCustomerId(Long customerId){
        return accountRepository.findByCustomerId(customerId)
                .stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }


    // Delete by account number
    @Override
    @Transactional
    public void deleteByAccNo (String accNo){
        Account account = accountRepository.findByAccountNo(accNo)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with accNo: " + accNo));
        accountRepository.delete(account);
    }

    // Update account by account number
    @Override
    @Transactional
    public AccountResponseDto updateByAccNo(String accNo, UpdateAccountDto  updateAccountDto) {
        Account account = accountRepository.findByAccountNo(accNo)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with accNo: " + accNo));
        accountMapper.updateFromDto(updateAccountDto, account);
        account.setUpdatedAt(Instant.now());
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toDto(savedAccount);
    }

    // Disable by account number
    @Override
    @Transactional
    public AccountResponseDto disableByAccNo(String accNo) {
        Account account = accountRepository.findByAccountNo(accNo)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with accNo: " + accNo));
        account.setIsDeleted(true);
        account.setUpdatedAt(Instant.now());
        return  accountMapper.toDto(accountRepository.save(account));
    }

}

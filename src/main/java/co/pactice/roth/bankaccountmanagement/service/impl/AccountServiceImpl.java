package co.pactice.roth.bankaccountmanagement.service.impl;

import co.pactice.roth.bankaccountmanagement.domain.Account;
import co.pactice.roth.bankaccountmanagement.dto.AccountResponseDto;
import co.pactice.roth.bankaccountmanagement.dto.CreateAccountDto;
import co.pactice.roth.bankaccountmanagement.dto.PagedResponse;
import co.pactice.roth.bankaccountmanagement.dto.UpdateAccountDto;
import co.pactice.roth.bankaccountmanagement.exception.ResourceNotFoundException;
import co.pactice.roth.bankaccountmanagement.mapper.AccountMapper;
import co.pactice.roth.bankaccountmanagement.repository.AccountRepository;
import co.pactice.roth.bankaccountmanagement.service.AccountService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.keycloak.util.JsonSerialization.mapper;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final RestClient.Builder builder;

    // Create account
    @Override
    @Transactional
    public AccountResponseDto create(CreateAccountDto createAccountDto) {

        UUID generated = UUID.randomUUID();

        if (accountRepository.existsByAccountNo(generated)) {
            throw new IllegalArgumentException("Account already exists");
        }

        // Map DTO -> entity (ActNo is null here)
        Account entity = accountMapper.toEntity(createAccountDto);

        //generate UUID for account
        entity.setAccountNo(UUID.randomUUID());

        entity.setCreatedAt(Instant.now());
        entity.setIsDeleted(false);

        Account saved =  accountRepository.save(entity);
        System.out.println("SAVED accountNo = " + saved.getAccountNo()); // <-- Debug
        return accountMapper.toDto(saved);
    }

    // Find all accounts
    @Override
    @Transactional(readOnly = true)
    public List<AccountResponseDto> findAll() {
        return accountRepository.findAll().stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    // Pagination
    @Override
    @Transactional(readOnly = true)
    public PagedResponse<AccountResponseDto> findAllPaged(int page, int size, String sortBy, String direction){
        if (page < 0) page = 0;
        if (size < 0) size = 10;
        if (size > 100) size = 100;

        Sort sort = Sort.by(Sort.Direction.fromString(direction == null ? "ASC" : direction), (sortBy == null ? "accountNo" : sortBy));
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Account> accountPage = accountRepository.findAll(pageable);

        List<AccountResponseDto> dtoList = accountPage.map(accountMapper::toDto).getContent();

        return PagedResponse.<AccountResponseDto>builder()
                .content(dtoList)
                .page(accountPage.getNumber())
                .size(accountPage.getSize())
                .totalElements(accountPage.getTotalElements())
                .totalPages(accountPage.getTotalPages())
                .last(accountPage.isLast())
                .build();
    }

    // Find by account number
    @Override
    @Transactional(readOnly = true)
    public AccountResponseDto findByAccNo(UUID accNo) {
        Account account = accountRepository.findByAccountNo(accNo)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with accNo: " + accNo));
        return accountMapper.toDto(account);
    }

    // Find by customer Id
    @Override
    @Transactional(readOnly = true)
    public List<AccountResponseDto> findByCustomerId(Long customerId) {
        return accountRepository.findByCustomerId(customerId)
                .stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }


    // Delete by account number
    @Override
    @Transactional
    public void deleteByAccNo(UUID accNo) {
        Account account = accountRepository.findByAccountNo(accNo)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with accNo: " + accNo));
        accountRepository.delete(account);
    }

    // Update account by account number
    @Override
    @Transactional
    public AccountResponseDto updateByAccNo(UUID accNo, UpdateAccountDto updateAccountDto) {
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
    public AccountResponseDto disableByAccNo(UUID accNo) {
        Account account = accountRepository.findByAccountNo(accNo)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with accNo: " + accNo));
        account.setIsDeleted(true);
        account.setUpdatedAt(Instant.now());
        return accountMapper.toDto(accountRepository.save(account));
    }


}

package co.pactice.roth.bankaccountmanagement.controller;

import co.pactice.roth.bankaccountmanagement.dto.*;
import co.pactice.roth.bankaccountmanagement.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static java.util.stream.DoubleStream.builder;

@RestController
@RequestMapping("api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponseDto>> create (@Valid @RequestBody CreateAccountDto  createAccountDto) {
        AccountResponseDto created = accountService.create(createAccountDto);
        ApiResponse<AccountResponseDto> response = ApiResponse.<AccountResponseDto>builder()
                .status(201).message("Account created").data(created).build();
        return ResponseEntity.status(201).body(response);
    }

//    @GetMapping
//    public ResponseEntity<ApiResponse<List<AccountResponseDto>>> findAll() {
//        List<AccountResponseDto> list = accountService.findAll();
//        ApiResponse<List<AccountResponseDto>> response = ApiResponse.<List<AccountResponseDto>>builder()
//                .status(200).message("Ok").data(list).build();
//        return ResponseEntity.ok(response);
//    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<AccountResponseDto>>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "ASC") String direction){

        PagedResponse<AccountResponseDto> pagedResponse = accountService.findAllPaged(page, size, sortBy, direction);

        ApiResponse<PagedResponse<AccountResponseDto>> response = ApiResponse.<PagedResponse<AccountResponseDto>>builder()
                .status(200)
                .message("Ok")
                .data(pagedResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{accountNo}")
    public ResponseEntity<ApiResponse<AccountResponseDto>> findByAccountNo(@PathVariable UUID accountNo){
        AccountResponseDto accountResponseDto = accountService.findByAccNo(accountNo);
        return ResponseEntity.ok(ApiResponse.<AccountResponseDto>builder().status(200).message("Ok").data(accountResponseDto).build());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<AccountResponseDto>>> findByCustomerId(@PathVariable Long customerId){
        List<AccountResponseDto> list = accountService.findByCustomerId(customerId);
        return ResponseEntity.ok(ApiResponse.<List<AccountResponseDto>>builder().status(200).message("Ok").data(list).build());
    }

    @DeleteMapping("/{accountNo}")
    public ResponseEntity<ApiResponse<Void>> deleteByAccountNo(@PathVariable UUID accountNo){
        accountService.deleteByAccNo(accountNo);
        return ResponseEntity.ok(ApiResponse.<Void>builder().status(200).message("Deleted").data(null).build());
    }

    @PutMapping("/{accountNo}")
    public ResponseEntity<ApiResponse<AccountResponseDto>> updateByAccountNo(
            @PathVariable UUID accountNo,
            @Valid @RequestBody UpdateAccountDto dto){
        AccountResponseDto updated = accountService.updateByAccNo(accountNo, dto);
        return ResponseEntity.ok(ApiResponse.<AccountResponseDto>builder().status(200).message("Updated").data(updated).build());
    }

    @PatchMapping("/{accountNo}/disable")
    public ResponseEntity<ApiResponse<AccountResponseDto>> disableByAccountNo(@PathVariable UUID accountNo){
        AccountResponseDto accountResponseDto = accountService.disableByAccNo(accountNo);
        return ResponseEntity.ok(ApiResponse.<AccountResponseDto>builder().status(200).message("Disabled").data(accountResponseDto).build());
    }
}

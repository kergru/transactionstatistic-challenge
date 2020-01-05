package com.kgr.challenge.transactionstatistics.endpoint;

import com.kgr.challenge.transactionstatistics.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.ResponseEntity.accepted;

@RequiredArgsConstructor
@RestController
public class AddTransactionEndpoint {

    private final TransactionService transactionService;

    @PostMapping(value = "/sales", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> addTransaction(@RequestParam("sales_amount") double salesAmount) {
        transactionService.saveTransaction(salesAmount);
        return accepted().build();
    }
}

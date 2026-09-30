package com.movo.transactionstore.api.controller;

import com.movo.security.CurrentUserProvider;
import com.movo.transactionstore.application.TransactionService;
import com.movo.transactionstore.infrastructure.web.dto.CreateTransactionRequest;
import com.movo.transactionstore.infrastructure.web.dto.TransactionResponse;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionStoreController {

    private final CurrentUserProvider provider;
    private final TransactionService transactionService;

    public TransactionStoreController(CurrentUserProvider provider, TransactionService transactionService) {
        this.provider = provider;
        this.transactionService = transactionService;
    }

    // create transaction
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TransactionResponse> createTransaction(@Valid @RequestBody CreateTransactionRequest request,
            ServerWebExchange exchange) {
        return provider.getCurrentUser(exchange).flatMap(userIdentity ->  transactionService.createTransaction(userIdentity.userId(), request));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<List<TransactionResponse>> getTransactions() {
        return Mono.just(List.of(TransactionResponse.dummy()));
    }

    @GetMapping("/{transactionId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<TransactionResponse> getTransaction(@PathVariable String transactionId) {
        return Mono.just(TransactionResponse.dummy());
    }

}

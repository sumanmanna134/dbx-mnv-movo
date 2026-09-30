package com.movo.transactionstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.movo")
public class TransactionStoreApplication {
    static void main(String[] args) {
        SpringApplication.run(TransactionStoreApplication.class, args);
    }
}

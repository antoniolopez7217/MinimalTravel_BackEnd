package com.example.minimaltravel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.minimaltravel.dto.BalanceDTO;
import com.example.minimaltravel.repository.TransactionUserRepository;


@Service
public class BalanceService {

    private final TransactionUserRepository transactionUserRepository;

    @Autowired
    public BalanceService(TransactionUserRepository transactionUserRepository) {
        this.transactionUserRepository = transactionUserRepository;
    }

    public List<BalanceDTO> calculateBalances() {
        return transactionUserRepository.getBalances();
    }
}


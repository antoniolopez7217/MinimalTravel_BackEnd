package com.example.minimaltravel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.minimaltravel.dto.BalanceDTO;
import com.example.minimaltravel.service.BalanceService;

@RestController
@RequestMapping("/api/balances")
public class BalanceController {

    private final BalanceService balanceService;

    @Autowired
    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    // Obtener todos los balances entre usuarios
    @GetMapping
    public ResponseEntity<List<BalanceDTO>> getAllBalances() {
        List<BalanceDTO> balances = balanceService.calculateBalances();
        return ResponseEntity.ok(balances);
    }
}


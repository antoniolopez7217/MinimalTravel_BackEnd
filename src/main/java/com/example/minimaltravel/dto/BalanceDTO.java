package com.example.minimaltravel.dto;

public class BalanceDTO {
    private Long creditorUserId;   // ID del usuario que debe recibir dinero
    private String creditorUserName;
    private Long debtorUserId;     // ID del usuario que debe pagar
    private String debtorUserName;
    private Double amount;         // Cantidad que el debtor debe al creditor

    // Constructor, getters y setters
    public BalanceDTO(Long creditorUserId, String creditorUserName, 
                      Long debtorUserId, String debtorUserName, Double amount) {
        this.creditorUserId = creditorUserId;
        this.creditorUserName = creditorUserName;
        this.debtorUserId = debtorUserId;
        this.debtorUserName = debtorUserName;
        this.amount = amount;
    }

    public Long getCreditorUserId() { return creditorUserId; }
    public String getCreditorUserName() { return creditorUserName; }
    public Long getDebtorUserId() { return debtorUserId; }
    public String getDebtorUserName() { return debtorUserName; }
    public Double getAmount() { return amount; }
}

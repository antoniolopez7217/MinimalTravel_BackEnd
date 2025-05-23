package com.example.minimaltravel.dto;

import java.util.Date;
import java.util.List;

// Envía datos de transacciones al frontend
public class TransactionResponseDTO {
    private Long transactionId;
    private String description;
    private Double amount;
    private String category;
    private Date creationDate;
    private Long creditorUserId;
    private String creditorUserName;
    private List<ParticipantResponse> participants; // Lista de participantes como objetos internos

    // Clase interna para participantes en la respuesta
    public static class ParticipantResponse {
        private Long userId;
        private String userName;
        private Double amount;

        // Getters y setters
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public String getUserName() { return userName; }
        public void setUserName(String userName) { this.userName = userName; }
        public Double getAmount() { return amount; }
        public void setAmount(Double amount) { this.amount = amount; }
    }

    // Getters y setters para TransactionResponseDTO
    public Long getTransactionId() { return transactionId; }
    public void setTransactionId(Long transactionId) { this.transactionId = transactionId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
    public Long getCreditorUserId() { return creditorUserId; }
    public void setCreditorUserId(Long creditorUserId) { this.creditorUserId = creditorUserId; }
    public String getCreditorUserName() { return creditorUserName; }
    public void setCreditorUserName(String creditorUserName) { this.creditorUserName = creditorUserName; }
    public List<ParticipantResponse> getParticipants() { return participants; }
    public void setParticipants(List<ParticipantResponse> participants) { this.participants = participants; }
}

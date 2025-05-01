package com.example.minimaltravel.dto;

import java.util.List;

// Recibe datos del frontend para crear/actualizar transacciones
public class TransactionRequestDTO {
    private String description;
    private Double amount;
    private String category;
    private Long creditorUserId;
    private List<ParticipantInfo> participants; // Lista de participantes como objetos internos

    // Clase interna para participantes
    public static class ParticipantInfo {
        private Long userId;
        private Double amount;

        // Getters y setters
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public Double getAmount() { return amount; }
        public void setAmount(Double amount) { this.amount = amount; }
    }

    // Getters y setters para TransactionRequestDTO
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Long getCreditorUserId() { return creditorUserId; }
    public void setCreditorUserId(Long creditorUserId) { this.creditorUserId = creditorUserId; }
    public List<ParticipantInfo> getParticipants() { return participants; }
    public void setParticipants(List<ParticipantInfo> participants) { this.participants = participants; }
}

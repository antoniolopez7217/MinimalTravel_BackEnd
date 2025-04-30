package com.example.minimaltravel.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Transaction {

	public enum TransactionCategory {
        Comida, Transporte, Ocio, Ropa, Compras, Actividades, Otros
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId; // Primary key

    private String description;

	private Long amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // Formato ISO-8601s
    private Date creationDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('Comida', 'Transporte', 'Ocio', 'Ropa', 'Compras', 'Actividades', 'Otros')")
    private TransactionCategory category;
    
    // Relación ManyToOne con User, usando creditorUserId como clave foránea
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creditorUserId", referencedColumnName = "userId")
    private User creditorUser;

    // Campo solo-lectura para exponer el userId directamente si lo necesitas
    @Column(name = "creditorUserId", insertable = false, updatable = false)
    private Long creditorUserId;

	// Relación uno-a-muchos con TransactionUser
	@JsonIgnore
	@OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
	private List<TransactionUser> participants = new ArrayList<>();
	
    // Getters and setters
    public Long getTransactionId() {
        return transactionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

	public TransactionCategory getCategory() {
        return category;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    public User getCreditorUser() { return creditorUser; }
    public void setCreditorUser(User creditorUser) { 
        this.creditorUser = creditorUser;
        this.creditorUserId = creditorUser != null ? creditorUser.getUserId() : null;
    }

    public Long getCreditordUserId() { return creditorUserId; }
    // No setter para creditorUserId, se gestiona automáticamente por JPA

	public List<TransactionUser> getParticipants() {
        return participants;
    }

    // Setter para participants
    public void setParticipants(List<TransactionUser> participants) {
        this.participants = participants;
    }
}
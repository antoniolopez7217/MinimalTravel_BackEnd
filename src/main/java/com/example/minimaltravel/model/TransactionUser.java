package com.example.minimaltravel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TransactionUser {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "transaction_id")
	private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User debtorUser;

    private Double amount; // Importe que debe pagar ese usuario en este gasto

    public Long getId() {
        return id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public User getDebtorUser() {
        return debtorUser;
    }

    public void setDebtorUser(User debtorUser) {
        this.debtorUser = debtorUser;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}

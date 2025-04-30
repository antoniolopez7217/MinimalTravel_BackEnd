package com.example.minimaltravel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.minimaltravel.model.TransactionUser;

import jakarta.transaction.Transactional;

public interface TransactionUserRepository extends JpaRepository<TransactionUser, Long> {
	List<TransactionUser> findByTransaction_TransactionId(Long transactionId);

    @Modifying
    @Transactional
    @Query("DELETE FROM TransactionUser tu WHERE tu.transaction.transactionId = :transactionId")
    void deleteByTransactionId(Long transactionId);
}

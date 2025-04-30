package com.example.minimaltravel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.minimaltravel.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	    @Query("SELECT t FROM Transaction t LEFT JOIN FETCH t.creditorUser") // Carga el usuario en la misma consulta
    List<Transaction> findAllWithUser();

}

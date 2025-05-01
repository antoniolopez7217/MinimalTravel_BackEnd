package com.example.minimaltravel.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.minimaltravel.dto.TransactionRequestDTO;
import com.example.minimaltravel.dto.TransactionResponseDTO;
import com.example.minimaltravel.model.Transaction;
import com.example.minimaltravel.model.TransactionUser;
import com.example.minimaltravel.model.User;
import com.example.minimaltravel.repository.TransactionRepository;
import com.example.minimaltravel.repository.TransactionUserRepository;
import com.example.minimaltravel.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final TransactionUserRepository transactionUserRepository;

    @Autowired
    public TransactionService(
        TransactionRepository transactionRepository,
        UserRepository userRepository,
        TransactionUserRepository transactionUserRepository
    ) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.transactionUserRepository = transactionUserRepository;
    }

    @Transactional
    public TransactionResponseDTO createTransaction(TransactionRequestDTO dto) {
        // Obtener usuario que pagó el gasto (creditor)
        User creditor = userRepository.findById(dto.getCreditorUserId())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear y guardar la transacción
        Transaction transaction = new Transaction();
        transaction.setDescription(dto.getDescription());
        transaction.setAmount(dto.getAmount());
        transaction.setCategory(dto.getCategory());
        transaction.setCreationDate(new Date());
        transaction.setCreditorUser(creditor);
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Guardar participantes
        for (TransactionRequestDTO.ParticipantInfo participant : dto.getParticipants()) {
            User debtor = userRepository.findById(participant.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario participante no encontrado"));

            TransactionUser transactionUser = new TransactionUser();
            transactionUser.setTransaction(savedTransaction);
            transactionUser.setDebtorUser(debtor);
            transactionUser.setAmount(participant.getAmount());
            transactionUserRepository.save(transactionUser);
        }

        return convertToDTO(savedTransaction);
    }

    public List<TransactionResponseDTO> getAllTransactions() {
        return transactionRepository.findAll()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public TransactionResponseDTO getTransactionById(Long id) {
        return transactionRepository.findById(id)
            .map(this::convertToDTO)
            .orElse(null);
    }

    @Transactional
    public void deleteTransaction(Long id) {
        transactionUserRepository.deleteByTransaction_TransactionId(id);
        transactionRepository.deleteById(id);
    }

    // Método para convertir Transaction a DTO
    private TransactionResponseDTO convertToDTO(Transaction transaction) {
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setTransactionId(transaction.getTransactionId());
        dto.setDescription(transaction.getDescription());
        dto.setAmount(transaction.getAmount());
        dto.setCategory(transaction.getCategory());
        dto.setCreationDate(transaction.getCreationDate());
        dto.setCreditorUserId(transaction.getCreditorUser().getUserId());
        dto.setCreditorUserName(transaction.getCreditorUser().getuserName());

        // Convertir participantes
        List<TransactionResponseDTO.ParticipantResponse> participants = 
            transactionUserRepository.findByTransaction_TransactionId(transaction.getTransactionId())
                .stream()
                .map(tu -> {
                    TransactionResponseDTO.ParticipantResponse p = new TransactionResponseDTO.ParticipantResponse();
                    p.setUserId(tu.getDebtorUser().getUserId());
                    p.setUserName(tu.getDebtorUser().getuserName());
                    p.setAmount(tu.getAmount());
                    return p;
                })
                .collect(Collectors.toList());

        dto.setParticipants(participants);
        return dto;
    }

    @Transactional
    public TransactionResponseDTO updateTransaction(Long id, TransactionRequestDTO dto) {
        // 1. Buscar la transacción existente
        Transaction transaction = transactionRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Transacción no encontrada"));

        // 2. Actualizar campos básicos
        transaction.setDescription(dto.getDescription());
        transaction.setAmount(dto.getAmount());
        transaction.setCategory(dto.getCategory());

        // 3. Actualizar usuario que pagó (si cambió)
        if (!transaction.getCreditorUser().getUserId().equals(dto.getCreditorUserId())) {
            User creditor = userRepository.findById(dto.getCreditorUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            transaction.setCreditorUser(creditor);
        }

        // 4. Eliminar participantes antiguos
        transactionUserRepository.deleteByTransaction_TransactionId(id);

        // 5. Añadir nuevos participantes
        for (TransactionRequestDTO.ParticipantInfo participant : dto.getParticipants()) {
            User debtor = userRepository.findById(participant.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario participante no encontrado"));

            TransactionUser transactionUser = new TransactionUser();
            transactionUser.setTransaction(transaction);
            transactionUser.setDebtorUser(debtor);
            transactionUser.setAmount(participant.getAmount());
            transactionUserRepository.save(transactionUser);
        }

        // 6. Guardar cambios
        Transaction updatedTransaction = transactionRepository.save(transaction);

        // 7. Convertir a DTO y devolver
        return convertToDTO(updatedTransaction);
    }

}

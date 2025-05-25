package com.example.minimaltravel.model;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId; // Primary key

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date creationDate;

    private String status; // "Eliminado", "Pendiente", "Completado"
    
    // Relación ManyToOne con User, usando assignedUserId como clave foránea
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignedUserId", referencedColumnName = "userId")
    private User assignedUser;

    // Campo solo-lectura para exponer el userId directamente
    @Column(name = "assignedUserId", insertable = false, updatable = false)
    private Long assignedUserId;

    // Getters and setters
    public Long getTaskId() {
        return taskId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getAssignedUser() { return assignedUser; }
    public void setAssignedUser(User assignedUser) { 
        this.assignedUser = assignedUser;
        this.assignedUserId = assignedUser != null ? assignedUser.getUserId() : null;
    }

    public Long getAssignedUserId() { return assignedUserId; }
    // No setter para assignedUserId, se gestiona automáticamente por JPA
}
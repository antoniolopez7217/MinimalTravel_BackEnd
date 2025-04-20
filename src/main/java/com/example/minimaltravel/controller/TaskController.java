package com.example.minimaltravel.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.minimaltravel.dto.TaskResponse;
import com.example.minimaltravel.model.Task;
import com.example.minimaltravel.model.User;
import com.example.minimaltravel.repository.TaskRepository;
import com.example.minimaltravel.repository.UserRepository;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository; // Asegúrate de inyectar el UserRepository

    // Obtener todas las tareas con nombre de usuario
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        List<Task> tasks = taskRepository.findAllWithUser(); // Necesita JOIN FETCH en el repositorio
        return ResponseEntity.ok(tasks.stream().map(this::convertToResponse).collect(Collectors.toList()));
    }

    // Crear nueva tarea (con asignación de usuario por ID)
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskResponse request) {
        Task task = new Task();
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setCreationDate(new Date());
        
        // Asignar usuario si se proporciona ID
        if (request.getAssignedUserId() != null) {
            User user = userRepository.findById(request.getAssignedUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            task.setAssignedUser(user);
        }
        
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.ok(convertToResponse(savedTask));
    }

    // Actualizar tarea existente
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @RequestBody TaskResponse request) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        
        // Actualizar usuario asignado
        if (request.getAssignedUserId() != null) {
            User user = userRepository.findById(request.getAssignedUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            task.setAssignedUser(user);
        } else {
            task.setAssignedUser(null);
        }
        
        Task updatedTask = taskRepository.save(task);
        return ResponseEntity.ok(convertToResponse(updatedTask));
    }

    // Eliminar tarea (marcar como "Deleted")
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        task.setStatus("Deleted");
        taskRepository.save(task);
    }

    // Conversión de Task a TaskResponse
    private TaskResponse convertToResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setTaskId(task.getTaskId());
        response.setDescription(task.getDescription());
        response.setCreationDate(task.getCreationDate());
        response.setStatus(task.getStatus());
        
        if (task.getAssignedUser() != null) {
            response.setAssignedUserId(task.getAssignedUser().getUserId());
            response.setAssignedUserName(task.getAssignedUser().getuserName());
        }
        
        return response;
    }
}

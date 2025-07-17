package com.example.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Sistema", description = "Endpoints del sistema y verificación de salud")
@CrossOrigin(origins = "*")
public class HelloController {
    
    @Autowired
    private DataSource dataSource;
    
    @GetMapping("/hello")
    @Operation(summary = "Saludo del sistema", description = "Endpoint de prueba para verificar que el backend funciona")
    public Map<String, String> hello() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hola desde el backend 😎😎");
        response.put("timestamp", java.time.LocalDateTime.now().toString());
        response.put("version", "1.0.0");
        return response;
    }
    
    @GetMapping("/health/db")
    @Operation(summary = "Verificación de base de datos", description = "Verifica la conectividad con la base de datos PostgreSQL")
    public Map<String, String> checkDatabase() {
        Map<String, String> response = new HashMap<>();
        try {
            Connection connection = dataSource.getConnection();
            String dbName = connection.getCatalog();
            connection.close();
            response.put("status", "success");
            response.put("message", "✅ Conexión exitosa a la base de datos: " + dbName);
            response.put("database", dbName != null ? dbName : "mediconsultas");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "❌ Error de conexión: " + e.getMessage());
        }
        return response;
    }
    
    @GetMapping("/info")
    @Operation(summary = "Información del sistema", description = "Retorna información general del sistema de consultas médicas")
    public Map<String, Object> systemInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("app", "Sistema de Consultas Médicas");
        info.put("version", "1.0.0");
        info.put("description", "API REST para gestión de médicos y citas médicas");
        info.put("endpoints", new String[]{
            "/api/medicos - Gestión de médicos",
            "/api/citas - Gestión de citas",
            "/swagger-ui.html - Documentación interactiva"
        });
        info.put("timestamp", java.time.LocalDateTime.now().toString());
        return info;
    }
}

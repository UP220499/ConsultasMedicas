package com.example.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos para iniciar sesión")
public class LoginRequest {
    
    @Schema(description = "Email del usuario", example = "usuario@example.com", required = true)
    private String email;
    
    @Schema(description = "Contraseña del usuario", required = true)
    private String password;
    
    // Constructors
    public LoginRequest() {}
    
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

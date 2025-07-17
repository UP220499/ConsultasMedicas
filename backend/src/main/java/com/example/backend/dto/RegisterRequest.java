package com.example.backend.dto;

import com.example.backend.entity.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos para registrar un nuevo usuario")
public class RegisterRequest {
    
    @Schema(description = "Email del usuario", example = "usuario@example.com", required = true)
    private String email;
    
    @Schema(description = "Contraseña del usuario", required = true)
    private String password;
    
    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez", required = true)
    private String nombre;
    
    @Schema(description = "Teléfono del usuario", example = "+52 55 1234 5678")
    private String telefono;
    
    @Schema(description = "Tipo de usuario", example = "PACIENTE", required = true)
    private Usuario.TipoUsuario tipo = Usuario.TipoUsuario.PACIENTE;
    
    // Constructors
    public RegisterRequest() {}
    
    public RegisterRequest(String email, String password, String nombre, Usuario.TipoUsuario tipo) {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.tipo = tipo;
    }
    
    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public Usuario.TipoUsuario getTipo() { return tipo; }
    public void setTipo(Usuario.TipoUsuario tipo) { this.tipo = tipo; }
}

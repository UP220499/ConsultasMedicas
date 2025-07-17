package com.example.backend.dto;

import com.example.backend.entity.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de autenticación")
public class AuthResponse {
    
    @Schema(description = "Indica si la autenticación fue exitosa")
    private boolean success;
    
    @Schema(description = "Mensaje de respuesta")
    private String message;
    
    @Schema(description = "Datos del usuario autenticado")
    private UsuarioDto usuario;
    
    // Constructors
    public AuthResponse() {}
    
    public AuthResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    
    public AuthResponse(boolean success, String message, UsuarioDto usuario) {
        this.success = success;
        this.message = message;
        this.usuario = usuario;
    }
    
    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public UsuarioDto getUsuario() { return usuario; }
    public void setUsuario(UsuarioDto usuario) { this.usuario = usuario; }
    
    // DTO interno para datos del usuario
    public static class UsuarioDto {
        private Long id;
        private String email;
        private String nombre;
        private String telefono;
        private Usuario.TipoUsuario tipo;
        
        public UsuarioDto() {}
        
        public UsuarioDto(Usuario usuario) {
            this.id = usuario.getId();
            this.email = usuario.getEmail();
            this.nombre = usuario.getNombre();
            this.telefono = usuario.getTelefono();
            this.tipo = usuario.getTipo();
        }
        
        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        
        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }
        
        public Usuario.TipoUsuario getTipo() { return tipo; }
        public void setTipo(Usuario.TipoUsuario tipo) { this.tipo = tipo; }
    }
}

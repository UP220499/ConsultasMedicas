package com.example.backend.entity;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Schema(description = "Entidad que representa un usuario del sistema")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del usuario", example = "1")
    private Long id;
    
    @Column(nullable = false, unique = true)
    @Schema(description = "Email del usuario (debe ser único)", example = "usuario@example.com")
    private String email;
    
    @Column(nullable = false)
    @Schema(description = "Contraseña del usuario")
    private String password;
    
    @Column(nullable = false)
    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String nombre;
    
    @Column
    @Schema(description = "Teléfono del usuario", example = "+52 55 1234 5678")
    private String telefono;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Tipo de usuario", example = "PACIENTE")
    private TipoUsuario tipo = TipoUsuario.PACIENTE;
    
    @Column(nullable = false)
    @Schema(description = "Fecha de registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();
    
    @Column(nullable = false)
    @Schema(description = "Estado activo del usuario")
    private Boolean activo = true;
    
    public enum TipoUsuario {
        ADMIN, PACIENTE
    }
    
    // Constructors
    public Usuario() {}
    
    public Usuario(String email, String password, String nombre, TipoUsuario tipo) {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.tipo = tipo;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public TipoUsuario getTipo() { return tipo; }
    public void setTipo(TipoUsuario tipo) { this.tipo = tipo; }
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}

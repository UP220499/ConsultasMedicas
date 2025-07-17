package com.example.backend.entity;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "medicos")
@Schema(description = "Entidad que representa a un médico")
public class Medico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del médico", example = "1")
    private Long id;
    
    @Column(nullable = false)
    @Schema(description = "Nombre completo del médico", example = "Dr. Juan Pérez")
    private String nombre;
    
    @Column(nullable = false)
    @Schema(description = "Especialidad médica", example = "Cardiología")
    private String especialidad;
    
    @Column(nullable = false)
    @Schema(description = "Horario de atención", example = "Lunes a Viernes, 9am - 2pm")
    private String horario;
    
    @Column
    @Schema(description = "Email del médico", example = "juan.perez@hospital.com")
    private String email;
    
    @Column
    @Schema(description = "Teléfono del médico", example = "+52 55 1234 5678")
    private String telefono;
    
    // Constructors
    public Medico() {}
    
    public Medico(String nombre, String especialidad, String horario) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.horario = horario;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    
    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}

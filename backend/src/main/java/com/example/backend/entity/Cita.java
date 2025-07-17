package com.example.backend.entity;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Entity
@Table(name = "citas")
@Schema(description = "Entidad que representa una cita médica")
public class Cita {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la cita", example = "1")
    private Long id;
    
    @Column(nullable = false)
    @Schema(description = "Nombre del paciente", example = "Ana García")
    private String paciente;
    
    @Column(nullable = false)
    @Schema(description = "Email del paciente", example = "ana@example.com")
    private String email;
    
    @Column
    @Schema(description = "Teléfono del paciente", example = "+52 55 9876 5432")
    private String telefono;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medico_id", nullable = false)
    @Schema(description = "Médico asignado a la cita")
    private Medico medico;
    
    @Column(nullable = false)
    @Schema(description = "Fecha y hora de la cita", example = "2025-01-20T10:30:00")
    private LocalDateTime fechaHora;
    
    @Column(length = 500)
    @Schema(description = "Motivo de la consulta", example = "Revisión cardiológica")
    private String motivo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Estado de la cita", example = "PROGRAMADA")
    private EstadoCita estado = EstadoCita.PROGRAMADA;
    
    @Column(nullable = false)
    @Schema(description = "Fecha de creación de la cita")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    public enum EstadoCita {
        PROGRAMADA, COMPLETADA, CANCELADA
    }
    
    // Constructors
    public Cita() {}
    
    public Cita(String paciente, String email, Medico medico, LocalDateTime fechaHora, String motivo) {
        this.paciente = paciente;
        this.email = email;
        this.medico = medico;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getPaciente() { return paciente; }
    public void setPaciente(String paciente) { this.paciente = paciente; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
    
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    
    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }
    
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}

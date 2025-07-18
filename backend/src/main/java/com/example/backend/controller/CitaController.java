package com.example.backend.controller;

import com.example.backend.entity.Cita;
import com.example.backend.entity.Medico;
import com.example.backend.repository.CitaRepository;
import com.example.backend.repository.MedicoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
@Tag(name = "Citas", description = "API para gestión de citas médicas")
@CrossOrigin(origins = "*")
public class CitaController {
    
    @Autowired
    private CitaRepository citaRepository;
    
    @Autowired
    private MedicoRepository medicoRepository;
    
    @GetMapping
    @Operation(summary = "Obtener todas las citas", description = "Retorna la lista completa de citas médicas")
    public List<Cita> getAllCitas() {
        return citaRepository.findAll();
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener cita por ID", description = "Retorna una cita específica por su ID")
    public ResponseEntity<Cita> getCitaById(
            @Parameter(description = "ID de la cita", example = "1") @PathVariable Long id) {
        Optional<Cita> cita = citaRepository.findById(id);
        return cita.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/paciente/{email}")
    @Operation(summary = "Obtener citas por email del paciente", description = "Retorna todas las citas de un paciente")
    public List<Cita> getCitasByEmail(
            @Parameter(description = "Email del paciente", example = "paciente@example.com") @PathVariable String email) {
        return citaRepository.findCitasByEmailOrderByFecha(email);
    }
    
    @GetMapping("/medico/{medicoId}")
    @Operation(summary = "Obtener citas por médico", description = "Retorna todas las citas de un médico específico")
    public ResponseEntity<List<Cita>> getCitasByMedico(@PathVariable Long medicoId) {
        Optional<Medico> medico = medicoRepository.findById(medicoId);
        if (medico.isPresent()) {
            List<Cita> citas = citaRepository.findByMedico(medico.get());
            return ResponseEntity.ok(citas);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/estado/{estado}")
    @Operation(summary = "Obtener citas por estado", description = "Retorna citas filtradas por estado")
    public List<Cita> getCitasByEstado(@PathVariable Cita.EstadoCita estado) {
        return citaRepository.findByEstado(estado);
    }
    
    @PostMapping
    @Operation(summary = "Crear nueva cita", description = "Registra una nueva cita médica")
    public ResponseEntity<Cita> createCita(@RequestBody Cita cita) {
        // Validar que el médico existe
        if (cita.getMedico() != null && cita.getMedico().getId() != null) {
            Optional<Medico> medico = medicoRepository.findById(cita.getMedico().getId());
            if (medico.isPresent()) {
                cita.setMedico(medico.get());
                Cita savedCita = citaRepository.save(cita);
                return ResponseEntity.ok(savedCita);
            }
        }
        return ResponseEntity.badRequest().build();
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cita", description = "Actualiza los datos de una cita existente")
    public ResponseEntity<Cita> updateCita(@PathVariable Long id, @RequestBody Cita citaDetails) {
        Optional<Cita> optionalCita = citaRepository.findById(id);
        if (optionalCita.isPresent()) {
            Cita cita = optionalCita.get();
            cita.setPaciente(citaDetails.getPaciente());
            cita.setEmail(citaDetails.getEmail());
            cita.setTelefono(citaDetails.getTelefono());
            cita.setFechaHora(citaDetails.getFechaHora());
            cita.setMotivo(citaDetails.getMotivo());
            cita.setEstado(citaDetails.getEstado());
            
            if (citaDetails.getMedico() != null && citaDetails.getMedico().getId() != null) {
                Optional<Medico> medico = medicoRepository.findById(citaDetails.getMedico().getId());
                medico.ifPresent(cita::setMedico);
            }
            
            return ResponseEntity.ok(citaRepository.save(cita));
        }
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado de cita", description = "Cambia el estado de una cita (PROGRAMADA, COMPLETADA, CANCELADA)")
    public ResponseEntity<Cita> updateEstadoCita(@PathVariable Long id, @RequestParam Cita.EstadoCita estado) {
        Optional<Cita> optionalCita = citaRepository.findById(id);
        if (optionalCita.isPresent()) {
            Cita cita = optionalCita.get();
            cita.setEstado(estado);
            return ResponseEntity.ok(citaRepository.save(cita));
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cita", description = "Elimina una cita del sistema")
    public ResponseEntity<?> deleteCita(@PathVariable Long id) {
        if (citaRepository.existsById(id)) {
            citaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
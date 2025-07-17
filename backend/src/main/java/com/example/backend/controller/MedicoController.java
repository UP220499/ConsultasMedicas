package com.example.backend.controller;

import com.example.backend.entity.Medico;
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
@RequestMapping("/api/medicos")
@Tag(name = "Médicos", description = "API para gestión de médicos")
@CrossOrigin(origins = "*")
public class MedicoController {
    
    @Autowired
    private MedicoRepository medicoRepository;
    
    @GetMapping
    @Operation(summary = "Obtener todos los médicos", description = "Retorna la lista completa de médicos disponibles")
    public List<Medico> getAllMedicos() {
        return medicoRepository.findAll();
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener médico por ID", description = "Retorna un médico específico por su ID")
    public ResponseEntity<Medico> getMedicoById(
            @Parameter(description = "ID del médico", example = "1") @PathVariable Long id) {
        Optional<Medico> medico = medicoRepository.findById(id);
        return medico.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/especialidad/{especialidad}")
    @Operation(summary = "Buscar médicos por especialidad", description = "Retorna médicos que coincidan con la especialidad")
    public List<Medico> getMedicosByEspecialidad(
            @Parameter(description = "Especialidad médica", example = "Cardiología") @PathVariable String especialidad) {
        return medicoRepository.findByEspecialidadContainingIgnoreCase(especialidad);
    }
    
    @GetMapping("/especialidades")
    @Operation(summary = "Obtener especialidades disponibles", description = "Retorna lista de especialidades únicas")
    public List<String> getEspecialidades() {
        return medicoRepository.findDistinctEspecialidades();
    }
    
    @PostMapping
    @Operation(summary = "Crear nuevo médico", description = "Registra un nuevo médico en el sistema")
    public Medico createMedico(@RequestBody Medico medico) {
        return medicoRepository.save(medico);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar médico", description = "Actualiza los datos de un médico existente")
    public ResponseEntity<Medico> updateMedico(@PathVariable Long id, @RequestBody Medico medicoDetails) {
        Optional<Medico> optionalMedico = medicoRepository.findById(id);
        if (optionalMedico.isPresent()) {
            Medico medico = optionalMedico.get();
            medico.setNombre(medicoDetails.getNombre());
            medico.setEspecialidad(medicoDetails.getEspecialidad());
            medico.setHorario(medicoDetails.getHorario());
            medico.setEmail(medicoDetails.getEmail());
            medico.setTelefono(medicoDetails.getTelefono());
            return ResponseEntity.ok(medicoRepository.save(medico));
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar médico", description = "Elimina un médico del sistema")
    public ResponseEntity<?> deleteMedico(@PathVariable Long id) {
        if (medicoRepository.existsById(id)) {
            medicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

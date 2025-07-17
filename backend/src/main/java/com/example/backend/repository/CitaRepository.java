package com.example.backend.repository;

import com.example.backend.entity.Cita;
import com.example.backend.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    
    List<Cita> findByPacienteContainingIgnoreCase(String paciente);
    
    List<Cita> findByEmailIgnoreCase(String email);
    
    List<Cita> findByMedico(Medico medico);
    
    List<Cita> findByEstado(Cita.EstadoCita estado);
    
    @Query("SELECT c FROM Cita c WHERE c.medico.id = :medicoId AND c.fechaHora BETWEEN :inicio AND :fin")
    List<Cita> findByMedicoAndFechaBetween(@Param("medicoId") Long medicoId, 
                                          @Param("inicio") LocalDateTime inicio, 
                                          @Param("fin") LocalDateTime fin);
    
    @Query("SELECT c FROM Cita c WHERE c.email = :email ORDER BY c.fechaHora DESC")
    List<Cita> findCitasByEmailOrderByFecha(@Param("email") String email);
}

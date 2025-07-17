package com.example.backend.repository;

import com.example.backend.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    
    List<Medico> findByEspecialidadContainingIgnoreCase(String especialidad);
    
    List<Medico> findByNombreContainingIgnoreCase(String nombre);
    
    @Query("SELECT DISTINCT m.especialidad FROM Medico m ORDER BY m.especialidad")
    List<String> findDistinctEspecialidades();
}

package com.example.backend.config;

import com.example.backend.entity.Medico;
import com.example.backend.entity.Cita;
import com.example.backend.entity.Usuario;
import com.example.backend.repository.MedicoRepository;
import com.example.backend.repository.CitaRepository;
import com.example.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {
    
    @Autowired
    private MedicoRepository medicoRepository;
    
    @Autowired
    private CitaRepository citaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Solo cargar datos si no existen médicos
        if (medicoRepository.count() == 0) {
            loadUsuarios();
            loadMedicos();
            loadCitasEjemplo();
        }
    }
    
    private void loadUsuarios() {
        // Usuario administrador
        Usuario admin = new Usuario();
        admin.setEmail("admin@mediconsultas.com");
        admin.setPassword("admin123");
        admin.setNombre("Administrador del Sistema");
        admin.setTelefono("+52 55 0000 0000");
        admin.setTipo(Usuario.TipoUsuario.ADMIN);
        usuarioRepository.save(admin);
        
        // Usuarios pacientes de ejemplo
        Usuario paciente1 = new Usuario();
        paciente1.setEmail("maria@example.com");
        paciente1.setPassword("123456");
        paciente1.setNombre("María González");
        paciente1.setTelefono("+52 55 9876 5432");
        paciente1.setTipo(Usuario.TipoUsuario.PACIENTE);
        usuarioRepository.save(paciente1);
        
        Usuario paciente2 = new Usuario();
        paciente2.setEmail("juan@example.com");
        paciente2.setPassword("123456");
        paciente2.setNombre("Juan Martínez");
        paciente2.setTelefono("+52 55 1111 2222");
        paciente2.setTipo(Usuario.TipoUsuario.PACIENTE);
        usuarioRepository.save(paciente2);
    }
    
    private void loadMedicos() {
        Medico medico1 = new Medico("Dr. Juan Pérez", "Cardiología", "Lunes a Viernes, 9am - 2pm");
        medico1.setEmail("juan.perez@hospital.com");
        medico1.setTelefono("+52 55 1234 5678");
        
        Medico medico2 = new Medico("Dra. Ana Torres", "Pediatría", "Martes y Jueves, 3pm - 6pm");
        medico2.setEmail("ana.torres@hospital.com");
        medico2.setTelefono("+52 55 2345 6789");
        
        Medico medico3 = new Medico("Dr. Miguel Lara", "Dermatología", "Miércoles y Viernes, 10am - 1pm");
        medico3.setEmail("miguel.lara@hospital.com");
        medico3.setTelefono("+52 55 3456 7890");
        
        Medico medico4 = new Medico("Dra. Carmen Ruiz", "Ginecología", "Lunes a Miércoles, 2pm - 7pm");
        medico4.setEmail("carmen.ruiz@hospital.com");
        medico4.setTelefono("+52 55 4567 8901");
        
        medicoRepository.save(medico1);
        medicoRepository.save(medico2);
        medicoRepository.save(medico3);
        medicoRepository.save(medico4);
    }
    
    private void loadCitasEjemplo() {
        Medico cardiologo = medicoRepository.findByEspecialidadContainingIgnoreCase("Cardiología").get(0);
        
        Cita cita1 = new Cita("María González", "maria@example.com", cardiologo, 
                             LocalDateTime.now().plusDays(1), "Revisión cardiológica rutinaria");
        cita1.setTelefono("+52 55 9876 5432");
        
        citaRepository.save(cita1);
    }
}

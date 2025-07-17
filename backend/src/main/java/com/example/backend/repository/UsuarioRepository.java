package com.example.backend.repository;

import com.example.backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByEmail(String email);
    
    Optional<Usuario> findByEmailAndPassword(String email, String password);
    
    List<Usuario> findByTipo(Usuario.TipoUsuario tipo);
    
    List<Usuario> findByActivoTrue();
    
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.activo = true")
    Optional<Usuario> findByEmailAndActivo(@Param("email") String email);
    
    @Query("SELECT u FROM Usuario u WHERE u.tipo = :tipo AND u.activo = true")
    List<Usuario> findByTipoAndActivo(@Param("tipo") Usuario.TipoUsuario tipo);
}

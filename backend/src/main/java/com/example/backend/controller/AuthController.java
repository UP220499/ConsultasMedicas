package com.example.backend.controller;

import com.example.backend.dto.*;
import com.example.backend.entity.Usuario;
import com.example.backend.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "API para login y registro de usuarios")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario con email y contraseña")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            // Validar campos obligatorios
            if (request.getEmail() == null || request.getEmail().trim().isEmpty() ||
                request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new AuthResponse(false, "Email y contraseña son obligatorios"));
            }
            
            // Buscar usuario por email y password (en producción debería ser hasheado)
            Optional<Usuario> usuarioOpt = usuarioRepository.findByEmailAndPassword(
                request.getEmail().trim(), request.getPassword());
            
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                
                // Verificar que el usuario esté activo
                if (!usuario.getActivo()) {
                    return ResponseEntity.badRequest()
                        .body(new AuthResponse(false, "Usuario desactivado"));
                }
                
                // Login exitoso
                AuthResponse.UsuarioDto usuarioDto = new AuthResponse.UsuarioDto(usuario);
                return ResponseEntity.ok(new AuthResponse(true, "Login exitoso", usuarioDto));
                
            } else {
                return ResponseEntity.badRequest()
                    .body(new AuthResponse(false, "Credenciales incorrectas"));
            }
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(new AuthResponse(false, "Error interno del servidor"));
        }
    }
    
    @PostMapping("/register")
    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario en el sistema")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        try {
            // Validar campos obligatorios
            if (request.getEmail() == null || request.getEmail().trim().isEmpty() ||
                request.getPassword() == null || request.getPassword().trim().isEmpty() ||
                request.getNombre() == null || request.getNombre().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new AuthResponse(false, "Email, contraseña y nombre son obligatorios"));
            }
            
            // Verificar que el email no exista
            if (usuarioRepository.existsByEmail(request.getEmail().trim())) {
                return ResponseEntity.badRequest()
                    .body(new AuthResponse(false, "El email ya está registrado"));
            }
            
            // Solo permitir crear admin con credenciales especiales
            if (request.getTipo() == Usuario.TipoUsuario.ADMIN) {
                if (!"admin@mediconsultas.com".equals(request.getEmail()) || 
                    !"admin123".equals(request.getPassword())) {
                    return ResponseEntity.badRequest()
                        .body(new AuthResponse(false, "No autorizado para crear cuenta de administrador"));
                }
            }
            
            // Crear nuevo usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setEmail(request.getEmail().trim());
            nuevoUsuario.setPassword(request.getPassword()); // En producción: hashear
            nuevoUsuario.setNombre(request.getNombre().trim());
            nuevoUsuario.setTelefono(request.getTelefono());
            nuevoUsuario.setTipo(request.getTipo());
            
            Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
            
            AuthResponse.UsuarioDto usuarioDto = new AuthResponse.UsuarioDto(usuarioGuardado);
            return ResponseEntity.ok(new AuthResponse(true, "Usuario registrado exitosamente", usuarioDto));
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(new AuthResponse(false, "Error interno del servidor"));
        }
    }
    
    @PostMapping("/validate")
    @Operation(summary = "Validar sesión", description = "Valida si un usuario existe y está activo")
    public ResponseEntity<AuthResponse> validateUser(@RequestBody LoginRequest request) {
        try {
            Optional<Usuario> usuarioOpt = usuarioRepository.findByEmailAndActivo(request.getEmail());
            
            if (usuarioOpt.isPresent()) {
                AuthResponse.UsuarioDto usuarioDto = new AuthResponse.UsuarioDto(usuarioOpt.get());
                return ResponseEntity.ok(new AuthResponse(true, "Usuario válido", usuarioDto));
            } else {
                return ResponseEntity.badRequest()
                    .body(new AuthResponse(false, "Usuario no encontrado"));
            }
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(new AuthResponse(false, "Error interno del servidor"));
        }
    }
}

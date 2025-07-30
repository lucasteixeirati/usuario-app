package br.com.usuarioapp.usuario_app.repository;

import br.com.usuarioapp.usuario_app.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}

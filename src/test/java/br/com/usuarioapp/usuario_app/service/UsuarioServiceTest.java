package br.com.usuarioapp.usuario_app.service;

import br.com.usuarioapp.usuario_app.model.Usuario;
import br.com.usuarioapp.usuario_app.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceTest {

    private UsuarioRepository repository;
    private UsuarioService service;

    @BeforeEach
    void setUp() {
        repository = mock(UsuarioRepository.class);
        service = new UsuarioService(repository);
    }

    @Test
    void deveListarUsuarios() {
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNome("Lucas");
        usuario1.setEmail("lucas@email.com");

        when(repository.findAll()).thenReturn(List.of(usuario1));

        List<Usuario> usuarios = service.listar();

        assertEquals(1, usuarios.size());
        assertEquals("Lucas", usuarios.get(0).getNome());
    }

    @Test
    void deveBuscarUsuarioPorId() {
        Usuario usuario = new Usuario();
        usuario.setId(2L);

        when(repository.findById(2L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> encontrado = service.buscarPorId(2L);

        assertTrue(encontrado.isPresent());
        assertEquals(2L, encontrado.get().getId());
    }

    @Test
    void deveSalvarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNome("Ana");

        when(repository.save(usuario)).thenReturn(usuario);

        Usuario salvo = service.salvar(usuario);

        assertEquals("Ana", salvo.getNome());
    }

    @Test
    void deveAtualizarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNome("Carlos");

        when(repository.save(ArgumentMatchers.any(Usuario.class))).thenReturn(usuario);

        Usuario atualizado = service.atualizar(10L, usuario);

        assertEquals("Carlos", atualizado.getNome());
        assertEquals(10L, atualizado.getId());
    }

    @Test
    void deveDeletarUsuario() {
        service.deletar(5L);
        verify(repository, times(1)).deleteById(5L);
    }
}

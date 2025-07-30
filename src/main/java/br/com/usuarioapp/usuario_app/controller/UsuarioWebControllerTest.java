package br.com.usuarioapp.usuario_app.controller;

import br.com.usuarioapp.usuario_app.service.UsuarioService;
import br.com.usuarioapp.usuario_app.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioWebController.class)
class UsuarioWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService service;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(1L, "Lucas", "lucas@email.com");
    }

    @Test
    void deveMostrarListaDeUsuarios() throws Exception {
        when(service.listar()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(view().name("usuarios/lista"))
                .andExpect(model().attributeExists("usuarios"));
    }

    @Test
    void deveMostrarFormularioDeNovoUsuario() throws Exception {
        mockMvc.perform(get("/usuarios/novo"))
                .andExpect(status().isOk())
                .andExpect(view().name("usuarios/form"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    void deveSalvarUsuarioENavegarParaLista() throws Exception {
        when(service.salvar(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/usuarios")
                        .param("nome", "Lucas")
                        .param("email", "lucas@email.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/usuarios"));
    }

    @Test
    void deveMostrarFormularioDeEdicao() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/usuarios/editar/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("usuarios/form"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    void deveAtualizarUsuarioERedirecionar() throws Exception {
        when(service.atualizar(eq(1L), any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/usuarios/atualizar/1")
                        .param("nome", "Lucas Atualizado")
                        .param("email", "lucas@email.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/usuarios"));
    }
}

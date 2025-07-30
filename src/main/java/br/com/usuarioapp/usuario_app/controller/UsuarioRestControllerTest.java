package br.com.usuarioapp.usuario_app.controller;

import br.com.usuarioapp.usuario_app.model.Usuario;
import br.com.usuarioapp.usuario_app.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioRestController.class)
class UsuarioRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService service;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Lucas");
        usuario.setEmail("lucas@email.com");
    }

    @Test
    void deveListarUsuarios() throws Exception {
        when(service.listar()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Lucas"));
    }

    @Test
    void deveBuscarUsuarioPorId() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Lucas"));
    }

    @Test
    void deveRetornar404SeUsuarioNaoExistir() throws Exception {
        when(service.buscarPorId(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveCriarUsuario() throws Exception {
        when(service.salvar(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Lucas"));
    }

    @Test
    void deveAtualizarUsuario() throws Exception {
        when(service.atualizar(eq(1L), any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("lucas@email.com"));
    }

    @Test
    void deveDeletarUsuario() throws Exception {
        doNothing().when(service).deletar(1L);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

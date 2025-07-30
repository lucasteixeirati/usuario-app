package br.com.usuarioapp.usuario_app.controller;

import br.com.usuarioapp.usuario_app.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuarioWebController {

    private final UsuarioService service;

    public UsuarioWebController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/lista")
    public String listarHtml(Model model) {
        model.addAttribute("usuarios", service.listar());
        return "usuarios";
    }
}


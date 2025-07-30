package br.com.usuarioapp.usuario_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Gera getters, setters, toString, equals, hashCode
@NoArgsConstructor // Gera construtor vazio
@AllArgsConstructor // Gera construtor com todos os campos
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;
}

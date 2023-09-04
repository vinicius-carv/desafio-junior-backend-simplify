package com.vinidev.DesafioSimplifyTec.rest;

import com.vinidev.DesafioSimplifyTec.DAO.TarefaDAO;
import com.vinidev.DesafioSimplifyTec.DAO.UsuarioDAO;
import com.vinidev.DesafioSimplifyTec.common.MetodosTarefa;
import com.vinidev.DesafioSimplifyTec.common.MetodosUsuario;
import com.vinidev.DesafioSimplifyTec.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EndpointController {
    private final TarefaDAO tarefaDAO;
    private final UsuarioDAO usuarioDAO;
    // Injetando os DAOs para os endpoints
    @Autowired
    public EndpointController(TarefaDAO tarefaDAO, UsuarioDAO usuarioDAO) {
        this.tarefaDAO = tarefaDAO;
        this.usuarioDAO = usuarioDAO;
    }
    MetodosUsuario gerenciadorUsuario = new MetodosUsuario();
    MetodosTarefa gerenciadorTarefa = new MetodosTarefa();
    @PostMapping(value = "/cadastroUsuario", consumes = "application/x-www-form-urlencoded")
    public String cadastroUsuario(Usuario usuario){
        System.out.println("Cadastrando usuario: "+usuario.toString());
        usuarioDAO.save(usuario);
        return "Usuario salvo, bem-vindo "+usuario.getNome();
    }
}

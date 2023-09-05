package com.vinidev.DesafioSimplifyTec.rest;

import com.vinidev.DesafioSimplifyTec.DAO.TarefaDAO;
import com.vinidev.DesafioSimplifyTec.DAO.UsuarioDAO;
import com.vinidev.DesafioSimplifyTec.common.MetodosTarefa;
import com.vinidev.DesafioSimplifyTec.common.MetodosUsuario;
import com.vinidev.DesafioSimplifyTec.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api")
public class EndpointController {
    private final TarefaDAO tarefaDAO;
    private final UsuarioDAO usuarioDAO;

    @Autowired
    public EndpointController(TarefaDAO tarefaDAO, UsuarioDAO usuarioDAO) {
        this.tarefaDAO = tarefaDAO;
        this.usuarioDAO = usuarioDAO;
    }

    MetodosUsuario gerenciadorUsuario = new MetodosUsuario();
    MetodosTarefa gerenciadorTarefa = new MetodosTarefa();

    @PostMapping(value = "/cadastroUsuario", consumes = "application/x-www-form-urlencoded")
    public String cadastroUsuario(Usuario usuario) {
        System.out.println("Cadastrando usuario: " + usuario.toString());

        // You should hash and salt the user's password here before saving it to the database.
        // Example: usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        usuarioDAO.save(usuario);
        return "Usuario salvo, bem-vindo " + usuario.getNome();
    }

    @PostMapping(value = "/loginUsuario", consumes = "application/x-www-form-urlencoded")
    public RedirectView loginUsuario(Usuario usuario, RedirectAttributes attributes, Model model) {
        System.out.println("Logando usuario: " + usuario.getEmail());

        Usuario usuarioLogin = usuarioDAO.findByEmail(usuario.getEmail());
        if (usuarioLogin != null) {
            // You should use a password encoder here to compare the hashed password.
            // Example: if (passwordEncoder.matches(usuario.getPassword(), usuarioLogin.getPassword())) {
            if (usuario.getSenha().equals(usuarioLogin.getSenha())) {
                System.out.println("Sucesso em logar");
                model.addAttribute("Usuario", usuarioLogin);
                return new RedirectView("/dashboard");
            } else {
                return new RedirectView("/login");
            }
        } else {
            return new RedirectView("/login");
        }
    }
}
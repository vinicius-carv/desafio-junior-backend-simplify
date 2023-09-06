package com.vinidev.DesafioSimplifyTec.rest;

import com.vinidev.DesafioSimplifyTec.DAO.TarefaDAO;
import com.vinidev.DesafioSimplifyTec.DAO.UsuarioDAO;
import com.vinidev.DesafioSimplifyTec.common.MetodosTarefa;
import com.vinidev.DesafioSimplifyTec.common.MetodosUsuario;
import com.vinidev.DesafioSimplifyTec.common.ServicoTarefa;
import com.vinidev.DesafioSimplifyTec.entity.Tarefa;
import com.vinidev.DesafioSimplifyTec.entity.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api")
public class EndpointController {
    private final TarefaDAO tarefaDAO;
    private final UsuarioDAO usuarioDAO;
    private final ServicoTarefa servicoTarefa;

    @Autowired
    public EndpointController(TarefaDAO tarefaDAO, UsuarioDAO usuarioDAO, ServicoTarefa servicoTarefa) {
        this.tarefaDAO = tarefaDAO;
        this.usuarioDAO = usuarioDAO;
        this.servicoTarefa = servicoTarefa;
    }

    MetodosUsuario gerenciadorUsuario = new MetodosUsuario();
    MetodosTarefa gerenciadorTarefa = new MetodosTarefa();

    @PostMapping(value = "/cadastroUsuario", consumes = "application/x-www-form-urlencoded")
    public RedirectView cadastroUsuario(Usuario usuario) {
        System.out.println("Cadastrando usuario: " + usuario.toString());

        usuarioDAO.save(usuario);
        return new RedirectView("/dashboard");
    }

    @PostMapping(value = "/loginUsuario", consumes = "application/x-www-form-urlencoded", produces = "application/x-www-form-urlencoded")
    public RedirectView loginUsuario(Usuario usuario, HttpSession session) {
        System.out.println("Logando usuario: " + usuario.getEmail());

        Usuario usuarioLogin = usuarioDAO.findByEmail(usuario.getEmail());
        if (usuarioLogin != null) {
            if (usuario.getSenha().equals(usuarioLogin.getSenha())) {
                System.out.println("Sucesso em realizar o login");
                session.setAttribute("loggedUser", usuarioLogin);
                return new RedirectView("/dashboard");
            } else {
                return new RedirectView("/login");
            }
        } else {
            return new RedirectView("/login");
        }
    }
    @PostMapping(value = "/criarTarefa", consumes = "application/x-www-form-urlencoded")
    public RedirectView novaTarefa(Tarefa novaTarefa, HttpSession session){
        Usuario loggedUser = (Usuario) session.getAttribute("loggedUser");
        novaTarefa.setIdCriador(loggedUser.getId());
        System.out.println("Criando tarefa: "+ novaTarefa.toString());
        tarefaDAO.save(novaTarefa);
        System.out.println("Nova tarefa criada id: "+novaTarefa.getId());
        return new RedirectView("/dashboard");
    }
    @GetMapping(value = "/excluirTarefa/{id}")
    public RedirectView excluirTarefa(@PathVariable("id") int id, HttpSession session, TarefaDAO tarefaDAO){
        Usuario loggedUser = (Usuario) session.getAttribute("loggedUser");
        Tarefa tarefaExcluida = servicoTarefa.findById(id);
        if (loggedUser.getId() == tarefaExcluida.getIdCriador()) {
            servicoTarefa.deleteTask(id);
            System.out.println("Tarefa excluida");
            return new RedirectView("/tarefas");
        }else if(loggedUser.getId() != tarefaExcluida.getIdCriador()){
            return new RedirectView("/dashboard");
        }else{
            return new RedirectView("/login");
        }
    }
    @PostMapping(value = "/editarTarefa", consumes = "application/x-www-form-urlencoded")
    public RedirectView editarTarefa(Tarefa tarefaEditada, HttpSession session){
        Usuario loggedUser = (Usuario) session.getAttribute("loggedUser");
        tarefaEditada.setIdCriador(loggedUser.getId());
        System.out.println("Criando tarefa: "+ tarefaEditada.toString());
        tarefaDAO.save(tarefaEditada);
        System.out.println("Nova tarefa criada id: "+tarefaEditada.getId());
        return new RedirectView("/dashboard");
    }
}
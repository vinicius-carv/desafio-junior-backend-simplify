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
    @GetMapping(value = "/concluirTarefa/{id}")
    public RedirectView concluirTarefa(@PathVariable("id") int id, HttpSession session){
        Usuario loggedUser = (Usuario) session.getAttribute("loggedUser");
        Tarefa tarefaConcluida = tarefaDAO.findById(id);
        tarefaConcluida.setRealizado(1);
        System.out.println("Finalzando tarefa: "+ tarefaConcluida.toString());
        tarefaDAO.update(tarefaConcluida);
        return new RedirectView("/dashboard");
    }
    @GetMapping(value = "/abrirTarefa/{id}")
    public RedirectView abrirTarefa(@PathVariable("id") int id, HttpSession session){
        Usuario loggedUser = (Usuario) session.getAttribute("loggedUser");
        Tarefa tarefaAberta = tarefaDAO.findById(id);
        tarefaAberta.setRealizado(0);
        System.out.println("Reabrindo tarefa: "+ tarefaAberta.toString());
        tarefaDAO.update(tarefaAberta);
        return new RedirectView("/dashboard");
    }
    @PostMapping(value = "/editarTarefa/{id}", consumes = "application/x-www-form-urlencoded")
    public RedirectView editarTarefa(@PathVariable("id") int id, Tarefa tarefaEditada, HttpSession session) {
        Usuario loggedUser = (Usuario) session.getAttribute("loggedUser");
        System.out.println("Editando tarefa com id: " + id);
        System.out.println("Tarefa com novos dados: " + tarefaEditada.toString());

        // Fetch the existing task from the database
        Tarefa tarefaASerEditada = servicoTarefa.findById(id);
        System.out.println("Tarefa com dados antigos: " + tarefaASerEditada.toString());
        // Check if the task exists
        if (tarefaASerEditada == null) {
            // Handle the case where the task does not exist, e.g., return an error page or message
            // You can also redirect to a different page if needed
            return new RedirectView("/error-page");
        }

        // Set the properties of the task only if it exists
        tarefaASerEditada.setNome(tarefaEditada.getNome());
        tarefaASerEditada.setDescricao(tarefaEditada.getDescricao());
        tarefaASerEditada.setRealizado(tarefaEditada.getRealizado());
        tarefaASerEditada.setPrioridade(tarefaEditada.getPrioridade());
        System.out.println("Editando tarefa: " + tarefaASerEditada.toString());

        // Update the task in the database
        tarefaDAO.update(tarefaASerEditada);
        System.out.println("Tarefa editada id: " + tarefaASerEditada.getId());

        // Redirect to the dashboard or another appropriate page
        return new RedirectView("/dashboard");
    }
}
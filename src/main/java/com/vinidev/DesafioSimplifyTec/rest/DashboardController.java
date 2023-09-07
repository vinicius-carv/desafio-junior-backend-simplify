package com.vinidev.DesafioSimplifyTec.rest;

import com.vinidev.DesafioSimplifyTec.DAO.TarefaDAO;
import com.vinidev.DesafioSimplifyTec.common.MetodosTarefa;
import com.vinidev.DesafioSimplifyTec.common.ServicoTarefa;
import com.vinidev.DesafioSimplifyTec.entity.Tarefa;
import com.vinidev.DesafioSimplifyTec.entity.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class DashboardController {
    private final ServicoTarefa servicoTarefa;
    @Autowired
    public DashboardController(ServicoTarefa servicoTarefa) {
        this.servicoTarefa = servicoTarefa;
    }
    @GetMapping("/dashboard")
    public String dashboardPage(HttpSession session, Model model, TarefaDAO tarefaDAO) {
        Usuario loggedUser = (Usuario) session.getAttribute("loggedUser");
        if (loggedUser != null) {
            model.addAttribute("Usuario", loggedUser);

            // Fetch the number of tasks for the logged-in user using the service
            int numTarefas = servicoTarefa.getTaskCountByUser(loggedUser.getId());
            model.addAttribute("numTarefasUsuario", numTarefas);

            return "dashboard";
        } else {
            // Handle the case where the user is not logged in
            return "redirect:/login";
        }
    }
    @GetMapping(value = "/novaTarefa", produces = "application/x-www-form-urlencoded")
    public String criarTarefaPage(HttpSession session, Model model){
        Usuario loggedUser = (Usuario) session.getAttribute("loggedUser");
        if (loggedUser != null) {
            Tarefa novaTarefa = new Tarefa();
            model.addAttribute("Usuario", loggedUser);
            System.out.println("id criador: "+loggedUser.getId());
            model.addAttribute("Tarefa", novaTarefa);
            return "novaTarefa";
        } else {
            // Handle the case where the user is not logged in
            return "redirect:/login";
        }
    }
    @GetMapping(value = "/editarTarefa/{id}", produces = "application/x-www-form-urlencoded")
    public String editarTarefaPage(@PathVariable("id") int id, HttpSession session, Model model){
        System.out.println("pagina editar tarefa" + id);
        Usuario loggedUser = (Usuario) session.getAttribute("loggedUser");
        if (loggedUser != null) {
            Tarefa novaTarefa = servicoTarefa.findById(id);
            if(loggedUser.getId()==novaTarefa.getIdCriador()){
                model.addAttribute("Usuario", loggedUser);
                model.addAttribute("id",id);
                System.out.println("id criador: "+loggedUser.getId());
                model.addAttribute("Tarefa", novaTarefa);
                return "editarTarefa";
            }else{
                return "redirect:/dashboard";
            }
        } else {
            // Handle the case where the user is not logged in
            return "redirect:/login";
        }
    }
    @GetMapping(value = "/tarefas")
    public String verTarefasPage(HttpSession session, Model model){
        Usuario loggedUser = (Usuario) session.getAttribute("loggedUser");
        if(loggedUser!=null){
            List<Tarefa> listaTarefas = servicoTarefa.findByUser(loggedUser.getId());
            model.addAttribute("tarefasUsuario", listaTarefas);
            return "tarefas";
        }else{
            return "redirect:/login";
        }
    }
    @GetMapping(value = "/tarefa/{id}")
    public String detalhesTarefaPage(@PathVariable("id") int id, HttpSession session, Model model) {
        Usuario loggedUser = (Usuario) session.getAttribute("loggedUser");
        if (loggedUser != null) {
            // Assuming you have a method to find a specific task by its ID
            Tarefa tarefa = servicoTarefa.findById(id);
            if(loggedUser.getId() == tarefa.getIdCriador()){
                model.addAttribute("tarefa", tarefa);

                return "detalhesTarefa";
            }else{
                return "redirect:/dashboard";
            }
        }else {
            // Handle the case where the user is not logged in
            return "redirect:/login";
        }
    }
}

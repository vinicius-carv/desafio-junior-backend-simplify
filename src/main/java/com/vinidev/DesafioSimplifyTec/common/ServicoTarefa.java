package com.vinidev.DesafioSimplifyTec.common;

import com.vinidev.DesafioSimplifyTec.DAO.TarefaDAO;
import com.vinidev.DesafioSimplifyTec.entity.Tarefa;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoTarefa {
    private final TarefaDAO tarefaDAO;


    @Autowired
    public ServicoTarefa(TarefaDAO tarefaDAO) {
        this.tarefaDAO = tarefaDAO;
    }

    public int getTaskCountByUser(int userId) {
        return tarefaDAO.numTaskByUser(userId);
    }
    public List<Tarefa> findByUser(int idCriador){
        return tarefaDAO.findByUser(idCriador);
    }

    public Tarefa findById(int id) {
        return tarefaDAO.findById(id);
    }
    public void deleteTask(int id){
        Tarefa tarefaDeletar = tarefaDAO.findById(id);
        tarefaDAO.delete(tarefaDeletar);
    }
    public void editTask(int id, String nome, String descricao, int realizado, int prioridade){
        Tarefa tarefaEditada = tarefaDAO.findById(id);
        tarefaEditada.setNome(nome);
        tarefaEditada.setDescricao(descricao);
        tarefaEditada.setRealizado(realizado);
        tarefaEditada.setPrioridade(prioridade);
        tarefaDAO.update(tarefaEditada);
        System.out.println("Tarefa editada com sucesso, novos detalhes: " + tarefaEditada.toString());
    }
}

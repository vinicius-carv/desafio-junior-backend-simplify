package com.vinidev.DesafioSimplifyTec.DAO;


import com.vinidev.DesafioSimplifyTec.entity.Tarefa;

import java.util.List;

public interface TarefaDAO {
    // Implementacao do CRUD das Tarefas
    // Salvar e commit do usuario
    void save(Tarefa tarefaAtual);
    // Encontrar via id
    Tarefa findById(int id);
    // Retorna todos as tarefas
    List<Tarefa> findAll();
    // Retorna as tarefas com determinado nome
    List<Tarefa> findByName(String name);
    // Retorna tarefas por usuario
    List<Tarefa> findByUser(int idCriador);
    // Atualiza a tarefa
    int numTaskByUser(int idCriador);
    void update(Tarefa tarefaAtual);
    // Deleta uma tarefa
    void delete(Tarefa tarefaDeletada);
}

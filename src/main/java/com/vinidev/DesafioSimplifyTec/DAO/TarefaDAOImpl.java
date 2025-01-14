package com.vinidev.DesafioSimplifyTec.DAO;

import com.vinidev.DesafioSimplifyTec.entity.Tarefa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TarefaDAOImpl implements TarefaDAO{
    // O Entity Manager nesse caso, e utilizado para realizar as operacoes com o banco de dados
    @PersistenceContext
    EntityManager gerenciadorDeEntidade;
    // Injetando o Entity manager
    @Autowired
    public TarefaDAOImpl(EntityManager theEntityManager){
        gerenciadorDeEntidade = theEntityManager;
    }
    // Salvando Tarefa
    @Override
    @Transactional
    public void save(Tarefa tarefaAtual){
        gerenciadorDeEntidade.persist(tarefaAtual);
    }
    // Encontrando Tarefa por id
    @Override
    public Tarefa findById(int id){
        return gerenciadorDeEntidade.find(Tarefa.class, id);
    }
    // Retorna todas as tarefas do banco
    @Override
    public List<Tarefa> findAll(){
        return gerenciadorDeEntidade.createQuery("select p from Tarefa",Tarefa.class).getResultList();
    }
    // Encontrar por nome
    @Override
    public List<Tarefa> findByName(String name){
        TypedQuery<Tarefa> theQuery = gerenciadorDeEntidade.createQuery("SELECT p from Tarefa p where nome=:nome",Tarefa.class);
        theQuery.setParameter("nome",name);
        return theQuery.getResultList();
    }
    // Encontra por usuario criador
    @Override
    public List<Tarefa> findByUser(int idCriador){
        TypedQuery<Tarefa> theQuery = gerenciadorDeEntidade.createQuery("SELECT p from Tarefa p where idCriador=:idCriador",Tarefa.class);
        theQuery.setParameter("idCriador",idCriador);
        return theQuery.getResultList();
    }
    @Override
    public int numTaskByUser(int idCriador) {
        TypedQuery<Long> query = gerenciadorDeEntidade.createQuery(
                "SELECT COUNT(t) FROM Tarefa t WHERE t.idCriador = :idCriador", Long.class);
        query.setParameter("idCriador", idCriador);
        return query.getSingleResult().intValue();
    }
    // Atualiza/altera dados da Tarefa
    @Override
    @Transactional
    public void update(Tarefa tarefaAtual){
        gerenciadorDeEntidade.merge(tarefaAtual);
    }
    // Deleta a tarefa ¯\_(ツ)_/¯
    @Override
    @Transactional
    public void delete(Tarefa tarefaDeletada){
        gerenciadorDeEntidade.remove(tarefaDeletada);
    }
}

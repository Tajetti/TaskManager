package br.com.tajetti.Service;

import br.com.tajetti.Model.DAO.TarefaDAO;
import br.com.tajetti.Model.Entity.StatusTarefa;
import br.com.tajetti.Model.Entity.Tarefa;

import java.util.List;

public class TarefaService {

    private final TarefaDAO tarefaDAO;

    public TarefaService() {
        this.tarefaDAO = new TarefaDAO();
    }

    public void criarTarefa(String titulo, String descricao) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O titulo não pode esta vazio!");
        }
        Tarefa tarefa = new Tarefa(titulo, descricao);
        tarefaDAO.salvar(tarefa);
    }

    public List<Tarefa> listarTodas() {
        return tarefaDAO.listarTodas();
    }

    public Tarefa buscarPorId(int id) {
        Tarefa tarefa = tarefaDAO.findById(id);

        if(tarefa == null) {
            throw new IllegalArgumentException("Tarefa não encontrada para o id: " + id);
        }

        return tarefa;
    }

    public boolean deletar(int id) {
        boolean deletou = tarefaDAO.deletar(id);
        if(!deletou) {
            throw new IllegalArgumentException("Tarefa não encontrada para excluir");
        }
        return deletou;
    }

    public void atualizarTarefa(int id, String titulo, String descricao) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O titulo não pode esta vazio!");
        }
        tarefaDAO.atualizar(id, titulo, descricao);
    }

    public void atualizarStatus(int id, StatusTarefa status) {
        tarefaDAO.atualizarStatus(status, id);
    }
}

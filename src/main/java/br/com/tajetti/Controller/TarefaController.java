package br.com.tajetti.Controller;

import br.com.tajetti.Model.Entity.StatusTarefa;
import br.com.tajetti.Model.Entity.Tarefa;
import br.com.tajetti.Service.TarefaService;

import java.util.List;

public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController() {
        this.tarefaService = new TarefaService();
    }

    public void buscarPorId(int id) {
        Tarefa tarefa = tarefaService.buscarPorId(id);

        if (tarefa != null) {
            System.out.println(tarefa);
        } else {
            System.out.println("❌ Tarefa não encontrada");
        }
    }

    public void listarTodos() {
        tarefaService.listarTodas();
    }

    public List<Tarefa> listar() {
        return tarefaService.listarTodas();
    }

    public void criarTarefa(String titulo, String descricao) {
        try {
            tarefaService.criarTarefa(titulo, descricao);
            System.out.println("✅ Tarefa criada com sucesso");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        boolean deletado = tarefaService.deletar(id);

        if (deletado) {
            System.out.println("🗑️ Tarefa deletada");
        } else {
            System.out.println("❌ Tarefa não encontrada");
        }
    }

    public void atualizarTarefa(int id, String titulo, String descricao) {
        try {
            tarefaService.atualizarTarefa(id, titulo, descricao);
            System.out.println("✅ Tarefa atualizada com sucesso");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    public void marcarComoConcluida(int id) {
        tarefaService.atualizarStatus(id, StatusTarefa.CONCLUIDA);
        System.out.println("✅ Tarefa marcada como concluída");
    }
}

package br.com.tajetti.Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.com.tajetti.Model.Database.ConnectionFactory;
import br.com.tajetti.Model.Entity.Tarefa;
import br.com.tajetti.Model.Entity.StatusTarefa;

public class TarefaDAO {
    
    public TarefaDAO() {
        criarTabelaSeNaoExistir();
    }

    private void criarTabelaSeNaoExistir() {
        String sql = """
            CREATE TABLE IF NOT EXISTS tarefas (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                titulo VARCHAR(255) NOT NULL,
                descricao TEXT,
                status VARCHAR(50) DEFAULT 'PENDENTE',
                criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar tabela", e);
        }
    }

    public void salvar(Tarefa tarefa) {
        String sql = "INSERT INTO tarefas (titulo, descricao, status, criado_em) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, tarefa.getTitulo());
            ps.setString(2, tarefa.getDescricao());
            ps.setString(3, tarefa.getStatus().name());
            ps.setTimestamp(4, Timestamp.valueOf(tarefa.getCriadoEm()));

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar tarefa", e);
        }
    }

    public List<Tarefa> listarTodas() {
        List<Tarefa> tarefas = new ArrayList<>();
        String sql = "SELECT id, titulo, descricao, status, criado_em FROM tarefas";

        try(Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

                while(rs.next()) {
                    int id = rs.getInt("id");
                    String titulo = rs.getString("titulo");
                    String descricao = rs.getString("descricao");
                    String statusStr = rs.getString("status");
                    Timestamp criadoEmTs = rs.getTimestamp("criado_em");
                    Tarefa tarefa = new Tarefa(
                            id,
                            titulo,
                            descricao,
                            StatusTarefa.valueOf(statusStr),
                            criadoEmTs.toLocalDateTime()
                    );
                    tarefas.add(tarefa);
                }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar tarefas", e);
        }
        return tarefas;
    }

    public Tarefa findById(int id) {
        String sql = "SELECT * FROM TAREFAS WHERE id = ?";

        try(Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);){

            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()) {
                    return new Tarefa(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("descricao"),
                            StatusTarefa.valueOf(rs.getString("status")),
                            rs.getTimestamp("criado_em").toLocalDateTime()
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao procurar o id");
        }
        return null;
    }

    public void atualizarStatus(StatusTarefa status, int id) {
        String sql = "UPDATE tarefas SET status = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status.name());
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar status da tarefa", e);

        }
    }

    public void atualizar(int id, String titulo, String descricao) {
        String sql = "UPDATE tarefas SET titulo = ?, descricao = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, titulo);
            ps.setString(2, descricao);
            ps.setInt(3, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar tarefa", e);
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM tarefas WHERE id = ?";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int linhasafetadas = ps.executeUpdate();

            return linhasafetadas > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar tarefa", e);
        }

    }
}
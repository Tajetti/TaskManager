package br.com.tajetti.Model.Entity;

import java.time.LocalDateTime;

public class Tarefa {
    
    private int id;
    private String titulo;
    private String descricao;
    private StatusTarefa status;
    private LocalDateTime criadoEm;

    public Tarefa(String titulo, String descricao) { // INSERT
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = StatusTarefa.PENDENTE;
        this.criadoEm = LocalDateTime.now();
    }

    public Tarefa(int id, String titulo, String descricao, StatusTarefa status, LocalDateTime criadoEm) { // SELECT
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.criadoEm = criadoEm;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public StatusTarefa getStatus() {
        return status;
    }

    public String getTitulo() {
        return titulo;
    }


    public void concluir() {
        this.status = StatusTarefa.CONCLUIDA;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", status=" + status +
                ", criadoEm=" + criadoEm +
                '}';
    }

}

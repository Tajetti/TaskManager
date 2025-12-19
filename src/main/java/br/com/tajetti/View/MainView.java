package br.com.tajetti.View;

import br.com.tajetti.Controller.TarefaController;
import br.com.tajetti.Model.Entity.Tarefa;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.Optional;

public class MainView {

    private final TarefaController controller;
    private final TableView<Tarefa> tabela;
    private final BorderPane layout;

    public MainView() {
        this.controller = new TarefaController();
        this.tabela = new TableView<>();
        this.layout = new BorderPane();

        tabela.setId("tarefas-table");
        configurarTabela();
        configurarTopo();
        carregarTarefas();
    }

    private void configurarTabela() {

        TableColumn<Tarefa, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(d.getValue().getTitulo())
        );

        TableColumn<Tarefa, String> colDescricao = new TableColumn<>("Descrição");
        colDescricao.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(d.getValue().getDescricao())
        );

        TableColumn<Tarefa, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(d.getValue().getStatus().name())
        );

        tabela.getColumns().addAll(colTitulo, colDescricao, colStatus);
    }

    private void configurarTopo() {
        Button btnNova = new Button("➕ Nova Tarefa");
        btnNova.setId("nova-tarefa-btn");

        btnNova.setOnAction(e -> {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Nova Tarefa");
            dialog.setHeaderText("Adicionar nova tarefa");

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            TextField tituloField = new TextField();
            tituloField.setPromptText("Digite o título da tarefa");

            TextField descField = new TextField();
            descField.setPromptText("Digite a descrição da tarefa");

            grid.add(new Label("Título:"), 0, 0);
            grid.add(tituloField, 1, 0);
            grid.add(new Label("Descrição:"), 0, 1);
            grid.add(descField, 1, 1);

            dialog.getDialogPane().setContent(grid);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String titulo = tituloField.getText().trim();
                String descricao = descField.getText().trim();
                if (!titulo.isEmpty()) {
                    controller.criarTarefa(titulo, descricao);
                    carregarTarefas();
                }
            }
        });

        Button btnEditar = new Button("✏️ Editar Tarefa");
        btnEditar.setId("editar-tarefa-btn");

        btnEditar.setOnAction(e -> {
            Tarefa selected = tabela.getSelectionModel().getSelectedItem();
            if (selected == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Seleção Necessária");
                alert.setHeaderText("Nenhuma tarefa selecionada");
                alert.setContentText("Por favor, selecione uma tarefa na tabela para editar.");
                alert.showAndWait();
                return;
            }

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Editar Tarefa");
            dialog.setHeaderText("Editar tarefa selecionada");

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            TextField tituloField = new TextField(selected.getTitulo());
            TextField descField = new TextField(selected.getDescricao());

            grid.add(new Label("Título:"), 0, 0);
            grid.add(tituloField, 1, 0);
            grid.add(new Label("Descrição:"), 0, 1);
            grid.add(descField, 1, 1);

            dialog.getDialogPane().setContent(grid);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String titulo = tituloField.getText().trim();
                String descricao = descField.getText().trim();
                if (!titulo.isEmpty()) {
                    controller.atualizarTarefa(selected.getId(), titulo, descricao);
                    carregarTarefas();
                }
            }
        });

        Button btnConcluir = new Button("✅ Concluir Tarefa");
        btnConcluir.setId("concluir-tarefa-btn");

        btnConcluir.setOnAction(e -> {
            Tarefa selected = tabela.getSelectionModel().getSelectedItem();
            if (selected == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Seleção Necessária");
                alert.setHeaderText("Nenhuma tarefa selecionada");
                alert.setContentText("Por favor, selecione uma tarefa na tabela para marcar como concluída.");
                alert.showAndWait();
                return;
            }
            controller.marcarComoConcluida(selected.getId());
            carregarTarefas();
        });

        Button btnDeletar = new Button("🗑️ Deletar Tarefa");
        btnDeletar.setId("deletar-tarefa-btn");

        btnDeletar.setOnAction(e -> {
            Tarefa selected = tabela.getSelectionModel().getSelectedItem();
            if (selected == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Seleção Necessária");
                alert.setHeaderText("Nenhuma tarefa selecionada");
                alert.setContentText("Por favor, selecione uma tarefa na tabela para deletar.");
                alert.showAndWait();
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmar Exclusão");
            confirm.setHeaderText("Tem certeza que deseja deletar a tarefa?");
            confirm.setContentText("Tarefa: " + selected.getTitulo());

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                controller.deletar(selected.getId());
                carregarTarefas();
            }
        });

        HBox topo = new HBox(10, btnNova, btnEditar, btnConcluir, btnDeletar);
        topo.setId("topo-hbox");
        layout.setTop(topo);
        layout.setCenter(tabela);
    }

    private void carregarTarefas() {
        tabela.setItems(
                FXCollections.observableArrayList(controller.listar())
        );
    }

    public BorderPane getLayout() {
        return layout;
    }
}

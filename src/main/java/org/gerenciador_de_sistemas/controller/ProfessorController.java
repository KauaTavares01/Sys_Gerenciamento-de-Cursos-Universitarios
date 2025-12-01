package org.gerenciador_de_sistemas.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.gerenciador_de_sistemas.dao.ProfessorDAO;
import org.gerenciador_de_sistemas.model.Professor;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProfessorController implements Initializable {

    @FXML private TextField txtNome;
    @FXML private TextField txtEmail;
    @FXML private TextField txtFormacao;

    @FXML private TableView<Professor> tbProfessores;
    @FXML private TableColumn<Professor, Long> colId;
    @FXML private TableColumn<Professor, String> colNome;
    @FXML private TableColumn<Professor, String> colEmail;
    @FXML private TableColumn<Professor, String> colFormacao;

    private final ProfessorDAO professorDAO = new ProfessorDAO();
    private ObservableList<Professor> observableProfessores;

    private Professor professorAtual;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColunas();
        carregarTabela();
        prepararNovo();
    }

    private void configurarColunas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colFormacao.setCellValueFactory(new PropertyValueFactory<>("formacao"));
    }

    private void carregarTabela() {
        List<Professor> lista = professorDAO.listarTodos();
        observableProfessores = FXCollections.observableArrayList(lista);
        tbProfessores.setItems(observableProfessores);
    }

    private void prepararNovo() {
        professorAtual = new Professor();
        txtNome.clear();
        txtEmail.clear();
        txtFormacao.clear();
    }

    @FXML
    private void onNovo() {
        prepararNovo();
    }

    @FXML
    private void onSalvar() {
        professorAtual.setNome(txtNome.getText());
        professorAtual.setEmail(txtEmail.getText());
        professorAtual.setFormacao(txtFormacao.getText());

        professorDAO.salvarOuAtualizar(professorAtual);
        carregarTabela();
        prepararNovo();
    }

    @FXML
    private void onExcluir() {
        Professor selecionado = tbProfessores.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            professorDAO.excluir(selecionado);
            carregarTabela();
        }
    }

    @FXML
    private void onTabelaClick() {
        Professor sel = tbProfessores.getSelectionModel().getSelectedItem();
        if (sel != null) {
            professorAtual = sel;
            txtNome.setText(sel.getNome());
            txtEmail.setText(sel.getEmail());
            txtFormacao.setText(sel.getFormacao());
        }
    }
}

package br.com.sistema.controll;

import br.com.sistema.conexao.Conexao;
import br.com.sistema.model.HistoricoVeiculo;
import br.com.sistema.model.dao.HistoricoVeiculoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaHistoricoVeiculo {

	@FXML
	private TableView<HistoricoVeiculo> tbDespesaVeiculo;

	@FXML
	private TableColumn<HistoricoVeiculo, String> clnTipo;

	@FXML
	private TableColumn<HistoricoVeiculo, String> clnDescricao;

	@FXML
	private TableColumn<HistoricoVeiculo, String> clnData;

	@FXML
	private TableColumn<HistoricoVeiculo, String> clnValor;

	@FXML
	private TableColumn<HistoricoVeiculo, String> clnResponsavel;

	@FXML
	private TextField lblNomeCliente;

	@FXML
	private TextField lblCpfCnpj;

	private Conexao conexao = Principal.getConexao();
	private static Integer despesa_id;
	private HistoricoVeiculoDAO historicoVeiculoDAO = null;
	private TelaHome telaListaVeiculos = null;

	public void initialize() {

		buscaDados();

		tbDespesaVeiculo.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarDespesa((HistoricoVeiculo) newValue));
		tbDespesaVeiculo.setOnMouseClicked(e -> {
			listaDadosCliente();
		});

	}

	public void selecionarDespesa(HistoricoVeiculo d) {
		despesa_id = d.getId();
		System.out.println("pegou id da tabela : " + despesa_id);
	}

	public void buscaDados() {
		telaListaVeiculos = new TelaHome();
		this.historicoVeiculoDAO = Principal.getHistoricoVeiculoDAO();
		clnData.setCellValueFactory(new PropertyValueFactory<>("dataFormatada"));
		clnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		clnResponsavel.setCellValueFactory(new PropertyValueFactory<>("responsavel"));
		clnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
		clnValor.setCellValueFactory(new PropertyValueFactory<>("valorFormatado"));
		ObservableList<HistoricoVeiculo> lista = FXCollections
				.observableArrayList(historicoVeiculoDAO.listarTodosIdVeiculo(telaListaVeiculos.getIdVeiculo()));
		tbDespesaVeiculo.setItems(lista);
		conexao.fecharConexao();

	}

	public void listaDadosCliente() {
		lblNomeCliente.setText("");
		lblCpfCnpj.setText("");
		this.historicoVeiculoDAO = Principal.getHistoricoVeiculoDAO();
		for (HistoricoVeiculo h : historicoVeiculoDAO.listarDadosCliente(despesa_id)) {
			lblNomeCliente.setText(h.getNomeCliente());
			lblCpfCnpj.setText(h.getCpfCnpj());
		}
		conexao.fecharConexao();

	}

}

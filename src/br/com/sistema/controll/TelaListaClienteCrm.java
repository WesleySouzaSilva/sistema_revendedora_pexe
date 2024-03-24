package br.com.sistema.controll;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.sistema.conexao.Conexao;
import br.com.sistema.model.ClienteCrm;
import br.com.sistema.model.Relatorio;
import br.com.sistema.model.dao.PessoaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

public class TelaListaClienteCrm {

	@FXML
	private Button btnEditar;

	@FXML
	private Button btnExcluir;

	@FXML
	private Button btnImprimir;

	@FXML
	private Button btnNovo;

	@FXML
	private Button btnPesquisar;

	@FXML
	private TableColumn<ClienteCrm, String> clnMarca;

	@FXML
	private TableColumn<ClienteCrm, String> clnModelo;

	@FXML
	private TableColumn<ClienteCrm, String> clnNome;

	@FXML
	private TableColumn<ClienteCrm, String> clnTelefone;

	@FXML
	private TableColumn<ClienteCrm, String> clnTipo;

	@FXML
	private TableColumn<ClienteCrm, BigDecimal> clnValorFinal;

	@FXML
	private TableColumn<ClienteCrm, BigDecimal> clnValorInicial;

	@FXML
	private ComboBox<String> cmbBuscar;

	@FXML
	private TableView<ClienteCrm> tbClientes;

	@FXML
	private TextField txtPesquisa;

	@FXML
	private Label lblQtdeInteresse;

	private PessoaDAO pessoaDAO = null;
	private Conexao conexao = Principal.getConexao();
	List<ClienteCrm> listaVeiculosEncontrados = new ArrayList<>();
	private static Integer clienteId = null;

	public void initialize() {

		comboBox();

		cmbBuscar.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtPesquisa.requestFocus();
					return;
				}

			}
		});

		txtPesquisa.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					pesquisar();
					return;
				}

			}
		});

		btnPesquisar.setOnAction(e -> {
			pesquisar();

		});

		btnEditar.setOnAction(e -> {
			try {
				editar();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btnExcluir.setOnAction(e -> {
			excluir();
		});

		btnImprimir.setOnAction(e -> {
			try {
				imprimir();
			} catch (JRException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btnNovo.setOnAction(e -> {
			try {
				chamarTela("TelaNovoVeiculoInteresse");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		tbClientes.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarTabela((ClienteCrm) newValue));

	}

	public Integer getIdTabela() {
		return clienteId;
	}

	private void selecionarTabela(ClienteCrm c) {
		if (c != null) {
			clienteId = c.getId();
		}
	}

	private Integer getQtdeEncontrado() {
		String estoque = "ESTOQUE";
		String consignado = "CONSIGNADO";
		this.pessoaDAO = Principal.getPessoaDAO();
		List<ClienteCrm> clientesEncontradosEstoque = pessoaDAO.obterClientesEncontrados(estoque);
		List<ClienteCrm> clientesEncontradosConsignado = pessoaDAO.obterClientesEncontrados(consignado);
		conexao.fecharConexao();

		listaVeiculosEncontrados.addAll(criarClientesCrm(clientesEncontradosEstoque));
		listaVeiculosEncontrados.addAll(criarClientesCrm(clientesEncontradosConsignado));

		return clientesEncontradosEstoque.size() + clientesEncontradosConsignado.size();
	}

	private List<ClienteCrm> criarClientesCrm(List<ClienteCrm> clientes) {
		List<ClienteCrm> clientesCriados = new ArrayList<>();

		for (ClienteCrm c : clientes) {
			ClienteCrm cliente = new ClienteCrm(null, c.getNomeCliente(), c.getTelefone(), c.getTipo(), c.getMarca(),
					c.getModelo(), c.getValorInicial(), c.getValorFinal(), c.getVeiculo(), c.getCategoria(),
					c.getMarcaVeiculo(), c.getAnoModelo(), c.getValorVenda(), c.getCor(), c.getPlaca(), c.getKm());

			clientesCriados.add(cliente);
		}

		return clientesCriados;
	}

	private void pesquisar() {
		if (cmbBuscar.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbBuscar);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione o menu BUSCAR!");
			dlg.showAndWait();
			cmbBuscar.requestFocus();
			return;

		} else {
			clnNome.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
			clnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
			clnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
			clnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
			clnModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
			clnValorInicial.setCellValueFactory(new PropertyValueFactory<>("valorInicial"));
			clnValorFinal.setCellValueFactory(new PropertyValueFactory<>("valorFinal"));

			switch (cmbBuscar.getValue()) {
			case "Tipo":
				this.pessoaDAO = Principal.getPessoaDAO();
				if (txtPesquisa.getText().isEmpty()) {
					ObservableList<ClienteCrm> lista = FXCollections
							.observableArrayList(pessoaDAO.listarTodosClienteCrm());
					tbClientes.setItems(lista);
				} else {
					ObservableList<ClienteCrm> lista = FXCollections
							.observableArrayList(pessoaDAO.listarTodosClienteCrm("tipo", txtPesquisa.getText()));
					tbClientes.setItems(lista);
				}
				conexao.fecharConexao();
				break;

			case "Marca":
				this.pessoaDAO = Principal.getPessoaDAO();
				if (txtPesquisa.getText().isEmpty()) {
					ObservableList<ClienteCrm> lista = FXCollections
							.observableArrayList(pessoaDAO.listarTodosClienteCrm());
					tbClientes.setItems(lista);
				} else {
					ObservableList<ClienteCrm> lista = FXCollections
							.observableArrayList(pessoaDAO.listarTodosClienteCrm("marca", txtPesquisa.getText()));
					tbClientes.setItems(lista);
				}
				conexao.fecharConexao();
				break;

			case "Modelo":
				this.pessoaDAO = Principal.getPessoaDAO();
				if (txtPesquisa.getText().isEmpty()) {
					ObservableList<ClienteCrm> lista = FXCollections
							.observableArrayList(pessoaDAO.listarTodosClienteCrm());
					tbClientes.setItems(lista);
				} else {
					ObservableList<ClienteCrm> lista = FXCollections
							.observableArrayList(pessoaDAO.listarTodosClienteCrm("modelo", txtPesquisa.getText()));
					tbClientes.setItems(lista);
				}
				conexao.fecharConexao();
				break;

			case "Todos":
				this.pessoaDAO = Principal.getPessoaDAO();
				if (txtPesquisa.getText().isEmpty()) {
					ObservableList<ClienteCrm> lista = FXCollections
							.observableArrayList(pessoaDAO.listarTodosClienteCrm());
					tbClientes.setItems(lista);
				} else {
					ObservableList<ClienteCrm> lista = FXCollections
							.observableArrayList(pessoaDAO.listarTodosClienteCrm("tipo", txtPesquisa.getText()));
					tbClientes.setItems(lista);
				}
				conexao.fecharConexao();
				break;

			default:
				break;
			}
		}

		lblQtdeInteresse.setText(String.valueOf(getQtdeEncontrado()));

	}

	private void imprimir() throws JRException {
		Relatorio relatorio = new Relatorio();
		if (listaVeiculosEncontrados.isEmpty()) {
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Não foram encontrados dados, relatorio não será gerado!");
			dlg.showAndWait();
			btnImprimir.requestFocus();
			return;
		} else {

			relatorio.gerarRelatorio(listaVeiculosEncontrados, "RelatorioClienteInteresse");
		}

	}

	private void editar() throws IOException {
		if (tbClientes.getSelectionModel().getSelectedItem() == null) {
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("selecione o cliente que deseja EDITAR!");
			dlg.showAndWait();
			btnExcluir.requestFocus();
			return;
		} else {
			chamarTela("");
		}

	}

	private void excluir() {
		if (tbClientes.getSelectionModel().getSelectedItem() == null) {
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("selecione o cliente que deseja EXCLUIR!");
			dlg.showAndWait();
			btnExcluir.requestFocus();
			return;
		} else {
			ButtonType sim = new ButtonType("SIM", ButtonBar.ButtonData.YES);
			ButtonType nao = new ButtonType("NÃO", ButtonBar.ButtonData.NO);
			Alert alert = new Alert(AlertType.CONFIRMATION,
					"Deseja realmente EXCLUIR o cliente cadastrado do alerta de interesse de compra ?", sim, nao);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get().equals(sim)) {
				this.pessoaDAO = Principal.getPessoaDAO();
				boolean sucesso = pessoaDAO.apagarClienteCrm(clienteId);
				conexao.fecharConexao();
				if (sucesso) {
					Alert dlg = new Alert(AlertType.INFORMATION);
					dlg.setContentText("Despesa fixa salva com sucesso!");
					dlg.showAndWait();
					pesquisar();
					lblQtdeInteresse.setText(String.valueOf(getQtdeEncontrado()));
				}
			}
		}

	}

	private void chamarTela(String tela) throws IOException {
		Stage stage = new Stage();
		Image image = new Image("/br/com/sistema/icones/W3.png");

		stage.getIcons().add(image);
		URL FXML = this.getClass().getResource("/br/com/sistema/view/" + tela + ".fxml");

		Parent painel = (Parent) FXMLLoader.load(FXML);
		stage.setScene(new Scene(painel));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
		stage.setResizable(false);
	}

	private void comboBox() {
		ArrayList<String> lista = new ArrayList<>();
		String tipo = new String("Tipo");
		String marca = new String("Marca");
		String modelo = new String("Modelo");
		String todos = new String("Todos");

		lista.add(tipo);
		lista.add(marca);
		lista.add(modelo);
		lista.add(todos);

		cmbBuscar.getItems().addAll(lista);
	}

}

package br.com.sistema.controll;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import br.com.sistema.conexao.Conexao;
import br.com.sistema.listeners.ListenerParaMaiusculas;
import br.com.sistema.model.Despesa;
import br.com.sistema.model.DespesaContasPagar;
import br.com.sistema.model.Relatorio;
import br.com.sistema.model.Veiculo;
import br.com.sistema.model.dao.DespesaDAO;
import br.com.sistema.model.dao.VeiculoDAO;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

public class TelaListaContasPagar {

	@FXML
	private Button btnPagarTotal;

	@FXML
	private ComboBox<String> cmbBuscar;

	@FXML
	private TextField txtPesquisa;

	@FXML
	private Button btnAtualizar;

	@FXML
	private DatePicker txtDataInicial;

	@FXML
	private DatePicker txtDataFinal;

	@FXML
	private TableView<Despesa> tbPagamento;

	@FXML
	private TableColumn<Despesa, String> clnTipo;

	@FXML
	private TableColumn<Despesa, String> clnDescricao;

	@FXML
	private TableColumn<Despesa, String> clnResponsavel;

	@FXML
	private TableColumn<Despesa, String> clnValor;

	@FXML
	private TableColumn<Despesa, String> clnDataVencimento;

	@FXML
	private TableColumn<Despesa, String> clnDataPagamento;

	@FXML
	private TableColumn<Despesa, String> clnSituacao;

	@FXML
	private TextField txtTipo;

	@FXML
	private TextField txtDescricao;

	@FXML
	private TextField txtResponsavel;

	@FXML
	private TextField txtData;

	@FXML
	private TextField txtValor;

	@FXML
	private TextField txtSituacao;

	@FXML
	private Tab tabDetalhesVeiculo;

	@FXML
	private TextField txtVeiculoNome;

	@FXML
	private TextField txtVeiculoMarca;

	@FXML
	private TextField txtVeiculoRenavam;

	@FXML
	private TextField txtVeiculoPlaca;

	@FXML
	private TextField txtVeiculoCor;

	@FXML
	private TextField txtVeiculoAno;

	@FXML
	private TextField txtVeiculoDataEntrada;

	@FXML
	private TextField txtVeiculoValorEntrada;

	@FXML
	private TextField txtVeiculoValorVenda;

	@FXML
	private Button btnImprimir;

	@FXML
	private Button btnDespesaFixa;

	@FXML
	private Button btnNovoRecebimento;

	@FXML
	private Button btnExcluirRecebimento;

	@FXML
	private Label lblValorPagar;

	@FXML
	private Label lblValorPago;

	@FXML
	private Label lblValorTotal;

	private Conexao conexao;
	private static Date dataInicial, dataFinal;
	private DespesaDAO despesaDAO = null;
	private VeiculoDAO veiculoDAO = null;
	private DecimalFormat decimal = new DecimalFormat("###,###,###,##0.00");
	private static Integer pagamento_id, veiculo_id;

	public void initialize() {

		comboBoxDetalhes();

		txtPesquisa.textProperty().addListener(new ListenerParaMaiusculas(txtPesquisa));
		tbPagamento.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarPagamento((Despesa) newValue));

		tbPagamento.setOnMouseClicked(e -> {
			lista();
			try {
				listaDadosCompraVeiculo();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btnAtualizar.setOnAction(e -> {
			pesquisa();
		});

		btnImprimir.setOnAction(e -> {
			try {
				imprimir();
			} catch (JRException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btnPagarTotal.setOnAction(e -> {
			if (tbPagamento.getSelectionModel().getSelectedItem() == null) {
				Alert dlg = new Alert(AlertType.WARNING);
				dlg.setContentText("selecione o pagamento para Pagar Total!!!");
				dlg.showAndWait();
				tbPagamento.requestFocus();
				return;
			} else {
				try {
					receberTotal();
					pesquisa();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnNovoRecebimento.setOnAction(e -> {
			try {
				chamarTela("TelaNovaDespesa");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btnDespesaFixa.setOnAction(e -> {
			try {
				chamarTela("TelaDespesasFixa");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btnExcluirRecebimento.setOnAction(e -> {
			if (tbPagamento.getSelectionModel().getSelectedItem() == null) {
				Alert dlg = new Alert(AlertType.WARNING);
				dlg.setContentText("selecione o pagamento que deseja EXCLUIR!");
				dlg.showAndWait();
				tbPagamento.requestFocus();
				return;
			} else {
				excluirPagamento();
				pesquisa();
			}
		});

		txtDataInicial.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtDataFinal.requestFocus();
				}

			}
		});

		txtDataFinal.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					cmbBuscar.requestFocus();
				}

			}
		});

		cmbBuscar.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					pesquisa();
				}

			}
		});

		clnSituacao.setCellFactory(column -> {
			return new TableCell<Despesa, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					// Verifica se a célula está vazia
					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						// Define o texto da célula
						setText(item);

						// Verifica o valor da célula e define a cor com base nele
						if (item.equals("PAGO")) {
							setTextFill(Color.GREEN); // Texto em verde
						} else if (item.equals("PENDENTE")) {
							setTextFill(Color.RED); // Texto em vermelho
						} else {
							// Define a cor padrão ou qualquer outra lógica desejada
							setTextFill(Color.BLACK);
						}
					}
				}
			};
		});

		clnDataPagamento.setCellFactory(column -> {
			return new TableCell<Despesa, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					// Verifica se a célula está vazia
					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						// Define o texto da célula
						setText(item);

						// Verifica o valor da célula e define a cor com base nele
						if (item.isEmpty()) {
							setTextFill(Color.BLACK); // Texto em verde
						} else {
							setTextFill(Color.GREEN);
						}
					}
				}
			};
		});

	}

	public void selecionarPagamento(Despesa d) {
		if (d != null) {
			pagamento_id = d.getId();
		}
	}

	public Integer getIdPagamento() {
		return pagamento_id;
	}

	public void lista() {
		this.conexao = new Conexao();
		this.despesaDAO = new DespesaDAO(conexao);
		for (Despesa d : despesaDAO.listarTodosId(pagamento_id)) {
			txtData.setText(d.getDataVencimentoFormatada());
			txtDescricao.setText(d.getDescricao());
			txtResponsavel.setText(d.getResponsavel());
			txtSituacao.setText(d.getSituacao());
			txtTipo.setText(d.getTipo());
			txtValor.setText(d.getValorFormatado());
		}

		conexao.fecharConexao();
	}

	public void listaDadosCompraVeiculo() throws SQLException {
		this.conexao = new Conexao();
		txtVeiculoNome.setText("");
		txtVeiculoMarca.setText("");
		txtVeiculoRenavam.setText("");
		txtVeiculoPlaca.setText("");
		txtVeiculoCor.setText("");
		txtVeiculoAno.setText("");
		txtVeiculoDataEntrada.setText("");
		txtVeiculoValorEntrada.setText("");
		txtVeiculoValorVenda.setText("");
		if (verificaVeiculo(pagamento_id)) {
			this.veiculoDAO = new VeiculoDAO(conexao);
			for (Veiculo v : veiculoDAO.listarTodosId(getIdVeiculo(pagamento_id))) {
				txtVeiculoNome.setText(v.getVeiculo());
				txtVeiculoMarca.setText(v.getMarca());
				txtVeiculoRenavam.setText(v.getRenavam());
				txtVeiculoPlaca.setText(v.getPlaca());
				txtVeiculoCor.setText(v.getCor());
				txtVeiculoAno.setText(v.getAno_modelo());
				txtVeiculoDataEntrada.setText(v.getDataEntradaFormatada());
				txtVeiculoValorEntrada.setText(v.getValorEntradaFormatado());
				txtVeiculoValorVenda.setText(v.getValorVendaFormatado());
			}
		}
		conexao.fecharConexao();
	}

	public void comboBoxDetalhes() {
		ArrayList<String> listaBusca = new ArrayList<>();
		String em = new String("Loja");
		String f = new String("Funcionario");
		String v = new String("Veiculos");
		String ve = new String("Diversas");
		String t = new String("Todos");
		listaBusca.add(em);
		listaBusca.add(f);
		listaBusca.add(v);
		listaBusca.add(ve);
		listaBusca.add(t);

		ObservableList<String> observaLista = FXCollections.observableArrayList(listaBusca);
		cmbBuscar.setItems(observaLista);
	}

	public void pesquisa() {
		String cmb = null;
		if (txtDataInicial.getValue() == null) {
			ValidationFields.checkEmptyFields(txtDataInicial);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione a Data Inicial!");
			dlg.showAndWait();
			txtDataInicial.requestFocus();
			return;
		} else {
			dataInicial = java.sql.Date.valueOf(txtDataInicial.getValue());
		}

		if (txtDataFinal.getValue() == null) {
			ValidationFields.checkEmptyFields(txtDataFinal);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione a Data Final!");
			dlg.showAndWait();
			txtDataFinal.requestFocus();
			return;
		} else {
			dataFinal = java.sql.Date.valueOf(txtDataFinal.getValue());
		}

		if (cmbBuscar.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbBuscar);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione o campo Buscar!");
			dlg.showAndWait();
			cmbBuscar.requestFocus();
			return;
		} else {
			decimal = new DecimalFormat("###,###,###,##0.00");
			cmb = cmbBuscar.getValue().toString();
			clnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
			clnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
			clnResponsavel.setCellValueFactory(new PropertyValueFactory<>("responsavel"));
			clnDataVencimento.setCellValueFactory(new PropertyValueFactory<>("dataVencimentoFormatada"));
			clnDataPagamento.setCellValueFactory(new PropertyValueFactory<>("dataPagamentoFormatada"));
			clnValor.setCellValueFactory(new PropertyValueFactory<>("valorFormatado"));
			clnSituacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));
			this.conexao = new Conexao();
			this.despesaDAO = new DespesaDAO(conexao);
			switch (cmb) {
			case "Loja":

				ObservableList<Despesa> lis = FXCollections
						.observableArrayList(despesaDAO.listarTodosEmpresa(dataInicial, dataFinal));
				tbPagamento.setItems(lis);

				lblValorTotal.setText(
						decimal.format(despesaDAO.getTotalDespesasContasPagar(dataInicial, dataFinal, "DESPESA LOJA")));
				lblValorPagar.setText(
						decimal.format(despesaDAO.getPagarDespesasContasPagar(dataInicial, dataFinal, "DESPESA LOJA")));
				lblValorPago.setText(
						decimal.format(despesaDAO.getPagoDespesasContasPagar(dataInicial, dataFinal, "DESPESA LOJA")));

				break;

			case "Funcionario":

				ObservableList<Despesa> list = FXCollections
						.observableArrayList(despesaDAO.listarTodosFuncionario(dataInicial, dataFinal));
				tbPagamento.setItems(list);
				lblValorTotal.setText(decimal
						.format(despesaDAO.getTotalDespesasContasPagar(dataInicial, dataFinal, "DESPESA FUNCIONARI%")));
				lblValorPagar.setText(decimal
						.format(despesaDAO.getPagarDespesasContasPagar(dataInicial, dataFinal, "DESPESA FUNCIONARI%")));
				lblValorPago.setText(decimal
						.format(despesaDAO.getPagoDespesasContasPagar(dataInicial, dataFinal, "DESPESA FUNCIONARI%")));

				break;
			case "Veiculos":

				ObservableList<Despesa> listaa = FXCollections
						.observableArrayList(despesaDAO.listarTodosVeiculo(dataInicial, dataFinal));
				tbPagamento.setItems(listaa);
				lblValorTotal
						.setText(decimal.format(despesaDAO.getTotalDespesasVeiculoContasPagar(dataInicial, dataFinal)));
				lblValorPagar
						.setText(decimal.format(despesaDAO.getPagarDespesasVeiculoContasPagar(dataInicial, dataFinal)));
				lblValorPago
						.setText(decimal.format(despesaDAO.getPagoDespesasVeiculoContasPagar(dataInicial, dataFinal)));

				break;

			case "Diversas":

				ObservableList<Despesa> li = FXCollections
						.observableArrayList(despesaDAO.listarTodosDiversas(dataInicial, dataFinal));
				tbPagamento.setItems(li);

				lblValorTotal.setText(decimal
						.format(despesaDAO.getTotalDespesasContasPagar(dataInicial, dataFinal, "DESPESA DIVERSAS")));
				lblValorPagar.setText(decimal
						.format(despesaDAO.getPagarDespesasContasPagar(dataInicial, dataFinal, "DESPESA DIVERSAS")));
				lblValorPago.setText(decimal
						.format(despesaDAO.getPagoDespesasContasPagar(dataInicial, dataFinal, "DESPESA DIVERSAS")));

				break;

			case "Todos":
				ObservableList<Despesa> lista = FXCollections
						.observableArrayList(despesaDAO.listarTodos(dataInicial, dataFinal));
				tbPagamento.setItems(lista);
				lblValorTotal.setText(decimal.format(despesaDAO.getTotalDespesasContasPagar(dataInicial, dataFinal)));
				lblValorPagar.setText(decimal.format(despesaDAO.getPagarDespesasContasPagar(dataInicial, dataFinal)));
				lblValorPago.setText(decimal.format(despesaDAO.getPagoDespesasContasPagar(dataInicial, dataFinal)));

				break;

			default:
				break;
			}

			conexao.fecharConexao();
		}

	}

	public void receberTotal() throws SQLException {
		this.conexao = new Conexao();
		if (verificaPagamento(pagamento_id)) {
			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Confirmação de Pagamento");
			alerta.setHeaderText("Pagamento TOTAL já efetuado ! ");
			alerta.showAndWait();
			return;
		} else {
			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Confirmação de Pagamento");
			alerta.setHeaderText("Você quer mesmo pagar total ? ");
			alerta.setContentText("A operação não poderá ser desfeita ! \nVocê tem certeza?");
			Optional<ButtonType> escolha = alerta.showAndWait();

			if (escolha.get() == ButtonType.OK) {
				String situacao = new String("PAGO");
				Date data = new Date();
				Date dataPagamentoTotal = new java.sql.Date(data.getTime());
				Despesa despesa = new Despesa(pagamento_id, null, null, null, dataPagamentoTotal, null, null, null,
						null, null, null, null, null, situacao);
				this.despesaDAO = new DespesaDAO(conexao);
				boolean sucesso = despesaDAO.atualizarPagamentoSituacao(despesa);
				conexao.fecharConexao();
				if (sucesso) {
					Alert dlg = new Alert(AlertType.INFORMATION);
					dlg.setContentText("Pagamento atualizado com sucesso !");
					dlg.showAndWait();
					pesquisa();

				}
			}
		}
	}

	public void excluirPagamento() {
		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.setTitle("Confirmação de EXCLUSÃO");
		alerta.setHeaderText("Você quer mesmo excluir a despesa ? ");
		alerta.setContentText("A operação não poderá ser desfeita! \nVocê tem certeza?");
		Optional<ButtonType> escolha = alerta.showAndWait();

		if (escolha.get() == ButtonType.OK) {
			this.conexao = new Conexao();
			this.despesaDAO = new DespesaDAO(conexao);
			Despesa despesa = new Despesa(pagamento_id);
			boolean sucesso = despesaDAO.apagar(despesa);
			conexao.fecharConexao();
			if (sucesso) {
				Alert dlg = new Alert(AlertType.INFORMATION);
				dlg.setContentText("Despesa excluida com sucesso!");
				dlg.showAndWait();

			}
		}
	}

	public void imprimir() throws JRException {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		decimal = new DecimalFormat("###,###,###,##0.00");
		ArrayList<DespesaContasPagar> lista = new ArrayList<>();
		String cmb = null, tipo = null, descricao = null, responsavel = null, dataVencimento = null,
				dataPagamento = null, valor = null, situacao = null, dataInicio = null, dataFim = null,
				valorPagar = null, valorPago = null, valorTotal = null;
		if (txtDataInicial.getValue() == null) {
			ValidationFields.checkEmptyFields(txtDataInicial);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione a Data Inicial!");
			dlg.showAndWait();
			txtDataInicial.requestFocus();
			return;
		} else {
			dataInicial = java.sql.Date.valueOf(txtDataInicial.getValue());
		}

		if (txtDataFinal.getValue() == null) {
			ValidationFields.checkEmptyFields(txtDataFinal);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione a Data Final!");
			dlg.showAndWait();
			txtDataFinal.requestFocus();
			return;
		} else {
			dataFinal = java.sql.Date.valueOf(txtDataFinal.getValue());
		}

		if (cmbBuscar.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbBuscar);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione o campo Buscar!");
			dlg.showAndWait();
			cmbBuscar.requestFocus();
			return;
		} else {
			cmb = cmbBuscar.getValue().toString();
			this.conexao = new Conexao();
			this.despesaDAO = new DespesaDAO(conexao);
			switch (cmb) {
			case "Loja":

				for (Despesa d : despesaDAO.listarTodosEmpresa(dataInicial, dataFinal)) {
					valorPagar = decimal
							.format(despesaDAO.getPagarDespesasContasPagar(dataInicial, dataFinal, "DESPESA LOJA"));
					valorPago = decimal
							.format(despesaDAO.getPagoDespesasContasPagar(dataInicial, dataFinal, "DESPESA LOJA"));
					valorTotal = decimal
							.format(despesaDAO.getTotalDespesasContasPagar(dataInicial, dataFinal, "DESPESA LOJA"));
					tipo = d.getTipo();
					descricao = d.getDescricao();
					responsavel = d.getResponsavel();
					dataVencimento = d.getDataVencimentoFormatada();
					dataPagamento = d.getDataPagamentoFormatada();
					valor = d.getValorFormatado();
					situacao = d.getSituacao();
					dataInicio = df.format(dataInicial);
					dataFim = df.format(dataFinal);

					DespesaContasPagar des = new DespesaContasPagar(null, tipo, descricao, responsavel, dataVencimento,
							dataPagamento, valor, situacao, dataInicio, dataFim, valorPagar, valorPago, valorTotal);
					lista.add(des);
				}
				Relatorio relatorio = new Relatorio();
				relatorio.gerarRelatorio(lista, "RelatorioDespesas");

				break;

			case "Funcionario":

				for (Despesa d : despesaDAO.listarTodosFuncionario(dataInicial, dataFinal)) {
					valorPagar = decimal.format(
							despesaDAO.getPagarDespesasContasPagar(dataInicial, dataFinal, "DESPESA FUNCIONARI%"));
					valorPago = decimal.format(
							despesaDAO.getPagoDespesasContasPagar(dataInicial, dataFinal, "DESPESA FUNCIONARI%"));
					valorTotal = decimal.format(
							despesaDAO.getTotalDespesasContasPagar(dataInicial, dataFinal, "DESPESA FUNCIONARI%"));
					tipo = d.getTipo();
					descricao = d.getDescricao();
					responsavel = d.getResponsavel();
					dataVencimento = d.getDataVencimentoFormatada();
					dataPagamento = d.getDataPagamentoFormatada();
					valor = d.getValorFormatado();
					situacao = d.getSituacao();
					dataInicio = df.format(dataInicial);
					dataFim = df.format(dataFinal);

					DespesaContasPagar des = new DespesaContasPagar(null, tipo, descricao, responsavel, dataVencimento,
							dataPagamento, valor, situacao, dataInicio, dataFim, valorPagar, valorPago, valorTotal);
					lista.add(des);
				}
				Relatorio relatori = new Relatorio();
				relatori.gerarRelatorio(lista, "RelatorioDespesas");

				break;
			case "Veiculos":

				for (Despesa d : despesaDAO.listarTodosVeiculo(dataInicial, dataFinal)) {
					valorPagar = decimal.format(despesaDAO.getPagarDespesasVeiculoContasPagar(dataInicial, dataFinal));
					valorPago = decimal.format(despesaDAO.getPagoDespesasVeiculoContasPagar(dataInicial, dataFinal));
					valorTotal = decimal.format(despesaDAO.getTotalDespesasVeiculoContasPagar(dataInicial, dataFinal));
					tipo = d.getTipo();
					descricao = d.getDescricao();
					responsavel = d.getResponsavel();
					dataVencimento = d.getDataVencimentoFormatada();
					dataPagamento = d.getDataPagamentoFormatada();
					valor = d.getValorFormatado();
					situacao = d.getSituacao();
					dataInicio = df.format(dataInicial);
					dataFim = df.format(dataFinal);

					DespesaContasPagar des = new DespesaContasPagar(null, tipo, descricao, responsavel, dataVencimento,
							dataPagamento, valor, situacao, dataInicio, dataFim, valorPagar, valorPago, valorTotal);
					lista.add(des);
				}
				Relatorio relato = new Relatorio();
				relato.gerarRelatorio(lista, "RelatorioDespesas");

				break;

			case "Diversas":

				for (Despesa d : despesaDAO.listarTodosDiversas(dataInicial, dataFinal)) {
					valorPagar = decimal
							.format(despesaDAO.getPagarDespesasContasPagar(dataInicial, dataFinal, "DESPESA DIVERSAS"));
					valorPago = decimal
							.format(despesaDAO.getPagoDespesasContasPagar(dataInicial, dataFinal, "DESPESA DIVERSAS"));
					valorTotal = decimal
							.format(despesaDAO.getTotalDespesasContasPagar(dataInicial, dataFinal, "DESPESA DIVERSAS"));
					tipo = d.getTipo();
					descricao = d.getDescricao();
					responsavel = d.getResponsavel();
					dataVencimento = d.getDataVencimentoFormatada();
					dataPagamento = d.getDataPagamentoFormatada();
					valor = d.getValorFormatado();
					situacao = d.getSituacao();
					dataInicio = df.format(dataInicial);
					dataFim = df.format(dataFinal);

					DespesaContasPagar des = new DespesaContasPagar(null, tipo, descricao, responsavel, dataVencimento,
							dataPagamento, valor, situacao, dataInicio, dataFim, valorPagar, valorPago, valorTotal);
					lista.add(des);
				}
				Relatorio relatoR = new Relatorio();
				relatoR.gerarRelatorio(lista, "RelatorioDespesas");

				break;

			case "Todos":

				for (Despesa d : despesaDAO.listarTodos(dataInicial, dataFinal)) {
					valorPagar = decimal.format(despesaDAO.getPagarDespesasContasPagar(dataInicial, dataFinal));
					valorPago = decimal.format(despesaDAO.getPagoDespesasContasPagar(dataInicial, dataFinal));
					valorTotal = decimal.format(despesaDAO.getTotalDespesasContasPagar(dataInicial, dataFinal));
					tipo = d.getTipo();
					descricao = d.getDescricao();
					responsavel = d.getResponsavel();
					dataVencimento = d.getDataVencimentoFormatada();
					dataPagamento = d.getDataPagamentoFormatada();
					valor = d.getValorFormatado();
					situacao = d.getSituacao();
					dataInicio = df.format(dataInicial);
					dataFim = df.format(dataFinal);

					DespesaContasPagar des = new DespesaContasPagar(null, tipo, descricao, responsavel, dataVencimento,
							dataPagamento, valor, situacao, dataInicio, dataFim, valorPagar, valorPago, valorTotal);
					lista.add(des);
				}
				Relatorio relatorioss = new Relatorio();
				relatorioss.gerarRelatorio(lista, "RelatorioDespesas");

				break;

			default:
				break;
			}
			conexao.fecharConexao();
		}

	}

	public boolean verificaPagamento(Integer id) throws SQLException {
		boolean result = false;

		String sql = "SELECT * FROM despesas WHERE id = ? AND situacao = 'PAGO'";

		PreparedStatement cmd = conexao.getConexao().prepareStatement(sql);
		cmd.setInt(1, id);

		ResultSet rs = cmd.executeQuery();

		if (rs.next()) {
			result = true;

		}

		System.out.println("resultado : " + result);
		return result;

	}

	public boolean verificaVeiculo(Integer despesa_id) throws SQLException {
		boolean result = false;

		String sql = "SELECT * FROM despesas WHERE id = ?";

		PreparedStatement cmd = conexao.getConexao().prepareStatement(sql);
		cmd.setInt(1, despesa_id);

		ResultSet rs = cmd.executeQuery();

		if (rs.next()) {
			result = true;
			System.out.println("pegou id veiculo : " + veiculo_id);

		}

		System.out.println("resultado : " + result);
		return result;

	}

	public Integer getIdVeiculo(Integer despesa_id) throws SQLException {
		Integer result = null;

		String sql = "SELECT * FROM despesas WHERE id = ?";

		PreparedStatement cmd = conexao.getConexao().prepareStatement(sql);
		cmd.setInt(1, despesa_id);

		ResultSet rs = cmd.executeQuery();

		if (rs.next()) {
			result = rs.getInt("veiculo_id");

		}

		System.out.println("resultado : " + result);
		return result;

	}

	public void chamarTela(String tela) throws IOException {
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

}

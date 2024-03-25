package br.com.sistema.controll;

import java.io.IOException;
import java.math.BigDecimal;
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
import br.com.sistema.model.ContasReceber;
import br.com.sistema.model.DetalhesPagamento;
import br.com.sistema.model.Relatorio;
import br.com.sistema.model.dao.ContasReceberDAO;
import br.com.sistema.model.dao.DetalhesPagamentoDAO;
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

public class TelaListaContasReceber {

	@FXML
	private Button btnAtualizar;

	@FXML
	private Button btnExcluirPagamento;

	@FXML
	private Button btnImprimir;

	@FXML
	private Button btnNovoPagamento;

	@FXML
	private Button btnReceberParcelado;

	@FXML
	private Button btnReceberTotal;

	@FXML
	private Button btnEditarPagamento;

	@FXML
	private TableView<ContasReceber> tbPagamento;

	@FXML
	private TableColumn<ContasReceber, String> clnCliente;

	@FXML
	private TableColumn<ContasReceber, String> clnDataVencimento;

	@FXML
	private TableColumn<ContasReceber, String> clnDescricao;

	@FXML
	private TableColumn<ContasReceber, Integer> clnNumeroParcela;

	@FXML
	private TableColumn<ContasReceber, Integer> clnQtdeParcelas;

	@FXML
	private TableColumn<ContasReceber, String> clnSituacao;

	@FXML
	private TableColumn<ContasReceber, String> clnValorTotal;

	@FXML
	private TableView<DetalhesPagamento> tbDetalhesPagamento;

	@FXML
	private TableColumn<DetalhesPagamento, String> clnDataPagamento;

	@FXML
	private TableColumn<DetalhesPagamento, String> clnDetalhesTipo;

	@FXML
	private TableColumn<DetalhesPagamento, BigDecimal> clnDetalhesValor;

	@FXML
	private ComboBox<String> cmbBuscar;

	@FXML
	private Label lblSaldo;

	@FXML
	private Label lblValorPago;

	@FXML
	private Label lblValorReceber;

	@FXML
	private Label lblValorTotal;

	@FXML
	private DatePicker txtDataFinal;

	@FXML
	private DatePicker txtDataInicial;

	@FXML
	private TextField txtPesquisa;

	@FXML
	private TextField txtSaldoDevedor;

	@FXML
	private TextField txtTotalReceber;

	@FXML
	private TextField txtTotalRecebido;

	private Conexao conexao;
	private ContasReceberDAO contasReceberDAO = null;
	private DetalhesPagamentoDAO detalhesPagamentoDAO = null;
	private static Date dataInicial, dataFinal;
	private DecimalFormat decimal = new DecimalFormat("###,###,###,##0.00");
	private static Integer contas_id;

	public void initialize() {

		comboBoxBusca();

		btnAtualizar.setOnAction(e -> {
			preencherTabela();
		});

		tbPagamento.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarPagamento((ContasReceber) newValue));

		tbPagamento.setOnMouseClicked(e -> {
			if (tbPagamento.getSelectionModel().getSelectedItems() != null) {
				valorPagamentoReceber();
				preencherTabelaDetalhesPagamento();

			}
		});

		txtDataInicial.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtDataFinal.requestFocus();
					return;
				}

			}
		});

		txtDataFinal.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					cmbBuscar.requestFocus();
					return;
				}

			}
		});

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
					preencherTabela();
					return;
				}

			}
		});

		btnImprimir.setOnAction(e -> {
			try {
				imprimir();
			} catch (JRException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btnReceberTotal.setOnAction(e -> {
			if (tbPagamento.getSelectionModel().getSelectedItem() == null) {
				Alert dlg = new Alert(AlertType.WARNING);
				dlg.setContentText("selecione o pagamento para Receber Total!!!");
				dlg.showAndWait();
				tbPagamento.requestFocus();
				return;
			} else {
				try {
					receberTotal();
					preencherTabela();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnReceberParcelado.setOnAction(e -> {
			if (tbPagamento.getSelectionModel().getSelectedItem() == null) {
				Alert dlg = new Alert(AlertType.WARNING);
				dlg.setContentText("selecione o pagamento para Receber Parcelado!!!");
				dlg.showAndWait();
				tbPagamento.requestFocus();
				return;
			} else {
				try {
					receberParcelado();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnNovoPagamento.setOnAction(e -> {
			try {
				chamarTela("TelaNovoPagamento");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btnExcluirPagamento.setOnAction(e -> {
			if (tbPagamento.getSelectionModel().getSelectedItem() != null) {
				excluir();
			} else {
				ValidationFields.checkEmptyFields(tbPagamento);
				Alert dlg = new Alert(AlertType.WARNING);
				dlg.setContentText("Selecione o pagamento para EXCLUIR!");
				dlg.showAndWait();
				btnExcluirPagamento.requestFocus();
				return;
			}
		});

		btnEditarPagamento.setOnAction(e -> {
			if (tbPagamento.getSelectionModel().getSelectedItem() != null) {
				try {
					this.conexao = new Conexao();
					if (validaRecebido(contas_id)) {
						ValidationFields.checkEmptyFields(tbPagamento);
						Alert dlg = new Alert(AlertType.WARNING);
						dlg.setContentText("Pagamento total ja efetuado!");
						dlg.showAndWait();
						btnExcluirPagamento.requestFocus();
						return;
					} else {
						try {
							chamarTela("TelaEditarPagamento");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				conexao.fecharConexao();

			} else {
				ValidationFields.checkEmptyFields(tbPagamento);
				Alert dlg = new Alert(AlertType.WARNING);
				dlg.setContentText("Selecione o pagamento para EDITAR!");
				dlg.showAndWait();
				btnEditarPagamento.requestFocus();
				return;
			}
		});

		clnSituacao.setCellFactory(column -> {
			return new TableCell<ContasReceber, String>() {
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

	}

	public void selecionarPagamento(ContasReceber r) {
		if (r != null) {
			contas_id = r.getId();
			System.out.println("id tabela : " + contas_id);
		}
	}

	public Integer getIdTabela() {
		return contas_id;
	}

	public void comboBoxBusca() {
		ArrayList<String> lista = new ArrayList<>();
		String numero = new String("Nome Cliente");
		String nome = new String("Descrição");
		String cpf_cnpj = new String("Situação");
		String todos = new String("Todos");
		lista.add(numero);
		lista.add(nome);
		lista.add(cpf_cnpj);
		lista.add(todos);
		ObservableList<String> observa = FXCollections.observableArrayList(lista);
		cmbBuscar.setItems(observa);

	}

	public void preencherTabela() {
		if (txtDataInicial.getValue() == null) {
			ValidationFields.checkEmptyFields(txtDataInicial);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha com a Data Incial!");
			dlg.showAndWait();
			txtDataInicial.requestFocus();
			return;
		} else {
			dataInicial = java.sql.Date.valueOf(txtDataInicial.getValue());
		}

		if (txtDataFinal.getValue() == null) {
			ValidationFields.checkEmptyFields(txtDataFinal);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha com a Data Final!");
			dlg.showAndWait();
			txtDataFinal.requestFocus();
			return;
		} else {
			dataFinal = java.sql.Date.valueOf(txtDataFinal.getValue());
		}

		if (cmbBuscar.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbBuscar);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione o tipo de buscar!");
			dlg.showAndWait();
			cmbBuscar.requestFocus();
			return;
		} else {
			String cmb = cmbBuscar.getValue().toString();
			clnCliente.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
			clnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
			clnQtdeParcelas.setCellValueFactory(new PropertyValueFactory<>("qtdeParcela"));
			clnNumeroParcela.setCellValueFactory(new PropertyValueFactory<>("numeroParcela"));
			clnValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotalFormatado"));
			clnDataVencimento.setCellValueFactory(new PropertyValueFactory<>("dataVencimentoFormatada"));
			clnSituacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));
			this.conexao = new Conexao();
			this.contasReceberDAO = new ContasReceberDAO(conexao);
			switch (cmb) {

			case "Nome Cliente":
				if (txtPesquisa.getText().isEmpty()) {
					ValidationFields.checkEmptyFields(txtPesquisa);
					Alert dlg = new Alert(AlertType.ERROR);
					dlg.setContentText("Preencha com o Nome do Cliente!");
					dlg.showAndWait();
					txtPesquisa.requestFocus();
					return;
				} else {
					String pesquisa = txtPesquisa.getText();
					ObservableList<ContasReceber> lis = FXCollections.observableArrayList(
							contasReceberDAO.listarTodos(dataInicial, dataFinal, "p.nome", pesquisa));
					lblValorPago.setText(decimal.format(contasReceberDAO.getValorContasReceberCliente(dataInicial,
							dataFinal, "c.valor_pago", "p.nome", pesquisa)));
					BigDecimal pago = contasReceberDAO.getValorContasReceberCliente(dataInicial, dataFinal,
							"c.valor_pago", "p.nome", pesquisa);
					lblValorReceber.setText(decimal.format(contasReceberDAO.getValorContasReceberCliente(dataInicial,
							dataFinal, "c.valor_receber", "p.nome", pesquisa)));
					BigDecimal receber = contasReceberDAO.getValorContasReceberCliente(dataInicial, dataFinal,
							"c.valor_receber", "p.nome", pesquisa);
					lblValorTotal.setText(decimal.format(contasReceberDAO.getValorContasReceberCliente(dataInicial,
							dataFinal, "c.valor_total", "p.nome", pesquisa)));
					BigDecimal calcula = pago.subtract(receber);
					lblSaldo.setText(decimal.format(calcula));
					tbPagamento.setItems(lis);

				}
				break;

			case "Descrição":
				if (txtPesquisa.getText().isEmpty()) {
					ValidationFields.checkEmptyFields(txtPesquisa);
					Alert dlg = new Alert(AlertType.ERROR);
					dlg.setContentText("Preencha com a DESCRIÇÃO!");
					dlg.showAndWait();
					txtPesquisa.requestFocus();
					return;
				} else {
					String pesquisa = txtPesquisa.getText();
					ObservableList<ContasReceber> lis = FXCollections.observableArrayList(
							contasReceberDAO.listarTodos(dataInicial, dataFinal, "c.descricao", pesquisa));
					lblValorPago.setText(decimal.format(contasReceberDAO.getValorContasReceber(dataInicial, dataFinal,
							"valor_pago", "descricao", pesquisa)));
					BigDecimal pago = contasReceberDAO.getValorContasReceber(dataInicial, dataFinal, "valor_pago",
							"descricao", pesquisa);
					lblValorReceber.setText(decimal.format(contasReceberDAO.getValorContasReceber(dataInicial,
							dataFinal, "valor_receber", "descricao", pesquisa)));
					BigDecimal receber = contasReceberDAO.getValorContasReceber(dataInicial, dataFinal, "valor_receber",
							"descricao", pesquisa);
					lblValorTotal.setText(decimal.format(contasReceberDAO.getValorContasReceber(dataInicial, dataFinal,
							"valor_total", "descricao", pesquisa)));
					BigDecimal calcula = pago.subtract(receber);
					lblSaldo.setText(decimal.format(calcula));
					tbPagamento.setItems(lis);
				}

				break;

			case "Situação":
				if (txtPesquisa.getText().isEmpty()) {
					ValidationFields.checkEmptyFields(txtPesquisa);
					Alert dlg = new Alert(AlertType.ERROR);
					dlg.setContentText("Preencha com a SITUAÇÃO!");
					dlg.showAndWait();
					txtPesquisa.requestFocus();
					return;
				} else {
					String pesquisa = txtPesquisa.getText();
					ObservableList<ContasReceber> lis = FXCollections.observableArrayList(
							contasReceberDAO.listarTodos(dataInicial, dataFinal, "c.situacao", pesquisa));
					lblValorPago.setText(decimal.format(contasReceberDAO.getValorContasReceber(dataInicial, dataFinal,
							"valor_pago", "situacao", pesquisa)));
					BigDecimal pago = contasReceberDAO.getValorContasReceber(dataInicial, dataFinal, "valor_pago",
							"situacao", pesquisa);
					lblValorReceber.setText(decimal.format(contasReceberDAO.getValorContasReceber(dataInicial,
							dataFinal, "valor_receber", "situacao", pesquisa)));
					BigDecimal receber = contasReceberDAO.getValorContasReceber(dataInicial, dataFinal, "valor_receber",
							"situacao", pesquisa);
					lblValorTotal.setText(decimal.format(contasReceberDAO.getValorContasReceber(dataInicial, dataFinal,
							"valor_total", "situacao", pesquisa)));

					BigDecimal calcula = pago.subtract(receber);
					lblSaldo.setText(decimal.format(calcula));
					tbPagamento.setItems(lis);

				}

				break;

			case "Todos":
				ObservableList<ContasReceber> lis = FXCollections
						.observableArrayList(contasReceberDAO.listarTodos(dataInicial, dataFinal));
				lblValorPago.setText(
						decimal.format(contasReceberDAO.getValorContasReceber(dataInicial, dataFinal, "valor_pago")));
				BigDecimal pago = contasReceberDAO.getValorContasReceber(dataInicial, dataFinal, "valor_pago");
				lblValorReceber.setText(decimal
						.format(contasReceberDAO.getValorContasReceber(dataInicial, dataFinal, "valor_receber")));
				BigDecimal receber = contasReceberDAO.getValorContasReceber(dataInicial, dataFinal, "valor_receber");
				lblValorTotal.setText(
						decimal.format(contasReceberDAO.getValorContasReceber(dataInicial, dataFinal, "valor_total")));
				BigDecimal calcula = pago.subtract(receber);
				lblSaldo.setText(decimal.format(calcula));
				tbPagamento.setItems(lis);

				break;

			default:
				break;
			}
			conexao.fecharConexao();

		}

	}

	public void receberParcelado() throws SQLException, IOException {
		if (validaRecebido(contas_id)) {
			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Confirmação de Pagamento");
			alerta.setHeaderText("Pagamento TOTAL já efetuado ! ");
			alerta.showAndWait();
			return;
		} else {
			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Confirmação de Pagamento");
			alerta.setHeaderText("Você quer mesmo receber PARCELADO ? ");
			alerta.setContentText("A operação não poderá ser desfeita ! \nVocê tem certeza?");
			Optional<ButtonType> escolha = alerta.showAndWait();

			if (escolha.get() == ButtonType.OK) {
				Stage stage = new Stage();
				Image image = new Image("/br/com/sistema/icones/W3.png");

				stage.setTitle("Sistema Ordem de Serviço");
				stage.getIcons().add(image);
				URL FXML = this.getClass().getResource("/br/com/sistema/view/TelaPagamentoParcelado.fxml");

				Parent painel = (Parent) FXMLLoader.load(FXML);
				stage.setScene(new Scene(painel));
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.show();
				stage.setResizable(false);
			} else {

			}
		}
	}

	public void receberTotal() throws SQLException {
		this.conexao = new Conexao();
		if (validaRecebido(contas_id)) {
			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Confirmação de Pagamento");
			alerta.setHeaderText("Pagamento TOTAL já efetuado ! ");
			alerta.showAndWait();
			conexao.fecharConexao();
			return;
		} else {
			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Confirmação de Pagamento");
			alerta.setHeaderText("Você quer mesmo receber total ? ");
			alerta.setContentText("A operação não poderá ser desfeita ! \nVocê tem certeza?");
			Optional<ButtonType> escolha = alerta.showAndWait();

			if (escolha.get() == ButtonType.OK) {
				this.conexao = new Conexao();
				String totalRecebido = new String("PGTO TOTAL");
				Date data = new Date();
				Date dataPagamentoTotal = new java.sql.Date(data.getTime());
				this.contasReceberDAO = new ContasReceberDAO(conexao);
				BigDecimal receber = contasReceberDAO.getValorContasReceber(contas_id, "valor_receber");
				BigDecimal valorTotal = contasReceberDAO.getValorContasReceber(contas_id, "valor_total");
				if(receber.compareTo(BigDecimal.ZERO) == 0) {
					receber = valorTotal;
				}
				System.out.println("pegou valor total receber : " + receber);
				BigDecimal valorZero = new BigDecimal("0.00");

				ContasReceber contas = new ContasReceber(contas_id, dataPagamentoTotal, receber, null, null, null, null,
						null, null, totalRecebido, null, null, null, null, null, null, null, null, null);
				boolean suc = contasReceberDAO.inserirPagamentoParcelado(contas);
				System.out.println("inseriu historico pagamento parcelado : " + suc);
				boolean atu = contasReceberDAO.atualizarValor(contas_id, "valor_receber", valorZero);
				boolean atus = contasReceberDAO.atualizarValor(contas_id, "valor_pago", valorTotal);
				System.out.println("sucesso atualizar valor_receber : " + atu);
				System.out.println("sucesso atualizar valor_pago : " + atus);
				ContasReceber co = new ContasReceber();
				co.setId(contas_id);
				co.setSituacao("PAGO");
				co.setValorTotal(valorTotal);
				co.setDataVencimento(dataPagamentoTotal);
				boolean sucesso = contasReceberDAO.atualizar(co);
				conexao.fecharConexao();
				if (sucesso) {
					Alert dlg = new Alert(AlertType.INFORMATION);
					dlg.setContentText("Pagamento atualizado com sucesso !");
					dlg.showAndWait();
					preencherTabela();
					preencherTabelaDetalhesPagamento();
				}

			}
		}
	}

	public void preencherTabelaDetalhesPagamento() {
		this.conexao = new Conexao();
		this.detalhesPagamentoDAO = new DetalhesPagamentoDAO(conexao);
		clnDetalhesTipo.setCellValueFactory(new PropertyValueFactory<DetalhesPagamento, String>("tipo"));
		clnDetalhesValor.setCellValueFactory(new PropertyValueFactory<DetalhesPagamento, BigDecimal>("valor"));
		clnDataPagamento.setCellValueFactory(new PropertyValueFactory<DetalhesPagamento, String>("dataFormatada"));
		ObservableList<DetalhesPagamento> listaAberto = FXCollections
				.observableArrayList(detalhesPagamentoDAO.buscarIdPagamento(contas_id));
		tbDetalhesPagamento.setItems(listaAberto);
		conexao.fecharConexao();

	}

	public void valorPagamentoReceber() {
		this.conexao = new Conexao();
		this.contasReceberDAO = new ContasReceberDAO(conexao);
		txtTotalRecebido.setText(decimal.format(contasReceberDAO.getValorContasReceber(contas_id, "valor_pago")));
		BigDecimal recebido = contasReceberDAO.getValorContasReceber(contas_id, "valor_pago");
		txtTotalReceber.setText(decimal.format(contasReceberDAO.getValorContasReceber(contas_id, "valor_receber")));
		BigDecimal total = contasReceberDAO.getValorContasReceber(contas_id, "valor_total");
		if (recebido.compareTo(total) == 0) {
			txtSaldoDevedor.setText("0,00");
		} else {
			txtSaldoDevedor.setText(decimal.format(contasReceberDAO.getValorContasReceber(contas_id, "valor_total")));
		}
		conexao.fecharConexao();
	}

	public void excluir() {
		ContasReceber contas = new ContasReceber(contas_id);
		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.setTitle("Confirmação de EXCLUSÃO");
		alerta.setHeaderText("Você quer mesmo excluir o pagamento selecionado? ");
		alerta.setContentText("O registro será excluido!" + "\nVocê tem certeza?");
		Optional<ButtonType> escolha = alerta.showAndWait();

		if (escolha.get() == ButtonType.OK) {
			this.conexao = new Conexao();
			this.contasReceberDAO = new ContasReceberDAO(conexao);
			Integer idVeiculo = contasReceberDAO.getIdVeiculo(contas_id);
			System.out.println("pegou id do veiculo " + idVeiculo);
			boolean sucess = contasReceberDAO.apagarHistoricoVeiculo("VENDA", idVeiculo);
			boolean su = contasReceberDAO.atualizarSituacaoVeiculo("ESTOQUE", idVeiculo);
			boolean suce = contasReceberDAO.apagarPagamento(contas);
			System.out.println("sucesso excluir historico venda : " + sucess);
			System.out.println("sucesso atualizar status veiculo : " + su);
			conexao.fecharConexao();
			if (suce) {
				Alert dlg = new Alert(AlertType.INFORMATION);
				dlg.setContentText("Conta excluida com sucesso !");
				dlg.showAndWait();
				preencherTabela();
			}
		}
	}

	public void imprimir() throws JRException {
		decimal = new DecimalFormat("###,###,###,##0.00");
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<ContasReceber> lista = new ArrayList<>();
		String dataFormatada = null, valorFormatado = null, nomeCliente = null, descricao = null, situacao = null,
				saldo = null, valorPago = null, valorPagar = null, valorTotal = null, dataInicio = null, dataFim = null;
		Integer qtdeParcela = null, numeroParcela = null;
		if (txtDataInicial.getValue() == null) {
			ValidationFields.checkEmptyFields(txtDataInicial);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha com a Data Incial!");
			dlg.showAndWait();
			txtDataInicial.requestFocus();
			return;
		} else {
			dataInicial = java.sql.Date.valueOf(txtDataInicial.getValue());
		}

		if (txtDataFinal.getValue() == null) {
			ValidationFields.checkEmptyFields(txtDataFinal);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha com a Data Final!");
			dlg.showAndWait();
			txtDataFinal.requestFocus();
			return;
		} else {
			dataFinal = java.sql.Date.valueOf(txtDataFinal.getValue());
		}

		if (cmbBuscar.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbBuscar);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione o tipo de buscar!");
			dlg.showAndWait();
			cmbBuscar.requestFocus();
			return;
		} else {
			String cmb = cmbBuscar.getValue().toString();
			this.conexao = new Conexao();
			this.contasReceberDAO = new ContasReceberDAO(conexao);
			switch (cmb) {

			case "Descrição":
				if (txtPesquisa.getText().isEmpty()) {
					ValidationFields.checkEmptyFields(txtPesquisa);
					Alert dlg = new Alert(AlertType.ERROR);
					dlg.setContentText("Preencha com a DESCRIÇÃO!");
					dlg.showAndWait();
					txtPesquisa.requestFocus();
					return;
				} else {
					String pesquisa = txtPesquisa.getText();
					for (ContasReceber c : contasReceberDAO.listarTodos(dataInicial, dataFinal, "c.descricao",
							pesquisa)) {
						nomeCliente = c.getNomeCliente();
						descricao = c.getDescricao();
						valorFormatado = c.getValorTotalFormatado();
						dataFormatada = c.getDataVencimentoFormatada();
						situacao = c.getSituacao();
						BigDecimal pago = contasReceberDAO.getValorContasReceber(dataInicial, dataFinal, "valor_pago",
								"descricao", pesquisa);
						valorPago = decimal.format(contasReceberDAO.getValorContasReceber(dataInicial, dataFinal,
								"valor_pago", "descricao", pesquisa));
						valorPagar = decimal.format(contasReceberDAO.getValorContasReceber(dataInicial, dataFinal,
								"valor_receber", "descricao", pesquisa));
						BigDecimal receber = contasReceberDAO.getValorContasReceber(dataInicial, dataFinal,
								"valor_receber", "descricao", pesquisa);

						valorTotal = decimal.format(contasReceberDAO.getValorContasReceber(dataInicial, dataFinal,
								"valor_total", "descricao", pesquisa));

						BigDecimal calcula = pago.subtract(receber);
						qtdeParcela = c.getQtdeParcela();
						numeroParcela = c.getNumeroParcela();
						saldo = decimal.format(calcula);
						dataFim = formatador.format(dataFinal);
						dataInicio = formatador.format(dataInicial);

						ContasReceber contas = new ContasReceber(null, null, null, null, null, qtdeParcela,
								numeroParcela, null, null, situacao, dataFormatada, valorFormatado, valorPago,
								valorPagar, nomeCliente, descricao, saldo, dataInicio, dataFim, valorTotal);

						lista.add(contas);
					}

					Relatorio relatorio = new Relatorio();
					relatorio.gerarRelatorioDespesas(lista);
				}

				break;

			case "Nome Cliente":
				if (txtPesquisa.getText().isEmpty()) {
					ValidationFields.checkEmptyFields(txtPesquisa);
					Alert dlg = new Alert(AlertType.ERROR);
					dlg.setContentText("Digite o nome do cliente!");
					dlg.showAndWait();
					txtPesquisa.requestFocus();
					return;
				} else {
					String pesquisa = txtPesquisa.getText();
					for (ContasReceber c : contasReceberDAO.listarTodos(dataInicial, dataFinal, "p.nome", pesquisa)) {
						nomeCliente = c.getNomeCliente();
						descricao = c.getDescricao();
						valorFormatado = c.getValorTotalFormatado();
						dataFormatada = c.getDataVencimentoFormatada();
						situacao = c.getSituacao();
						BigDecimal pago = contasReceberDAO.getValorContasReceberCliente(dataInicial, dataFinal,
								"c.valor_pago", "p.nome", pesquisa);
						valorPago = decimal.format(contasReceberDAO.getValorContasReceberCliente(dataInicial, dataFinal,
								"c.valor_pago", "p.nome", pesquisa));
						valorPagar = decimal.format(contasReceberDAO.getValorContasReceberCliente(dataInicial,
								dataFinal, "c.valor_receber", "p.nome", pesquisa));
						BigDecimal receber = contasReceberDAO.getValorContasReceberCliente(dataInicial, dataFinal,
								"c.valor_receber", "p.nome", pesquisa);

						valorTotal = decimal.format(contasReceberDAO.getValorContasReceberCliente(dataInicial,
								dataFinal, "c.valor_total", "p.nome", pesquisa));

						BigDecimal calcula = pago.subtract(receber);
						qtdeParcela = c.getQtdeParcela();
						numeroParcela = c.getNumeroParcela();
						saldo = decimal.format(calcula);
						dataFim = formatador.format(dataFinal);
						dataInicio = formatador.format(dataInicial);

						ContasReceber contas = new ContasReceber(null, null, null, null, null, qtdeParcela,
								numeroParcela, null, null, situacao, dataFormatada, valorFormatado, valorPago,
								valorPagar, nomeCliente, descricao, saldo, dataInicio, dataFim, valorTotal);

						lista.add(contas);
					}

					Relatorio relatorio = new Relatorio();
					relatorio.gerarRelatorioDespesas(lista);
				}
				break;

			case "Situação":
				if (txtPesquisa.getText().isEmpty()) {
					ValidationFields.checkEmptyFields(txtPesquisa);
					Alert dlg = new Alert(AlertType.ERROR);
					dlg.setContentText("Digite o tipo de situação!");
					dlg.showAndWait();
					txtPesquisa.requestFocus();
					return;
				} else {
					String pesquisa = txtPesquisa.getText();
					for (ContasReceber c : contasReceberDAO.listarTodos(dataInicial, dataFinal, "c.situacao",
							pesquisa)) {
						nomeCliente = c.getNomeCliente();
						descricao = c.getDescricao();
						valorFormatado = c.getValorTotalFormatado();
						dataFormatada = c.getDataVencimentoFormatada();
						situacao = c.getSituacao();
						BigDecimal pago = contasReceberDAO.getValorContasReceber(dataInicial, dataFinal, "valor_pago",
								"situacao", pesquisa);
						valorPago = decimal.format(contasReceberDAO.getValorContasReceber(dataInicial, dataFinal,
								"valor_pago", "situacao", pesquisa));
						valorPagar = decimal.format(contasReceberDAO.getValorContasReceber(dataInicial, dataFinal,
								"valor_receber", "situacao", pesquisa));
						BigDecimal receber = contasReceberDAO.getValorContasReceber(dataInicial, dataFinal,
								"valor_receber", "situacao", pesquisa);

						valorTotal = decimal.format(contasReceberDAO.getValorContasReceber(dataInicial, dataFinal,
								"valor_total", "situacao", pesquisa));

						BigDecimal calcula = pago.subtract(receber);
						qtdeParcela = c.getQtdeParcela();
						numeroParcela = c.getNumeroParcela();
						saldo = decimal.format(calcula);
						dataFim = formatador.format(dataFinal);
						dataInicio = formatador.format(dataInicial);

						ContasReceber contas = new ContasReceber(null, null, null, null, null, qtdeParcela,
								numeroParcela, null, null, situacao, dataFormatada, valorFormatado, valorPago,
								valorPagar, nomeCliente, descricao, saldo, dataInicio, dataFim, valorTotal);

						lista.add(contas);
					}

					Relatorio relatorio = new Relatorio();
					relatorio.gerarRelatorioDespesas(lista);

				}

				break;

			case "Todos":
				for (ContasReceber c : contasReceberDAO.listarTodos(dataInicial, dataFinal)) {
					nomeCliente = c.getNomeCliente();
					descricao = c.getDescricao();
					valorFormatado = c.getValorTotalFormatado();
					dataFormatada = c.getDataVencimentoFormatada();
					situacao = c.getSituacao();
					BigDecimal pago = contasReceberDAO.getValorContasReceber(dataInicial, dataFinal, "valor_pago");
					valorPago = decimal
							.format(contasReceberDAO.getValorContasReceber(dataInicial, dataFinal, "valor_pago"));
					valorPagar = decimal
							.format(contasReceberDAO.getValorContasReceber(dataInicial, dataFinal, "valor_receber"));
					BigDecimal receber = contasReceberDAO.getValorContasReceber(dataInicial, dataFinal,
							"valor_receber");

					valorTotal = decimal
							.format(contasReceberDAO.getValorContasReceber(dataInicial, dataFinal, "valor_total"));

					BigDecimal calcula = pago.subtract(receber);
					qtdeParcela = c.getQtdeParcela();
					numeroParcela = c.getNumeroParcela();
					saldo = decimal.format(calcula);
					dataFim = formatador.format(dataFinal);
					dataInicio = formatador.format(dataInicial);

					ContasReceber contas = new ContasReceber(null, null, null, null, null, qtdeParcela, numeroParcela,
							null, null, situacao, dataFormatada, valorFormatado, valorPago, valorPagar, nomeCliente,
							descricao, saldo, dataInicio, dataFim, valorTotal);

					lista.add(contas);
				}

				Relatorio relatorio = new Relatorio();
				relatorio.gerarRelatorioDespesas(lista);

				break;

			default:
				break;
			}
			conexao.fecharConexao();

		}
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

	public boolean validaRecebido(Integer id) throws SQLException {
		boolean result = false;

		String sql = "SELECT * FROM contas_receber WHERE id = ? AND situacao = 'PAGO' ";

		PreparedStatement cmd = conexao.getConexao().prepareStatement(sql);
		cmd.setInt(1, id);
		ResultSet rs = cmd.executeQuery();
		if (rs.next()) {
			result = true;
		}

		conexao.fecharConexao();
		return result;
	}
}

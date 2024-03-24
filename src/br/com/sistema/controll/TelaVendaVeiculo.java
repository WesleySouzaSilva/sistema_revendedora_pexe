package br.com.sistema.controll;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import br.com.sistema.conexao.Conexao;
import br.com.sistema.filtros.FiltroFlutuante;
import br.com.sistema.filtros.FiltroInteiro;
import br.com.sistema.listeners.ListenerParaMaiusculas;
import br.com.sistema.listeners.ListenerValorMinMax;
import br.com.sistema.model.ContasReceber;
import br.com.sistema.model.Funcionario;
import br.com.sistema.model.HistoricoVeiculo;
import br.com.sistema.model.PagamentoVeiculo;
import br.com.sistema.model.Pessoa;
import br.com.sistema.model.Veiculo;
import br.com.sistema.model.dao.ContasReceberDAO;
import br.com.sistema.model.dao.FuncionarioDAO;
import br.com.sistema.model.dao.HistoricoVeiculoDAO;
import br.com.sistema.model.dao.PagamentoVeiculoDAO;
import br.com.sistema.model.dao.PessoaDAO;
import br.com.sistema.model.dao.VeiculoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaVendaVeiculo {

	@FXML
	private TextField txtNomeVeiculo;

	@FXML
	private TextField txtPlaca;

	@FXML
	private TextField txtRenavam;

	@FXML
	private TextField txtCor;

	@FXML
	private TextField txtAno;

	@FXML
	private TextField txtValorVenda;

	@FXML
	private TableView<Pessoa> tbPessoa;

	@FXML
	private TableColumn<Pessoa, String> clnNome;

	@FXML
	private TableColumn<Pessoa, String> clnCpfCnpj;

	@FXML
	private ComboBox<String> cmbBuscar;

	@FXML
	private ComboBox<Funcionario> cmbFuncionario;

	@FXML
	private ComboBox<String> cmbGarantia;

	@FXML
	private TextField txtPesquisa;

	@FXML
	private Button btnPesquisar;

	@FXML
	private TextField txtPagamentoVeiculo;

	@FXML
	private TextField txtPagamentoCliente;

	@FXML
	private TextField txtParcela;

	@FXML
	private TextField txtValorParcela;

	@FXML
	private TextField txtObservacao;

	@FXML
	private ComboBox<String> cmbTipoPagamento;

	@FXML
	private DatePicker txtDataPagamento;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnConfirmar;

	private Conexao conexao = Principal.getConexao();
	private VeiculoDAO veiculoDAO = null;
	private PessoaDAO pessoaDAO = null;
	private static Integer pessoa_id = null;
	private TelaHome tela;
	private PagamentoVeiculoDAO pagamentoVeiculoDAO = null;
	private ContasReceberDAO contasReceberDAO = null;
	private HistoricoVeiculoDAO historicoVeiculoDAO = null;
	private FuncionarioDAO funcionarioDAO = null;

	public void initialize() {

		comboBoxGarantia();
		listarDados();
		comboBoxPagamento();
		comboBoxBusca();
		listaFuncionario();

		tbPessoa.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarPessoa((Pessoa) newValue));

		txtPesquisa.textProperty().addListener(new ListenerParaMaiusculas(txtPesquisa));
		txtParcela.addEventFilter(KeyEvent.KEY_TYPED, new FiltroInteiro(2));
		txtValorParcela.addEventFilter(KeyEvent.KEY_TYPED, new FiltroFlutuante(9, false));
		txtValorParcela.focusedProperty().addListener(new ListenerValorMinMax(txtValorParcela, 0, 100000));

		btnCancelar.setOnAction(e -> {
			cancelar();
		});

		btnConfirmar.setOnAction(e -> {
			confirmar();
		});

		btnPesquisar.setOnAction(e -> {
			acaoPesquisarPessoa();
		});

		cmbBuscar.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtPesquisa.requestFocus();
				}

			}
		});

		txtPesquisa.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					acaoPesquisarPessoa();
				}

			}
		});

		cmbBuscar.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtPesquisa.requestFocus();
				}

			}
		});

		cmbFuncionario.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					cmbTipoPagamento.requestFocus();
				}

			}
		});

		cmbTipoPagamento.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtParcela.requestFocus();
				}

			}
		});

		txtParcela.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtValorParcela.requestFocus();
				}

			}
		});

		txtValorParcela.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtObservacao.requestFocus();
				}

			}
		});

		txtObservacao.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					cmbGarantia.requestFocus();
				}

			}
		});

		cmbGarantia.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtValorParcela.requestFocus();
				}

			}
		});

		txtDataPagamento.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					confirmar();
				}

			}
		});

	}

	public void selecionarPessoa(Pessoa p) {
		pessoa_id = p.getId();
		txtPagamentoCliente.setText(p.getNome());
	}

	public void listarDados() {
		tela = new TelaHome();
		this.veiculoDAO = Principal.getVeiculoDAO();
		for (Veiculo v : veiculoDAO.listarTodosId(tela.getIdVeiculo())) {
			txtNomeVeiculo.setText(v.getVeiculo());
			txtPagamentoVeiculo.setText(v.getVeiculo());
			txtPlaca.setText(v.getPlaca());
			txtRenavam.setText(v.getRenavam());
			txtCor.setText(v.getCor());
			txtAno.setText(v.getAno_modelo());
			txtValorVenda.setText(v.getValorVendaFormatado());
			txtValorVenda.setText(v.getValorVendaFormatado());
		}
		conexao.fecharConexao();

	}

	public void acaoPesquisarPessoa() {
		if (cmbBuscar.getValue() == null) {
			Alert dlg = new Alert(AlertType.INFORMATION);
			ValidationFields.checkEmptyFields(cmbBuscar);
			dlg.setContentText("selecione o campo BUSCAR!!!");
			dlg.showAndWait();
			cmbBuscar.requestFocus();
		} else {
			String cmb = cmbBuscar.getValue().toString();
			switch (cmb) {
			case "Nome":
				if (txtPesquisa.getText().isEmpty()) {
					Alert dlg = new Alert(AlertType.INFORMATION);
					dlg.setContentText("Preencha o campo pesquisa com o Nome do cliente!!!");
					dlg.showAndWait();
					ValidationFields.checkEmptyFields(txtPesquisa);
					txtPesquisa.requestFocus();

				} else {
					this.pessoaDAO = Principal.getPessoaDAO();
					String sql = txtPesquisa.getText();
					clnNome.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("nome"));
					clnCpfCnpj.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("cpfcnpj"));
					ObservableList<Pessoa> lista = FXCollections.observableArrayList(pessoaDAO.buscarNome(sql));
					tbPessoa.setItems(lista);
					conexao.fecharConexao();
				}

				break;
			case "Cpf_Cnpj":
				if (txtPesquisa.getText().isEmpty()) {
					Alert dlg = new Alert(AlertType.INFORMATION);
					dlg.setContentText("Preencha o campo pesquisa com o Cpf_Cnpj do cliente!!!");
					dlg.showAndWait();
					ValidationFields.checkEmptyFields(txtPesquisa);
					txtPesquisa.requestFocus();

				} else {
					this.pessoaDAO = Principal.getPessoaDAO();
					String sql = txtPesquisa.getText();
					clnNome.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("Nome"));
					clnCpfCnpj.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("cpfcnpj"));
					ObservableList<Pessoa> lista = FXCollections
							.observableArrayList(pessoaDAO.buscarCpfCnpjCliente(formatarCpfCnpj(sql)));
					tbPessoa.setItems(lista);
					conexao.fecharConexao();

				}

				break;
			case "Todos":
				this.pessoaDAO = Principal.getPessoaDAO();
				clnNome.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("Nome"));
				clnCpfCnpj.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("cpfcnpj"));
				ObservableList<Pessoa> lista = FXCollections.observableArrayList(pessoaDAO.listarTodos());
				tbPessoa.setItems(lista);
				conexao.fecharConexao();

				break;

			default:
				break;

			}

		}
	}

	public void confirmar() {
		String tipoPagamento = null, vendedor = null;
		BigDecimal valor = null, lucroVenda = null;
		Date dataPagamento = null, dataGarantia = null;
		String observacao = null, garantia = null;
		Date dataAtual = new Date();
		Date dataHoje = new Date();
		Integer qtdeParcela = null;
		tela = new TelaHome();
		Calendar calendario = Calendar.getInstance();

		if (tbPessoa.getSelectionModel().getSelectedItem() == null) {
			ValidationFields.checkEmptyFields(txtPagamentoCliente);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Você esqueceu de selecionar o CLIENTE na aba 'Cliente'!");
			dlg.showAndWait();
			txtPagamentoCliente.requestFocus();
			return;
		}

		if (cmbFuncionario.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbFuncionario);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione o vendedor do veiculo!");
			dlg.showAndWait();
			cmbFuncionario.requestFocus();
			return;

		} else {

			vendedor = cmbFuncionario.getValue().toString();

		}

		if (cmbTipoPagamento.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbTipoPagamento);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione o tipo de pagamento!");
			dlg.showAndWait();
			cmbTipoPagamento.requestFocus();
			return;

		} else {

			tipoPagamento = cmbTipoPagamento.getValue().toString();

		}

		if (txtParcela.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtParcela);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Parcela!");
			dlg.showAndWait();
			txtParcela.requestFocus();
			return;

		} else {

			qtdeParcela = Integer.parseInt(txtParcela.getText());

		}

		if (txtObservacao.getText().isEmpty()) {
			observacao = new String("");

		} else {

			observacao = txtObservacao.getText();

		}

		if (cmbGarantia.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbGarantia);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione a garantia!");
			dlg.showAndWait();
			cmbGarantia.requestFocus();
			return;

		} else {

			garantia = cmbGarantia.getValue();

			switch (cmbGarantia.getValue()) {
			case "SEM GARANTIA":
				System.out.println("dataAtual sem passar no calendario : " + dataAtual);
				calendario.setTime(dataAtual);
				dataAtual = calendario.getTime();
				dataGarantia = new java.sql.Date(dataAtual.getTime());
				System.out.println("dataGarantia : " + cmbGarantia.getValue() + " data : " + dataGarantia);

				break;

			case "3 MESES GARANTIA":
				System.out.println("dataAtual sem passar no calendario : " + dataAtual);
				calendario.setTime(dataAtual);
				calendario.add(Calendar.DATE, +90);
				dataAtual = calendario.getTime();
				dataGarantia = new java.sql.Date(dataAtual.getTime());
				System.out.println("dataGarantia : " + cmbGarantia.getValue() + " data : " + dataGarantia);

				break;

			case "1 ANO GARANTIA":
				System.out.println("dataAtual sem passar no calendario : " + dataAtual);
				calendario.setTime(dataAtual);
				calendario.add(Calendar.DATE, +365);
				dataAtual = calendario.getTime();
				dataGarantia = new java.sql.Date(dataAtual.getTime());
				System.out.println("dataGarantia : " + cmbGarantia.getValue() + " data : " + dataGarantia);

				break;

			case "2 ANO GARANTIA":
				System.out.println("dataAtual sem passar no calendario : " + dataAtual);
				calendario.setTime(dataAtual);
				calendario.add(Calendar.DATE, +730);
				dataAtual = calendario.getTime();
				dataGarantia = new java.sql.Date(dataAtual.getTime());
				System.out.println("dataGarantia : " + cmbGarantia.getValue() + " data : " + dataGarantia);

				break;

			default:
				break;
			}

		}

		if (txtValorParcela.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtValorParcela);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Valor Parcela!");
			dlg.showAndWait();
			txtValorParcela.requestFocus();
			return;

		} else {
			valor = new BigDecimal(txtValorParcela.getText().replace(".", "").replaceAll(",", "."));

		}

		if (txtDataPagamento.getValue() == null) {
			ValidationFields.checkEmptyFields(txtDataPagamento);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Data Pagamento!");
			dlg.showAndWait();
			txtDataPagamento.requestFocus();
			return;

		} else {
			Date data = java.sql.Date.valueOf(txtDataPagamento.getValue());
			System.out.println("comparando data atual: " + data.equals(dataHoje));
			if (data.after(dataHoje) || data.equals(dataHoje)) {
				dataPagamento = java.sql.Date.valueOf(txtDataPagamento.getValue());

			} else {

				ValidationFields.checkEmptyFields(txtDataPagamento);
				Alert dlg = new Alert(AlertType.ERROR);
				dlg.setContentText("A Data não pode ser inferior a Data Atual!");
				dlg.showAndWait();
				txtDataPagamento.requestFocus();
				return;

			}
		}

		if (txtParcela.getText() != null && txtValorParcela.getText() != null && txtDataPagamento.getValue() != null) {
			this.veiculoDAO = Principal.getVeiculoDAO();
			BigDecimal valorEntrada = veiculoDAO.getValorEntrada(tela.getIdVeiculo());
			BigDecimal valorDespesa = veiculoDAO.getTotalDespesaVeiculo(tela.getIdVeiculo());
			BigDecimal valorCalculo = valorEntrada.add(valorDespesa);
			BigDecimal valorVenda = veiculoDAO.getValorVendaVeiculo(tela.getIdVeiculo());
			lucroVenda = valorVenda.subtract(valorCalculo);
			System.out.println(
					"valor entrada do veiculo : " + valorEntrada + "\nValor despesas veiculo : " + valorDespesa);
			System.out.println("valor venda do veiculo : " + valorVenda + "\nValor custo veiculo : " + valorCalculo
					+ "\nValor lucro do veiculo : " + lucroVenda);
			conexao.fecharConexao();

			String situacao = new String("PENDENTE");
			String descricao = new String("VENDA DE VEICULO");

			Veiculo ve = new Veiculo(tela.getIdVeiculo());
			Pessoa pessoa = new Pessoa(pessoa_id, null, null, null, null, null, null, null, null, null);
			PagamentoVeiculo pagamentoVeiculo = new PagamentoVeiculo(null, dataHoje, lucroVenda, valor, tipoPagamento,
					txtParcela.getText(), observacao, garantia, ve, pessoa, dataGarantia);

			HistoricoVeiculo historico = new HistoricoVeiculo(null, "VENDA", descricao, dataHoje, valorVenda, vendedor,
					null, null, null, null, ve, pessoa);

			Veiculo vei = new Veiculo(tela.getIdVeiculo(), "VENDA", dataHoje, vendedor);

			ButtonType sim = new ButtonType("SIM", ButtonBar.ButtonData.YES);
			ButtonType nao = new ButtonType("NÃO", ButtonBar.ButtonData.NO);
			Alert alerta = new Alert(AlertType.INFORMATION,
					"Você quer mesmo registrar a venda para o Veiculo?\nO veiculo '" + txtNomeVeiculo.getText()
							+ "' será vendido!\nVocê tem certeza?",
					sim, nao);

			Optional<ButtonType> result = alerta.showAndWait();
			if (result.get().equals(sim)) {
				ContasReceber contas = new ContasReceber(null, dataPagamento, valor, new BigDecimal("0.0"), valor,
						qtdeParcela, 1, pessoa, ve, situacao, null, null, null, null, null, descricao, null, null,
						null);
				this.contasReceberDAO = Principal.getContasReceberDAO();
				boolean suce = contasReceberDAO.inserirPagamentoTemporario(contas);
				System.out.println("sucesso inserir contas receber : " + suce);
				conexao.fecharConexao();
				Integer par = qtdeParcela - 1;
				if (cmbTipoPagamento.getValue().equalsIgnoreCase("PROMISSORIA")
						|| cmbTipoPagamento.getValue().equalsIgnoreCase("CHEQUE")) {
					for (int i = 0; i < par; i++) {
						this.contasReceberDAO = Principal.getContasReceberDAO();
						Integer numeros = contasReceberDAO.getNumeroParcela(ve.getId());
						Integer nume = numeros + 1;
						Calendar c = Calendar.getInstance();
						c.setTime(dataPagamento);
						c.add(Calendar.MONTH, 1);
						Date data = c.getTime();
						dataPagamento = new java.sql.Date(data.getTime());
						ContasReceber conta = new ContasReceber(null, dataPagamento, valor, new BigDecimal("0.0"),
								valor, qtdeParcela, nume, pessoa, ve, situacao, null, null, null, null, null, descricao,
								null, null, null);
						boolean suces = contasReceberDAO.inserirPagamentoTemporario(conta);
						System.out.println("sucesso inserir contas receber temporario : " + suces);
						conexao.fecharConexao();
					}
					this.contasReceberDAO = Principal.getContasReceberDAO();
					for (ContasReceber c : contasReceberDAO.listarTodosPagamentoTemporarioParcelas(ve.getId())) {
						ContasReceber conta = new ContasReceber(null, c.getDataVencimento(), c.getValorTotal(),
								new BigDecimal("0.0"), c.getValorPendente(), c.getQtdeParcela(), c.getNumeroParcela(),
								pessoa, ve, situacao, null, null, null, null, null, descricao, null, null, null);
						boolean suces = contasReceberDAO.inserir(conta);
						System.out.println("sucesso inserir contas receber : " + suces);
					}

				} else {
					this.contasReceberDAO = Principal.getContasReceberDAO();
					ContasReceber conta = new ContasReceber(null, dataPagamento, valorVenda, new BigDecimal("0.0"),
							new BigDecimal("0.0"), 1, 1, pessoa, ve, situacao, null, null, null, null, null, descricao,
							null, null, null);
					boolean suces = contasReceberDAO.inserir(conta);
					System.out.println("sucesso inserir contas receber  : " + suces);
					conexao.fecharConexao();
				}
				this.contasReceberDAO = Principal.getContasReceberDAO();
				boolean apagarTemporario = contasReceberDAO.apagarPagamentoTemporario();
				System.out.println("apagou dados temporarios : " + apagarTemporario);
				conexao.fecharConexao();

				this.pagamentoVeiculoDAO = Principal.getPagamentoVeiculoDAO();
				boolean sucesso = pagamentoVeiculoDAO.inserir(pagamentoVeiculo);
				System.out.println("valor boolean :" + sucesso);
				if (sucesso) {
					this.historicoVeiculoDAO = Principal.getHistoricoVeiculoDAO();
					boolean suces = historicoVeiculoDAO.inserir(historico);
					System.out.println("sucesso inserir historico veiculo : " + suces);
					conexao.fecharConexao();
					this.veiculoDAO = Principal.getVeiculoDAO();
					boolean sucessoAtualizaVeiculo = veiculoDAO.atualizarSituacao(vei);
					System.out.println("sucesso atualizar veiculo : " + sucessoAtualizaVeiculo);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Confirmação de VENDA");
					alert.setHeaderText("Venda do veiculo salva com sucesso!");
					alert.showAndWait();
				}
				conexao.fecharConexao();
				voltarTela();

			}
		}

	}

	public void cancelar() {
		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.setTitle("Confirmação de Cancelamento");
		alerta.setHeaderText("Você quer mesmo cancelar ? ");
		alerta.setContentText("Todos os dados preenchidos serão perdidos \nVocê tem certeza?");
		Optional<ButtonType> escolha = alerta.showAndWait();

		if (escolha.get() == ButtonType.OK) {
			Stage stage = (Stage) btnCancelar.getScene().getWindow();
			stage.close();
		} else {

		}

	}

	public void voltarTela() {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	private void listaFuncionario() {
		this.funcionarioDAO = Principal.getFuncionarioDAO();
		cmbFuncionario.getItems().addAll(funcionarioDAO.listar());
		conexao.fecharConexao();
	}

	public void comboBoxBusca() {

		String nome = new String("Nome");
		String cpfCnpj = new String("Cpf_Cnpj");
		String todos = new String("Todos");

		ArrayList<String> listaBusca = new ArrayList<>();
		listaBusca.add(nome);
		listaBusca.add(cpfCnpj);
		listaBusca.add(todos);

		ObservableList<String> lista = FXCollections.observableArrayList(listaBusca);
		cmbBuscar.setItems(lista);
		lista.toString();
	}

	public void comboBoxPagamento() {

		String nome = new String("A VISTA");
		String cpfCnpj = new String("CARTAO DE CREDITO");
		String nome2 = new String("PROMISSORIA");
		String nome3 = new String("BOLETO");
		String nome4 = new String("FINANCIAMENTO BANCARIO");
		String nome5 = new String("TRANSFERENCIA BANCARIA");

		ArrayList<String> listaBusca = new ArrayList<>();
		listaBusca.add(nome);
		listaBusca.add(cpfCnpj);
		listaBusca.add(nome2);
		listaBusca.add(nome3);
		listaBusca.add(nome4);
		listaBusca.add(nome5);

		ObservableList<String> lista = FXCollections.observableArrayList(listaBusca);
		cmbTipoPagamento.setItems(lista);
		lista.toString();
	}

	public String formatarCpfCnpj(String campo) {
		Integer recebe = campo.length();
		if (recebe == 11) {
			String cpf = campo;
			String bloco1 = cpf.substring(0, 3);
			String bloco2 = cpf.substring(3, 6);
			String bloco3 = cpf.substring(6, 9);
			String bloco4 = cpf.substring(9, 11);
			cpf = bloco1 + "." + bloco2 + "." + bloco3 + "-" + bloco4;
			return cpf;
		} else {
			String cnpj = campo;
			String bloco1 = cnpj.substring(0, 2);
			String bloco2 = cnpj.substring(2, 5);
			String bloco3 = cnpj.substring(5, 8);
			String bloco4 = cnpj.substring(8, 12);
			String bloco5 = cnpj.substring(12, 14);
			cnpj = bloco1 + "." + bloco2 + "." + bloco3 + "/" + bloco4 + "-" + bloco5;
			return cnpj;
		}
	}

	private void comboBoxGarantia() {
		String semGarantia = new String("SEM GARANTIA");
		String garantia3Meses = new String("3 MESES GARANTIA");
		String garantia1Ano = new String("1 ANO GARANTIA");
		String garantia2Ano = new String("2 ANO GARANTIA");

		ArrayList<String> listaBusca = new ArrayList<>();
		listaBusca.add(semGarantia);
		listaBusca.add(garantia3Meses);
		listaBusca.add(garantia1Ano);
		listaBusca.add(garantia2Ano);

		cmbGarantia.getItems().addAll(listaBusca);
	}

}

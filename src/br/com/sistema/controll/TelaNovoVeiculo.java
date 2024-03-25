package br.com.sistema.controll;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.sistema.conexao.Conexao;
import br.com.sistema.filtros.FiltroChassis;
import br.com.sistema.filtros.FiltroFlutuante;
import br.com.sistema.filtros.FiltroInteiro;
import br.com.sistema.listeners.ListenerParaMaiusculas;
import br.com.sistema.listeners.ListenerValorMinMax;
import br.com.sistema.model.ClienteCrm;
import br.com.sistema.model.Despesa;
import br.com.sistema.model.HistoricoVeiculo;
import br.com.sistema.model.Pessoa;
import br.com.sistema.model.Veiculo;
import br.com.sistema.model.dao.DespesaDAO;
import br.com.sistema.model.dao.HistoricoVeiculoDAO;
import br.com.sistema.model.dao.PessoaDAO;
import br.com.sistema.model.dao.VeiculoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class TelaNovoVeiculo {

	@FXML
	private TableView<Pessoa> tbPessoa;

	@FXML
	private TableColumn<Pessoa, String> clnNome;

	@FXML
	private TableColumn<Pessoa, String> clnCpfCnpj;

	@FXML
	private TableColumn<Pessoa, String> clnAtivo;

	@FXML
	private TableColumn<Pessoa, String> clnRg;

	@FXML
	private TableColumn<Pessoa, String> clnTipo;;

	@FXML
	private ComboBox<String> cmbBuscar;

	@FXML
	private ComboBox<String> cmbCategoria;

	@FXML
	private ComboBox<String> cmbMarca;

	@FXML
	private ComboBox<String> cmbFinanciamento;

	@FXML
	private ComboBox<String> cmbLeilao;

	@FXML
	private ComboBox<String> cmbTipoEntrada;

	@FXML
	private TextField txtPesquisa;

	@FXML
	private Button btnPesquisar;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtPlaca;

	@FXML
	private TextField txtRenavam;

	@FXML
	private TextField txtCor;

	@FXML
	private TextField txtValorVenda;

	@FXML
	private TextField txtValorEntrada;

	@FXML
	private TextField txtCliente;

	@FXML
	private TextField txtAnoModelo;

	@FXML
	private TextField txtKm;

	@FXML
	private TextField txtValorFipe;

	@FXML
	private Button btnConfirmar;

	@FXML
	private Button btnCancelar;

	@FXML
	private Label lblConexao;

	private PessoaDAO pessoaDAO = null;
	private VeiculoDAO veiculoDAO = null;
	private DespesaDAO despesaDAO = null;
	private HistoricoVeiculoDAO historicoVeiculoDAO = null;
	private static Integer pessoa_id = null;
	private Conexao conexao;
	ArrayList<ClienteCrm> listaVeiculosEncontrados = new ArrayList<>();

	public void initialize() {
		comboBoxCategoriaVeiculo();
		comboBoxAlternativas();
		comboBoxTipoEntradaVeiculo();

		txtNome.textProperty().addListener(new ListenerParaMaiusculas(txtNome));
		txtRenavam.addEventFilter(KeyEvent.KEY_TYPED, new FiltroInteiro(11));
		txtKm.addEventFilter(KeyEvent.KEY_TYPED, new FiltroInteiro(12));
		txtCor.textProperty().addListener(new ListenerParaMaiusculas(txtCor));
		txtPesquisa.textProperty().addListener(new ListenerParaMaiusculas(txtPesquisa));
		txtValorEntrada.addEventFilter(KeyEvent.KEY_TYPED, new FiltroFlutuante(9, false));
		txtValorEntrada.focusedProperty().addListener(new ListenerValorMinMax(txtValorEntrada, 0, 100000));
		txtValorVenda.addEventFilter(KeyEvent.KEY_TYPED, new FiltroFlutuante(9, false));
		txtValorVenda.focusedProperty().addListener(new ListenerValorMinMax(txtValorVenda, 0, 100000));
		txtValorFipe.addEventFilter(KeyEvent.KEY_TYPED, new FiltroFlutuante(9, false));
		txtValorFipe.focusedProperty().addListener(new ListenerValorMinMax(txtValorFipe, 0, 100000));
		txtPlaca.addEventFilter(KeyEvent.KEY_TYPED, new FiltroChassis(7));
		txtPlaca.textProperty().addListener(new ListenerParaMaiusculas(txtPlaca));

		comboBoxBusca();
		btnPesquisar.setOnAction(e -> {
			acaoPesquisarPessoa();
		});

		cmbCategoria.setOnAction(e -> {
			comboBoxMarcaVeiculo();
		});

		btnConfirmar.setOnAction(e -> {
			try {
				confirmar();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btnCancelar.setOnAction(e -> {
			cancelar();
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
		tbPessoa.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarPessoa((Pessoa) newValue));

		cmbCategoria.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					cmbMarca.requestFocus();
				}

			}
		});

		cmbMarca.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtNome.requestFocus();
				}

			}
		});

		txtNome.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtPlaca.requestFocus();
				}

			}
		});

		txtPlaca.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtAnoModelo.requestFocus();
				}

			}
		});

		txtAnoModelo.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtRenavam.requestFocus();
				}

			}
		});

		txtRenavam.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtCor.requestFocus();
				}

			}
		});

		txtCor.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtKm.requestFocus();
				}

			}
		});

		txtKm.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					cmbLeilao.requestFocus();
				}

			}
		});

		cmbLeilao.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					cmbFinanciamento.requestFocus();
				}

			}
		});

		cmbFinanciamento.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					cmbTipoEntrada.requestFocus();
				}

			}
		});

		cmbTipoEntrada.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtValorEntrada.requestFocus();
				}

			}
		});

		txtValorEntrada.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtValorVenda.requestFocus();
				}

			}
		});

		txtValorVenda.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtValorFipe.requestFocus();
				}

			}
		});

		txtValorFipe.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					try {
						confirmar();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
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

			clnNome.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("nome"));
			clnCpfCnpj.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("cpfcnpj"));
			clnAtivo.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("ativo"));
			clnRg.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("rg"));
			clnTipo.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("tipo"));
			this.conexao = new Conexao();
			this.pessoaDAO = new PessoaDAO(conexao);
			switch (cmb) {
			case "Nome":
				if (txtPesquisa.getText().isEmpty()) {
					Alert dlg = new Alert(AlertType.INFORMATION);
					dlg.setContentText("Preencha o campo pesquisa com o Nome do cliente!!!");
					dlg.showAndWait();
					ValidationFields.checkEmptyFields(txtPesquisa);
					txtPesquisa.requestFocus();

				} else {
					ObservableList<Pessoa> lista = FXCollections
							.observableArrayList(pessoaDAO.buscarNome(txtPesquisa.getText()));
					tbPessoa.setItems(lista);
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
					ObservableList<Pessoa> lista = FXCollections.observableArrayList(
							pessoaDAO.buscarCpfCnpjCliente(formatarCpfCnpj(txtPesquisa.getText())));
					tbPessoa.setItems(lista);

				}

				break;
			case "Todos":
				ObservableList<Pessoa> lista = FXCollections.observableArrayList(pessoaDAO.listarTodos());
				tbPessoa.setItems(lista);

				break;

			default:
				break;

			}
			conexao.fecharConexao();

		}
	}

	public void selecionarPessoa(Pessoa pessoa) {

		if (pessoa != null) {

			pessoa_id = Integer.valueOf(pessoa.getId());
			txtCliente.setText(pessoa.getNome());
			System.out.println("pessoa id :" + pessoa_id);

		}
	}

	public void confirmar() throws SQLException {
		String veiculo = null, ano_modelo = null, marca = null, placa = null, renavam = null, cor = null,
				situacao = null, sinistroRs = null, financiamento = null, categoria = null, km = null;
		Date dataEntrada = null;
		BigDecimal valorEntrada = null;
		BigDecimal valorVenda = null;
		BigDecimal valorFipe = null;

		if (tbPessoa.getSelectionModel().getSelectedItem() == null) {
			ValidationFields.checkEmptyFields(txtNome);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Você esqueceu de selecioanr o CLIENTE na aba Cliente!");
			dlg.showAndWait();
			txtNome.requestFocus();
			return;

		}

		if (cmbCategoria.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbCategoria);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione a categoria Veiculo!");
			dlg.showAndWait();
			cmbCategoria.requestFocus();
			return;

		} else {

			categoria = cmbCategoria.getValue().toString();

		}

		if (cmbMarca.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbMarca);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione a marca Veiculo!");
			dlg.showAndWait();
			cmbMarca.requestFocus();
			return;

		} else {

			marca = cmbMarca.getValue().toString();

		}

		if (txtNome.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtNome);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Veiculo!");
			dlg.showAndWait();
			txtNome.requestFocus();
			return;

		} else {

			veiculo = txtNome.getText();

		}

		if (txtPlaca.getText().isEmpty()) {
			placa = new String();

		} else {

			String validar = txtPlaca.getText();
			System.out.println("validar placa : " + validar);

			if (validaPlaca(validar)) {

			} else {
				Alert dlg = new Alert(AlertType.WARNING);
				dlg.setContentText("Placa digitada não e válida!!!");
				dlg.showAndWait();
				txtPlaca.requestFocus();
				return;
			}

			placa = txtPlaca.getText();

		}

		if (txtAnoModelo.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtAnoModelo);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Ano/Modelo!");
			dlg.showAndWait();
			txtAnoModelo.requestFocus();
			return;

		} else {
			ano_modelo = txtAnoModelo.getText();

		}

		if (txtRenavam.getText().isEmpty()) {
			renavam = new String("");

		} else {
			renavam = txtRenavam.getText();

		}

		if (txtCor.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtCor);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Cor!");
			dlg.showAndWait();
			txtCor.requestFocus();
			return;

		} else {
			cor = txtCor.getText();

		}

		if (txtKm.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtKm);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo KM!");
			dlg.showAndWait();
			txtKm.requestFocus();
			return;

		} else {
			km = txtKm.getText();

		}

		if (cmbLeilao.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbLeilao);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione se possui Leilão/RS");
			dlg.showAndWait();
			cmbLeilao.requestFocus();
			return;

		} else {

			sinistroRs = cmbLeilao.getValue();

		}

		if (cmbFinanciamento.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbFinanciamento);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione se possui Financiamento");
			dlg.showAndWait();
			cmbFinanciamento.requestFocus();
			return;

		} else {

			financiamento = cmbFinanciamento.getValue();

		}

		if (cmbTipoEntrada.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbTipoEntrada);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione o tipo de entrada");
			dlg.showAndWait();
			cmbTipoEntrada.requestFocus();
			return;

		} else {

			situacao = cmbTipoEntrada.getValue();

		}

		if (txtValorEntrada.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtValorEntrada);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Valor Entrada!");
			dlg.showAndWait();
			txtValorEntrada.requestFocus();
			return;

		} else {
			valorEntrada = new BigDecimal(txtValorEntrada.getText().replace(",", "."));

		}

		if (txtValorVenda.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtValorVenda);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Valor Venda!");
			dlg.showAndWait();
			txtValorVenda.requestFocus();
			return;

		} else {
			BigDecimal valor = new BigDecimal(txtValorVenda.getText().replace(",", "."));
			if (valorEntrada.compareTo(valor) == 1) {
				ValidationFields.checkEmptyFields(txtValorVenda);
				Alert dlg = new Alert(AlertType.ERROR);
				dlg.setContentText("O VALOR DE VENDA esta menor que o VALOR DE ENTRADA!\nValor Entrada : "
						+ txtValorEntrada.getText() + "\nValor Venda : " + txtValorVenda.getText()
						+ "\nNão é possivel continuar, altere o valor para prosseguir!");
				dlg.showAndWait();
				txtValorVenda.requestFocus();
				return;

			} else {
				valorVenda = new BigDecimal(txtValorVenda.getText().replace(",", "."));
			}

		}

		if (txtValorFipe.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtValorFipe);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Valor Fipe!");
			dlg.showAndWait();
			txtValorFipe.requestFocus();
			return;

		} else {
			valorFipe = new BigDecimal(txtValorFipe.getText().replace(",", "."));

		}
		
		this.conexao = new Conexao();
		if (verificaVeiculo(veiculo, placa, renavam)) {
			ValidationFields.checkEmptyFields(txtNome);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Veiculo " + veiculo + " com a placa " + placa + " ja está cadastrado!");
			dlg.showAndWait();
			txtNome.requestFocus();
			conexao.fecharConexao();
			return;
		}

		Pessoa pessoa = new Pessoa(pessoa_id, null, null, null, null, null, null, null, null, null);

		dataEntrada = java.sql.Date.valueOf(LocalDate.now());

		Pessoa pe = new Pessoa(pessoa_id, null, null, null, null, null, null, null, null, null);
		Veiculo veiculo2 = new Veiculo(null, veiculo, marca, ano_modelo, renavam, placa, cor, situacao, null, null,
				null, null, dataEntrada, null, valorEntrada, valorVenda, pessoa, sinistroRs, financiamento, categoria,
				km, valorFipe, null, null, null);
		ButtonType sim = new ButtonType("SIM", ButtonBar.ButtonData.YES);
		ButtonType nao = new ButtonType("NÃO", ButtonBar.ButtonData.NO);
		Alert alert = new Alert(AlertType.INFORMATION, "Deseja realmente cadastrar um novo veiculo ?", sim, nao);

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get().equals(sim)) {
			this.conexao = new Conexao();
			this.veiculoDAO = new VeiculoDAO(conexao);
			boolean sucesso = veiculoDAO.inserir(veiculo2);
			System.out.println("valor boolean :" + sucesso);
			conexao.fecharConexao();
			if (sucesso) {
				TelaLogin tela = new TelaLogin();
				String tipo = new String("ENTRADA");
				String descricao = new String("ENTRADA DE VEICULO NO ESTOQUE");
				salvarHistorico(tipo, descricao, situacao, tela.permissaoUsuario(), valorEntrada, dataEntrada, veiculo2,
						pessoa);
				if (cmbTipoEntrada.getValue().equalsIgnoreCase("ESTOQUE")) {
					salvarDespesa("DESPESA COMPRA VEICULO", descricao, "PENDENTE", tela.permissaoUsuario(),
							valorEntrada, dataEntrada, veiculo2, pe);
				}
				Alert aler = new Alert(AlertType.INFORMATION);
				aler.setTitle("Confirmação de INCLUSÃO");
				aler.setHeaderText("Veiculo salvo com sucesso!");
				aler.showAndWait();
				verificaClienteInteresse(cmbCategoria.getValue(), marca, valorVenda);

			}
			voltarTela();

		}
		
		conexao.fecharConexao();

	}

	public void salvarHistorico(String tipo, String descricao, String situacao, String responsavel, BigDecimal valor,
			Date dataEntrada, Veiculo veiculo, Pessoa pessoa) {
		this.conexao = new Conexao();
		HistoricoVeiculo h = new HistoricoVeiculo(null, tipo, descricao, dataEntrada, valor, responsavel, null, null,
				null, null, veiculo, pessoa);
		this.historicoVeiculoDAO = new HistoricoVeiculoDAO(conexao);
		boolean sucesso = historicoVeiculoDAO.inserir(h);
		if (sucesso) {
			System.out.println("historico do veiculo salvo com sucesso!");
		}
		conexao.fecharConexao();

	}

	public void salvarDespesa(String tipo, String descricao, String situacao, String responsavel, BigDecimal valor,
			Date data, Veiculo veiculo, Pessoa pessoa) {
		Despesa despesa = new Despesa(null, tipo, descricao, data, null, valor, null, responsavel, null, null, veiculo,
				null, pessoa, situacao);
		this.conexao = new Conexao();
		this.despesaDAO = new DespesaDAO(conexao);
		boolean sucesso = despesaDAO.inserirDespesaVeiculoCompra(despesa);
		if (sucesso) {
			System.out.println("despesa compra do veiculo salva com sucesso!");
		}
		conexao.fecharConexao();

	}

	private void verificaClienteInteresse(String marca, String modelo, BigDecimal valorVenda) {
		this.conexao = new Conexao();
		this.pessoaDAO = new PessoaDAO(conexao);
		List<ClienteCrm> lista = pessoaDAO.obterClientesEncontrados(marca, modelo, valorVenda);
		conexao.fecharConexao();

		if (!lista.isEmpty()) {

			StringBuilder stringBuilder = new StringBuilder();
			for (ClienteCrm cliente : lista) {

				stringBuilder.append("-----------------------------------------------------").append("\n");
				stringBuilder.append("Nome: ").append(cliente.getNomeCliente()).append("\n");
				stringBuilder.append("Telefone: ").append(cliente.getTelefone()).append("\n");
				stringBuilder.append("Tipo: ").append(cliente.getTipo()).append("\n");
				stringBuilder.append("Marca: ").append(cliente.getMarca()).append("\n");
				stringBuilder.append("Modelo: ").append(cliente.getModelo()).append("\n");
				stringBuilder.append("valor Inicial: ").append(cliente.getValorInicial()).append("\n");
				stringBuilder.append("valor Final: ").append(cliente.getValorFinal()).append("\n");
				stringBuilder.append("-----------------------------------------------------").append("\n");
				stringBuilder.append("\n");
				stringBuilder.append("\n");
			}

			if (stringBuilder.length() > 0) {
				String texto = stringBuilder.toString();
				TextArea textArea = new TextArea(texto);
				textArea.setEditable(false);

				Alert alerta = new Alert(AlertType.WARNING);
				alerta.setHeaderText("Clientes Interessados Encontrados Para o Veiculo cadastrado !");
				alerta.getDialogPane().setContent(textArea);
				alerta.showAndWait();
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

	public void comboBoxAlternativas() {

		String nome = new String("SIM");
		String cpfCnpj = new String("NAO");

		ArrayList<String> listaBusca = new ArrayList<>();
		listaBusca.add(nome);
		listaBusca.add(cpfCnpj);
		cmbFinanciamento.getItems().addAll(listaBusca);
		cmbLeilao.getItems().addAll(listaBusca);

	}

	public void comboBoxTipoEntradaVeiculo() {

		String nome = new String("ESTOQUE");
		String cpfCnpj = new String("CONSIGNADO");

		ArrayList<String> listaBusca = new ArrayList<>();
		listaBusca.add(nome);
		listaBusca.add(cpfCnpj);
		cmbTipoEntrada.getItems().addAll(listaBusca);

	}

	public void comboBoxCategoriaVeiculo() {
		this.conexao = new Conexao();
		cmbCategoria.getItems().clear();
		this.veiculoDAO = new VeiculoDAO(conexao);
		cmbCategoria.getItems().addAll(veiculoDAO.listarCategoriaMarca());
		conexao.fecharConexao();
	}

	public void comboBoxMarcaVeiculo() {
		this.conexao = new Conexao();
		this.veiculoDAO = new VeiculoDAO(conexao);
		cmbMarca.getItems().clear();
		if (cmbCategoria.getSelectionModel().getSelectedItem() != null) {
			cmbMarca.getItems()
					.addAll(veiculoDAO.listarModeloMarca(cmbCategoria.getSelectionModel().getSelectedItem()));
		}
		conexao.fecharConexao();
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

	public boolean validaPlaca(String placa) {
		boolean result = false;

		Pattern pattern = Pattern.compile("[A-Z]{3}[0-9]{1}[A-Z]{1}[0-9]{2}|[A-Z]{3}\\-[0-9]{4}");
		Matcher mat = pattern.matcher(placa);
		if (!mat.matches()) {
			result = false;
		} else {
			result = true;

		}
		return result;

	}

	public boolean verificaVeiculo(String nome, String placa, String renavam) throws SQLException {
		boolean result = false;

		String sql = "SELECT * FROM veiculo WHERE veiculo = '" + nome + "' AND placa = '" + placa + "' AND renavam = '"
				+ renavam + "' AND situacao = 'ESTOQUE'";

		PreparedStatement cmd = conexao.getConexao().prepareStatement(sql);
		ResultSet rs = cmd.executeQuery();
		if (rs.next()) {
			result = true;

		}
		conexao.fecharConexao();
		return result;
	}

}

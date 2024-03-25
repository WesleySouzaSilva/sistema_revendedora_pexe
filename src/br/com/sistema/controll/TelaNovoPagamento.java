package br.com.sistema.controll;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import br.com.sistema.model.Pessoa;
import br.com.sistema.model.dao.ContasReceberDAO;
import br.com.sistema.model.dao.PessoaDAO;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class TelaNovoPagamento {

	@FXML
	private TableView<Pessoa> tbPessoa;

	@FXML
	private TableColumn<Pessoa, String> clnNome;

	@FXML
	private TableColumn<Pessoa, String> clnCpfCnpj;

	@FXML
	private ComboBox<String> cmbBuscar;

	@FXML
	private TextField txtPesquisa;

	@FXML
	private Button btnPesquisar;

	@FXML
	private TextField txtNomeCliente;

	@FXML
	private TextField txtDescricao;

	@FXML
	private DatePicker txtData;

	@FXML
	private TextField txtValor;

	@FXML
	private TextField txtParcela;

	@FXML
	private Button btnSalvar;

	@FXML
	private Button btnCancelar;

	private Conexao conexao;
	private ContasReceberDAO contasReceberDAO = null;
	private PessoaDAO pessoaDAO = null;
	private static Integer pessoa_id;

	public void initialize() {

		comboBoxBusca();

		txtNomeCliente.setEditable(false);
		txtPesquisa.textProperty().addListener(new ListenerParaMaiusculas(txtPesquisa));
		txtParcela.addEventFilter(KeyEvent.KEY_TYPED, new FiltroInteiro(11));

		txtDescricao.textProperty().addListener(new ListenerParaMaiusculas(txtDescricao));

		txtValor.addEventFilter(KeyEvent.KEY_TYPED, new FiltroFlutuante(20, false));
		txtValor.focusedProperty().addListener(new ListenerValorMinMax(txtValor, 0, 100000));

		tbPessoa.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarPessoa((Pessoa) newValue));

		tbPessoa.setOnMouseClicked(e -> {
			txtNomeCliente.setText(pessoaDAO.getNomeiD(pessoa_id));
		});

		txtPesquisa.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					pesquisaPessoa();
					return;
				}

			}
		});

		txtDescricao.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtValor.requestFocus();
					return;
				}

			}
		});

		txtValor.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtData.requestFocus();
					return;
				}

			}
		});

		txtData.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					confirmar();
				}

			}
		});

		btnPesquisar.setOnAction(e -> {
			pesquisaPessoa();
		});

		btnCancelar.setOnAction(e -> {
			cancelar();
		});

		btnSalvar.setOnAction(e -> {
			confirmar();
		});
	}

	public void selecionarPessoa(Pessoa p) {
		pessoa_id = p.getId();
		System.out.println("pegou id pessoa : " + pessoa_id);
	}

	public void pesquisaPessoa() {
		this.conexao = new Conexao();
		this.pessoaDAO = new PessoaDAO(conexao);
		if (cmbBuscar.getValue() == null) {
			Alert dlg = new Alert(AlertType.INFORMATION);
			ValidationFields.checkEmptyFields(cmbBuscar);
			dlg.setContentText("selecione o campo BUSCAR!!!");
			dlg.showAndWait();
			cmbBuscar.requestFocus();
		} else {
			if (cmbBuscar.getValue().equals("Nome")) {
				if (txtPesquisa.getText().isEmpty()) {
					Alert dlg = new Alert(AlertType.INFORMATION);
					dlg.setContentText("Preencha o campo pesquisa com o Nome do cliente!!!");
					dlg.showAndWait();
					ValidationFields.checkEmptyFields(txtPesquisa);
					txtPesquisa.requestFocus();

				} else {
					String sql = txtPesquisa.getText();
					clnNome.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("nome"));
					clnCpfCnpj.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("cpfcnpj"));
					ObservableList<Pessoa> lista = FXCollections.observableArrayList(pessoaDAO.buscarNome(sql));
					tbPessoa.setItems(lista);
				}

			} else {

				if (cmbBuscar.getValue().equals("Cpf_Cnpj")) {
					if (txtPesquisa.getText().isEmpty()) {
						Alert dlg = new Alert(AlertType.INFORMATION);
						dlg.setContentText("Preencha o campo pesquisa com o Cpf_Cnpj do cliente!!!");
						dlg.showAndWait();
						ValidationFields.checkEmptyFields(txtPesquisa);
						txtPesquisa.requestFocus();

					} else {
						String sql = txtPesquisa.getText();
						clnNome.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("Nome"));
						clnCpfCnpj.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("cpfcnpj"));
						ObservableList<Pessoa> lista = FXCollections
								.observableArrayList(pessoaDAO.buscar(formatarCpfCnpj(sql)));
						tbPessoa.setItems(lista);

					}
				}
			}

		}

		conexao.fecharConexao();

	}

	public void confirmar() {
		String descricao = null, parcelas = null;
		Date data = null;
		BigDecimal valor = null;
		SimpleDateFormat formatarData = new SimpleDateFormat("dd/MM/yyyy");

		if (tbPessoa.getSelectionModel().getSelectedItem() == null) {
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione o Cliente!");
			dlg.showAndWait();
			return;

		}

		if (txtDescricao.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtDescricao);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha a Descrição!");
			dlg.showAndWait();
			txtDescricao.requestFocus();
			return;
		} else {

			descricao = txtDescricao.getText();
		}

		if (txtValor.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtValor);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Valor!");
			dlg.showAndWait();
			txtValor.requestFocus();
			return;

		} else {

			valor = new BigDecimal(txtValor.getText().replace(".", "").replaceAll(",", "."));

		}

		if (txtParcela.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtParcela);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Qtde parcelas!");
			dlg.showAndWait();
			txtParcela.requestFocus();
			return;

		} else {

			parcelas = txtParcela.getText();

		}

		if (txtData.getValue() == null) {
			ValidationFields.checkEmptyFields(txtData);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Data!");
			dlg.showAndWait();
			txtData.requestFocus();
			return;

		} else {
			LocalDate d = LocalDate.now();
			Date dataHoje = java.sql.Date.valueOf(d);
			Date datas = java.sql.Date.valueOf(txtData.getValue());
			System.out.println("comparando data atual: " + datas.equals(dataHoje));
			if (datas.after(dataHoje) || datas.equals(dataHoje)) {
				data = java.sql.Date.valueOf(txtData.getValue());

			} else {

				ButtonType sim = new ButtonType("SIM", ButtonBar.ButtonData.YES);
				ButtonType nao = new ButtonType("NÃO", ButtonBar.ButtonData.NO);
				Alert alerta = new Alert(AlertType.INFORMATION,
						"A data informada e menor que a data atual !!!\nData informada : " + formatarData.format(datas)
								+ "\nData atual : " + formatarData.format(dataHoje)
								+ " \nDeseja continuar mesmo assim ?",
						sim, nao);

				Optional<ButtonType> result = alerta.showAndWait();
				if (result.get().equals(sim)) {
					data = java.sql.Date.valueOf(txtData.getValue());
				}

			}
		}

		if (txtParcela.getText() != null && txtValor.getText() != null && txtData.getValue() != null) {
			String situacao = new String("PENDENTE");
			Pessoa p = new Pessoa(pessoa_id, null, null, null, null, null, null, null, null, null);
			ContasReceber contas = new ContasReceber(null, data, valor, new BigDecimal(0), valor,
					Integer.parseInt(parcelas), 1, p, null, situacao, null, null, null, null, null, descricao, null,
					null, null);
			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Confirmação de INCLUSÃO");
			alerta.setHeaderText("Você quer mesmo cadastrar um nova PAGAMENTO ? ");
			alerta.setContentText("O registro será salvo!" + "\nVocê tem certeza?");
			Optional<ButtonType> escolha = alerta.showAndWait();

			if (escolha.get() == ButtonType.OK) {
				this.conexao = new Conexao();
				this.contasReceberDAO = new ContasReceberDAO(conexao);
				boolean suce = contasReceberDAO.inserirContas(contas);
				System.out.println("valor boolean registro contas receber:" + suce);
				Integer qtdeParcelas = Integer.parseInt(parcelas) - 1;
				for (int i = 0; i < qtdeParcelas; i++) {
					Integer par = contasReceberDAO.getNumeroParcelaCliente(pessoa_id, descricao, valor) + 1;
					Date dataParcela = contasReceberDAO.getDataParcelaCliente(pessoa_id, descricao, valor);
					Calendar c = Calendar.getInstance();
					c.setTime(dataParcela);
					c.add(Calendar.MONTH, 1);
					Date datas = c.getTime();
					Date dataPagamento = new java.sql.Date(datas.getTime());
					ContasReceber conta = new ContasReceber(null, dataPagamento, valor, new BigDecimal(0), valor,
							Integer.parseInt(parcelas), par, p, null, situacao, null, null, null, null, null, descricao,
							null, null, null);
					boolean sucesso = contasReceberDAO.inserirContas(conta);
					System.out.println("inseriu aprcelas : " + sucesso);
				}
				conexao.fecharConexao();

			}

			Alert dlg = new Alert(AlertType.CONFIRMATION);
			dlg.setContentText("Dados salvos com sucesso!");
			dlg.showAndWait();
			acaoSair();
		}
	}

	public void comboBoxBusca() {
		ArrayList<String> lista = new ArrayList<>();
		String nome = new String("Nome");
		String cpf_cnpj = new String("Cpf_Cnpj");
		lista.add(nome);
		lista.add(cpf_cnpj);
		ObservableList<String> observa = FXCollections.observableArrayList(lista);
		cmbBuscar.setItems(observa);

	}

	public void acaoSair() {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
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

}

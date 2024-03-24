package br.com.sistema.controll;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import br.com.sistema.filtros.FiltroEmail;
import br.com.sistema.filtros.FiltroFone;
import br.com.sistema.filtros.FiltroInteiro;
import br.com.sistema.filtros.FiltroLetras;
import br.com.sistema.listeners.ListenerFormatarCep;
import br.com.sistema.listeners.ListenerFormatarCpf;
import br.com.sistema.listeners.ListenerFormatarFone;
import br.com.sistema.listeners.ListenerParaMaiusculas;
import br.com.sistema.listeners.ListenerParaMinusculas;
import br.com.sistema.listeners.ListenerValidarEmail;
import br.com.sistema.model.Cidade;
import br.com.sistema.model.Email;
import br.com.sistema.model.Endereco;
import br.com.sistema.model.Estado;
import br.com.sistema.model.Pessoa;
import br.com.sistema.model.Telefone;
import br.com.sistema.model.dao.CidadeDAO;
import br.com.sistema.model.dao.EmailDAO;
import br.com.sistema.model.dao.EnderecoDAO;
import br.com.sistema.model.dao.EstadoDAO;
import br.com.sistema.model.dao.PessoaDAO;
import br.com.sistema.model.dao.PessoaDAOPF;
import br.com.sistema.model.dao.TelefoneDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class TelaEditarClientePF {

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtCpf;

	@FXML
	private TextField txtRg;

	@FXML
	private TextField txtDataNascimento;

	@FXML
	private TextField txtSexo;

	@FXML
	private TextField txtRua;

	@FXML
	private TextField txtNumero;

	@FXML
	private TextField txtBairro;

	@FXML
	private TextField txtCep;

	@FXML
	private ComboBox<String> cmbSexo;

	@FXML
	private ComboBox<Estado> cmbUf;

	@FXML
	private ComboBox<Cidade> cmbCidade;

	@FXML
	private TextField txtTelComercial;

	@FXML
	private TextField txtTelResidencial;

	@FXML
	private TextField txtTelCelular;

	@FXML
	private TextField txtTelWhatsapp;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtEmailNFS;

	@FXML
	private Button btnConfirmar;

	@FXML
	private Button btnSair;

	private PessoaDAO pessoaDAO = Principal.getPessoaDAO();
	private PessoaDAOPF pessoaDAOPF = Principal.getPessoaDAOPF();
	private EnderecoDAO enderecoDAO = Principal.getEnderecoDAO();
	private EmailDAO emailDAO = Principal.getEmailDAO();
	private TelefoneDAO telefoneDAO = Principal.getTelefoneDAO();
	private CidadeDAO cidadeDAO = Principal.getCidadeDAO();
	private EstadoDAO estadoDAO = Principal.getEstadoDAO();
	private TelaPrincipalClientes telaHome;
	private Date dataSql;
	private Date dataHoje;
	private Date dataMin;

	private List<String> listaSexo = new ArrayList<>();

	public void initialize() {

		carregaComboBoxSexo();
		carregaComboBoxEstados();
		acaoBuscarCliente();

		cmbUf.setOnAction(e -> {

			comboBoxCidadePorEstado();

		});

		btnSair.setOnAction(e -> {
			voltarTela();
		});

		btnConfirmar.setOnAction(e -> {
			salvarCliente();
		});

		txtCpf.addEventFilter(KeyEvent.KEY_TYPED, new FiltroInteiro(11));
		txtCpf.focusedProperty().addListener(new ListenerFormatarCpf(txtCpf));

		txtNome.textProperty().addListener(new ListenerParaMaiusculas(txtNome));
		txtNome.addEventFilter(KeyEvent.KEY_TYPED, new FiltroLetras());

		txtRg.addEventFilter(KeyEvent.KEY_TYPED, new FiltroInteiro(9));

		txtBairro.textProperty().addListener(new ListenerParaMaiusculas(txtBairro));
		txtBairro.addEventFilter(KeyEvent.KEY_TYPED, new FiltroLetras());

		txtCep.addEventFilter(KeyEvent.KEY_TYPED, new FiltroInteiro(8));
		txtCep.focusedProperty().addListener(new ListenerFormatarCep(txtCep));

		txtNumero.addEventFilter(KeyEvent.KEY_TYPED, new FiltroInteiro(9));

		txtRua.textProperty().addListener(new ListenerParaMaiusculas(txtRua));
		txtRua.addEventFilter(KeyEvent.KEY_TYPED, new FiltroLetras());

		txtTelCelular.addEventFilter(KeyEvent.KEY_TYPED, new FiltroFone());
		txtTelCelular.focusedProperty().addListener(new ListenerFormatarFone(txtTelCelular));
		txtTelComercial.addEventFilter(KeyEvent.KEY_TYPED, new FiltroFone());
		txtTelComercial.focusedProperty().addListener(new ListenerFormatarFone(txtTelComercial));
		txtTelResidencial.addEventFilter(KeyEvent.KEY_TYPED, new FiltroFone());
		txtTelResidencial.focusedProperty().addListener(new ListenerFormatarFone(txtTelResidencial));
		txtTelWhatsapp.addEventFilter(KeyEvent.KEY_TYPED, new FiltroFone());
		txtTelWhatsapp.focusedProperty().addListener(new ListenerFormatarFone(txtTelWhatsapp));

		txtEmail.addEventFilter(KeyEvent.KEY_TYPED, new FiltroEmail());
		txtEmail.textProperty().addListener(new ListenerParaMinusculas(txtEmail));
		txtEmail.focusedProperty().addListener(new ListenerValidarEmail(txtEmail));

		txtNome.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtCpf.requestFocus();
					return;
				}

			}
		});

		txtCpf.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtRg.requestFocus();
					return;
				}

			}
		});

		txtRg.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtDataNascimento.requestFocus();
					return;
				}

			}
		});

		txtDataNascimento.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					cmbSexo.requestFocus();
					return;
				}

			}
		});

		cmbSexo.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtRua.requestFocus();
					return;
				}

			}
		});

		txtRua.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtBairro.requestFocus();
					return;
				}

			}
		});

		txtBairro.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtNumero.requestFocus();
					return;
				}

			}
		});

		txtNumero.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					cmbUf.requestFocus();
					return;
				}

			}
		});

		cmbUf.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					cmbCidade.requestFocus();
					return;
				}

			}
		});

		cmbCidade.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtCep.requestFocus();
					return;
				}

			}
		});
	}

	public void carregaComboBoxEstados() {
		cmbUf.getItems().addAll(estadoDAO.listar());
		cmbUf.toString();
	}

	public void comboBoxCidadePorEstado() {
		if (cmbUf.getValue().toString().isEmpty()) {

		} else {
			cmbCidade.getItems().clear();
			Estado nome = cmbUf.getSelectionModel().getSelectedItem();
			Estado id = estadoDAO.buscar(nome.getNome());

			System.out.println("valor cmbEstado: " + nome);
			System.out.println("valor Objeto estado: " + id.getId());

			cmbCidade.getItems().addAll(cidadeDAO.buscarCidade(id.getId()));
			cmbCidade.toString();
			
		}

	}

	public void voltarTela() {
		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.setTitle("Confirmação de Cancelamento");
		alerta.setHeaderText("Você quer mesmo cancelar ? ");
		alerta.setContentText("Todos os dados preenchidos serão perdidos \nVocê tem certeza?");
		Optional<ButtonType> escolha = alerta.showAndWait();

		if (escolha.get() == ButtonType.OK) {
			Stage stage = (Stage) btnSair.getScene().getWindow();
			stage.close();
		} else {

		}
	}

	public void voltarTelaIncial() {
		Stage stage = (Stage) btnSair.getScene().getWindow();
		stage.close();
	}

	public void acaoBuscarCliente() {
		telaHome = new TelaPrincipalClientes();

		for (Pessoa pessoa : pessoaDAO.buscarCidade(telaHome.pessoaId())) {
			System.out.println("id tela principal: " + telaHome);
			txtNome.setText(pessoa.getNome());

			txtCpf.setText(pessoa.getCpfcnpj());
			txtDataNascimento.setText(pessoa.getDataNascimentoFormatada());
			txtBairro.setText(pessoa.getEndereco().getBairro());
			txtCep.setText(pessoa.getEndereco().getCep());

			txtEmail.setText(pessoa.getEmail().getEmail());
			txtRua.setText(pessoa.getEndereco().getRua());

			txtNumero.setText(pessoa.getEndereco().getNumero());
			txtTelCelular.setText(pessoa.getTelefone().getTelCelular());
			txtTelComercial.setText(pessoa.getTelefone().getTelComercial());
			txtTelResidencial.setText(pessoa.getTelefone().getTelResidencial());
			txtTelWhatsapp.setText(pessoa.getTelefone().getTelWhatsapp());
			txtRg.setText(pessoa.getRg());

		}

	}

	public void salvarCliente() {
		telaHome = new TelaPrincipalClientes();

		String nome = null, cpf = null, rg = null, sexo = null, rua = null, bairro = null, numero = null, cidade = null,
				cep = null, uf = null, telCelular = null, telComercial = null, telResidencial = null,
				telWhatsapp = null, emai = null;

		Date dataNascimento = null;

		String data = null;

		if (txtNome.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtNome);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Nome!!!");
			dlg.showAndWait();
			txtNome.requestFocus();
			return;

		} else {

			nome = txtNome.getText();
		}

		if (txtCpf.getText().isEmpty()) {
			cpf = new String("");

		} else {

			cpf = txtCpf.getText();
		}

		if (txtRg.getText().isEmpty()) {
			rg = new String("");

		} else {

			rg = txtRg.getText();
		}

		if (txtDataNascimento.getText() == null) {
			data = null;
		} else {
			Date dataAtual = new Date();
			data = txtDataNascimento.getText();

			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			String dataString = format.format(dataAtual);
			if (data.equals("")) {

			} else {

				try {
					Date datas = format.parse(data);
					Date dataF = format.parse(dataString);
					String dataMinima = "01/01/1910";
					Date dataM = format.parse(dataMinima);

					dataMin = new java.sql.Date(dataM.getTime());
					dataSql = new java.sql.Date(datas.getTime());
					dataHoje = new java.sql.Date(dataF.getTime());
					System.out.println("data hoje : " + dataHoje);
					System.out.println("data sql :" + dataSql);
					System.out.println("data Minima :" + dataMin);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				if (dataSql.getTime() >= dataHoje.getTime() || dataSql.getTime() <= dataMin.getTime()) {
					ValidationFields.checkEmptyFields(txtDataNascimento);
					Alert dlg = new Alert(AlertType.ERROR);
					dlg.setContentText("Data Nascimento inválida!!!");
					dlg.showAndWait();
					txtDataNascimento.requestFocus();
					return;
				} else {
					dataNascimento = dataSql;
					System.out.println("Data Nascimento:  " + dataNascimento);
				}

			}
		}

		if (cmbSexo.getValue() == null) {
			sexo = new String("");

		} else {

			sexo = cmbSexo.getValue().toString();
		}

		if (txtRua.getText() == null) {
			rua = new String("");

		} else {

			rua = txtRua.getText();
		}

		if (txtBairro.getText() == null) {
			bairro = new String("");

		} else {

			bairro = txtBairro.getText();
		}

		if (txtNumero.getText() == null) {
			numero = new String("");

		} else {

			numero = txtNumero.getText();
		}

		if (cmbUf.getValue() == null) {
			uf = new String("");

		} else {

			uf = cmbUf.getValue().toString();

		}
		if (cmbCidade.getValue() == null) {
			cidade = new String("");

		} else {

			cidade = cmbCidade.getValue().toString();

		}

		if (txtCep.getText() == null) {
			cep = new String("");

		} else {

			cep = txtCep.getText();
		}

		if (txtTelCelular.getText() == null || txtTelComercial.getText() == null || txtTelResidencial.getText() == null
				|| txtTelWhatsapp.getText() == null) {
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha um dos campos Telefones!!!");
			dlg.showAndWait();
		} else {
			telCelular = txtTelCelular.getText();
			telComercial = txtTelComercial.getText();
			telResidencial = txtTelResidencial.getText();
			telWhatsapp = txtTelWhatsapp.getText();
		}

		if (txtEmail.getText().isEmpty()) {
			emai = txtEmail.getText();

		} else {

			emai = txtEmail.getText();

		}

		Integer endereco_id = pessoaDAO.getEndereco(telaHome.pessoaId());
		System.out.println("buscou endereco_id editar : " + endereco_id);
		Integer email_id = pessoaDAO.getEmail(telaHome.pessoaId());
		System.out.println("buscou email_id editar : " + email_id);
		Integer telefone_id = pessoaDAO.getTelefone(telaHome.pessoaId());
		System.out.println("buscou telefone_id editar : " + telefone_id);

		Endereco enderecos = new Endereco(endereco_id, rua, bairro, numero, cidade, uf, cep);
		Telefone telefones = new Telefone(telefone_id, telComercial, telCelular, telResidencial, telWhatsapp);
		Email emails = new Email(email_id, emai);

		Pessoa pessoa = new Pessoa(telaHome.pessoaId(), nome, cpf, rg, sexo, dataNascimento, null, enderecos, telefones,
				emails, null, null);

		boolean sucesso = true;
		if (sucesso) {
			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Confirmação de ATUALIZAÇÃO ");
			alerta.setHeaderText("Você quer mesmo atualizar o Cliente ? ");
			alerta.setContentText("O cliente " + txtNome.getText() + " será atualizado!" + "\nVocê tem certeza?");
			Optional<ButtonType> escolha = alerta.showAndWait();

			if (escolha.get() == ButtonType.OK) {
				boolean sucessos = enderecoDAO.atualizar(enderecos);
				boolean suces = telefoneDAO.atualizar(telefones);
				boolean secess = emailDAO.atualizar(emails);
				boolean su = pessoaDAOPF.atualizar(pessoa);
				System.out.println("atualizar endereco : " + sucessos);
				System.out.println("atualizar telefone : " + suces);
				System.out.println("atualizar email : " + secess);
				System.out.println("atualizar pessoa : " + su);
				voltarTelaIncial();

			} else {

			}

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Confirmação de INCLUSÃO");
			alert.setHeaderText("Erro ao cadastrar um novo cliente! ");
		}
	}

	@FXML
	void acaoData() {
		TextFieldFormatter tff = new TextFieldFormatter();
		tff.setMask("##/##/####");
		tff.setCaracteresValidos("0987654321");
		tff.setTf(txtDataNascimento);
		tff.formatter();

	}

	public void carregaComboBoxSexo() {

		String feminino = new String("Feminino");
		String masculino = new String("Masculino");

		listaSexo.add(feminino);
		listaSexo.add(masculino);

		ObservableList<String> observa = FXCollections.observableArrayList(listaSexo);

		cmbSexo.setItems(observa);
		observa.toString();

	}
}

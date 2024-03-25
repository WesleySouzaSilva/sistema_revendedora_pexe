package br.com.sistema.controll;

import java.util.Optional;

import br.com.sistema.conexao.Conexao;
import br.com.sistema.filtros.FiltroFone;
import br.com.sistema.filtros.FiltroInteiro;
import br.com.sistema.listeners.ListenerFormatarCep;
import br.com.sistema.listeners.ListenerFormatarCnpj;
import br.com.sistema.listeners.ListenerFormatarFone;
import br.com.sistema.listeners.ListenerParaMaiusculas;
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
import br.com.sistema.model.dao.PessoaDAOPJ;
import br.com.sistema.model.dao.TelefoneDAO;
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

public class TelaEditarClientePJ {

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtCnpj;

	@FXML
	private TextField txtRua;

	@FXML
	private TextField txtNumero;

	@FXML
	private TextField txtBairro;

	@FXML
	private TextField txtCep;

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
	private Button btnCancelar;

	@FXML
	private Button btnConfirmar;

	private EnderecoDAO enderecoDAO = null;
	private EmailDAO emailDAO = null;
	private TelefoneDAO telefoneDAO = null;
	private PessoaDAO pessoaDAO = null;
	private PessoaDAOPJ pessoaDAOPJ = null;
	private CidadeDAO cidadeDAO = null;
	private EstadoDAO estadoDAO = null;
	private Conexao conexao;
	private TelaPrincipalClientes tela;

	public void initialize() {

		acaoBuscarCliente();
		carregaComboBoxEstados();
		cmbUf.setOnAction(e -> {

			comboBoxCidadePorEstado();

		});

		btnCancelar.setOnAction(e -> {
			voltarTela();
		});

		btnConfirmar.setOnAction(e -> {
			salvarCliente();
		});

		txtCnpj.focusedProperty().addListener(new ListenerFormatarCnpj(txtCnpj));
		txtBairro.textProperty().addListener(new ListenerParaMaiusculas(txtBairro));
		txtCep.addEventFilter(KeyEvent.KEY_TYPED, new FiltroInteiro(8));
		txtCep.focusedProperty().addListener(new ListenerFormatarCep(txtCep));
		txtRua.textProperty().addListener(new ListenerParaMaiusculas(txtRua));
		txtNumero.textProperty().addListener(new ListenerParaMaiusculas(txtNumero));

		txtTelCelular.addEventFilter(KeyEvent.KEY_TYPED, new FiltroFone());
		txtTelCelular.focusedProperty().addListener(new ListenerFormatarFone(txtTelCelular));
		txtTelComercial.addEventFilter(KeyEvent.KEY_TYPED, new FiltroFone());
		txtTelComercial.focusedProperty().addListener(new ListenerFormatarFone(txtTelComercial));
		txtTelResidencial.addEventFilter(KeyEvent.KEY_TYPED, new FiltroFone());
		txtTelResidencial.focusedProperty().addListener(new ListenerFormatarFone(txtTelResidencial));
		txtTelWhatsapp.addEventFilter(KeyEvent.KEY_TYPED, new FiltroFone());
		txtTelWhatsapp.focusedProperty().addListener(new ListenerFormatarFone(txtTelWhatsapp));

		txtNome.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtCnpj.requestFocus();
					return;
				}

			}
		});

		txtCnpj.setOnKeyPressed(new EventHandler<KeyEvent>() {

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
		this.conexao = new Conexao();
		this.estadoDAO = new EstadoDAO(conexao);
		cmbUf.getItems().addAll(estadoDAO.listar());
		cmbUf.toString();
		conexao.fecharConexao();

	}

	public void comboBoxCidadePorEstado() {
		this.conexao = new Conexao();
		this.estadoDAO = new EstadoDAO(conexao);
		this.cidadeDAO = new CidadeDAO(conexao);
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
		conexao.fecharConexao();
		conexao.fecharConexao();
	}

	public void voltarTela() {
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

	public void voltarTelaIncial() {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	public void acaoBuscarCliente() {
		tela = new TelaPrincipalClientes();
		this.conexao = new Conexao();
		this.pessoaDAO = new PessoaDAO(conexao);
		for (Pessoa pessoa : pessoaDAO.buscarCidade(tela.pessoaId())) {
			txtNome.setText(pessoa.getNome());
			txtCnpj.setText(pessoa.getCpfcnpj());
			txtBairro.setText(pessoa.getEndereco().getBairro());
			txtCep.setText(pessoa.getEndereco().getCep());

			txtEmail.setText(pessoa.getEmail().getEmail());
			txtRua.setText(pessoa.getEndereco().getRua());

			txtNumero.setText(pessoa.getEndereco().getNumero());

			txtTelCelular.setText(pessoa.getTelefone().getTelCelular());
			txtTelComercial.setText(pessoa.getTelefone().getTelComercial());
			txtTelResidencial.setText(pessoa.getTelefone().getTelResidencial());
			txtTelWhatsapp.setText(pessoa.getTelefone().getTelWhatsapp());

		}
		conexao.fecharConexao();

	}

	public void salvarCliente() {
		tela = new TelaPrincipalClientes();
		String nome = null, cnpj = null, rua = null, bairro = null, numero = null, cidade = null, cep = null, uf = null,
				telCelular = null, telComercial = null, telResidencial = null, telWhatsapp = null, emai = null;

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
		if (txtCnpj.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtCnpj);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo CNPJ!!!");
			dlg.showAndWait();
			txtCnpj.requestFocus();
			return;

		} else {
			cnpj = txtCnpj.getText();
		}

		if (txtRua.getText().isEmpty())

		{
			ValidationFields.checkEmptyFields(txtRua);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Endereço!!!");
			dlg.showAndWait();
			txtRua.requestFocus();
			return;

		} else {

			rua = txtRua.getText();
		}

		if (txtBairro.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtBairro);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Bairro!!!");
			dlg.showAndWait();
			txtBairro.requestFocus();
			return;

		} else {

			bairro = txtBairro.getText();
		}

		if (txtNumero.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtNumero);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Numero!!!");
			dlg.showAndWait();
			txtNumero.requestFocus();
			return;

		} else {

			numero = txtNumero.getText();
		}

		if (cmbUf.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbUf);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione o Estado!!!");
			dlg.showAndWait();
			cmbUf.requestFocus();
			return;

		} else {

			uf = cmbUf.getValue().toString();

		}
		if (cmbCidade.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbCidade);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione a Cidade!!!");
			dlg.showAndWait();
			cmbCidade.requestFocus();
			return;

		} else {

			cidade = cmbCidade.getValue().toString();

		}

		if (txtCep.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtCep);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo CEP!!!");
			dlg.showAndWait();
			txtCep.requestFocus();
			return;

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
			ValidationFields.checkEmptyFields(txtEmail);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Email!!!");
			dlg.showAndWait();
			txtEmail.requestFocus();
			return;

		} else {

			emai = txtEmail.getText();

		}
		this.conexao = new Conexao();
		this.pessoaDAO = new PessoaDAO(conexao);
		Integer endereco_id = pessoaDAO.getEndereco(tela.pessoaId());
		System.out.println("buscou endereco_id editar : " + endereco_id);
		Integer email_id = pessoaDAO.getEmail(tela.pessoaId());
		System.out.println("buscou email_id editar : " + email_id);
		Integer telefone_id = pessoaDAO.getTelefone(tela.pessoaId());
		System.out.println("buscou telefone_id editar : " + telefone_id);
		conexao.fecharConexao();

		Endereco enderecos = new Endereco(endereco_id, rua, bairro, numero, cidade, uf, cep);
		Telefone telefones = new Telefone(telefone_id, telComercial, telCelular, telResidencial, telWhatsapp);
		Email emails = new Email(email_id, emai);

		Pessoa pessoa = new Pessoa(tela.pessoaId(), nome, cnpj, null, null, null, null, enderecos, telefones, emails,
				null, null);

		if (txtBairro.getText() != null && txtCep.getText() != null && cmbCidade.getValue() != null
				&& txtCnpj.getText() != null && txtRua.getText() != null && cmbUf.getValue() != null
				&& txtEmail.getText() != null && txtEmailNFS.getText() != null && txtNome.getText() != null
				&& txtNumero.getText() != null) {

			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Confirmação de ATUALIZAÇÃO ");
			alerta.setHeaderText("Você quer mesmo atualizar o Cliente ? ");
			alerta.setContentText("O cliente " + txtNome.getText() + " será atualizado!" + "\nVocê tem certeza?");
			Optional<ButtonType> escolha = alerta.showAndWait();

			if (escolha.get() == ButtonType.OK) {
				this.enderecoDAO = new EnderecoDAO(conexao);
				boolean sucessos = enderecoDAO.atualizar(enderecos);
				this.telefoneDAO = new TelefoneDAO(conexao);
				boolean suces = telefoneDAO.atualizar(telefones);
				this.emailDAO = new EmailDAO(conexao);
				boolean secess = emailDAO.atualizar(emails);
				this.pessoaDAOPJ = new PessoaDAOPJ(conexao);
				boolean su = pessoaDAOPJ.atualizar(pessoa);
				System.out.println("atualizar endereco : " + sucessos);
				System.out.println("atualizar telefone : " + suces);
				System.out.println("atualizar email : " + secess);
				System.out.println("atualizar pessoa : " + su);
				conexao.fecharConexao();
				voltarTelaIncial();

			}
			conexao.fecharConexao();
		}

	}
}

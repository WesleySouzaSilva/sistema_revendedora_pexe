package br.com.sistema.controll;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import br.com.sistema.conexao.Conexao;
import br.com.sistema.filtros.FiltroEmail;
import br.com.sistema.filtros.FiltroFone;
import br.com.sistema.filtros.FiltroInteiro;
import br.com.sistema.listeners.ListenerFormatarCep;
import br.com.sistema.listeners.ListenerFormatarCnpj;
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

public class TelaCadastroClientesPJ {

	private EnderecoDAO enderecoDAO = null;
	private EstadoDAO estadoDAO = null;
	private CidadeDAO cidadeDAO = null;
	private TelefoneDAO telefoneDAO = null;
	private EmailDAO emailDAO = null;
	private PessoaDAOPJ pessoaDAOPJ = null;
	private Conexao conexao;

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

	public void initialize() {

		carregaComboBoxEstados();

		cmbUf.setOnAction(e -> {

			comboBoxCidadePorEstado();

		});

		txtNome.textProperty().addListener(new ListenerParaMaiusculas(txtNome));
		txtCnpj.addEventFilter(KeyEvent.KEY_TYPED, new FiltroInteiro(14));
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
		txtEmail.addEventFilter(KeyEvent.KEY_TYPED, new FiltroEmail());
		txtEmail.textProperty().addListener(new ListenerParaMinusculas(txtEmail));
		txtEmail.focusedProperty().addListener(new ListenerValidarEmail(txtEmail));

		txtEmailNFS.setVisible(false);

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

	@FXML
	void acaoConfirmar() throws SQLException {
		String nome = null, rua = null, bairro = null, numero = null, cidade = null, cep = null, uf = null,
				telCelular = null, telComercial = null, telResidencial = null, telWhatsapp = null, emai = null;

		String cnpj = null;
		String cpfFormatado = null;

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
			String cpfs = txtCnpj.getText();
			cpfFormatado = cpfs;
			this.conexao = new Conexao();
			if (isRegistro(cpfFormatado)) {
				ValidationFields.checkEmptyFields(txtCnpj);
				Alert dlg = new Alert(AlertType.INFORMATION);
				dlg.setContentText("CNPJ " + txtCnpj.getText() + " Já cadastrado");
				dlg.showAndWait();
				txtCnpj.requestFocus();
				return;
			}
			conexao.fecharConexao();
			cnpj = cpfFormatado;
			System.out.println("cnpj :" + cnpj);

		}

		if (txtRua.getText().isEmpty()) {
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
			emai = txtEmail.getText();

		} else {

			emai = txtEmail.getText();

		}

		String ativo = new String("SIM");
		String tipo = new String("PJ");

		if (txtBairro.getText() != null && txtCep.getText() != null && cmbCidade.getValue() != null
				&& txtCnpj.getText() != null && txtRua.getText() != null && cmbUf.getValue() != null
				&& txtEmail.getText() != null && txtEmailNFS.getText() != null && txtNome.getText() != null
				&& txtNumero.getText() != null) {

			boolean sucesso = true;
			if (sucesso) {
				Alert alerta = new Alert(AlertType.CONFIRMATION);
				alerta.setTitle("Confirmação de INCLUSÃO");
				alerta.setHeaderText("Você quer mesmo cadastrar um novo Cliente ? ");
				alerta.setContentText("O cliente " + txtNome.getText() + " será salvo!" + "\nVocê tem certeza?");
				Optional<ButtonType> escolha = alerta.showAndWait();

				if (escolha.get() == ButtonType.OK) {
					this.conexao = new Conexao();
					this.enderecoDAO = new EnderecoDAO(conexao);
					Endereco enderecos = new Endereco(null, rua, bairro, numero, cidade, uf, cep);
					boolean sucess = enderecoDAO.inserir(enderecos);
					if (sucess) {
						enderecos.getId();
					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Confirmação de INCLUSÃO");
						alert.setHeaderText("Erro ao cadastrar um novo endereco! ");
					}
					this.telefoneDAO = new TelefoneDAO(conexao);
					Telefone telefones = new Telefone(null, telComercial, telCelular, telResidencial, telWhatsapp);
					boolean suce = telefoneDAO.inserir(telefones);
					if (suce) {
						telefones.getId();
					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Confirmação de INCLUSÃO");
						alert.setHeaderText("Erro ao cadastrar um novo telefone! ");
					}
					this.emailDAO = new EmailDAO(conexao);
					Email emails = new Email(null, emai);
					boolean su = emailDAO.inserir(emails);
					if (su) {
						emails.getId();
					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Confirmação de INCLUSÃO");
						alert.setHeaderText("Erro ao cadastrar um novo email! ");
					}

					Pessoa pessoa = new Pessoa(null, nome, cnpj, null, null, null, null, enderecos, telefones, emails,
							ativo, tipo);
					this.pessoaDAOPJ = new PessoaDAOPJ(conexao);
					boolean sucessos = pessoaDAOPJ.inserir(pessoa);
					System.out.println("inserir pessoa pj : " + sucessos);
					conexao.fecharConexao();
					voltarTela();

				} else {

				}

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Confirmação de INCLUSÃO");
				alert.setHeaderText("Erro ao cadastrar um novo cliente! ");
			}
		}

	}

	@FXML
	void acaoCancelar() {
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
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	public boolean isRegistro(String campo) throws SQLException {
		boolean result = false;

		String sql = "SELECT * FROM pessoa WHERE Cpf_Cnpj = ? AND ativo = 'SIM' ";

		PreparedStatement cmd = conexao.getConexao().prepareStatement(sql);
		cmd.setString(1, campo);
		ResultSet rs = cmd.executeQuery();
		if (rs.next()) {
			result = true;
		}
		return result;
	}
}
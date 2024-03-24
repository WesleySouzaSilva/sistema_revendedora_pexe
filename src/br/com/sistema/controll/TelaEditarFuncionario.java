package br.com.sistema.controll;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import br.com.sistema.conexao.Conexao;
import br.com.sistema.filtros.FiltroEmail;
import br.com.sistema.filtros.FiltroFone;
import br.com.sistema.filtros.FiltroInteiro;
import br.com.sistema.filtros.FiltroLetras;
import br.com.sistema.listeners.ListenerFormatarCep;
import br.com.sistema.listeners.ListenerFormatarFone;
import br.com.sistema.listeners.ListenerParaMaiusculas;
import br.com.sistema.listeners.ListenerParaMinusculas;
import br.com.sistema.listeners.ListenerValidarEmail;
import br.com.sistema.model.Cidade;
import br.com.sistema.model.Email;
import br.com.sistema.model.Endereco;
import br.com.sistema.model.Estado;
import br.com.sistema.model.Funcionario;
import br.com.sistema.model.Telefone;
import br.com.sistema.model.dao.CidadeDAO;
import br.com.sistema.model.dao.EmailDAO;
import br.com.sistema.model.dao.EnderecoDAO;
import br.com.sistema.model.dao.EstadoDAO;
import br.com.sistema.model.dao.FuncionarioDAO;
import br.com.sistema.model.dao.TelefoneDAO;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class TelaEditarFuncionario {

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtProfissao;

	@FXML
	private TextField txtDataAdmissao;

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
	private Button btnSalvar;

	@FXML
	private Button btnCancelar;

	private EnderecoDAO enderecoDAO = null;
	private EstadoDAO estadoDAO = null;
	private CidadeDAO cidadeDAO = null;
	private TelefoneDAO telefoneDAO = null;
	private EmailDAO emailDAO = null;
	private FuncionarioDAO funcionarioDAO = null;
	private Date dataSql;
	private Date dataHoje;
	private Date dataMin;
	private Conexao conexao = Principal.getConexao();

	public void initialize() {

		carregaComboBoxEstados();
		carregaDadosFuncionario();

		txtNome.textProperty().addListener(new ListenerParaMaiusculas(txtNome));
		txtNome.addEventFilter(KeyEvent.KEY_TYPED, new FiltroLetras());

		txtProfissao.textProperty().addListener(new ListenerParaMaiusculas(txtProfissao));
		txtProfissao.addEventFilter(KeyEvent.KEY_TYPED, new FiltroLetras());

		txtDataAdmissao.addEventFilter(KeyEvent.KEY_TYPED, new FiltroInteiro(8));

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

		cmbUf.setOnAction(e -> {

			comboBoxCidadePorEstado();

		});

		textFieldInicial(txtNome);

		btnCancelar.setOnAction(e -> {
			cancelar();
		});

		btnSalvar.setOnAction(e -> {
			try {
				salvar();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		txtNome.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtProfissao.requestFocus();
					return;
				}

			}
		});

		txtProfissao.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtDataAdmissao.requestFocus();
					return;
				}

			}
		});

		txtDataAdmissao.setOnKeyPressed(new EventHandler<KeyEvent>() {

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

	public void salvar() throws SQLException {
		TelaListaFuncionario tela = new TelaListaFuncionario();
		String nome = null, profissao = null, rua = null, bairro = null, numero = null, cidade = null, cep = null,
				uf = null, telCelular = null, telComercial = null, telResidencial = null, telWhatsapp = null,
				email = null, ativo = null;
		Integer idEndereco = null, idTelefone = null, idEmail = null, idFuncionario = null;

		Date dataAdmissao = null;
		this.funcionarioDAO = Principal.getFuncionarioDAO();
		idEndereco = funcionarioDAO.getIdEndereco(tela.IdFuncionario());
		idTelefone = funcionarioDAO.getIdTelefone(tela.IdFuncionario());
		idEmail = funcionarioDAO.getIdEmail(tela.IdFuncionario());
		conexao.fecharConexao();

		idFuncionario = tela.IdFuncionario();

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

		if (txtProfissao.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtProfissao);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Profissão!!!");
			dlg.showAndWait();
			txtProfissao.requestFocus();
			return;

		} else {

			profissao = txtProfissao.getText();

		}

		if (txtDataAdmissao.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtDataAdmissao);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Data Admissão!!!");
			dlg.showAndWait();
			txtDataAdmissao.requestFocus();
			return;

		} else {

			Date dataAtual = new Date();
			String data = txtDataAdmissao.getText();

			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			String dataString = format.format(dataAtual);

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

			if (dataSql.getTime() > dataHoje.getTime() || dataSql.getTime() <= dataMin.getTime()) {
				ValidationFields.checkEmptyFields(txtDataAdmissao);
				Alert dlg = new Alert(AlertType.ERROR);
				dlg.setContentText("Data Admissão inválida!!!");
				dlg.showAndWait();
				txtDataAdmissao.requestFocus();
				return;
			} else {
				dataAdmissao = dataSql;
				System.out.println("Data Admissão :  " + dataAdmissao);
			}

		}

		if (txtRua.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtRua);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Rua!!!");
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
			dlg.setContentText("Preencha o campo Cep!!!");
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
			email = txtEmail.getText();

		} else {

			email = txtEmail.getText();

		}

		Endereco enderecos = new Endereco(idEndereco, rua, bairro, numero, cidade, uf, cep);
		this.enderecoDAO = Principal.getEnderecoDAO();
		boolean sucess = enderecoDAO.atualizar(enderecos);
		if (sucess) {
			enderecos.getId();
			conexao.fecharConexao();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Confirmação de INCLUSÃO");
			alert.setHeaderText("Erro ao cadastrar um novo endereco! ");
		}

		Telefone telefones = new Telefone(idTelefone, telComercial, telCelular, telResidencial, telWhatsapp);
		this.telefoneDAO = Principal.getTelefoneDAO();
		boolean suce = telefoneDAO.atualizar(telefones);
		if (suce) {
			telefones.getId();
			conexao.fecharConexao();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Confirmação de INCLUSÃO");
			alert.setHeaderText("Erro ao cadastrar um novo telefone! ");
		}

		Email emails = new Email(idEmail, email);
		this.emailDAO = Principal.getEmailDAO();
		boolean su = emailDAO.atualizar(emails);
		if (su) {
			emails.getId();
			conexao.fecharConexao();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Confirmação de INCLUSÃO");
			alert.setHeaderText("Erro ao cadastrar um novo email! ");
		}

		ativo = new String("SIM");
		Funcionario funcionario = new Funcionario(idFuncionario, nome, profissao, ativo, enderecos, telefones, emails,
				dataAdmissao, null);

		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.setTitle("Confirmação de INCLUSÃO");
		alerta.setHeaderText("Você quer mesmo cadastrar um novo Funcionario ? ");
		alerta.setContentText("O Funcionario " + txtNome.getText() + " será salvo!" + "\nVocê tem certeza?");
		Optional<ButtonType> escolha = alerta.showAndWait();

		if (escolha.get() == ButtonType.OK) {
			this.funcionarioDAO = Principal.getFuncionarioDAO();
			boolean sucesso = funcionarioDAO.atualizar(funcionario);
			System.out.println("sucesso atualizar funcionario :" + sucesso);
			conexao.fecharConexao();
			acaoSair();

		}

	}

	public void carregaDadosFuncionario() {
		TelaListaFuncionario tela = new TelaListaFuncionario();
		this.funcionarioDAO = Principal.getFuncionarioDAO();
		for (Funcionario f : funcionarioDAO.listarTodosDadosId(tela.IdFuncionario())) {
			txtNome.setText(f.getNome());
			txtProfissao.setText(f.getProfissao());
			txtDataAdmissao.setText(f.getDataAdmissaoFormatado());
			txtRua.setText(f.getRua());
			txtBairro.setText(f.getBairro());
			txtNumero.setText(f.getNumero());
			txtCep.setText(f.getCep());
			txtTelCelular.setText(f.getTelCelular());
			txtTelComercial.setText(f.getTelComercial());
			txtTelResidencial.setText(f.getTelResidencial());
			txtTelWhatsapp.setText(f.getTelWhatsapp());
			txtEmail.setText(f.getEmailFormatado());

		}
		conexao.fecharConexao();
	}

	public void carregaComboBoxEstados() {
		this.estadoDAO = Principal.getEstadoDAO();
		cmbUf.getItems().addAll(estadoDAO.listar());
		cmbUf.toString();
		conexao.fecharConexao();

	}

	public void comboBoxCidadePorEstado() {
		if (cmbUf.getValue().toString().isEmpty()) {

		} else {
			cmbCidade.getItems().clear();
			Estado nome = cmbUf.getSelectionModel().getSelectedItem();
			Estado id = estadoDAO.buscar(nome.getNome());

			System.out.println("valor cmbUf: " + nome);
			System.out.println("valor Objeto estado: " + id.getId());

			this.cidadeDAO = Principal.getCidadeDAO();
			cmbCidade.getItems().addAll(cidadeDAO.buscarCidade(id.getId()));
			cmbCidade.toString();
			conexao.fecharConexao();
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

	public void acaoSair() {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	void acaoData() {
		TextFieldFormatter tff = new TextFieldFormatter();
		tff.setMask("##/##/####");
		tff.setCaracteresValidos("0987654321");
		tff.setTf(txtDataAdmissao);
		tff.formatter();
	}

	public boolean verificaSalario(Date data, BigDecimal valor) throws SQLException {
		conexao = Principal.getConexao();
		boolean result = false;

		String sql = "SELECT * FROM salario_funcionario WHERE valor = ? AND data = '" + data + "'";

		PreparedStatement cmd = conexao.getConexao().prepareStatement(sql);
		cmd.setBigDecimal(1, valor);
		ResultSet rs = cmd.executeQuery();
		if (rs.next()) {
			result = true;

		}

		return result;

	}

	public void textFieldInicial(TextField tf) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				tf.requestFocus();
			}
		});
	}

}

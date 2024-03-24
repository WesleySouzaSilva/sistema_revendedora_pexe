package br.com.sistema.controll;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import br.com.sistema.conexao.Conexao;
import br.com.sistema.listeners.ListenerParaMaiusculas;
import br.com.sistema.model.Usuario;
import br.com.sistema.model.dao.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class TelaNovoUsuario {

	@FXML
	private TextField txtNome;

	@FXML
	private PasswordField txtSenha;

	@FXML
	private ComboBox<String> cmbPermissao;

	@FXML
	private Button btnConfirmar;

	@FXML
	private Button btnCancelar;

	private Conexao conexao = Principal.getConexao();
	private ObservableList<String> observa;
	private ArrayList<String> lista = new ArrayList<>();
	private UsuarioDAO usuarioDAO = null;

	public void initialize() {

		txtNome.textProperty().addListener(new ListenerParaMaiusculas(txtNome));
		tipoUsuario();

		btnCancelar.setOnAction(e -> {
			cancelar();
		});

		btnConfirmar.setOnAction(e -> {
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
					txtSenha.requestFocus();
					return;
				}

			}
		});

		txtSenha.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					cmbPermissao.requestFocus();
					return;
				}

			}
		});

		cmbPermissao.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					try {
						salvar();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				}

			}
		});
	}

	public void salvar() throws SQLException {
		String nome = null, senha = null, permissao = null;
		if (txtNome.getText().isEmpty() || txtNome.getText() == null) {
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo NOME!!!");
			dlg.showAndWait();
			txtNome.requestFocus();
			return;
		} else {
			boolean verifica = true;
			String teste = txtNome.getText();
			if (verifica == isRegistro(teste)) {
				ValidationFields.checkEmptyFields(txtNome);
				Alert dlg = new Alert(AlertType.INFORMATION);
				dlg.setContentText("Usuario " + txtNome.getText() + " Já cadastrado");
				dlg.showAndWait();
				txtNome.requestFocus();
				return;
			} else {
				nome = txtNome.getText();

			}
		}

		if (txtSenha.getText().isEmpty() || txtSenha.getText() == null) {
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo SENHA !!");
			dlg.showAndWait();
			txtSenha.requestFocus();
			return;
		} else {
			senha = txtSenha.getText();
		}

		if (cmbPermissao.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbPermissao);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecine o campo PERMISSÃO");
			dlg.showAndWait();
			cmbPermissao.requestFocus();
			return;

		} else {

			permissao = cmbPermissao.getValue().toString();
		}

		Usuario usuario = new Usuario(null, nome, senha, permissao);
		if (txtNome.getText() != null && txtSenha.getText() != null && cmbPermissao.getValue() != null) {

			boolean sucess = true;

			if (sucess) {
				Alert alerta = new Alert(AlertType.CONFIRMATION);
				alerta.setTitle("Confirmação de INCLUSÃO");
				alerta.setHeaderText("Você quer mesmo cadastrar um novo USUARIO ? ");
				alerta.setContentText("O Usuario " + txtNome.getText() + " será cadastrado! \nVocê tem certeza?");
				Optional<ButtonType> escolha = alerta.showAndWait();

				if (escolha.get() == ButtonType.OK) {
					this.usuarioDAO = Principal.getUsuarioDAO();
					boolean sucesso = usuarioDAO.inserir(usuario);
					System.out.println("sucesso : " + sucesso);

					conexao.fecharConexao();
					voltarTela();

				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Confirmação de INCLUSÃO");
					alert.setHeaderText("Erro ao cadastrar um novo Usuario! ");
				}
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

	public void tipoUsuario() {
		String administrador = new String("ADMINISTRADOR");
		String usuario = new String("USUARIO");

		lista.add(administrador);
		lista.add(usuario);

		observa = FXCollections.observableArrayList(lista);
		cmbPermissao.setItems(observa);

	}

	public void voltarTela() {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	public boolean isRegistro(String campo) throws SQLException {
		conexao = Principal.getConexao();
		boolean result = false;

		String sql = "SELECT * FROM usuario WHERE nome = ? ";

		PreparedStatement cmd = conexao.getConexao().prepareStatement(sql);
		cmd.setString(1, campo);
		ResultSet rs = cmd.executeQuery();
		if (rs.next()) {
			result = true;

		}

		conexao.fecharConexao();
		return result;

	}
}

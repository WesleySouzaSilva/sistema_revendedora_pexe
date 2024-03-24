package br.com.sistema.controll;

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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class TelaEditarUsuario {

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
	private Integer id;

	public void initialize() {
		TelaPrincipalUsuario usu = new TelaPrincipalUsuario();
		id = usu.idUsuario();
		System.out.println("id recebido :" + id);

		txtNome.textProperty().addListener(new ListenerParaMaiusculas(txtNome));
		tipoUsuario();
		acaoUsuario();

		btnCancelar.setOnAction(e -> {
			cancelar();
		});

		btnConfirmar.setOnAction(e -> {
			salvar();
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
					salvar();
					return;
				}

			}
		});
	}

	public void salvar() {
		String nome = null, senha = null, permissao = null;
		if (txtNome.getText().isEmpty() || txtNome.getText() == null) {
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo NOME!!!");
			dlg.showAndWait();
			txtNome.requestFocus();
			return;
		} else {
			nome = txtNome.getText();
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

		Usuario usuario = new Usuario(id, nome, senha, permissao);
		if (txtNome.getText() != null && txtSenha.getText() != null && cmbPermissao.getValue() != null) {

			boolean sucess = true;

			if (sucess) {
				Alert alerta = new Alert(AlertType.CONFIRMATION);
				alerta.setTitle("Confirmação de EDIÇÃO");
				alerta.setHeaderText("Você quer mesmo EDITAR o USUARIO ? ");
				alerta.setContentText("O Usuario " + txtNome.getText() + " será Editado! \nVocê tem certeza?");
				Optional<ButtonType> escolha = alerta.showAndWait();

				if (escolha.get() == ButtonType.OK) {
					this.usuarioDAO = Principal.getUsuarioDAO();
					boolean sucesso = usuarioDAO.atualizar(usuario);
					System.out.println("sucesso : " + sucesso);

					conexao.fecharConexao();
					voltarTela();

				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Confirmação de INCLUSÃO");
					alert.setHeaderText("Erro ao editar um novo Usuario! ");
				}
			}
		}

	}

	public void voltarTela() {
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

	public void tipoUsuario() {
		String administrador = new String("ADMINISTRADOR");
		String usuario = new String("USUARIO");

		lista.add(administrador);
		lista.add(usuario);

		observa = FXCollections.observableArrayList(lista);
		cmbPermissao.setItems(observa);

	}

	public void acaoUsuario() {
		this.usuarioDAO = Principal.getUsuarioDAO();
		for (Usuario usuario : usuarioDAO.buscarId(id)) {
			txtNome.setText(usuario.getNome());
			txtSenha.setText(usuario.getSenha());
		}
		conexao.fecharConexao();
	}
}

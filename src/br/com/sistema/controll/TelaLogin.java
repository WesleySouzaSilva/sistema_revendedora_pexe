package br.com.sistema.controll;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

import br.com.sistema.conexao.Conexao;
import br.com.sistema.listeners.ListenerParaMaiusculas;
import br.com.sistema.model.dao.UsuarioDAO;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class TelaLogin {

	@FXML
	private Button btnConfirmar;

	@FXML
	private Button btnCancelar;

	@FXML
	private PasswordField txtSenha;

	@FXML
	private TextField txtUsuario;

	@FXML
	private Button btnSair;

	private Conexao conexao = null;
	private String permissao = null;
	private static String permissaoStatic = null;
	private static boolean isLogado = false;
	private UsuarioDAO usuarioDAO = null;
	private Date dataSistema = null;

	public void initialize() {

		//criarSql();
		// atualizaSql();

		dataSistema = new Date();
		permissao = new String();

		txtUsuario.textProperty().addListener(new ListenerParaMaiusculas(txtUsuario));
		txtSenha.textProperty().addListener(new ListenerParaMaiusculas(txtSenha));

		textFieldInicial(txtUsuario);

		btnConfirmar.setOnAction(e -> {
			try {
				acaoOK();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btnCancelar.setOnAction(e -> {
			acaoCancelar();
		});

		btnSair.setOnAction(e -> {
			acaoSair();
		});
		txtUsuario.setOnKeyPressed(new EventHandler<KeyEvent>() {

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
					try {
						acaoOK();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
	}

	public void acaoOK() throws IOException {
		this.conexao = new Conexao();
		this.usuarioDAO = new UsuarioDAO(conexao);
		String nome = txtUsuario.getText();
		String senha = txtSenha.getText();
		isLogado = usuarioDAO.logar(conexao, nome, senha);
		if (isLogado) {
			if (getVerificaLicenca().getTime() >= dataSistema.getTime()) {
				permissaoStatic = txtUsuario.getText();
				((Stage) txtUsuario.getScene().getWindow()).close();
				Stage stage1 = new Stage();

				Image image = new Image("/br/com/sistema/icones/W3.png");

				stage1.setTitle("WSYSTEC Sistema Controle Revendedora");
				stage1.getIcons().add(image);

				URL telaHomeFXML = this.getClass().getResource("/br/com/sistema/view/TelaHome.fxml");

				Parent painel = (Parent) FXMLLoader.load(telaHomeFXML);

				stage1.setScene(new Scene(painel));

				stage1.show();
				stage1.setResizable(false);
			} else {
				Alert dlg = new Alert(AlertType.WARNING);
				dlg.setContentText("O período de Teste Gratis acabou !\nEntre em contato com o Administrador!");
				dlg.showAndWait();
			}

		} else {

			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Usuario ou Senha incorretos!");
			dlg.showAndWait();
		}
		conexao.fecharConexao();
	}

	public void acaoCancelar() {
		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.setTitle("Confirmação de Cancelamento");
		alerta.setHeaderText("Você quer mesmo cancelar ? ");
		alerta.setContentText("Deseja realmente encerrar a aplicação \nVocê tem certeza?");
		Optional<ButtonType> escolha = alerta.showAndWait();

		if (escolha.get() == ButtonType.OK) {
			Stage stage = (Stage) btnCancelar.getScene().getWindow();
			stage.close();
		} else {

		}
		conexao.fecharConexao();
	}

	public void acaoSair() {

		Stage stage = (Stage) btnSair.getScene().getWindow();
		stage.close();
		conexao.fecharConexao();
	}

	public String permissaoUsuario() {

		return permissao = permissaoStatic;
	}

	public static boolean isLogado() {
		return isLogado;
	}

	public String usuarioNome() {
		return permissao;
	}

//	private void criarSql() {
//		this.usuarioDAO = Principal.getUsuarioDAO();
//		boolean sucesso = usuarioDAO.criarTabela(
//				"CREATE TABLE clientes_interesse_compra (\r\n" + "    id INT AUTO_INCREMENT PRIMARY KEY,\r\n"
//						+ "    nome VARCHAR(1000) NOT NULL,\r\n" + "    telefone VARCHAR(50) NOT NULL,\r\n"
//						+ "    tipo VARCHAR(100) NOT NULL,\r\n" + "    marca VARCHAR(100) NOT NULL,\r\n"
//						+ "    modelo VARCHAR(100) NOT NULL,\r\n" + "    valor_inicial DECIMAL(11,2) NOT NULL,\r\n"
//						+ "    valor_final DECIMAL(11,2) NOT NULL\r\n" + ");\r\n" + "");
//		System.out.println(sucesso);
//	}

//	private void atualizaSql() {
//		this.usuarioDAO = Principal.getUsuarioDAO();
//		boolean sucesso = usuarioDAO.atualizarSql("veiculo", "valor_fipe",
//				"ALTER TABLE `veiculo` ADD `valor_fipe` DECIMAL(11,2) NOT NULL AFTER `financiamento`;");
//		System.out.println(sucesso);
//	}

	public void textFieldInicial(TextField tf) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				tf.requestFocus();
			}
		});
	}

	public Date getVerificaLicenca() {
		Date result = null;

		String sql = "SELECT * FROM licenca ";

		try {
			PreparedStatement cmd = conexao.getConexao().prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();

			if (rs.next()) {
				result = rs.getDate("data_licenca");
				System.out.println("pegou a data de licenca do banco : " + result);
			}
			cmd.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}

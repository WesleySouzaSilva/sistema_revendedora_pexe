package br.com.sistema.controll;

import java.io.IOException;
import java.net.URL;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TelaPrincipalUsuario {

	@FXML
	private TableView<Usuario> tbUsuarios;

	@FXML
	private TableColumn<Usuario, String> clnNome;

	@FXML
	private TableColumn<Usuario, String> clnPermissao;

	@FXML
	private Button btnPesquisar;

	@FXML
	private ComboBox<String> cmbBuscar;

	@FXML
	private TextField txtPesquisa;

	@FXML
	private Button btnNovo;

	@FXML
	private Button btnEditar;

	@FXML
	private Button btnExcluir;

	private UsuarioDAO usuarioDAO = null;
	private Conexao conexao = Principal.getConexao();
	private ArrayList<String> listaBusca = null;
	private ObservableList<String> observaLista = null;
	private static Integer usuario_id;

	public void initialize() {

		TipoPesquisa();

		txtPesquisa.textProperty().addListener(new ListenerParaMaiusculas(txtPesquisa));
		btnExcluir.setOnAction(e -> {
			excluir();
		});

		btnEditar.setOnAction(e -> {
			try {
				editar();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btnNovo.setOnAction(e -> {
			try {
				adicionar();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btnPesquisar.setOnAction(e -> {
			listarTodos();
		});

		tbUsuarios.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarUsuario(newValue));

		txtPesquisa.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					listarTodos();
				}

			}
		});
	}

	public void listarTodos() {
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
					dlg.setContentText("Preencha o campo pesquisa com o Nome do usuario!!!");
					dlg.showAndWait();
					ValidationFields.checkEmptyFields(txtPesquisa);
					txtPesquisa.requestFocus();

				} else {
					this.usuarioDAO = Principal.getUsuarioDAO();
					String sql = txtPesquisa.getText();
					clnNome.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nome"));
					clnPermissao.setCellValueFactory(new PropertyValueFactory<Usuario, String>("permissao"));
					ObservableList<Usuario> lista = FXCollections.observableArrayList(usuarioDAO.buscarNome(sql));
					tbUsuarios.setItems(lista);
					conexao.fecharConexao();
				}

			} else {

				if (cmbBuscar.getValue().equals("Todos")) {
					txtPesquisa.clear();
					this.usuarioDAO = Principal.getUsuarioDAO();
					clnNome.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nome"));
					clnPermissao.setCellValueFactory(new PropertyValueFactory<Usuario, String>("permissao"));
					ObservableList<Usuario> lista = FXCollections.observableArrayList(usuarioDAO.listarTodos());
					tbUsuarios.setItems(lista);
					conexao.fecharConexao();

				} else {

				}
			}

		}

	}

	public void adicionar() throws IOException {
		Stage stage;
		stage = new Stage();
		Image image = new Image("/br/com/sistema/icones/funcionario.png");

		stage.setTitle("Controle de Usuario");
		stage.getIcons().add(image);
		URL FXML = this.getClass().getResource("/br/com/sistema/view/TelaNovoUsuario.fxml");

		Parent painel = (Parent) FXMLLoader.load(FXML);
		stage.setScene(new Scene(painel));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
		stage.setResizable(false);

	}

	public void excluir() {
		if (tbUsuarios.getSelectionModel().getSelectedItem() != null) {
			this.usuarioDAO = Principal.getUsuarioDAO();
			boolean apagar = true;
			Usuario us = new Usuario(usuario_id, null, null, null);
			if (apagar) {
				Alert alerta = new Alert(AlertType.CONFIRMATION);
				alerta.setTitle("Confirmação de EXCLUSÂO ");
				alerta.setHeaderText("Você quer mesmo excluir o USUARIO selecionado ? ");
				alerta.setContentText("O usuario será excluido!" + "\nVocê tem certeza?");
				Optional<ButtonType> escolha = alerta.showAndWait();
				if (escolha.get() == ButtonType.OK) {
					boolean teste = usuarioDAO.apagar(us);
					System.out.println("excluir usuario : " + teste);
					listarTodos();
				}
			}

			conexao.fecharConexao();
		} else {
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione o USUARIO que deseja Excluir!");
			dlg.showAndWait();
		}

	}

	public void selecionarUsuario(Usuario usuario) {
		if (usuario != null) {
			usuario_id = Integer.valueOf(usuario.getId());
		}
	}

	public void editar() throws IOException {
		Stage stage;
		if (tbUsuarios.getSelectionModel().getSelectedItem() != null) {
			this.usuarioDAO = Principal.getUsuarioDAO();
			boolean apagar = true;
			if (apagar) {
				Alert alerta = new Alert(AlertType.CONFIRMATION);
				alerta.setTitle("Confirmação de EDIÇÃO ");
				alerta.setHeaderText("Você quer mesmo Editar o USUARIO selecionado ? ");
				Optional<ButtonType> escolha = alerta.showAndWait();
				if (escolha.get() == ButtonType.OK) {
					stage = new Stage();
					Image image = new Image("/br/com/sistema/icones/funcionario.png");

					stage.setTitle("Controle de Usuario");
					stage.getIcons().add(image);
					URL FXML = this.getClass().getResource("/br/com/sistema/view/TelaEditarUsuario.fxml");

					Parent painel = (Parent) FXMLLoader.load(FXML);
					stage.setScene(new Scene(painel));
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.show();
					stage.setResizable(false);
				}
			}

			conexao.fecharConexao();
		} else {
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione o USUARIO que deseja Editar!");
			dlg.showAndWait();
		}

	}

	public void TipoPesquisa() {

		listaBusca = new ArrayList<>();
		String Nome = new String("Nome");
		String Todos = new String("Todos");
		listaBusca.add(Nome);
		listaBusca.add(Todos);

		observaLista = FXCollections.observableArrayList(listaBusca);
		cmbBuscar.setItems(observaLista);
	}

	public Integer idUsuario() {
		return usuario_id;
	}

}

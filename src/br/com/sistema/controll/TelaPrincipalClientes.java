package br.com.sistema.controll;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import br.com.sistema.conexao.Conexao;
import br.com.sistema.listeners.ListenerParaMaiusculas;
import br.com.sistema.model.ClientePFProperty;
import br.com.sistema.model.Email;
import br.com.sistema.model.Endereco;
import br.com.sistema.model.Pessoa;
import br.com.sistema.model.Telefone;
import br.com.sistema.model.dao.ClientePFPropertyDAO;
import br.com.sistema.model.dao.EmailDAO;
import br.com.sistema.model.dao.EnderecoDAO;
import br.com.sistema.model.dao.PessoaDAO;
import br.com.sistema.model.dao.TelefoneDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TelaPrincipalClientes {

	@FXML
	private TextField txtPesquisa;

	@FXML
	private Button btnPesquisar;

	@FXML
	private Button btnExcluir;

	@FXML
	private Button btnEditarCliente;

	@FXML
	private ComboBox<String> cmbBusca;

	@FXML
	public TableView<Pessoa> tbClientes;

	@FXML
	private TableColumn<Pessoa, String> clnNome;

	@FXML
	private TableColumn<Pessoa, String> clnCpfCnpj;

	@FXML
	private TableView<Endereco> tbEndereco;

	@FXML
	private TableView<Endereco> tbEndereco2;

	@FXML
	private TableColumn<Endereco, String> clnRua;

	@FXML
	private TableColumn<Endereco, String> clnBairro;

	@FXML
	private TableColumn<Endereco, String> clnNumero;

	@FXML
	private TableColumn<Endereco, String> clnCep;

	@FXML
	private TableColumn<Endereco, String> clnCidade;

	@FXML
	private TableColumn<Endereco, String> clnEstado;

	@FXML
	private TableView<Telefone> tbTelefone;

	@FXML
	private TableColumn<Telefone, String> clnCelular;

	@FXML
	private TableColumn<Telefone, String> clnComercial;

	@FXML
	private TableColumn<Telefone, String> clnResidencial;

	@FXML
	private TableColumn<Telefone, String> clnWhatsapp;

	@FXML
	private TableView<Email> tbEmail;

	@FXML
	private TableColumn<Email, String> clnEmail;

	@FXML
	private TableColumn<Email, String> clnEmailNFS;
	@FXML
	private Label lblNome;

	@FXML
	private Label lblCpfCnpj;

	@FXML
	private Label lblRg;

	@FXML
	private Label lblData;

	@FXML
	private Label lblSexo;

	@FXML
	private Label lblRua;

	@FXML
	private Label lblNumero;

	@FXML
	private Label lblBairro;

	@FXML
	private Label lblCidade;

	@FXML
	private Label lblEstado;

	@FXML
	private Label lblCep;

	@FXML
	private Label lblTelComercial;

	@FXML
	private Label lblTelResidencial;

	@FXML
	private Label lblTelCelular;

	@FXML
	private Label lblTelWhatsapp;

	@FXML
	private Label lblEmail;

	@FXML
	private Label lblEmailNfs;

	@FXML
	private Label lblHora;

	@FXML
	private MenuItem menuCadastroPF;

	@FXML
	private MenuItem menuCadastroPJ;

	private Conexao conexao = Principal.getConexao();
	private PessoaDAO pessoaDAO = Principal.getPessoaDAO();
	private EnderecoDAO enderecoDAO = Principal.getEnderecoDAO();
	private TelefoneDAO telefoneDAO = Principal.getTelefoneDAO();
	private EmailDAO emailDAO = Principal.getEmailDAO();
	private static Integer pessoa_id;
	private List<String> listaBusca = new ArrayList<>();
	private static Integer pessoa_id_static;
	private ClientePFPropertyDAO clientePFPropertyDAO = Principal.getClientePFPropertyDAO();

	public void initialize() {

		comboBoxBusca();
		btnPesquisar.setOnAction(e -> {
			acaoPesquisarPessoa();
		});

		menuCadastroPF.setOnAction(e -> {
			try {
				pessoaFisica();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		menuCadastroPJ.setOnAction(e -> {
			try {
				pessoaJuridica();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
		tbClientes.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarPessoa((Pessoa) newValue));

		tbClientes.setOnMouseClicked(e -> {
			if (tbClientes.getSelectionModel().getSelectedItem() != null) {

				acaoBuscarCliente();
				buscarEmail();
				buscarEndereco();
				buscarEndereco2();
				buscarTelefone();
			} else {
				Alert dlg = new Alert(AlertType.INFORMATION);
				ValidationFields.checkEmptyFields(tbClientes);
				dlg.setContentText("selecione o Cliente!!!");
				dlg.showAndWait();
				tbClientes.requestFocus();
			}

		});

		btnEditarCliente.setOnAction(e -> {
			if (tbClientes.getSelectionModel().getSelectedItem() != null) {

				try {
					editarCliente();
					acaoPesquisarPessoa();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else {
				Alert dlg = new Alert(AlertType.ERROR);
				dlg.setContentText("Selecione o cliente que deseja editar!!!");
				dlg.showAndWait();
				tbClientes.requestFocus();
				return;
			}
		});

		btnExcluir.setOnAction(e -> {
			if (tbClientes.getSelectionModel().getSelectedItem() == null) {
				Alert alerta = new Alert(AlertType.WARNING);
				alerta.setTitle("Confirmação de EXCLUSÃO");
				alerta.setHeaderText("Selecione o cliente que deseja excluir!");
				alerta.showAndWait();
				return;
			} else {

				Pessoa pessoa = new Pessoa(pessoa_id, null, null, null, null, null, null, null, null, null, "NAO",
						null);

				boolean sucess1 = true;
				if (sucess1) {
					Alert alerta = new Alert(AlertType.CONFIRMATION);
					alerta.setTitle("Confirmação de EXCLUSÃO");
					alerta.setHeaderText("Você quer mesmo excluir o cliente selecionado? ");
					alerta.setContentText(
							"O cliente " + nomeCliente(pessoa_id) + " será excluido!" + "\nVocê tem certeza?");
					Optional<ButtonType> escolha = alerta.showAndWait();

					if (escolha.get() == ButtonType.OK) {
						boolean sucesso = pessoaDAO.atualizar(pessoa);
						System.out.println("valor boolean :" + sucesso);
						acaoPesquisarPessoa();

					}
				}

			}
		});

		txtPesquisa.textProperty().addListener(new ListenerParaMaiusculas(txtPesquisa));

	}

	public void pessoaFisica() throws Exception {

		Image image = new Image("/br/com/sistema/icones/clients.png");
		Stage stage = new Stage();
		stage.setTitle("Sistema Clientes");
		stage.getIcons().add(image);
		URL FXML = this.getClass().getResource("/br/com/sistema/view/TelaCadastroClientesPF.fxml");

		Parent painel = (Parent) FXMLLoader.load(FXML);
		stage.setScene(new Scene(painel));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
		stage.setResizable(false);
	}

	public void pessoaJuridica() throws Exception {
		Image image = new Image("/br/com/sistema/icones/clients.png");
		Stage stage = new Stage();
		stage.setTitle("Sistema Clientes");
		stage.getIcons().add(image);
		URL FXML = this.getClass().getResource("/br/com/sistema/view/TelaCadastroClientesPJ.fxml");

		Parent painel = (Parent) FXMLLoader.load(FXML);
		stage.setScene(new Scene(painel));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
		stage.setResizable(false);
	}

	public void acaoPesquisarPessoa() {
		if (cmbBusca.getValue() == null) {
			Alert dlg = new Alert(AlertType.INFORMATION);
			ValidationFields.checkEmptyFields(cmbBusca);
			dlg.setContentText("selecione o campo BUSCAR!!!");
			dlg.showAndWait();
			cmbBusca.requestFocus();
		} else {
			String cmb = cmbBusca.getValue().toString();
			switch (cmb) {
			case "Nome":
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
					tbClientes.setItems(lista);
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
					String sql = txtPesquisa.getText();
					clnNome.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("Nome"));
					clnCpfCnpj.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("cpfcnpj"));
					ObservableList<Pessoa> lista = FXCollections
							.observableArrayList(pessoaDAO.buscarCpfCnpjCliente(formatarCpfCnpj(sql)));
					tbClientes.setItems(lista);

				}

				break;
			case "Todos":
				clnNome.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("Nome"));
				clnCpfCnpj.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("cpfcnpj"));
				ObservableList<Pessoa> lista = FXCollections.observableArrayList(pessoaDAO.listarTodos());
				tbClientes.setItems(lista);

				break;

			default:
				break;

			}

		}
	}

	public void selecionarPessoa(Pessoa pessoa) {

		if (pessoa != null) {

			pessoa_id = Integer.valueOf(pessoa.getId());
			System.out.println("pessoa id :" + pessoa_id);

		}
	}

	public void acaoBuscarCliente() {
		for (ClientePFProperty pessoa : clientePFPropertyDAO.buscarCidade(pessoa_id)) {
			lblNome.setText(pessoa.getNome());
			lblCpfCnpj.setText(pessoa.getCpfcnpj());
			lblData.setText(pessoa.getDataNascimento());
			lblBairro.setText(pessoa.getEndereco().getBairro());
			System.out.println("pegou o bairo : " + pessoa.getEndereco().getBairro());
			lblCep.setText(pessoa.getEndereco().getCep());
			lblCidade.setText(pessoa.getEndereco().getCidade());
			lblEmail.setText(pessoa.getEmail().getEmail());
			lblRua.setText(pessoa.getEndereco().getRua());
			lblEstado.setText(pessoa.getEndereco().getUf());
			lblNumero.setText(pessoa.getEndereco().getNumero());
			lblSexo.setText(pessoa.getSexo());
			lblTelCelular.setText(pessoa.getTelefone().getTelCelular());
			lblTelComercial.setText(pessoa.getTelefone().getTelComercial());
			lblTelResidencial.setText(pessoa.getTelefone().getTelResidencial());
			lblTelWhatsapp.setText(pessoa.getTelefone().getTelWhatsapp());
			lblRg.setText(pessoa.getRg());
			pessoa_id_static = pessoa_id;
		}

	}

	public void comboBoxBusca() {

		String nome = new String("Nome");
		String cpfCnpj = new String("Cpf_Cnpj");
		String todos = new String("Todos");

		listaBusca.add(nome);
		listaBusca.add(cpfCnpj);
		listaBusca.add(todos);

		ObservableList<String> lista = FXCollections.observableArrayList(listaBusca);
		cmbBusca.setItems(lista);
		lista.toString();
	}

	public void editarCliente() throws IOException, SQLException {
		Stage stage;
		String p = new String("PF");

		if (isRegistroPessoa(pessoa_id).equals(p)) {

			stage = new Stage();
			Image image = new Image("/br/com/sistema/icones/clients.png");

			stage.setTitle("Sistema Clientes");
			stage.getIcons().add(image);
			URL FXML = this.getClass().getResource("/br/com/sistema/view/TelaEditarClientePF.fxml");

			Parent painel = (Parent) FXMLLoader.load(FXML);
			stage.setScene(new Scene(painel));
			stage.initModality(Modality.APPLICATION_MODAL);

			stage.show();
			stage.setResizable(false);
		} else {
			stage = new Stage();
			Image image = new Image("/br/com/sistema/icones/clients.png");

			stage.setTitle("Sistema Clientes");
			stage.getIcons().add(image);
			URL FXML = this.getClass().getResource("/br/com/sistema/view/TelaEditarClientePJ.fxml");

			Parent painel = (Parent) FXMLLoader.load(FXML);
			stage.setScene(new Scene(painel));
			stage.initModality(Modality.APPLICATION_MODAL);

			stage.show();
			stage.setResizable(false);
		}
	}

	public void buscarEndereco() {
		clnRua.setCellValueFactory(new PropertyValueFactory<Endereco, String>("rua"));
		clnBairro.setCellValueFactory(new PropertyValueFactory<Endereco, String>("bairro"));
		clnNumero.setCellValueFactory(new PropertyValueFactory<Endereco, String>("numero"));
		ObservableList<Endereco> lista = FXCollections.observableArrayList(enderecoDAO.buscar(pessoa_id));
		tbEndereco.setItems(lista);
	}

	public void buscarTelefone() {
		clnCelular.setCellValueFactory(new PropertyValueFactory<Telefone, String>("telCelular"));
		clnComercial.setCellValueFactory(new PropertyValueFactory<Telefone, String>("telComercial"));
		clnResidencial.setCellValueFactory(new PropertyValueFactory<Telefone, String>("telResidencial"));
		clnWhatsapp.setCellValueFactory(new PropertyValueFactory<Telefone, String>("telWhatsapp"));
		ObservableList<Telefone> lista = FXCollections.observableArrayList(telefoneDAO.buscar(pessoa_id));
		tbTelefone.setItems(lista);
	}

	public void buscarEmail() {
		clnEmail.setCellValueFactory(new PropertyValueFactory<Email, String>("email"));
		clnEmailNFS.setCellValueFactory(new PropertyValueFactory<Email, String>("emailNFS"));
		ObservableList<Email> lista = FXCollections.observableArrayList(emailDAO.buscar(pessoa_id));
		tbEmail.setItems(lista);
	}

	public void buscarEndereco2() {
		clnCep.setCellValueFactory(new PropertyValueFactory<Endereco, String>("cep"));
		clnCidade.setCellValueFactory(new PropertyValueFactory<Endereco, String>("cidade"));
		clnEstado.setCellValueFactory(new PropertyValueFactory<Endereco, String>("uf"));
		ObservableList<Endereco> lista = FXCollections.observableArrayList(enderecoDAO.buscar(pessoa_id));
		tbEndereco2.setItems(lista);
	}

	public String isRegistroPessoa(Integer id) throws SQLException {
		String result = null;

		String sql = "SELECT * FROM pessoa WHERE id = " + id + " ";

		PreparedStatement cmd = conexao.getConexao().prepareStatement(sql);
		ResultSet rs = cmd.executeQuery();
		if (rs.next()) {
			result = rs.getString("tipo");

		}

		return result;

	}

	public Integer pessoaId() {
		pessoa_id_static = pessoa_id;
		System.out.println("pessoa id static : " + pessoa_id_static);
		return pessoa_id_static;
	}

	public boolean apagarValoresCampo(String campo) throws SQLException {
		boolean result = false;

		String sql = "SELECT * FROM pessoa WHERE Nome = ? ";

		PreparedStatement cmd = conexao.getConexao().prepareStatement(sql);
		cmd.setString(1, campo);
		ResultSet rs = cmd.executeQuery();
		if (rs.next()) {
			result = true;

		}

		return result;
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

	public String nomeCliente(Integer id) {
		String nome = null;
		String sql = "SELECT * FROM pessoa WHERE id = '" + id + "'";

		try {
			PreparedStatement cmd = conexao.getConexao().prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				nome = rs.getString("Nome");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nome;

	}
}

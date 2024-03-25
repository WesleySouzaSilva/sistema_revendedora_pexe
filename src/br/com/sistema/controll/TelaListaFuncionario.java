package br.com.sistema.controll;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import br.com.sistema.conexao.Conexao;
import br.com.sistema.listeners.ListenerParaMaiusculas;
import br.com.sistema.model.Despesa;
import br.com.sistema.model.DespesaFuncionario;
import br.com.sistema.model.Funcionario;
import br.com.sistema.model.Relatorio;
import br.com.sistema.model.dao.FuncionarioDAO;
import javafx.application.Platform;
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
import javafx.scene.control.DatePicker;
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
import net.sf.jasperreports.engine.JRException;

public class TelaListaFuncionario {

	@FXML
	private Button btnDemitir;

	@FXML
	private Button btnImprimir;

	@FXML
	private TableView<Funcionario> tbFuncionario;

	@FXML
	private TableColumn<Funcionario, String> clnNome;

	@FXML
	private TableColumn<Funcionario, String> clnProfissao;

	@FXML
	private TableColumn<Funcionario, String> clnSalario;

	@FXML
	private TableColumn<Funcionario, String> clnDataAdmissao;

	@FXML
	private TableColumn<Funcionario, String> clnDataDemissao;

	@FXML
	private TableColumn<Funcionario, String> clnAtivo;

	@FXML
	private MenuItem menuNovoFuncionario;

	@FXML
	private MenuItem menuEditarFuncionario;

	@FXML
	private MenuItem menuExcluirFuncionario;

	@FXML
	private Label lblRua;

	@FXML
	private Label lblBairro;

	@FXML
	private Label lblNumero;

	@FXML
	private Label lblCep;

	@FXML
	private Label lblCidade;

	@FXML
	private Label lblEstado;

	@FXML
	private Label lblTelCelular;

	@FXML
	private Label lblTelComercial;

	@FXML
	private Label lblTelResidencial;

	@FXML
	private Label lblTelWhatsapp;

	@FXML
	private Label lblEmail;

	@FXML
	private ComboBox<String> cmbBuscar;

	@FXML
	private TextField txtPesquisa;

	@FXML
	private DatePicker txtDataInicial;

	@FXML
	private DatePicker txtDataFinal;

	@FXML
	private Button btnPesquisarDetalhes;

	@FXML
	private TableView<Despesa> tbDespesa;

	@FXML
	private TableColumn<Despesa, String> clnDescricao;

	@FXML
	private TableColumn<Despesa, String> clnResponsavel;

	@FXML
	private TableColumn<Despesa, String> clnData;

	@FXML
	private TableColumn<Despesa, String> clnValor;

	@FXML
	private Button btnImprimirDespesa;

	@FXML
	private Label lblTotalDespesas;

	@FXML
	private Button btnPesquisar;

	@FXML
	private Button btnDespesaVale;

	private Conexao conexao;
	private FuncionarioDAO funcionarioDAO = null;
	private static Integer funcionario_id = null;
	private static Date dataInicial, dataFinal;

	public void initialize() {

		comboBox();

		btnPesquisar.setOnAction(e -> {
			pesquisar();
		});

		textFieldInicial(txtPesquisa);

		txtPesquisa.textProperty().addListener(new ListenerParaMaiusculas(txtPesquisa));

		tbFuncionario.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> FuncionarioTabela((Funcionario) newValue));
		tbFuncionario.setOnMouseClicked(e -> {
			buscaDadosFuncionario();
		});

		menuNovoFuncionario.setOnAction(e -> {
			try {
				chamarTela("TelaNovoFuncionario");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		menuEditarFuncionario.setOnAction(e -> {
			try {
				editar();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		menuExcluirFuncionario.setOnAction(e -> {
			excluir();
		});

		btnDespesaVale.setOnAction(e -> {
			try {
				despesaFuncionario();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btnDemitir.setOnAction(e -> {
			demitir();
		});

		btnImprimir.setOnAction(e -> {
			try {
				imprimir();
			} catch (JRException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		txtPesquisa.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					pesquisar();
					return;
				}

			}
		});

		txtDataInicial.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtDataFinal.requestFocus();
				}

			}
		});

		txtDataFinal.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					buscarDespesas();
				}

			}
		});

		btnPesquisarDetalhes.setOnAction(e -> {
			buscarDespesas();
		});

		btnImprimirDespesa.setOnAction(e -> {
			try {
				imprimirDespesaFuncionario();
			} catch (JRException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

	public void comboBox() {
		String nome = new String("Nome");
		String todos = new String("Todos");
		String ativo = new String("Ativo");

		ArrayList<String> lista = new ArrayList<>();
		lista.add(nome);
		lista.add(todos);
		lista.add(ativo);

		ObservableList<String> observa = FXCollections.observableArrayList(lista);
		cmbBuscar.setItems(observa);
	}

	public void FuncionarioTabela(Funcionario fun) {
		if (fun != null) {
			funcionario_id = fun.getId();
			System.out.println("id tabela : " + funcionario_id);
		}
	}

	public void pesquisar() {
		String cmb = null;
		if (cmbBuscar.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbBuscar);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione o campo Buscar!");
			dlg.showAndWait();
			cmbBuscar.requestFocus();
			return;
		} else {
			cmb = cmbBuscar.getValue().toString();
			clnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
			clnProfissao.setCellValueFactory(new PropertyValueFactory<>("profissao"));
			clnDataAdmissao.setCellValueFactory(new PropertyValueFactory<>("dataAdmissaoFormatado"));
			clnDataDemissao.setCellValueFactory(new PropertyValueFactory<>("dataDemissaoFormatado"));
			clnAtivo.setCellValueFactory(new PropertyValueFactory<>("ativo"));
			this.conexao = new Conexao();
			this.funcionarioDAO = new FuncionarioDAO(conexao);
			switch (cmb) {
			case "Nome":
				String pesquisa = txtPesquisa.getText();
				if (txtPesquisa.getText().isEmpty()) {
					ValidationFields.checkEmptyFields(txtPesquisa);
					Alert dlg = new Alert(AlertType.WARNING);
					dlg.setContentText("Preencah o campo pesquisa com o Nome do Funcionario!");
					dlg.showAndWait();
					txtPesquisa.requestFocus();
					return;
				} else {
					ObservableList<Funcionario> lista = FXCollections
							.observableArrayList(funcionarioDAO.listarTodosNome(pesquisa));
					tbFuncionario.setItems(lista);

				}
				break;

			case "Todos":
				ObservableList<Funcionario> lista = FXCollections.observableArrayList(funcionarioDAO.listarTodos());
				tbFuncionario.setItems(lista);

				break;

			case "Ativo":
				ObservableList<Funcionario> listas = FXCollections
						.observableArrayList(funcionarioDAO.listarTodosAtivo("SIM"));
				tbFuncionario.setItems(listas);

				break;
			default:
				break;
			}
			conexao.fecharConexao();

		}
	}

	public void buscarDespesas() {

		if (txtDataInicial.getValue() == null) {
			ValidationFields.checkEmptyFields(txtDataInicial);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione a Data Inicial!");
			dlg.showAndWait();
			txtDataInicial.requestFocus();
			return;
		} else {
			dataInicial = java.sql.Date.valueOf(txtDataInicial.getValue());
		}

		if (txtDataFinal.getValue() == null) {
			ValidationFields.checkEmptyFields(txtDataFinal);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione a Data Final!");
			dlg.showAndWait();
			txtDataFinal.requestFocus();
			return;
		} else {
			dataFinal = java.sql.Date.valueOf(txtDataFinal.getValue());
		}

		this.conexao = new Conexao();
		this.funcionarioDAO = new FuncionarioDAO(conexao);
		clnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		clnResponsavel.setCellValueFactory(new PropertyValueFactory<>("responsavel"));
		clnData.setCellValueFactory(new PropertyValueFactory<>("dataFormatada"));
		clnValor.setCellValueFactory(new PropertyValueFactory<>("valorFormatado"));
		ObservableList<Despesa> lista = FXCollections
				.observableArrayList(funcionarioDAO.listarTodosDespesa(dataInicial, dataFinal));
		tbDespesa.setItems(lista);
		lblTotalDespesas.setText(funcionarioDAO.getTotalDespesa(dataInicial, dataFinal));
		conexao.fecharConexao();

	}

	public void imprimirDespesaFuncionario() throws JRException {
		String descricao = null, responsavel = null, data = null, valor = null, dataInicio = null, dataFim = null,
				totalDespesa = null;
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<DespesaFuncionario> lista = new ArrayList<>();
		if (txtDataInicial.getValue() == null) {
			ValidationFields.checkEmptyFields(txtDataInicial);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione a Data Inicial!");
			dlg.showAndWait();
			txtDataInicial.requestFocus();
			return;
		} else {
			dataInicial = java.sql.Date.valueOf(txtDataInicial.getValue());
		}

		if (txtDataFinal.getValue() == null) {
			ValidationFields.checkEmptyFields(txtDataFinal);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione a Data Final!");
			dlg.showAndWait();
			txtDataFinal.requestFocus();
			return;
		} else {
			dataFinal = java.sql.Date.valueOf(txtDataFinal.getValue());
		}

		dataInicio = df.format(dataInicial);
		dataFim = df.format(dataFinal);

		this.conexao = new Conexao();
		this.funcionarioDAO = new FuncionarioDAO(conexao);
		totalDespesa = funcionarioDAO.getTotalDespesa(dataInicial, dataFinal);
		for (Despesa d : funcionarioDAO.listarTodosDespesa(dataInicial, dataFinal)) {
			descricao = d.getDescricao();
			responsavel = d.getResponsavel();
			data = d.getDataVencimentoFormatada();
			valor = d.getValorFormatado();

			DespesaFuncionario despesa = new DespesaFuncionario(funcionario_id, descricao, responsavel, valor, data,
					dataInicio, dataFim);
			lista.add(despesa);

		}

		DespesaFuncionario des = new DespesaFuncionario(totalDespesa);
		lista.add(des);

		Relatorio relatorio = new Relatorio();
		relatorio.gerarRelatorioDespesaFuncionario(lista);
		conexao.fecharConexao();
	}

	public void imprimir() throws JRException {
		String nome = null, salario = null, profissao = null, dataAdmissao = null, dataDemissao = null, ativo = null;
		String cmb = null;
		ArrayList<Funcionario> lista = new ArrayList<>();
		if (cmbBuscar.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbBuscar);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione o campo Buscar!");
			dlg.showAndWait();
			cmbBuscar.requestFocus();
			return;
		} else {
			cmb = cmbBuscar.getValue().toString();
			this.conexao = new Conexao();
			this.funcionarioDAO = new FuncionarioDAO(conexao);
			switch (cmb) {
			case "Nome":
				String pesquisa = txtPesquisa.getText();
				if (txtPesquisa.getText().isEmpty()) {
					ValidationFields.checkEmptyFields(txtPesquisa);
					Alert dlg = new Alert(AlertType.WARNING);
					dlg.setContentText("Preencah o campo pesquisa com o Nome do Funcionario!");
					dlg.showAndWait();
					txtPesquisa.requestFocus();
					return;
				} else {
					for (Funcionario f : funcionarioDAO.listarTodosNome(pesquisa)) {
						nome = f.getNome();
						profissao = f.getProfissao();
						dataAdmissao = f.getDataAdmissaoFormatado();
						dataDemissao = f.getDataDemissaoFormatado();
						salario = f.getSalarioFormatado();
						ativo = f.getAtivo();

						Funcionario funcionario = new Funcionario(nome, profissao, ativo, salario, dataAdmissao,
								dataDemissao, null);
						lista.add(funcionario);

					}

					Relatorio relatorio = new Relatorio();
					relatorio.gerarRelatorioFuncionario(lista);

				}
				break;

			case "Todos":
				for (Funcionario f : funcionarioDAO.listarTodos()) {
					nome = f.getNome();
					profissao = f.getProfissao();
					dataAdmissao = f.getDataAdmissaoFormatado();
					dataDemissao = f.getDataDemissaoFormatado();
					salario = f.getSalarioFormatado();
					ativo = f.getAtivo();

					Funcionario funcionario = new Funcionario(nome, profissao, ativo, salario, dataAdmissao,
							dataDemissao, null);
					lista.add(funcionario);

				}

				Relatorio relatorio = new Relatorio();
				relatorio.gerarRelatorioFuncionario(lista);

				break;

			case "Ativo":

				for (Funcionario f : funcionarioDAO.listarTodosAtivo("SIM")) {
					nome = f.getNome();
					profissao = f.getProfissao();
					dataAdmissao = f.getDataAdmissaoFormatado();
					dataDemissao = f.getDataDemissaoFormatado();
					salario = f.getSalarioFormatado();
					ativo = f.getAtivo();

					Funcionario funcionario = new Funcionario(nome, profissao, ativo, salario, dataAdmissao,
							dataDemissao, null);
					lista.add(funcionario);

				}

				Relatorio relatorios = new Relatorio();
				relatorios.gerarRelatorioFuncionario(lista);

				break;
			default:
				break;
			}
			conexao.fecharConexao();
		}
	}

	public void excluir() {
		Funcionario funcionario = null;
		if (tbFuncionario.getSelectionModel().getSelectedItem() == null) {
			ValidationFields.checkEmptyFields(tbFuncionario);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione o Funcionario que deseja Excluir!");
			dlg.showAndWait();
			tbFuncionario.requestFocus();
			return;
		} else {
			this.conexao = new Conexao();
			this.funcionarioDAO = new FuncionarioDAO(conexao);
			String nome = funcionarioDAO.getNome(funcionario_id);
			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Confirmação de EXCLUSÃO");
			alerta.setHeaderText("Você quer mesmo excluir o Funcionario ? ");
			alerta.setContentText("O Funcionario " + nome + " será excluido!" + "\nVocê tem certeza?");
			Optional<ButtonType> escolha = alerta.showAndWait();

			String ativo = new String("NAO");

			funcionario = new Funcionario(funcionario_id, null, null, ativo, null, null, null, null, null);

			if (escolha.get() == ButtonType.OK) {
				boolean sucesso = funcionarioDAO.apagar(funcionario);
				System.out.println("sucesso excluir funcionario :" + sucesso);
				pesquisar();

			}
			conexao.fecharConexao();
		}
	}

	public void demitir() {
		Funcionario funcionario = null;
		if (tbFuncionario.getSelectionModel().getSelectedItem() == null) {
			ValidationFields.checkEmptyFields(tbFuncionario);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione o Funcionario que deseja Demitir!");
			dlg.showAndWait();
			tbFuncionario.requestFocus();
			return;
		} else {
			this.conexao = new Conexao();
			this.funcionarioDAO = new FuncionarioDAO(conexao);
			if (funcionarioDAO.getStatusDemissao(funcionario_id).equalsIgnoreCase("NAO")) {
				ValidationFields.checkEmptyFields(tbFuncionario);
				Alert dlg = new Alert(AlertType.WARNING);
				dlg.setContentText("Funcionario ja foi demitido!");
				dlg.showAndWait();
				tbFuncionario.requestFocus();
				return;
			} else {
				Date data = new Date();
				String nome = funcionarioDAO.getNome(funcionario_id);
				Alert alerta = new Alert(AlertType.CONFIRMATION);
				alerta.setTitle("Confirmação de DEMISSÃO");
				alerta.setHeaderText("Você quer mesmo demitir o Funcionario ? ");
				alerta.setContentText("O Funcionario " + nome + " será demitido!" + "\nVocê tem certeza?");
				Optional<ButtonType> escolha = alerta.showAndWait();

				Date dataDemissao = new java.sql.Date(data.getTime());
				String ativo = new String("NAO");

				funcionario = new Funcionario(funcionario_id, null, null, ativo, null, null, null, null, dataDemissao);

				if (escolha.get() == ButtonType.OK) {
					boolean sucesso = funcionarioDAO.atualizarDemissao(funcionario);
					System.out.println("sucesso demitir funcionario :" + sucesso);
					pesquisar();

				}
				conexao.fecharConexao();
			}
		}
	}

	public void editar() throws IOException {
		if (tbFuncionario.getSelectionModel().getSelectedItem() == null) {
			ValidationFields.checkEmptyFields(tbFuncionario);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione o Funcionario que deseja Editar!");
			dlg.showAndWait();
			tbFuncionario.requestFocus();
			return;
		} else {
			this.conexao = new Conexao();
			this.funcionarioDAO = new FuncionarioDAO(conexao);
			if (funcionarioDAO.getStatusDemissao(funcionario_id).equalsIgnoreCase("SIM")) {
				chamarTela("TelaEditarFuncionario");
			} else {
				ValidationFields.checkEmptyFields(tbFuncionario);
				Alert dlg = new Alert(AlertType.WARNING);
				dlg.setContentText("Funcionario ja foi demitido, não e possivel editar os dados!");
				dlg.showAndWait();
				tbFuncionario.requestFocus();
				return;
			}
			conexao.fecharConexao();
		}
	}

	public void despesaFuncionario() throws IOException {
		if (tbFuncionario.getSelectionModel().getSelectedItem() == null) {
			ValidationFields.checkEmptyFields(tbFuncionario);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione o Funcionario que deseja marcar uma Despesa!");
			dlg.showAndWait();
			tbFuncionario.requestFocus();
			return;
		} else {
			this.conexao = new Conexao();
			this.funcionarioDAO = new FuncionarioDAO(conexao);
			if (funcionarioDAO.getStatusDemissao(funcionario_id).equalsIgnoreCase("SIM")) {
				chamarTela("TelaDespesaVale");
			} else {
				ValidationFields.checkEmptyFields(tbFuncionario);
				Alert dlg = new Alert(AlertType.WARNING);
				dlg.setContentText("Funcionario com status ativo = 'NAO'.\nNão e possivel adicionar DESPESA!");
				dlg.showAndWait();
				tbFuncionario.requestFocus();
				return;
			}
			conexao.fecharConexao();
		}
	}

	public void buscaDadosFuncionario() {
		this.conexao = new Conexao();
		this.funcionarioDAO = new FuncionarioDAO(conexao);
		for (Funcionario f : funcionarioDAO.listarEnderecoFuncionario(funcionario_id)) {
			lblRua.setText(f.getRua());
			lblBairro.setText(f.getBairro());
			lblNumero.setText(f.getNumero());
			lblCidade.setText(f.getCidade());
			lblEstado.setText(f.getEstado());
			lblCep.setText(f.getCep());
			lblTelCelular.setText(f.getTelCelular());
			lblTelComercial.setText(f.getTelComercial());
			lblTelResidencial.setText(f.getTelResidencial());
			lblTelWhatsapp.setText(f.getTelWhatsapp());
			lblEmail.setText(f.getEmailFormatado());
		}
		conexao.fecharConexao();

	}

	public void chamarTela(String tela) throws IOException {
		Stage stage = new Stage();
		Image image = new Image("/br/com/sistema/icones/W3.png");

		stage.getIcons().add(image);
		URL FXML = this.getClass().getResource("/br/com/sistema/view/" + tela + ".fxml");

		Parent painel = (Parent) FXMLLoader.load(FXML);
		stage.setScene(new Scene(painel));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
		stage.setResizable(false);
	}

	public Integer IdFuncionario() {
		return funcionario_id;
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

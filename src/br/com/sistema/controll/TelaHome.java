package br.com.sistema.controll;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.joda.time.DateTime;
import org.joda.time.Days;

import br.com.sistema.conexao.Conexao;
import br.com.sistema.listeners.ListenerParaMaiusculas;
import br.com.sistema.model.PagamentoVeiculo;
import br.com.sistema.model.Relatorio;
import br.com.sistema.model.Veiculo;
import br.com.sistema.model.VeiculoRelatorio;
import br.com.sistema.model.dao.PagamentoVeiculoDAO;
import br.com.sistema.model.dao.VeiculoDAO;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JRException;

public class TelaHome {

	@FXML
	private TableView<Veiculo> tbVeiculo;

	@FXML
	private TableColumn<Veiculo, String> clnVeiculo;

	@FXML
	private TableColumn<Veiculo, String> clnCategoria;

	@FXML
	private TableColumn<Veiculo, String> clnPlaca;

	@FXML
	private TableColumn<Veiculo, String> clnCor;

	@FXML
	private TableColumn<Veiculo, String> clnKm;

	@FXML
	private TableColumn<Veiculo, String> clnDataEntrada;

	@FXML
	private TableColumn<Veiculo, String> clnMarca;
	
	@FXML
	private TableColumn<Veiculo, String> clnAno;

	@FXML
	private TableColumn<Veiculo, String> clnValorVenda;

	@FXML
	private TableColumn<Veiculo, String> clnSituacao;

	@FXML
	private TableColumn<Veiculo, String> clnValorFipe;

	@FXML
	private ComboBox<String> cmbBuscar;

	@FXML
	private TextField txtPesquisa;

	@FXML
	private Button btnPesquisar;

	@FXML
	private ComboBox<String> cmbTipo;

	@FXML
	private Button btnNovoVeiculo;

	@FXML
	private Button btnEditarDados;

	@FXML
	private Button btnHistoricoVeiculo;

	@FXML
	private Button btnAdicionarDespesa;

	@FXML
	private Button btnVenda;

	@FXML
	private Button btnImprimirDados;

	@FXML
	private Button btnImprimirLista;

	@FXML
	private Button btnExcluir;

	@FXML
	private Label lblDadosVeiculo;

	@FXML
	private Label lblLeilaoRs;

	@FXML
	private Label lblFinanciamento;

	@FXML
	private Label lblDadosPlaca;
	
	@FXML
	private Label lblAno;

	@FXML
	private Label lblDadosRenavam;

	@FXML
	private Label lblDadosCor;

	@FXML
	private Label lblKm;

	@FXML
	private Label lblDadosDataEntrada;

	@FXML
	private Label lblDadosValorVenda;

	@FXML
	private Label lblCustoDiaPatio;

	@FXML
	private Label lblCustoTotalDespesa;

	@FXML
	private Label lblCustoValorEntrada;

	@FXML
	private Label lblCustoTotalVeiculo;

	@FXML
	private Label lblVendaDataVenda;

	@FXML
	private Label lblVendaLucroVenda;

	@FXML
	private Label lblVendaCliente;

	@FXML
	private Label lblVendaTipoPagamento;

	@FXML
	private Label lblVendaParcela;

	@FXML
	private Label lblVendaValorParcela;

	@FXML
	private Label lblVendaGarantia;

	@FXML
	private Label lblVencimentoGarantia;

	@FXML
	private Label lblVendaObservacao;

	@FXML
	private Label lblValorFipe;

	@FXML
	private Label lblUsuario;

	@FXML
	private Label lblData;

	@FXML
	private Label lblHora;

	@FXML
	private Label lblValorTotalSomaVeiculos;

	@FXML
	private Label lblValorTotalCompraVeiculos;

	@FXML
	private Label lblValorTotalFipeVeiculos;

	@FXML
	private Label lblQtdeVeiculos;

	@FXML
	private MenuItem menuListaUsuario;

	@FXML
	private MenuItem menuListaClientes;

	@FXML
	private MenuItem menuClientesPF;

	@FXML
	private MenuItem menuClientesPJ;

	@FXML
	private MenuItem menuListaFuncionarios;

	@FXML
	private MenuItem menuConsultaFipe;

	@FXML
	private MenuItem menuListaContasPagar;

	@FXML
	private MenuItem menuListaContasReceber;

	@FXML
	private MenuItem menuRelatorioDre;

	@FXML
	private MenuItem menuRelatorioVendedores;

	@FXML
	private MenuItem menuRelatorioGeralVendas;

	@FXML
	private MenuItem menuListaGarantia;

	@FXML
	private MenuItem menuCadastroClienteInteresse;

	@FXML
	private MenuItem menuListaClienteInteresse;

	private TelaLogin telaLogin = new TelaLogin();
	private SimpleDateFormat formatador = new SimpleDateFormat("HH:mm:ss a");
	private Timeline timeline = null;
	private Conexao conexao = null;
	private static Integer veiculo_id = null;
	private VeiculoDAO veiculoDAO;
	private PagamentoVeiculoDAO pagamentoVeiculoDAO;
	private DecimalFormat decimal;

	public void initialize() {

		menuListaClientes.setOnAction(e -> {
			try {
				chamarTela("TelaPrincipalClientes");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		menuCadastroClienteInteresse.setOnAction(e -> {
			try {
				chamarTela("TelaNovoVeiculoInteresse");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		menuListaClienteInteresse.setOnAction(e -> {
			try {
				chamarTela("TelaListaClienteCrm");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		menuListaContasPagar.setOnAction(e -> {
			this.conexao = new Conexao();
			try {
				if (verificaPermissaoUsuario(telaLogin.permissaoUsuario())) {
					try {
						chamarTela("TelaListaContasPagar");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					Alert dlg = new Alert(AlertType.WARNING);
					dlg.setContentText("Acesso negado!\nAcesso permitido apenas para ADMINISTRADOR!");
					dlg.showAndWait();
					return;
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			conexao.fecharConexao();
		});

		menuListaContasReceber.setOnAction(e -> {
			this.conexao = new Conexao();
			try {
				if (verificaPermissaoUsuario(telaLogin.permissaoUsuario())) {
					try {
						chamarTela("TelaListaContasReceber");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					Alert dlg = new Alert(AlertType.WARNING);
					dlg.setContentText("Acesso negado!\nAcesso permitido apenas para ADMINISTRADOR!");
					dlg.showAndWait();
					return;
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			conexao.fecharConexao();
		});

		menuListaFuncionarios.setOnAction(e -> {
			try {
				chamarTela("TelaListaFuncionario");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		menuRelatorioVendedores.setOnAction(e -> {
			this.conexao = new Conexao();
			try {
				if (verificaPermissaoUsuario(telaLogin.permissaoUsuario())) {
					try {
						chamarTela("TelaRelatorioVendedores");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					Alert dlg = new Alert(AlertType.WARNING);
					dlg.setContentText("Acesso negado!\nAcesso permitido apenas para ADMINISTRADOR!");
					dlg.showAndWait();
					return;
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			conexao.fecharConexao();
		});

		menuListaGarantia.setOnAction(e -> {
			this.conexao = new Conexao();
			try {
				if (verificaPermissaoUsuario(telaLogin.permissaoUsuario())) {
					try {
						chamarTela("TelaHistoricoGarantiaVeiculo");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					Alert dlg = new Alert(AlertType.WARNING);
					dlg.setContentText("Acesso negado!\nAcesso permitido apenas para ADMINISTRADOR!");
					dlg.showAndWait();
					return;
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			conexao.fecharConexao();
		});

		menuRelatorioGeralVendas.setOnAction(e -> {
			this.conexao = new Conexao();
			try {
				if (verificaPermissaoUsuario(telaLogin.permissaoUsuario())) {
					try {
						chamarTela("TelaRelatorioGeralVendas");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					Alert dlg = new Alert(AlertType.WARNING);
					dlg.setContentText("Acesso negado!\nAcesso permitido apenas para ADMINISTRADOR!");
					dlg.showAndWait();
					return;
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			conexao.fecharConexao();
		});

		menuRelatorioDre.setOnAction(e -> {
			this.conexao = new Conexao();
			try {
				if (verificaPermissaoUsuario(telaLogin.permissaoUsuario())) {
					try {
						chamarTela("TelaRelatorioDre");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					Alert dlg = new Alert(AlertType.WARNING);
					dlg.setContentText("Acesso negado!\nAcesso permitido apenas para ADMINISTRADOR!");
					dlg.showAndWait();
					return;
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			conexao.fecharConexao();
		});

		menuClientesPF.setOnAction(e -> {
			try {
				chamarTela("TelaCadastroClientesPF");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		menuClientesPJ.setOnAction(e -> {
			try {
				chamarTela("TelaCadastroClientesPJ");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		menuListaUsuario.setOnAction(e -> {
			this.conexao = new Conexao();
			try {
				if (verificaPermissaoUsuario(telaLogin.permissaoUsuario())) {
					try {
						chamarTela("TelaUsuarioPrincipal");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					Alert dlg = new Alert(AlertType.WARNING);
					dlg.setContentText("Acesso negado!\nAcesso permitido apenas para ADMINISTRADOR!");
					dlg.showAndWait();
					return;
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			conexao.fecharConexao();
		});

		decimal = new DecimalFormat("###,###,###,##0.00");

		comboBoxBusca();
		comboBoxTipo();

		txtPesquisa.textProperty().addListener(new ListenerParaMaiusculas(txtPesquisa));

		tbVeiculo.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarVeiculo((Veiculo) newValue));

		tbVeiculo.setOnMouseClicked(e -> {

			listarDadosVeiculo();
			try {
				listarDadosVenda();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				ListarDadosDespesas();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btnHistoricoVeiculo.setOnAction(e -> {
			this.conexao = new Conexao();
			if (tbVeiculo.getSelectionModel().getSelectedItem() != null) {
				try {
					if (verificaPermissaoUsuario(telaLogin.permissaoUsuario())) {
						try {
							chamarTela("TelaHistoricoVeiculo");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						Alert dlg = new Alert(AlertType.WARNING);
						dlg.setContentText("Acesso negado!\nAcesso permitido apenas para ADMINISTRADOR!");
						dlg.showAndWait();
						return;
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				Alert dlg = new Alert(AlertType.WARNING);
				dlg.setContentText("Selecione o veiculo que deseja visualizar o HISTORICO!");
				dlg.showAndWait();

				return;
			}
			conexao.fecharConexao();
		});

		btnNovoVeiculo.setOnAction(e -> {
			try {
				chamarTela("TelaNovoVeiculo");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btnExcluir.setOnAction(e -> {
			this.conexao = new Conexao();
			if (tbVeiculo.getSelectionModel().getSelectedItem() != null) {
				try {
					if (verificaVeiculoVendido(veiculo_id)) {
						Alert dlg = new Alert(AlertType.WARNING);
						dlg.setContentText("Veiculo foi vendido, não é possivel EXCLUIR!");
						dlg.showAndWait();
					} else {
						try {
							if (verificaPermissaoUsuario(telaLogin.permissaoUsuario())) {
								excluir();
							} else {
								Alert dlg = new Alert(AlertType.WARNING);
								dlg.setContentText("Acesso negado!\nAcesso permitido apenas para ADMINISTRADOR!");
								dlg.showAndWait();
								return;
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				Alert dlg = new Alert(AlertType.WARNING);
				dlg.setContentText("Selecione o veiculo que deseja EXCLUIR!");
				dlg.showAndWait();

				return;
			}
			conexao.fecharConexao();
		});

		btnImprimirLista.setOnAction(e -> {
			try {
				imprimirLista();
			} catch (JRException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btnImprimirDados.setOnAction(e -> {
			this.conexao = new Conexao();
			if (tbVeiculo.getSelectionModel().getSelectedItem() != null) {
				try {
					if (verificaPermissaoUsuario(telaLogin.permissaoUsuario())) {
						imprimirDadosVeiculo();
					} else {
						Alert dlg = new Alert(AlertType.WARNING);
						dlg.setContentText("Acesso negado!\nAcesso permitido apenas para ADMINISTRADOR!");
						dlg.showAndWait();
						return;
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JRException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else {
				Alert dlg = new Alert(AlertType.WARNING);
				dlg.setContentText("Selecione o veiculo, para imprimir o relatorio!");
				dlg.showAndWait();
			}
			conexao.fecharConexao();
		});

		btnAdicionarDespesa.setOnAction(e -> {
			this.conexao = new Conexao();
			if (tbVeiculo.getSelectionModel().getSelectedItem() != null) {
				try {
					if (verificaVeiculoVendido(veiculo_id)) {
						Alert dlg = new Alert(AlertType.WARNING);
						dlg.setContentText("Veiculo ja foi vendido.\nNão é possivel adicionar despesa ao veiculo!");
						dlg.showAndWait();
						return;

					} else {

						try {
							if (verificaPermissaoUsuario(telaLogin.permissaoUsuario())) {
								try {
									chamarTela("TelaDespesaVeiculo");
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							} else {
								Alert dlg = new Alert(AlertType.WARNING);
								dlg.setContentText("Acesso negado!\nAcesso permitido apenas para ADMINISTRADOR!");
								dlg.showAndWait();
								return;
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else {
				Alert dlg = new Alert(AlertType.WARNING);
				dlg.setContentText("Selecione o veiculo que deseja adicionar a DESPESA!");
				dlg.showAndWait();

				return;
			}
			conexao.fecharConexao();
		});

		btnVenda.setOnAction(e -> {
			this.conexao = new Conexao();
			if (tbVeiculo.getSelectionModel().getSelectedItem() != null) {
				try {
					if (verificaVeiculoVendido(veiculo_id)) {
						Alert dlg = new Alert(AlertType.WARNING);
						dlg.setContentText("Veiculo ja foi vendido.\nNão é possivel continuar!");
						dlg.showAndWait();
						return;
					} else {
						try {
							chamarTela("TelaVendaVeiculo");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else {
				Alert dlg = new Alert(AlertType.WARNING);
				dlg.setContentText("Selecione o veiculo que deseja adicionar a VENDA!");
				dlg.showAndWait();

				return;
			}
			conexao.fecharConexao();
		});

		btnEditarDados.setOnAction(e -> {
			this.conexao = new Conexao();
			if (tbVeiculo.getSelectionModel().getSelectedItem() != null) {
				try {
					if (verificaVeiculoVendido(veiculo_id)) {
						Alert dlg = new Alert(AlertType.WARNING);
						dlg.setContentText("Veiculo ja foi vendido.\nNão é possivel editar os dados do veiculo!");
						dlg.showAndWait();
						return;
					} else {

						try {
							if (verificaPermissaoUsuario(telaLogin.permissaoUsuario())) {
								try {
									chamarTela("TelaEditarVeiculo");
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							} else {
								Alert dlg = new Alert(AlertType.WARNING);
								dlg.setContentText("Acesso negado!\nAcesso permitido apenas para ADMINISTRADOR!");
								dlg.showAndWait();
								return;
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else {
				Alert dlg = new Alert(AlertType.WARNING);
				dlg.setContentText("Selecione o veiculo que deseja EDITAR os DADOS!");
				dlg.showAndWait();

				return;
			}
			conexao.fecharConexao();
		});

		btnPesquisar.setOnAction(e -> {
			try {
				pesquisar();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		cmbBuscar.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtPesquisa.requestFocus();
				}

			}
		});

		txtPesquisa.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					try {
						pesquisar();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});

		clnSituacao.setCellFactory(column -> {
			return new TableCell<Veiculo, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					// Verifica se a célula está vazia
					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						// Define o texto da célula
						setText(item);

						if (item.equals("ESTOQUE")) {
							setTextFill(Color.GREEN); // Texto em verde
						} else if (item.equals("CONSIGNADO")) {
							setTextFill(Color.BLUE); // Texto em vermelho
						} else {
							// Define a cor padrão ou qualquer outra lógica desejada
							setTextFill(Color.RED);
						}
					}
				}
			};
		});

		Date data = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String dataRecebida = format.format(data);
		lblData.setText(dataRecebida);
		telaLogin = new TelaLogin();
		lblUsuario.setText(telaLogin.permissaoUsuario());

		KeyFrame frame = new KeyFrame(Duration.millis(1000), e -> atualizaHoras());
		timeline = new Timeline(frame);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	public void selecionarVeiculo(Veiculo veiculo) {
		if (veiculo != null) {
			veiculo_id = veiculo.getId();
			System.out.println("pegou id do veiculo : " + veiculo_id);
		}
	}

	public Integer getIdVeiculo() {
		return veiculo_id;
	}

	public void pesquisar() throws SQLException {
		this.conexao = new Conexao();
		this.veiculoDAO = new VeiculoDAO(conexao);
		String cmb = null, tipo = null, tipoBusca = null, pesquisa = null;

		if (cmbTipo.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbTipo);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione o campo TIPO!");
			dlg.showAndWait();
			cmbTipo.requestFocus();
			return;

		} else {

			tipo = cmbTipo.getValue().toString();

		}

		if (cmbBuscar.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbBuscar);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione o campo BUSCAR!");
			dlg.showAndWait();
			cmbBuscar.requestFocus();
			return;

		} else {
			cmb = cmbBuscar.getValue().toString();
			clnVeiculo.setCellValueFactory(new PropertyValueFactory<>("veiculo"));
			clnCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
			clnPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
			clnCor.setCellValueFactory(new PropertyValueFactory<>("cor"));
			clnKm.setCellValueFactory(new PropertyValueFactory<>("km"));
			clnDataEntrada.setCellValueFactory(new PropertyValueFactory<>("dataEntradaFormatada"));
			clnAno.setCellValueFactory(new PropertyValueFactory<>("ano_modelo"));
			clnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
			clnValorVenda.setCellValueFactory(new PropertyValueFactory<>("valorVendaFormatado"));
			clnSituacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));
			clnValorFipe.setCellValueFactory(new PropertyValueFactory<>("valorFipeFormatado"));
			switch (tipo) {

			case "Estoque":
				pesquisa = txtPesquisa.getText();
				switch (cmb) {
				case "Nome Veiculo":
					pesquisa = txtPesquisa.getText();
					tipoBusca = cmbTipo.getValue().toUpperCase();

					if (txtPesquisa.getText().isEmpty()) {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.veiculo"));
						tbVeiculo.setItems(lista);
					} else {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.veiculo", pesquisa));
						tbVeiculo.setItems(lista);
					}

					break;

				case "Categoria Veiculos":

					pesquisa = txtPesquisa.getText();
					tipoBusca = cmbTipo.getValue().toUpperCase();

					if (txtPesquisa.getText().isEmpty()) {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.categoria"));
						tbVeiculo.setItems(lista);
					} else {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.categoria", pesquisa));
						tbVeiculo.setItems(lista);
					}

					break;

				case "Marca":
					pesquisa = txtPesquisa.getText();
					tipoBusca = cmbTipo.getValue().toUpperCase();

					if (txtPesquisa.getText().isEmpty()) {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.marca"));
						tbVeiculo.setItems(lista);
					} else {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.marca", pesquisa));
						tbVeiculo.setItems(lista);
					}

					break;
				case "Placa":
					pesquisa = txtPesquisa.getText();
					tipoBusca = cmbTipo.getValue().toUpperCase();

					if (txtPesquisa.getText().isEmpty()) {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.placa"));
						tbVeiculo.setItems(lista);
					} else {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.placa", pesquisa));
						tbVeiculo.setItems(lista);
					}

					break;
				case "Renavam":
					pesquisa = txtPesquisa.getText();
					tipoBusca = cmbTipo.getValue().toUpperCase();

					if (txtPesquisa.getText().isEmpty()) {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.renavam"));
						tbVeiculo.setItems(lista);
					} else {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.renavam", pesquisa));
						tbVeiculo.setItems(lista);
					}

					break;

				case "Todos":

					tipoBusca = cmbTipo.getValue().toUpperCase();
					System.out.println("tipo de busca : " + tipoBusca);

					ObservableList<Veiculo> list = FXCollections
							.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.data_entrada"));
					tbVeiculo.setItems(list);

					break;

				default:
					break;
				}

				lblQtdeVeiculos.setText(veiculoDAO.getQtdeVeiculos(tipoBusca));
				lblValorTotalSomaVeiculos
						.setText(decimal.format(veiculoDAO.getValorTotalSoma(tipoBusca, "valor_venda")));
				if (verificaPermissaoUsuario(telaLogin.permissaoUsuario())) {

					lblValorTotalCompraVeiculos
							.setText(decimal.format(veiculoDAO.getValorTotalSoma(tipoBusca, "valor_entrada")));
				} else {
					lblValorTotalCompraVeiculos.setText("");
				}
				lblValorTotalFipeVeiculos
						.setText(decimal.format(veiculoDAO.getValorTotalSoma(tipoBusca, "valor_fipe")));

				break;

			case "Venda":
				switch (cmb) {
				case "Nome Veiculo":
					pesquisa = txtPesquisa.getText();
					tipoBusca = cmbTipo.getValue().toUpperCase();

					if (txtPesquisa.getText().isEmpty()) {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.veiculo"));
						tbVeiculo.setItems(lista);
					} else {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.veiculo", pesquisa));
						tbVeiculo.setItems(lista);
					}

					break;

				case "Categoria Veiculos":

					pesquisa = txtPesquisa.getText();
					tipoBusca = cmbTipo.getValue().toUpperCase();

					if (txtPesquisa.getText().isEmpty()) {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodos("v.categoria"));
						tbVeiculo.setItems(lista);
					} else {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodos("v.categoria", pesquisa));
						tbVeiculo.setItems(lista);
					}

					break;

				case "Marca":
					pesquisa = txtPesquisa.getText();
					tipoBusca = cmbTipo.getValue().toUpperCase();

					if (txtPesquisa.getText().isEmpty()) {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.marca"));
						tbVeiculo.setItems(lista);
					} else {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.marca", pesquisa));
						tbVeiculo.setItems(lista);
					}

					break;
				case "Placa":
					pesquisa = txtPesquisa.getText();
					tipoBusca = cmbTipo.getValue().toUpperCase();

					if (txtPesquisa.getText().isEmpty()) {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.placa"));
						tbVeiculo.setItems(lista);
					} else {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.placa", pesquisa));
						tbVeiculo.setItems(lista);
					}

					break;
				case "Renavam":
					pesquisa = txtPesquisa.getText();
					tipoBusca = cmbTipo.getValue().toUpperCase();

					if (txtPesquisa.getText().isEmpty()) {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.renavam"));
						tbVeiculo.setItems(lista);
					} else {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.renavam", pesquisa));
						tbVeiculo.setItems(lista);
					}

					break;

				case "Todos":

					tipoBusca = cmbTipo.getValue().toUpperCase();

					ObservableList<Veiculo> lista = FXCollections
							.observableArrayList(veiculoDAO.listarTodosNome("VENDA", "v.data_entrada"));
					tbVeiculo.setItems(lista);

					break;

				default:
					break;
				}

				lblQtdeVeiculos.setText(veiculoDAO.getQtdeVeiculos(tipoBusca));
				lblValorTotalSomaVeiculos
						.setText(decimal.format(veiculoDAO.getValorTotalSoma(tipoBusca, "valor_venda")));
				if (verificaPermissaoUsuario(telaLogin.permissaoUsuario())) {

					lblValorTotalCompraVeiculos
							.setText(decimal.format(veiculoDAO.getValorTotalSoma(tipoBusca, "valor_entrada")));
				} else {
					lblValorTotalCompraVeiculos.setText("");
				}
				lblValorTotalFipeVeiculos
						.setText(decimal.format(veiculoDAO.getValorTotalSoma(tipoBusca, "valor_fipe")));

				break;

			case "Consignado":
				switch (cmb) {
				case "Nome Veiculo":
					pesquisa = txtPesquisa.getText();
					tipoBusca = cmbTipo.getValue().toUpperCase();

					if (txtPesquisa.getText().isEmpty()) {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.veiculo"));
						tbVeiculo.setItems(lista);
					} else {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.veiculo", pesquisa));
						tbVeiculo.setItems(lista);
					}

					break;

				case "Categoria Veiculos":

					pesquisa = txtPesquisa.getText();
					tipoBusca = cmbTipo.getValue().toUpperCase();

					if (txtPesquisa.getText().isEmpty()) {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodos("v.categoria"));
						tbVeiculo.setItems(lista);
					} else {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodos("v.categoria", pesquisa));
						tbVeiculo.setItems(lista);
					}

					break;

				case "Marca":
					pesquisa = txtPesquisa.getText();
					tipoBusca = cmbTipo.getValue().toUpperCase();

					if (txtPesquisa.getText().isEmpty()) {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.marca"));
						tbVeiculo.setItems(lista);
					} else {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.marca", pesquisa));
						tbVeiculo.setItems(lista);
					}

					break;
				case "Placa":
					pesquisa = txtPesquisa.getText();
					tipoBusca = cmbTipo.getValue().toUpperCase();

					if (txtPesquisa.getText().isEmpty()) {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.placa"));
						tbVeiculo.setItems(lista);
					} else {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.placa", pesquisa));
						tbVeiculo.setItems(lista);
					}

					break;
				case "Renavam":
					pesquisa = txtPesquisa.getText();
					tipoBusca = cmbTipo.getValue().toUpperCase();

					if (txtPesquisa.getText().isEmpty()) {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.renavam"));
						tbVeiculo.setItems(lista);
					} else {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodosNome(tipoBusca, "v.renavam", pesquisa));
						tbVeiculo.setItems(lista);
					}

					break;

				case "Todos":

					tipoBusca = cmbTipo.getValue().toUpperCase();

					ObservableList<Veiculo> lista = FXCollections
							.observableArrayList(veiculoDAO.listarTodosNome("CONSIGNADO", "v.data_entrada"));
					tbVeiculo.setItems(lista);

					break;

				default:
					break;
				}

				lblQtdeVeiculos.setText(veiculoDAO.getQtdeVeiculos(tipoBusca));
				lblValorTotalSomaVeiculos
						.setText(decimal.format(veiculoDAO.getValorTotalSoma(tipoBusca, "valor_venda")));
				if (verificaPermissaoUsuario(telaLogin.permissaoUsuario())) {

					lblValorTotalCompraVeiculos
							.setText(decimal.format(veiculoDAO.getValorTotalSoma(tipoBusca, "valor_entrada")));
				} else {
					lblValorTotalCompraVeiculos.setText("");
				}
				lblValorTotalFipeVeiculos
						.setText(decimal.format(veiculoDAO.getValorTotalSoma(tipoBusca, "valor_fipe")));
				break;

			case "Todos":
				switch (cmb) {
				case "Nome Veiculo":
					pesquisa = txtPesquisa.getText();
					tipoBusca = cmbTipo.getValue().toUpperCase();

					if (txtPesquisa.getText().isEmpty()) {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodos("v.veiculo"));
						tbVeiculo.setItems(lista);
					} else {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodos("v.veiculo", pesquisa));
						tbVeiculo.setItems(lista);
					}

					break;

				case "Categoria Veiculos":

					pesquisa = txtPesquisa.getText();
					tipoBusca = cmbTipo.getValue().toUpperCase();

					if (txtPesquisa.getText().isEmpty()) {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodos("v.categoria"));
						tbVeiculo.setItems(lista);
					} else {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodos("v.categoria", pesquisa));
						tbVeiculo.setItems(lista);
					}

					break;

				case "Marca":

					pesquisa = txtPesquisa.getText();
					tipoBusca = cmbTipo.getValue().toUpperCase();

					if (txtPesquisa.getText().isEmpty()) {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodos("v.marca"));
						tbVeiculo.setItems(lista);
					} else {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodos("v.marca", pesquisa));
						tbVeiculo.setItems(lista);
					}

					break;
				case "Placa":

					pesquisa = txtPesquisa.getText();
					tipoBusca = cmbTipo.getValue().toUpperCase();

					if (txtPesquisa.getText().isEmpty()) {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodos("v.placa"));
						tbVeiculo.setItems(lista);
					} else {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodos("v.placa", pesquisa));
						tbVeiculo.setItems(lista);
					}

					break;
				case "Renavam":

					pesquisa = txtPesquisa.getText();
					tipoBusca = cmbTipo.getValue().toUpperCase();

					if (txtPesquisa.getText().isEmpty()) {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodos("v.renavam"));
						tbVeiculo.setItems(lista);
					} else {
						ObservableList<Veiculo> lista = FXCollections
								.observableArrayList(veiculoDAO.listarTodos("v.renavam", pesquisa));
						tbVeiculo.setItems(lista);
					}

					break;
				case "Todos":

					ObservableList<Veiculo> lista = FXCollections
							.observableArrayList(veiculoDAO.listarTodos("v.data_entrada"));
					tbVeiculo.setItems(lista);

					break;
				default:

					break;

				}

				lblQtdeVeiculos.setText(veiculoDAO.getQtdeVeiculos());
				lblValorTotalSomaVeiculos.setText(decimal.format(veiculoDAO.getValorTotalSoma("valor_venda")));
				lblValorTotalFipeVeiculos.setText(decimal.format(veiculoDAO.getValorTotalSoma("valor_fipe")));
				lblValorTotalCompraVeiculos.setText(decimal.format(veiculoDAO.getValorTotalSoma("valor_entrada")));
				break;

			default:
				System.out.println("selecionou outro ao inves de todos!");
				break;
			}

		}

		conexao.fecharConexao();
	}

	private void excluir() {

		ButtonType sim = new ButtonType("SIM", ButtonBar.ButtonData.YES);
		ButtonType nao = new ButtonType("NÃO", ButtonBar.ButtonData.NO);
		Alert alerta = new Alert(AlertType.INFORMATION, "Você quer mesmo excluir os dados do veiculo selecionado ?",
				sim, nao);

		Optional<ButtonType> result = alerta.showAndWait();

		if (result.get().equals(sim)) {
			this.conexao = new Conexao();
			this.veiculoDAO = new VeiculoDAO(conexao);
			boolean sucesso = veiculoDAO.apagarVeiculo(veiculo_id);
			boolean suce = veiculoDAO.apagarDespesaVeiculo(veiculo_id);
			System.out.println("sucesso apagar despesaVeiculo : " + suce);
			conexao.fecharConexao();
			if (sucesso) {
				Alert dlg = new Alert(AlertType.INFORMATION);
				dlg.setContentText("Veiculo excluido com sucesso!");
				dlg.showAndWait();
				try {
					pesquisar();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public void imprimirLista() throws JRException, SQLException {

		String cmb = null, tipo = null, tipoBusca = null, pesquisa = null;
		Veiculo veiculo = null;
		ArrayList<Veiculo> lista = new ArrayList<>();
		String nomeVeiculo = null, placa = null, renavam = null, cor = null, dataEntrada = null, dataVenda = null,
				valorVenda = null, situacao = null, marca = null, categoria = null, financiamento = null,
				sinistroRs = null, km = null, valorFipeFormatado = null, ano = null;
		String qtde = null;
		String valorTotalSomaVeiculos = null, valorTotalCompraVeiculos = null, valorTotalFipeVeiculos = null;
		Integer id = null;

		Relatorio relatorio = new Relatorio();

		if (cmbTipo.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbTipo);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione o campo TIPO!");
			dlg.showAndWait();
			cmbTipo.requestFocus();
			return;

		} else {

			tipo = cmbTipo.getValue().toString();

		}

		if (cmbBuscar.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbBuscar);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione o campo BUSCAR!");
			dlg.showAndWait();
			cmbBuscar.requestFocus();
			return;

		} else {
			cmb = cmbBuscar.getValue().toString();
			this.conexao = new Conexao();
			this.veiculoDAO = new VeiculoDAO(conexao);

			switch (tipo) {
			case "Estoque":
				System.out.println("entrou no tipo ESTOQUE");
				pesquisa = txtPesquisa.getText();
				switch (cmb) {
				case "Nome Veiculo":
					pesquisa = txtPesquisa.getText();
					if (txtPesquisa.getText().isEmpty()) {
						tipoBusca = cmbTipo.getValue().toUpperCase();

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.veiculo")) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					} else {

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.veiculo", pesquisa)) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					}

					break;

				case "Categoria Veiculos":
					pesquisa = txtPesquisa.getText();
					if (txtPesquisa.getText().isEmpty()) {
						tipoBusca = cmbTipo.getValue().toUpperCase();

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.categoria")) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					} else {

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.categoria", pesquisa)) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					}

					break;

				case "Marca":
					pesquisa = txtPesquisa.getText();
					if (txtPesquisa.getText().isEmpty()) {
						tipoBusca = cmbTipo.getValue().toUpperCase();

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.marca")) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					} else {

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.marca", pesquisa)) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					}

					break;
				case "Placa":

					if (txtPesquisa.getText().isEmpty()) {
						tipoBusca = cmbTipo.getValue().toUpperCase();

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.placa")) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					} else {

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.placa", pesquisa)) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					}

					break;
				case "Renavam":

					if (txtPesquisa.getText().isEmpty()) {
						tipoBusca = cmbTipo.getValue().toUpperCase();

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.renavam")) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					} else {

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.renavam", pesquisa)) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					}

					break;

				case "Todos":

					for (Veiculo v : veiculoDAO.listarTodos("v.situacao", "ESTOQUE")) {
						id = v.getId();
						marca = v.getMarca();
						nomeVeiculo = v.getVeiculo();
						placa = v.getPlaca();
						ano = v.getAno_modelo();
						cor = v.getCor();
						renavam = v.getRenavam();
						dataEntrada = v.getDataEntradaFormatada();
						dataVenda = v.getDataVendaFormatada();
						valorVenda = v.getValorVendaFormatado();
						situacao = v.getSituacao();
						categoria = v.getCategoria();
						financiamento = v.getFinanciamento();
						sinistroRs = v.getSinistroRs();
						km = v.getKm();
						valorFipeFormatado = v.getValorFipeFormatado();

						veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao, dataEntrada,
								null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs, financiamento,
								categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
								valorTotalFipeVeiculos);

						lista.add(veiculo);
					}

					break;

				default:
					break;
				}

				qtde = veiculoDAO.getQtdeVeiculos("ESTOQUE");
				valorTotalSomaVeiculos = decimal.format(veiculoDAO.getValorTotalSoma("ESTOQUE", "valor_venda"));
				if (verificaPermissaoUsuario(telaLogin.permissaoUsuario())) {

					valorTotalCompraVeiculos = decimal.format(veiculoDAO.getValorTotalSoma("ESTOQUE", "valor_entrada"));
				} else {
					valorTotalCompraVeiculos = new String("0,00");
				}
				valorTotalFipeVeiculos = decimal.format(veiculoDAO.getValorTotalSoma("ESTOQUE", "valor_fipe"));
				Veiculo veiculo2 = new Veiculo(qtde, valorTotalSomaVeiculos, valorTotalCompraVeiculos,
						valorTotalFipeVeiculos);
				lista.add(veiculo2);

				break;

			case "Venda":
				System.out.println("entrou no tipo VENDA");
				switch (cmb) {

				case "Nome Veiculo":
					pesquisa = txtPesquisa.getText();
					if (txtPesquisa.getText().isEmpty()) {
						tipoBusca = cmbTipo.getValue().toUpperCase();

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.veiculo")) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					} else {

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.veiculo", pesquisa)) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					}

					break;

				case "Categoria Veiculos":
					pesquisa = txtPesquisa.getText();
					if (txtPesquisa.getText().isEmpty()) {
						tipoBusca = cmbTipo.getValue().toUpperCase();

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.categoria")) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					} else {

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.categoria", pesquisa)) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					}

					break;

				case "Marca":
					pesquisa = txtPesquisa.getText();
					if (txtPesquisa.getText().isEmpty()) {
						tipoBusca = cmbTipo.getValue().toUpperCase();

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.marca")) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					} else {

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.marca", pesquisa)) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					}

					break;
				case "Placa":

					if (txtPesquisa.getText().isEmpty()) {
						tipoBusca = cmbTipo.getValue().toUpperCase();

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.placa")) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					} else {

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.placa", pesquisa)) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					}

					break;

				case "Renavam":

					if (txtPesquisa.getText().isEmpty()) {
						tipoBusca = cmbTipo.getValue().toUpperCase();

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.renavam")) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					} else {

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.renavam", pesquisa)) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					}

					break;

				case "Todos":

					for (Veiculo v : veiculoDAO.listarTodos("v.situacao", "VENDA")) {
						id = v.getId();
						marca = v.getMarca();
						nomeVeiculo = v.getVeiculo();
						placa = v.getPlaca();
						ano = v.getAno_modelo();
						cor = v.getCor();
						renavam = v.getRenavam();
						dataEntrada = v.getDataEntradaFormatada();
						dataVenda = v.getDataVendaFormatada();
						valorVenda = v.getValorVendaFormatado();
						situacao = v.getSituacao();
						categoria = v.getCategoria();
						financiamento = v.getFinanciamento();
						sinistroRs = v.getSinistroRs();
						km = v.getKm();
						valorFipeFormatado = v.getValorFipeFormatado();

						veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao, dataEntrada,
								null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs, financiamento,
								categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
								valorTotalFipeVeiculos);

						lista.add(veiculo);
					}

					break;

				default:
					break;
				}

				qtde = veiculoDAO.getQtdeVeiculos("VENDA");
				valorTotalSomaVeiculos = decimal.format(veiculoDAO.getValorTotalSoma("VENDA", "valor_venda"));
				if (verificaPermissaoUsuario(telaLogin.permissaoUsuario())) {

					valorTotalCompraVeiculos = decimal.format(veiculoDAO.getValorTotalSoma("VENDA", "valor_entrada"));
				} else {
					valorTotalCompraVeiculos = new String("0,00");
				}
				valorTotalFipeVeiculos = decimal.format(veiculoDAO.getValorTotalSoma("VENDA", "valor_fipe"));
				Veiculo veicul = new Veiculo(qtde, valorTotalSomaVeiculos, valorTotalCompraVeiculos,
						valorTotalFipeVeiculos);
				lista.add(veicul);
				break;

			case "Consignado":
				System.out.println("entrou no tipo CONSIGNADO");
				switch (cmb) {

				case "Nome Veiculo":
					pesquisa = txtPesquisa.getText();
					if (txtPesquisa.getText().isEmpty()) {
						tipoBusca = cmbTipo.getValue().toUpperCase();

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.veiculo")) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					} else {

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.veiculo", pesquisa)) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					}

					break;

				case "Categoria Veiculos":
					pesquisa = txtPesquisa.getText();
					if (txtPesquisa.getText().isEmpty()) {
						tipoBusca = cmbTipo.getValue().toUpperCase();

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.categoria")) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					} else {

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.categoria", pesquisa)) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					}

					break;

				case "Marca":
					pesquisa = txtPesquisa.getText();
					if (txtPesquisa.getText().isEmpty()) {
						tipoBusca = cmbTipo.getValue().toUpperCase();

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.marca")) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);
							lista.add(veiculo);
						}

					} else {

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.marca", pesquisa)) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					}

					break;
				case "Placa":

					if (txtPesquisa.getText().isEmpty()) {
						tipoBusca = cmbTipo.getValue().toUpperCase();

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.placa")) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					} else {

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.placa", pesquisa)) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					}

					break;

				case "Renavam":

					if (txtPesquisa.getText().isEmpty()) {
						tipoBusca = cmbTipo.getValue().toUpperCase();

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.renavam")) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					} else {

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.renavam", pesquisa)) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					}

					break;

				case "Todos":
					System.out.println("passou aqui a listagem");

					for (Veiculo v : veiculoDAO.listarTodos("v.situacao", "CONSIGNADO")) {
						id = v.getId();
						marca = v.getMarca();
						nomeVeiculo = v.getVeiculo();
						placa = v.getPlaca();
						ano = v.getAno_modelo();
						cor = v.getCor();
						renavam = v.getRenavam();
						dataEntrada = v.getDataEntradaFormatada();
						dataVenda = v.getDataVendaFormatada();
						valorVenda = v.getValorVendaFormatado();
						situacao = v.getSituacao();
						categoria = v.getCategoria();
						financiamento = v.getFinanciamento();
						sinistroRs = v.getSinistroRs();
						km = v.getKm();
						valorFipeFormatado = v.getValorFipeFormatado();

						veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao, dataEntrada,
								null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs, financiamento,
								categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
								valorTotalFipeVeiculos);

						lista.add(veiculo);
					}

					break;

				default:
					break;

				}

				qtde = veiculoDAO.getQtdeVeiculos("CONSIGNADO");
				valorTotalSomaVeiculos = decimal.format(veiculoDAO.getValorTotalSoma("CONSIGNADO", "valor_venda"));
				if (verificaPermissaoUsuario(telaLogin.permissaoUsuario())) {

					valorTotalCompraVeiculos = decimal
							.format(veiculoDAO.getValorTotalSoma("CONSIGNADO", "valor_entrada"));
				} else {
					valorTotalCompraVeiculos = new String("0,00");
				}
				valorTotalFipeVeiculos = decimal.format(veiculoDAO.getValorTotalSoma("CONSIGNADO", "valor_fipe"));
				Veiculo veicu = new Veiculo(qtde, valorTotalSomaVeiculos, valorTotalCompraVeiculos,
						valorTotalFipeVeiculos);
				lista.add(veicu);
				break;

			case "Todos":
				System.out.println("entrou no tipo TODOS");
				switch (cmb) {
				case "Nome Veiculo":
					pesquisa = txtPesquisa.getText();
					if (txtPesquisa.getText().isEmpty()) {
						tipoBusca = cmbTipo.getValue().toUpperCase();

						for (Veiculo v : veiculoDAO.listarTodos("v.veiculo")) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					} else {

						for (Veiculo v : veiculoDAO.listarTodos("v.veiculo", pesquisa)) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					}


					break;

				case "Categoria Veiculos":
					pesquisa = txtPesquisa.getText();
					if (txtPesquisa.getText().isEmpty()) {
						tipoBusca = cmbTipo.getValue().toUpperCase();

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.categoria")) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					} else {

						for (Veiculo v : veiculoDAO.listarTodosNome(tipoBusca, "v.categoria", pesquisa)) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					}

					break;

				case "Marca":
					pesquisa = txtPesquisa.getText();
					if (txtPesquisa.getText().isEmpty()) {
						tipoBusca = cmbTipo.getValue().toUpperCase();

						for (Veiculo v : veiculoDAO.listarTodos("v.marca")) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					} else {

						for (Veiculo v : veiculoDAO.listarTodos("v.marca", pesquisa)) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					}

					break;
				case "Placa":

					if (txtPesquisa.getText().isEmpty()) {
						tipoBusca = cmbTipo.getValue().toUpperCase();

						for (Veiculo v : veiculoDAO.listarTodos("v.placa")) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					} else {

						for (Veiculo v : veiculoDAO.listarTodos("v.placa", pesquisa)) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					}

					break;
				case "Renavam":

					if (txtPesquisa.getText().isEmpty()) {
						tipoBusca = cmbTipo.getValue().toUpperCase();

						for (Veiculo v : veiculoDAO.listarTodos("v.renavam")) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					} else {

						for (Veiculo v : veiculoDAO.listarTodos("v.renavam", pesquisa)) {
							id = v.getId();
							marca = v.getMarca();
							nomeVeiculo = v.getVeiculo();
							placa = v.getPlaca();
							ano = v.getAno_modelo();
							cor = v.getCor();
							renavam = v.getRenavam();
							dataEntrada = v.getDataEntradaFormatada();
							dataVenda = v.getDataVendaFormatada();
							valorVenda = v.getValorVendaFormatado();
							situacao = v.getSituacao();
							categoria = v.getCategoria();
							financiamento = v.getFinanciamento();
							sinistroRs = v.getSinistroRs();
							km = v.getKm();
							valorFipeFormatado = v.getValorFipeFormatado();

							veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao,
									dataEntrada, null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs,
									financiamento, categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
									valorTotalFipeVeiculos);

							lista.add(veiculo);
						}

					}

					break;

				case "Todos":

					for (Veiculo v : veiculoDAO.listarTodos("v.data_entrada")) {
						id = v.getId();
						marca = v.getMarca();
						nomeVeiculo = v.getVeiculo();
						placa = v.getPlaca();
						ano = v.getAno_modelo();
						cor = v.getCor();
						renavam = v.getRenavam();
						dataEntrada = v.getDataEntradaFormatada();
						dataVenda = v.getDataVendaFormatada();
						valorVenda = v.getValorVendaFormatado();
						situacao = v.getSituacao();
						categoria = v.getCategoria();
						financiamento = v.getFinanciamento();
						sinistroRs = v.getSinistroRs();
						km = v.getKm();
						valorFipeFormatado = v.getValorFipeFormatado();

						veiculo = new Veiculo(id, nomeVeiculo, marca, ano, renavam, placa, cor, situacao, dataEntrada,
								null, valorVenda, dataVenda, null, null, null, null, null, sinistroRs, financiamento,
								categoria, km, null, valorFipeFormatado, valorTotalCompraVeiculos,
								valorTotalFipeVeiculos);

						lista.add(veiculo);
					}

					break;

				default:
					break;
				}

				qtde = veiculoDAO.getQtdeVeiculos();
				valorTotalSomaVeiculos = decimal.format(veiculoDAO.getValorTotalSoma("valor_venda"));
				valorTotalCompraVeiculos = decimal.format(veiculoDAO.getValorTotalSoma("valor_entrada"));
				valorTotalFipeVeiculos = decimal.format(veiculoDAO.getValorTotalSoma("valor_fipe"));

				conexao.fecharConexao();
				Veiculo veiculos = new Veiculo(qtde, valorTotalSomaVeiculos, valorTotalCompraVeiculos,
						valorTotalFipeVeiculos);
				lista.add(veiculos);
				break;

			default:
				break;
			}

			relatorio.gerarRelatorio(lista, "RelatorioDados");
		}

	}

	public void imprimirDadosVeiculo() throws JRException, SQLException {
		ArrayList<HashMap<String, String>> listaDadosVeiculo = new ArrayList<>();
		HashMap<String, String> mapa = new HashMap<>();
		Relatorio relatorio = new Relatorio();
		if (tbVeiculo.getSelectionModel().getSelectedItem() == null) {
			ValidationFields.checkEmptyFields(btnImprimirDados);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("Selecione o veiculo que deseja imprimir os detalhes!");
			dlg.showAndWait();
			btnImprimirDados.requestFocus();
			return;

		} else {
			this.conexao = new Conexao();
			this.veiculoDAO = new VeiculoDAO(conexao);
			for (Veiculo v : veiculoDAO.listarTodosId(veiculo_id)) {
				mapa.put("veiculo", v.getVeiculo());
				mapa.put("placa", v.getPlaca());
				mapa.put("cor", v.getCor());
				mapa.put("renavam", v.getRenavam());
				mapa.put("valorVenda", v.getValorVendaFormatado());
				mapa.put("dataEntrada", v.getDataEntradaFormatada());
				mapa.put("sinistroRs", v.getSinistroRs());
				mapa.put("categoria", v.getCategoria());
				mapa.put("financiamento", v.getFinanciamento());
				mapa.put("marca", v.getMarca());
				mapa.put("km", v.getKm());

			}

			Date dataEntradaVeiculo = veiculoDAO.getDataVeiculo(veiculo_id, "data_entrada");
			Date dataVenda = null;
			Calendar cal = Calendar.getInstance();
			cal.setTime(dataEntradaVeiculo);
			Integer diaEntrada = cal.get(Calendar.DAY_OF_YEAR);
			System.out.println("pegou o dia do ano da dataEntrada : " + diaEntrada);
			String diasPatio = null;
			if (verificaDataVendaVeiculo(veiculo_id) == null) {
				Calendar ca = Calendar.getInstance();
				Integer diaPatio = ca.get(Calendar.DAY_OF_YEAR);
				System.out.println("pegou o dia do ano da data atual : " + diaPatio);
				Integer calcula = diaPatio - diaEntrada;
				diasPatio = String.valueOf(calcula);
			} else {
				dataVenda = veiculoDAO.getDataVeiculo(veiculo_id, "data_venda");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dataVenda);
				Integer diaVenda = calendar.get(Calendar.DAY_OF_YEAR);
				System.out.println("pegou o dia do ano da data de venda : " + diaVenda);
				Integer calcular = diaVenda - diaEntrada;
				diasPatio = String.valueOf(calcular);
			}
			BigDecimal valorEntrada = veiculoDAO.getValorEntrada(veiculo_id);
			BigDecimal valorDespesa = veiculoDAO.getTotalDespesaVeiculo(veiculo_id);
			String custoTotal = decimal.format(valorDespesa);
			String valorEntradaVeiculo = decimal.format(valorEntrada);
			BigDecimal calculoCusto = valorEntrada.add(valorDespesa);
			String custoTotalVeiculo = decimal.format(calculoCusto);

			VeiculoRelatorio ve = new VeiculoRelatorio(diasPatio, custoTotal, valorEntradaVeiculo, custoTotalVeiculo);
			mapa.put("diaPatio", ve.getDiaPatio());
			mapa.put("totalDespesas", ve.getTotalDespesas());
			mapa.put("valorEntrada", ve.getValorEntrada());
			mapa.put("custoTotalVeiculo", ve.getCustoTotalVeiculo());

			for (VeiculoRelatorio veis : veiculoDAO.listarDadosVenda(veiculo_id)) {
				mapa.put("vendaCliente", veis.getVendaCliente());
				mapa.put("vendaDataVenda", veis.getVendaDataVenda());
				mapa.put("vendaLucroVenda", veis.getVendaLucroVenda());
				mapa.put("vendaTipoPagamento", veis.getVendaTipoPagamento());
				mapa.put("vendaParcela", veis.getVendaParcela());
				mapa.put("vendaValorParcela", veis.getVendaValorParcela());
				mapa.put("garantia", veis.getGarantia());
				mapa.put("observacao", veis.getObservacao());
				mapa.put("dataGarantiaFormatada", veis.getDataGarantiaFormatada());

			}

			listaDadosVeiculo.add(mapa);
			conexao.fecharConexao();
			relatorio.gerarRelatorio(listaDadosVeiculo, "RelatorioLista");

		}

	}

	public void listarDadosVenda() throws SQLException {
		this.conexao = new Conexao();
		this.pagamentoVeiculoDAO = new PagamentoVeiculoDAO(conexao);
		boolean visualizaDados = verificaPermissaoUsuario(telaLogin.permissaoUsuario());
		lblVendaDataVenda.setText("");
		lblVendaLucroVenda.setText("");
		lblVendaCliente.setText("");
		lblVendaTipoPagamento.setText("");
		lblVendaParcela.setText("");
		lblVendaValorParcela.setText("");
		lblVendaGarantia.setText("");
		lblVendaObservacao.setText("");
		lblVencimentoGarantia.setText("");
		if (verificaVendaVeiculo(veiculo_id)) {
			for (PagamentoVeiculo p : pagamentoVeiculoDAO.listarTodosId(getIdVendaVeiculo(veiculo_id))) {
				lblVendaDataVenda.setText(p.getDataVendaFormatada());
				if (visualizaDados) {
					lblVendaLucroVenda.setText(p.getLucroVendaFormatado());
				} else {
					lblVendaLucroVenda.setText("");
				}
				lblVendaCliente.setText(p.getNomeCliente());
				lblVendaTipoPagamento.setText(p.getTipoPagamento());
				lblVendaParcela.setText(p.getParcela());
				lblVendaValorParcela.setText(p.getValorParcelaFormatado());
				lblVendaGarantia.setText(p.getGarantia());
				lblVendaObservacao.setText(p.getObservacao());
				lblVencimentoGarantia.setText(p.getDataGarantiaFormatada());
			}
		}
		conexao.fecharConexao();
	}

	public void listarDadosVeiculo() {
		this.conexao = new Conexao();
		this.veiculoDAO = new VeiculoDAO(conexao);
		for (Veiculo v : veiculoDAO.listarTodosId(veiculo_id)) {
			lblDadosVeiculo.setText(v.getVeiculo());
			lblDadosPlaca.setText(v.getPlaca());
			lblDadosCor.setText(v.getCor());
			lblKm.setText(v.getKm());
			lblAno.setText(v.getAno_modelo());
			lblDadosRenavam.setText(v.getRenavam());
			lblDadosDataEntrada.setText(v.getDataEntradaFormatada());
			lblDadosValorVenda.setText(v.getValorVendaFormatado());
			lblLeilaoRs.setText(v.getSinistroRs());
			lblFinanciamento.setText(v.getFinanciamento());
			lblValorFipe.setText(v.getValorFipeFormatado());
		}
		conexao.fecharConexao();
	}

	public void ListarDadosDespesas() throws SQLException {
		this.conexao = new Conexao();
		this.veiculoDAO = new VeiculoDAO(conexao);
		lblCustoTotalDespesa.setText("");
		lblCustoValorEntrada.setText("");
		lblCustoTotalVeiculo.setText("");
		boolean visualizaDados = verificaPermissaoUsuario(telaLogin.permissaoUsuario());
		Integer diasContado = null;

		Date dataEntrada = veiculoDAO.getDataVeiculo(veiculo_id, "data_entrada");
		DateTime dateTimeEntrada = new DateTime(dataEntrada.getTime());
		Date dataVenda = null;
		Date dataHoje = new Date();
		DateTime dateTimeHoje = new DateTime(dataHoje.getTime());
		DateTime dateTimeVenda = null;
		if (verificaDataVendaVeiculo(veiculo_id) == null) {
			diasContado = Days.daysBetween(dateTimeEntrada, dateTimeHoje).getDays();
			lblCustoDiaPatio.setText(String.valueOf(diasContado));
		} else {

			dataVenda = veiculoDAO.getDataVeiculo(veiculo_id, "data_venda");
			dateTimeVenda = new DateTime(dataVenda.getTime());
			diasContado = Days.daysBetween(dateTimeEntrada, dateTimeVenda).getDays();
			lblCustoDiaPatio.setText(String.valueOf(diasContado));
		}
		BigDecimal valorEntrada = veiculoDAO.getValorEntrada(veiculo_id);
		BigDecimal valorDespesa = veiculoDAO.getTotalDespesaVeiculo(veiculo_id);
		if (visualizaDados) {
			lblCustoTotalDespesa.setText(decimal.format(valorDespesa));
			lblCustoValorEntrada.setText(decimal.format(valorEntrada));
			BigDecimal calculoCusto = valorEntrada.add(valorDespesa);
			lblCustoTotalVeiculo.setText(decimal.format(calculoCusto));
		} else {
			lblCustoTotalDespesa.setText("");
			lblCustoValorEntrada.setText("");
			lblCustoTotalVeiculo.setText("");
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

	public void comboBoxBusca() {

		String nome = new String("Nome Veiculo");
		String categoria = new String("Categoria Veiculos");
		String cpfCnpj = new String("Marca");
		String nomes = new String("Placa");
		String nomess = new String("Renavam");
		String todos = new String("Todos");

		ArrayList<String> listaBusca = new ArrayList<>();
		listaBusca.add(nome);
		listaBusca.add(categoria);
		listaBusca.add(cpfCnpj);
		listaBusca.add(nomes);
		listaBusca.add(nomess);
		listaBusca.add(todos);

		ObservableList<String> lista = FXCollections.observableArrayList(listaBusca);
		cmbBuscar.setItems(lista);
		lista.toString();
	}

	public void comboBoxTipo() {

		String nome = new String("Estoque");
		String cpfCnpj = new String("Venda");
		String nomess = new String("Consignado");
		String nomes = new String("Todos");

		ArrayList<String> listaBusca = new ArrayList<>();
		listaBusca.add(nome);
		listaBusca.add(cpfCnpj);
		listaBusca.add(nomess);
		listaBusca.add(nomes);

		ObservableList<String> lista = FXCollections.observableArrayList(listaBusca);
		cmbTipo.setItems(lista);
		lista.toString();
	}

	public Integer getIdVeiculoFipe(Integer veiculo_id) throws SQLException {
		Integer result = null;

		String sql = "SELECT * FROM veiculo WHERE id = " + veiculo_id + "";

		PreparedStatement cmd = conexao.getConexao().prepareStatement(sql);
		ResultSet rs = cmd.executeQuery();
		if (rs.next()) {
			result = rs.getInt("veiculo_fipe_id");

		}

		return result;
	}

	public Integer getIdVendaVeiculo(Integer veiculo_id) throws SQLException {
		Integer result = null;

		String sql = "SELECT * FROM pagamento_veiculo WHERE veiculo_id = " + veiculo_id + "";

		PreparedStatement cmd = conexao.getConexao().prepareStatement(sql);
		ResultSet rs = cmd.executeQuery();
		if (rs.next()) {
			result = rs.getInt("id");

		}

		return result;
	}

	public Date verificaDataVendaVeiculo(Integer veiculo_id) throws SQLException {
		Date result = null;

		String sql = "SELECT * FROM veiculo WHERE id = " + veiculo_id + "";

		PreparedStatement cmd = conexao.getConexao().prepareStatement(sql);
		ResultSet rs = cmd.executeQuery();
		if (rs.next()) {
			result = rs.getDate("data_venda");

		}

		return result;
	}

	public boolean verificaVendaVeiculo(Integer veiculo_id) throws SQLException {
		boolean result = false;

		String sql = "SELECT * FROM pagamento_veiculo WHERE veiculo_id = " + veiculo_id + "";

		PreparedStatement cmd = conexao.getConexao().prepareStatement(sql);
		ResultSet rs = cmd.executeQuery();
		if (rs.next()) {
			result = true;

		}

		return result;
	}

	public boolean verificaVeiculoVendido(Integer veiculo_id) throws SQLException {
		boolean result = false;

		String sql = "SELECT * FROM veiculo WHERE id = " + veiculo_id + " AND situacao = 'VENDA'";

		PreparedStatement cmd = conexao.getConexao().prepareStatement(sql);
		ResultSet rs = cmd.executeQuery();
		if (rs.next()) {
			result = true;

		}

		return result;
	}

	public void atualizaHoras() {
		Date dataHoje = new Date();
		lblHora.setText(formatador.format(dataHoje));
	}

	public void textFieldInicial(TextField tf) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				tf.requestFocus();
			}
		});
	}

	public boolean verificaPermissaoUsuario(String nome) throws SQLException {
		boolean result = false;

		String sql = "SELECT * FROM usuario WHERE nome = '" + nome + "' AND permissao = 'ADMINISTRADOR'";

		PreparedStatement cmd = conexao.getConexao().prepareStatement(sql);
		ResultSet rs = cmd.executeQuery();
		if (rs.next()) {
			result = true;

		}

		return result;
	}

}
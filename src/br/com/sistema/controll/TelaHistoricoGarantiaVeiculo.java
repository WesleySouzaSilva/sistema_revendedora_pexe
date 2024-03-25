package br.com.sistema.controll;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.sistema.conexao.Conexao;
import br.com.sistema.model.PagamentoVeiculo;
import br.com.sistema.model.dao.PagamentoVeiculoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TelaHistoricoGarantiaVeiculo {

	@FXML
	private TableView<PagamentoVeiculo> tbDespesaGarantia;

	@FXML
	private TableColumn<PagamentoVeiculo, String> clnDataGarantia;

	@FXML
	private TableColumn<PagamentoVeiculo, String> clnDataVenda;

	@FXML
	private TableColumn<PagamentoVeiculo, String> clnPlaca;

	@FXML
	private TableColumn<PagamentoVeiculo, String> clnVeiculo;

	@FXML
	private Label lblQtdeGarantiaVeiculo;

	@FXML
	private Button btnDespesaGarantia;

	private Conexao conexao;
	private static Integer despesa_id;
	private PagamentoVeiculoDAO pagamentoVeiculoDAO = null;

	public void initialize() {
		preencherTabela();

		btnDespesaGarantia.setOnAction(e -> {
			try {
				adicionarDespesa();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		tbDespesaGarantia.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarTabela((PagamentoVeiculo) newValue));

		clnDataGarantia.setCellFactory(column -> {
			return new TableCell<PagamentoVeiculo, String>() {
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

						// Verifica o valor da célula e define a cor com base nele
						if (item.isEmpty()) {
							setTextFill(Color.BLACK); // Texto em verde
						} else {
							setTextFill(Color.GREEN);
						}
					}
				}
			};
		});
	}

	private void selecionarTabela(PagamentoVeiculo p) {
		if (p != null) {
			despesa_id = p.getId();
		}
	}

	public Integer getIdGarantia() {
		return despesa_id;
	}

	private void preencherTabela() {
		SimpleDateFormat formatarData = new SimpleDateFormat("yyyy-MM-dd");
		Date data = new Date();
		this.conexao  = new Conexao();
		this.pagamentoVeiculoDAO = new PagamentoVeiculoDAO(conexao);
		clnDataGarantia.setCellValueFactory(new PropertyValueFactory<>("dataGarantiaFormatada"));
		clnDataVenda.setCellValueFactory(new PropertyValueFactory<>("dataVendaFormatada"));
		clnPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
		clnVeiculo.setCellValueFactory(new PropertyValueFactory<>("nomeVeiculo"));
		ObservableList<PagamentoVeiculo> lista = FXCollections
				.observableArrayList(pagamentoVeiculoDAO.listarTodosGarantia(formatarData.format(data)));
		lblQtdeGarantiaVeiculo
				.setText(String.valueOf(pagamentoVeiculoDAO.getQtdeVeiculosGarantia(formatarData.format(data))));
		tbDespesaGarantia.setItems(lista);
		conexao.fecharConexao();
	}

	private void adicionarDespesa() throws IOException {
		if (tbDespesaGarantia.getSelectionModel().getSelectedItem() == null) {
			ValidationFields.checkEmptyFields(btnDespesaGarantia);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione o veiculo que deseja adicionar DESPESA DE GARANTIA!");
			dlg.showAndWait();
			btnDespesaGarantia.requestFocus();
			return;
		} else {
			Stage stage = new Stage();
			Image image = new Image("/br/com/sistema/icones/W3.png");

			stage.getIcons().add(image);
			URL FXML = this.getClass().getResource("/br/com/sistema/view/TelaNovaDespesaGarantia.fxml");

			Parent painel = (Parent) FXMLLoader.load(FXML);
			stage.setScene(new Scene(painel));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
			stage.setResizable(false);
		}
	}
}

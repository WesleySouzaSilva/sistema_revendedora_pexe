package br.com.sistema.controll;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import br.com.sistema.model.Despesa;
import br.com.sistema.model.dao.DespesaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TelaDespesasFixa {

	@FXML
	private TableView<Despesa> tbDespesa;

	@FXML
	private TableColumn<Despesa, String> clnDespesa;

	@FXML
	private TableColumn<Despesa, Integer> clnId;

	@FXML
	private Button btnEditar;

	@FXML
	private Button btnExcluir;

	@FXML
	private Button btnNovo;

	private static Integer despesaId = null;
	private DespesaDAO despesaDAO = Principal.getDespesaDAO();

	public void initialize() {

		listaDados();

		btnNovo.setOnAction(e -> {
			try {
				chamarTela("TelaNovoDespesasFixa");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btnEditar.setOnAction(e -> {
			editarDados();
		});

		btnExcluir.setOnAction(e -> {
			excluirDados();
		});

		tbDespesa.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarTabela((Despesa) newValue));

	}

	private void selecionarTabela(Despesa d) {
		if (d != null) {
			despesaId = d.getId();
		}
	}

	public Integer idTabela() {
		return despesaId;
	}

	private void listaDados() {
		clnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		clnDespesa.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		ObservableList<Despesa> lista = FXCollections.observableArrayList(despesaDAO.listarDadosDespesaFixa());
		tbDespesa.setItems(lista);
	}

	private void editarDados() {
		if (tbDespesa.getSelectionModel().getSelectedItem() != null) {
			ButtonType sim = new ButtonType("SIM", ButtonBar.ButtonData.YES);
			ButtonType nao = new ButtonType("NÃO", ButtonBar.ButtonData.NO);
			Alert alert = new Alert(AlertType.CONFIRMATION, "Deseja realmente EDITAR a DESPESA selecionada ?", sim,
					nao);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get().equals(sim)) {
				try {
					chamarTela("TelaEditarDespesasFixa");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			Alert dlg = new Alert(AlertType.WARNING);
			ValidationFields.checkEmptyFields(btnNovo);
			dlg.setContentText("Selecione a DESPESA que deseja EDITAR !");
			dlg.showAndWait();
			return;
		}

	}

	private void excluirDados() {
		if (tbDespesa.getSelectionModel().getSelectedItem() != null) {
			ButtonType sim = new ButtonType("SIM", ButtonBar.ButtonData.YES);
			ButtonType nao = new ButtonType("NÃO", ButtonBar.ButtonData.NO);
			Alert alert = new Alert(AlertType.CONFIRMATION, "Deseja realmente EXCLUIR a DESPESA selecionada ?", sim,
					nao);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get().equals(sim)) {
				boolean sucesso = despesaDAO.apagarDespesaFixa(despesaId);
				if (sucesso) {
					Alert dlg = new Alert(AlertType.INFORMATION);
					dlg.setContentText("Despesa excluida com sucesso!");
					dlg.showAndWait();
					listaDados();
				}
			}
		} else {
			Alert dlg = new Alert(AlertType.WARNING);
			ValidationFields.checkEmptyFields(btnNovo);
			dlg.setContentText("Selecione a DESPESA que deseja EXCLUIR !");
			dlg.showAndWait();
			return;
		}
	}

	private void chamarTela(String tela) throws IOException {
		Stage stage = new Stage();
		Image image = new Image("/br/com/sistema/icones/W3.png");

		stage.getIcons().add(image);
		URL FXML = this.getClass().getResource("/br/com/sistema/view/" + tela + ".fxml");

		Parent painel = (Parent) FXMLLoader.load(FXML);
		stage.setScene(new Scene(painel));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				listaDados();//
			}
		});
		stage.show();
		stage.setResizable(false);
	}

}

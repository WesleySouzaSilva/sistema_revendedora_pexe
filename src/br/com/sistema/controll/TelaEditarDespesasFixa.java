package br.com.sistema.controll;

import java.sql.SQLException;
import java.util.Optional;

import br.com.sistema.conexao.Conexao;
import br.com.sistema.listeners.ListenerParaMaiusculas;
import br.com.sistema.model.Despesa;
import br.com.sistema.model.dao.DespesaDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TelaEditarDespesasFixa {

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnSalvar;

	@FXML
	private TextField txtDescricao;

	private DespesaDAO despesaDAO = null;
	private Conexao conexao;
	private TelaDespesasFixa despesasFixa = new TelaDespesasFixa();

	public void initialize() {
		listaDado();

		txtDescricao.textProperty().addListener(new ListenerParaMaiusculas(txtDescricao));

		btnSalvar.setOnAction(e -> {
			try {
				salvar();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btnCancelar.setOnAction(e -> {
			cancelar();
		});

	}

	private void listaDado() {
		this.conexao = new Conexao();
		this.despesaDAO = new DespesaDAO(conexao);
		for (Despesa d : despesaDAO.listarDadosDespesaFixa(despesasFixa.idTabela())) {
			txtDescricao.setText(d.getDescricao());
		}
		conexao.fecharConexao();
	}

	private void salvar() throws SQLException {
		String descricao = null;
		if (txtDescricao.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtDescricao);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Cor!");
			dlg.showAndWait();
			txtDescricao.requestFocus();
			return;

		} else {
			descricao = txtDescricao.getText();
		}

		ButtonType sim = new ButtonType("SIM", ButtonBar.ButtonData.YES);
		ButtonType nao = new ButtonType("NÃO", ButtonBar.ButtonData.NO);
		Alert alert = new Alert(AlertType.CONFIRMATION, "Deseja realmente ATUALIZAR os dados da DESPESA FIXA ?", sim,
				nao);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get().equals(sim)) {
			this.conexao = new Conexao();
			this.despesaDAO = new DespesaDAO(conexao);
			boolean sucesso = despesaDAO.atualizarDespesaFixa(despesasFixa.idTabela(), descricao);
			conexao.fecharConexao();
			if (sucesso) {
				Alert dlg = new Alert(AlertType.INFORMATION);
				dlg.setContentText("Despesa fixa atualizada com sucesso!");
				dlg.showAndWait();
				voltarTela();
			}
		}

	}

	private void cancelar() {
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

	private void voltarTela() {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		stage.close();
	}

}

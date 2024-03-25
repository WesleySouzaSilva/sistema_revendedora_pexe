package br.com.sistema.controll;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

public class TelaNovoDespesasFixa {

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnSalvar;

	@FXML
	private TextField txtDescricao;

	private DespesaDAO despesaDAO = null;
	private Conexao conexao ;
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
		this.conexao = new Conexao();
		if (txtDescricao.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtDescricao);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Cor!");
			dlg.showAndWait();
			txtDescricao.requestFocus();
			return;

		} else {
			if (verificaDespesaCadastrada(txtDescricao.getText())) {
				ValidationFields.checkEmptyFields(txtDescricao);
				Alert dlg = new Alert(AlertType.WARNING);
				dlg.setContentText("Despesa " + txtDescricao.getText()
						+ " já possui cadastro.\nNão é possivel salvar dados duplicados.");
				dlg.showAndWait();
				btnSalvar.requestFocus();
				return;
			} else {
				descricao = txtDescricao.getText();
			}
	
		}

		ButtonType sim = new ButtonType("SIM", ButtonBar.ButtonData.YES);
		ButtonType nao = new ButtonType("NÃO", ButtonBar.ButtonData.NO);
		Alert alert = new Alert(AlertType.CONFIRMATION, "Deseja realmente CADASTRAR uma NOVA DESPESA FIXA ?", sim, nao);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get().equals(sim)) {
			this.despesaDAO = new DespesaDAO(conexao);
			boolean sucesso = despesaDAO.inserirDespesaFixa(descricao);
			conexao.fecharConexao();
			if (sucesso) {
				Alert dlg = new Alert(AlertType.INFORMATION);
				dlg.setContentText("Despesa fixa salva com sucesso!");
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

	public boolean verificaDespesaCadastrada(String descricao) throws SQLException {
		boolean result = false;

		String sql = "SELECT * FROM despesa_fixa WHERE descricao = '" + descricao + "' ";

		PreparedStatement cmd = conexao.getConexao().prepareStatement(sql);
		ResultSet rs = cmd.executeQuery();
		if (rs.next()) {
			result = true;

		}

		return result;
	}

}

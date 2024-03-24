package br.com.sistema.controll;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import br.com.sistema.conexao.Conexao;
import br.com.sistema.filtros.FiltroFlutuante;
import br.com.sistema.listeners.ListenerParaMaiusculas;
import br.com.sistema.listeners.ListenerValorMinMax;
import br.com.sistema.model.Despesa;
import br.com.sistema.model.HistoricoVeiculo;
import br.com.sistema.model.Veiculo;
import br.com.sistema.model.dao.DespesaDAO;
import br.com.sistema.model.dao.HistoricoVeiculoDAO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class TelaDespesaVeiculo {

	@FXML
	private TextField txtDescricao;

	@FXML
	private TextField txtValor;

	@FXML
	private DatePicker txtData;

	@FXML
	private Button btnConfirmar;

	@FXML
	private Button btnCancelar;

	private Conexao conexao = Principal.getConexao();
	private DespesaDAO despesaDAO = Principal.getDespesaDAO();
	private HistoricoVeiculoDAO historicoVeiculoDAO = Principal.getHistoricoVeiculoDAO();

	public void initialize() {

		txtDescricao.textProperty().addListener(new ListenerParaMaiusculas(txtDescricao));
		txtValor.addEventFilter(KeyEvent.KEY_TYPED, new FiltroFlutuante(9, false));
		txtValor.focusedProperty().addListener(new ListenerValorMinMax(txtValor, 0, 100000));

		btnConfirmar.setOnAction(e -> {
			try {
				confirmar();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btnCancelar.setOnAction(e -> {
			cancelar();
		});

		txtDescricao.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtValor.requestFocus();
				}

			}
		});

		txtValor.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtData.requestFocus();
				}

			}
		});

		txtData.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					try {
						confirmar();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});

	}

	public void confirmar() throws SQLException {
		TelaHome tela = new TelaHome();
		TelaLogin telaLogin = new TelaLogin();
		String tipo = null, tipoDespesa = null, descricao = null, responsavel = null, situacao = null;
		BigDecimal valor = null;
		Date dataFinal = null;
		Veiculo veiculo = new Veiculo(tela.getIdVeiculo());

		if (txtDescricao.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtDescricao);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Descrição!");
			dlg.showAndWait();
			txtDescricao.requestFocus();
			return;

		} else {

			descricao = txtDescricao.getText();

		}

		if (txtDescricao.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtDescricao);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Descrição!");
			dlg.showAndWait();
			txtDescricao.requestFocus();
			return;

		} else {

			descricao = txtDescricao.getText();

		}

		if (txtValor.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtValor);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Valor!");
			dlg.showAndWait();
			txtValor.requestFocus();
			return;

		} else {
			valor = new BigDecimal(txtValor.getText().replace(".", "").replaceAll(",", "."));

		}

		if (txtData.getValue() == null) {
			ValidationFields.checkEmptyFields(txtData);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Data!");
			dlg.showAndWait();
			txtData.requestFocus();
			return;

		} else {
			LocalDate d = LocalDate.now();
			Date dataHoje = java.sql.Date.valueOf(d);
			Date data = java.sql.Date.valueOf(txtData.getValue());
			System.out.println("comparando data atual: " + data.equals(dataHoje));
			if (data.after(dataHoje) || data.equals(dataHoje)) {
				dataFinal = java.sql.Date.valueOf(txtData.getValue());

			} else {
				SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
				ButtonType sim = new ButtonType("SIM", ButtonBar.ButtonData.YES);
				ButtonType nao = new ButtonType("NÃO", ButtonBar.ButtonData.NO);
				Alert alert = new Alert(AlertType.WARNING,
						"A data informada está menor que a data atual!\nData atual : " + formatador.format(dataHoje)
								+ ".\nData Informada : " + formatador.format(data) + ".\nDeseja realmente continuar ?",
						sim, nao);

				Optional<ButtonType> result = alert.showAndWait();

				if (result.get().equals(sim)) {
					dataFinal = java.sql.Date.valueOf(txtData.getValue());
				}

			}
		}

		tipo = new String("DESPESA");
		tipoDespesa = new String("DESPESA VEICULO");
		situacao = new String("PENDENTE");
		responsavel = telaLogin.permissaoUsuario();

		if (verificaDespesaVeiculo(tela.getIdVeiculo(), tipoDespesa, descricao, valor, dataFinal)) {
			ValidationFields.checkEmptyFields(txtDescricao);
			Alert dlg = new Alert(AlertType.WARNING);
			dlg.setContentText("A despesa informada ja foi registrada ao veiculo!");
			dlg.showAndWait();
			txtDescricao.requestFocus();
			return;
		}

		Despesa d = new Despesa(null, tipoDespesa, descricao, dataFinal, null, valor, null, responsavel, null, null,
				veiculo, null, null, situacao);

		HistoricoVeiculo h = new HistoricoVeiculo(null, tipo, descricao, dataFinal, valor, responsavel, null, null,
				null, null, veiculo, null);

		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.setTitle("Confirmação de INCLUSÃO");
		alerta.setHeaderText("Você quer mesmo cadastrar uma nova Despesa ao Veiculo? ");
		alerta.setContentText("A despesa será salva!" + "\nVocê tem certeza?");
		Optional<ButtonType> escolha = alerta.showAndWait();

		if (escolha.get() == ButtonType.OK) {
			boolean sucesso = despesaDAO.inserirDespesaVeiculo(d);
			System.out.println("sucesso inserir despesa : " + sucesso);
			boolean suce = historicoVeiculoDAO.inserirHistorico(h);
			System.out.println("sucesso inserir historico : " + suce);
			if (sucesso) {
				Alert dlg = new Alert(AlertType.INFORMATION);
				dlg.setContentText("Despesa salva com sucesso!");
				dlg.showAndWait();
				voltarTela();
			}

		}

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

	public void voltarTela() {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	public boolean verificaDespesaVeiculo(Integer veiculo_id, String tipo, String descricao, BigDecimal valor,
			Date data) throws SQLException {
		boolean result = false;

		String sql = "SELECT * FROM despesas WHERE veiculo_id = " + veiculo_id + " AND tipo = '" + tipo
				+ "' AND descricao = '" + descricao + "' AND valor = " + valor + " AND data_vencimento = '" + data
				+ "'";

		PreparedStatement cmd = conexao.getConexao().prepareStatement(sql);
		ResultSet rs = cmd.executeQuery();

		if (rs.next()) {

			result = true;
		}
		return result;
	}

}

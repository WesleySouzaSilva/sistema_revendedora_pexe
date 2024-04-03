package br.com.sistema.controll;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import br.com.sistema.conexao.Conexao;
import br.com.sistema.filtros.FiltroFlutuante;
import br.com.sistema.listeners.ListenerParaMaiusculas;
import br.com.sistema.listeners.ListenerValorMinMax;
import br.com.sistema.model.Despesa;
import br.com.sistema.model.dao.DespesaDAO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class TelaNovaDespesa {

	@FXML
	private TextField txtDescricao;

	@FXML
	private TextField txtValor;

	@FXML
	private DatePicker txtData;

	@FXML
	private ComboBox<String> cmbTipo;

	@FXML
	private Button btnConfirmar;

	@FXML
	private Button btnCancelar;

	private Conexao conexao;
	private DespesaDAO despesaDAO = null;

	public void initialize() {

		comboBoxtipoDespesa();

		txtDescricao.textProperty().addListener(new ListenerParaMaiusculas(txtDescricao));
		txtValor.addEventFilter(KeyEvent.KEY_TYPED, new FiltroFlutuante(9, false));
		txtValor.focusedProperty().addListener(new ListenerValorMinMax(txtValor, 0, 100000));

		btnConfirmar.setOnAction(e -> {
			confirmar();
		});

		btnCancelar.setOnAction(e -> {
			cancelar();
		});

		cmbTipo.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtDescricao.requestFocus();
					return;
				}

			}
		});

		txtDescricao.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtValor.requestFocus();
					return;
				}

			}
		});

		txtValor.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtData.requestFocus();
					return;
				}

			}
		});

		txtData.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					confirmar();
					return;
				}

			}
		});

	}

	public void confirmar() {
		TelaLogin tela = new TelaLogin();
		String descricao = null;
		String situacao = new String("PENDENTE");
		String tipo = null;
		Date dataFinal = null;
		BigDecimal valor = null;

		if (cmbTipo.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbTipo);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione o menu TIPO despesa!");
			dlg.showAndWait();
			cmbTipo.requestFocus();
			return;

		} else {

			tipo = cmbTipo.getValue();

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
			valor = new BigDecimal(txtValor.getText().replace(",", "."));

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

		Despesa despesa = new Despesa(null, tipo, descricao, dataFinal, null, valor, null, tela.permissaoUsuario(),
				null, null, null, null, null, situacao);

		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.setTitle("Confirmação de INCLUSÃO");
		alerta.setHeaderText("Você quer mesmo cadastrar uma nova DESPESA? ");
		alerta.setContentText("A despesa será salva!" + "\nVocê tem certeza?");
		Optional<ButtonType> escolha = alerta.showAndWait();

		if (escolha.get() == ButtonType.OK) {
			this.conexao = new Conexao();
			this.despesaDAO = new DespesaDAO(conexao);
			boolean sucesso = despesaDAO.inserir(despesa);
			conexao.fecharConexao();
			if (sucesso) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Confirmação de INCLUSÃO");
				alert.setHeaderText("Despesa salva com sucesso!");
				alert.showAndWait();
				voltarTela();
			}
			conexao.fecharConexao();

		}

	}

	public void comboBoxtipoDespesa() {
		ArrayList<String> listaBusca = new ArrayList<>();
		String em = new String("DESPESA LOJA");
		String t = new String("DESPESA DIVERSAS");
		String fun = new String("DESPESA FUNCIONARIO");

		listaBusca.add(em);
		listaBusca.add(t);
		listaBusca.add(fun);

		cmbTipo.getItems().addAll(listaBusca);
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

}

package br.com.sistema.controll;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import br.com.sistema.conexao.Conexao;
import br.com.sistema.filtros.FiltroFlutuante;
import br.com.sistema.listeners.ListenerValorMinMax;
import br.com.sistema.model.ContasReceber;
import br.com.sistema.model.dao.ContasReceberDAO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class TelaEditarPagamento {

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnSalvar;

	@FXML
	private TextField txtCliente;

	@FXML
	private DatePicker txtData;

	@FXML
	private TextField txtDescricao;

	@FXML
	private TextField txtValor;

	private Conexao conexao = Principal.getConexao();
	private ContasReceberDAO contasReceberDAO = null;
	private DecimalFormat decimal = new DecimalFormat("###,###,###,##0.00");
	private TelaListaContasReceber telaListaContasReceber = new TelaListaContasReceber();
	private BigDecimal valortotal = null;

	public void initialize() {
		listaDados();

		txtValor.addEventFilter(KeyEvent.KEY_TYPED, new FiltroFlutuante(20, false));
		txtValor.focusedProperty().addListener(new ListenerValorMinMax(txtValor, 0, 100000));

		btnSalvar.setOnAction(e -> {
			confirmar();
		});

		btnCancelar.setOnAction(e -> {
			cancelar();
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
				}

			}
		});
	}

	private void listaDados() {
		this.contasReceberDAO = Principal.getContasReceberDAO();
		LocalDate data = contasReceberDAO.getDataContasReceberPorId(telaListaContasReceber.getIdTabela()).toLocalDate()
				.plusDays(1);
		for (ContasReceber c : contasReceberDAO.listarTodos(telaListaContasReceber.getIdTabela())) {
			txtCliente.setText(c.getNomeCliente());
			txtDescricao.setText(c.getDescricao());
			txtValor.setText(c.getValorTotalFormatado());
			valortotal = c.getValorTotal();
			txtData.setValue(data);
		}
		conexao.fecharConexao();
	}

	private void confirmar() {
		BigDecimal valor = null;
		Date dataFinal = null;
		BigDecimal valorTxt = null;

		if (txtValor.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtValor);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Valor!");
			dlg.showAndWait();
			txtValor.requestFocus();
			return;

		} else {

			valorTxt = new BigDecimal(txtValor.getText().replace(".", "").replaceAll(",", "."));
			if (valorTxt.compareTo(valortotal) == 1 || valorTxt.compareTo(valortotal) == 0) {

				valor = new BigDecimal(txtValor.getText().replace(".", "").replaceAll(",", "."));
			} else {
				ValidationFields.checkEmptyFields(txtValor);
				Alert dlg = new Alert(AlertType.WARNING);
				dlg.setContentText("O valor não pode ser menor que o valor cadastrado.\nValor anterior : "
						+ decimal.format(valortotal) + "\nValor digitado : " + decimal.format(valorTxt)
						+ "\nCorrija o valor para poder continuar!");
				dlg.showAndWait();
				txtValor.requestFocus();
				return;
			}

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

		ContasReceber contas = new ContasReceber(telaListaContasReceber.getIdTabela(), dataFinal, valor,
				new BigDecimal(0), valor, 1, 1, null, null, null, null, null, null, null, null, null, null, null, null);
		ButtonType sim = new ButtonType("SIM", ButtonBar.ButtonData.YES);
		ButtonType nao = new ButtonType("NÃO", ButtonBar.ButtonData.NO);
		Alert alerta = new Alert(AlertType.INFORMATION, "Você quer mesmo atualizar os dados do PAGAMENTO A RECEBER ? ",
				sim, nao);

		Optional<ButtonType> result = alerta.showAndWait();
		if (result.get().equals(sim)) {
			this.contasReceberDAO = Principal.getContasReceberDAO();
			boolean sucesso = contasReceberDAO.atualizar(contas);
			if (sucesso) {
				Alert dlg = new Alert(AlertType.INFORMATION);
				dlg.setContentText("Dados atualizados com sucesso!");
				dlg.showAndWait();
				acaoSair();
			}
			conexao.fecharConexao();
		}

	}

	private void acaoSair() {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
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

}

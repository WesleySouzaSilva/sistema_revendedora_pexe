package br.com.sistema.controll;

import java.math.BigDecimal;
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
import br.com.sistema.model.PagamentoVeiculo;
import br.com.sistema.model.Veiculo;
import br.com.sistema.model.dao.DespesaDAO;
import br.com.sistema.model.dao.HistoricoVeiculoDAO;
import br.com.sistema.model.dao.PagamentoVeiculoDAO;
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

public class TelaNovaDespesaGarantia {

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnConfirmar;

	@FXML
	private DatePicker txtData;

	@FXML
	private TextField txtDescricao;

	@FXML
	private TextField txtPlaca;

	@FXML
	private TextField txtTipo;

	@FXML
	private TextField txtValor;

	@FXML
	private TextField txtVeiculo;

	private TelaHistoricoGarantiaVeiculo telaHistoricoGarantiaVeiculo = new TelaHistoricoGarantiaVeiculo();

	private Conexao conexao = Principal.getConexao();
	private DespesaDAO despesaDAO = null;
	private PagamentoVeiculoDAO pagamentoVeiculoDAO = null;
	private HistoricoVeiculoDAO historicoVeiculoDAO = null;

	public void initialize() {
		listaDadosTxt();

		txtDescricao.textProperty().addListener(new ListenerParaMaiusculas(txtDescricao));
		txtValor.addEventFilter(KeyEvent.KEY_TYPED, new FiltroFlutuante(9, false));
		txtValor.focusedProperty().addListener(new ListenerValorMinMax(txtValor, 0, 100000));

		btnCancelar.setOnAction(e -> {
			cancelar();
		});

		btnConfirmar.setOnAction(e -> {
			confirmar();
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
					confirmar();
				}

			}
		});
	}

	private void listaDadosTxt() {
		txtTipo.setText("DESPESA VEICULO GARANTIA");
		this.pagamentoVeiculoDAO = Principal.getPagamentoVeiculoDAO();
		for (PagamentoVeiculo p : pagamentoVeiculoDAO.listarTodosId(telaHistoricoGarantiaVeiculo.getIdGarantia())) {
			txtPlaca.setText(p.getPlaca());
			txtVeiculo.setText(p.getNomeVeiculo());
		}
		conexao.fecharConexao();
	}

	private void confirmar() {
		TelaLogin telaLogin = new TelaLogin();
		Date dataFinal = new Date();
		String tipo = txtTipo.getText();
		String descricao = null;
		String situacao = new String("PENDENTE");
		BigDecimal valor = null;

		if (txtDescricao.getText().isEmpty()) {
			Alert dlg = new Alert(AlertType.ERROR);
			ValidationFields.checkEmptyFields(txtDescricao);
			dlg.setContentText("Preencha o campo DESCRIÇÃO!");
			dlg.showAndWait();
			txtDescricao.requestFocus();
		} else {
			descricao = txtDescricao.getText();
		}

		if (txtValor.getText().isEmpty()) {
			Alert dlg = new Alert(AlertType.ERROR);
			ValidationFields.checkEmptyFields(txtValor);
			dlg.setContentText("Preencha o campo VALOR!");
			dlg.showAndWait();
			txtValor.requestFocus();
		} else {
			valor = new BigDecimal(txtValor.getText().replace(",", "."));
		}

		if (txtData.getValue() == null) {
			ValidationFields.checkEmptyFields(txtData);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Data Pagamento!");
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

		this.pagamentoVeiculoDAO = Principal.getPagamentoVeiculoDAO();
		Integer veiculoId = pagamentoVeiculoDAO.getIdVeiculo(telaHistoricoGarantiaVeiculo.getIdGarantia());
		BigDecimal lucroVenda = pagamentoVeiculoDAO.getLucroVendaVeiculo(telaHistoricoGarantiaVeiculo.getIdGarantia());
		BigDecimal calculo = lucroVenda.subtract(valor);
		conexao.fecharConexao();

		Veiculo veiculo = new Veiculo(veiculoId);

		HistoricoVeiculo historico = new HistoricoVeiculo(null, "GARANTIA", descricao, dataFinal, valor,
				telaLogin.permissaoUsuario(), null, null, null, null, veiculo, null);

		Despesa despesa = new Despesa(null, tipo, descricao, dataFinal, dataFinal, valor, null,
				telaLogin.permissaoUsuario(), null, null, veiculo, null, null, situacao);

		ButtonType sim = new ButtonType("SIM", ButtonBar.ButtonData.YES);
		ButtonType nao = new ButtonType("NÃO", ButtonBar.ButtonData.NO);
		Alert alerta = new Alert(AlertType.INFORMATION, "Você quer mesmo registrar uma nova despesa para o veiculo ? ",
				sim, nao);

		Optional<ButtonType> result = alerta.showAndWait();

		if (result.get().equals(sim)) {
			this.pagamentoVeiculoDAO = Principal.getPagamentoVeiculoDAO();
			boolean sucesso = pagamentoVeiculoDAO.atualizarValor(calculo, telaHistoricoGarantiaVeiculo.getIdGarantia());
			System.out.println("sucesso atualizou lucro_venda : " + sucesso);
			conexao.fecharConexao();

			this.historicoVeiculoDAO = Principal.getHistoricoVeiculoDAO();
			boolean suce = historicoVeiculoDAO.inserirHistorico(historico);
			conexao.fecharConexao();
			System.out.println("sucesso inserir historico : " + suce);

			this.despesaDAO = Principal.getDespesaDAO();
			boolean suc = despesaDAO.inserirDespesaVeiculo(despesa);
			conexao.fecharConexao();
			if (suc) {
				Alert dlg = new Alert(AlertType.INFORMATION);
				dlg.setContentText("Dados salvos com sucesso!");
				dlg.showAndWait();
				acaoSair();
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

	private void acaoSair() {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}
}

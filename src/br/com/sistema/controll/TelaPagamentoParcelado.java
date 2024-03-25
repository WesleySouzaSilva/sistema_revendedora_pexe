package br.com.sistema.controll;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import br.com.sistema.conexao.Conexao;
import br.com.sistema.filtros.FiltroInteiro;
import br.com.sistema.listeners.ListenerFormatarMonetario;
import br.com.sistema.model.ContasReceber;
import br.com.sistema.model.DetalhesPagamento;
import br.com.sistema.model.dao.DetalhesPagamentoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaPagamentoParcelado {

	@FXML
	private TextField txtValor;

	@FXML
	private ComboBox<String> cmbTipo;

	@FXML
	private DatePicker txtData;

	@FXML
	private Button btnConfirmar;

	private Conexao conexao;

	private TelaListaContasReceber tela = new TelaListaContasReceber();
	private DetalhesPagamentoDAO detalhesPagamentoDAO = null;

	public void initialize() {

		txtValor.addEventFilter(KeyEvent.KEY_TYPED, new FiltroInteiro(9));
		txtValor.focusedProperty().addListener(new ListenerFormatarMonetario(txtValor, 2));
		carregaComboBox();

		btnConfirmar.setOnAction(e -> {
			confirmar();
		});

		cmbTipo.setOnKeyPressed(new EventHandler<KeyEvent>() {

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

	public void confirmar() {

		String tipo = null;
		BigDecimal valor;
		Date dataFinal = null;

		if (cmbTipo.getValue() == null) {
			Alert dlg = new Alert(AlertType.INFORMATION);
			ValidationFields.checkEmptyFields(cmbTipo);
			dlg.setContentText("selecione o Tipo!!!");
			dlg.showAndWait();
			cmbTipo.requestFocus();
			return;
		} else {
			tipo = cmbTipo.getValue().toString();
		}

		if (txtValor.getText() == null || txtValor.getText().isEmpty()) {
			Alert dlg = new Alert(AlertType.INFORMATION);
			ValidationFields.checkEmptyFields(cmbTipo);
			dlg.setContentText("Preencha com o valor de recebimento!!!");
			dlg.showAndWait();
			txtValor.requestFocus();
			return;
		} else {
			this.conexao = new Conexao();
			String teste = txtValor.getText();
			BigDecimal txt = new BigDecimal(teste.replace(".", "").replaceAll(",", "."));
			Integer id = tela.getIdTabela();
			BigDecimal b = new BigDecimal(filtroValor(id));

			if (txt.compareTo(b) == 1) {
				Alert dlg = new Alert(AlertType.WARNING);
				ValidationFields.checkEmptyFields(txtValor);
				String valorReceber = String.valueOf(filtroValor(id));
				dlg.setContentText("Valor " + txtValor.getText()
						+ " é maior do que o saldo devedor! \nSaldo devedor = : " + valorReceber);
				dlg.showAndWait();
				txtValor.requestFocus();
				return;
			} else {
				String valorTxt = txtValor.getText();
				valor = new BigDecimal(valorTxt.replace(".", "").replaceAll(",", "."));
				System.out.println("valor formatado : " + valor);
			}
			conexao.fecharConexao();
		}

		if (txtData.getValue() == null) {
			Alert dlg = new Alert(AlertType.INFORMATION);
			ValidationFields.checkEmptyFields(txtData);
			dlg.setContentText("selecione o campo Data Recebimento!!!");
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

		if (cmbTipo.getValue() != null || txtValor.getText() != null || txtData.getValue() != null) {
			tela = new TelaListaContasReceber();
			ContasReceber c = new ContasReceber(tela.getIdTabela());
			System.out.println("get contas receber : " + c.getId());
			DetalhesPagamento detalhes = new DetalhesPagamento(null, tipo, valor, dataFinal, null, c);

			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Confirmação de Pagamento");
			alerta.setHeaderText("Você quer mesmo confirmar o recebimento PARCELADO ? ");
			Optional<ButtonType> escolha = alerta.showAndWait();

			if (escolha.get() == ButtonType.OK) {
				this.conexao = new Conexao();
				this.detalhesPagamentoDAO = new DetalhesPagamentoDAO(conexao);
				boolean sucesso = detalhesPagamentoDAO.inserir(detalhes);
				BigDecimal valorRestante = detalhesPagamentoDAO.getTotalRecebido(tela.getIdTabela());
				BigDecimal valorTotal = detalhesPagamentoDAO.getTotalPagamentoReceber(tela.getIdTabela());
				BigDecimal calcula = valorTotal.subtract(valorRestante);
				ContasReceber co = new ContasReceber(tela.getIdTabela());
				DetalhesPagamento deta = new DetalhesPagamento(null, null, calcula, null, null, co);
				DetalhesPagamento detal = new DetalhesPagamento(null, null, valor, null, null, co);
				boolean su = detalhesPagamentoDAO.atualizarValor(deta);
				System.out.println("sucesso atualizar recebimento : " + su);
				boolean suc = detalhesPagamentoDAO.atualizarValorRecebido(detal);
				System.out.println("sucesso atualizar valor_pago : " + suc);
				conexao.fecharConexao();

				if (sucesso) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmação de Pagamento");
					alerta.setHeaderText("Recebimento salvo com sucesso !");
					alert.showAndWait();
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Confirmação de Pagamento");
					alerta.setHeaderText("Recebimento falhou !");
					alert.showAndWait();
				}

			} 
			
			Stage stage = (Stage) btnConfirmar.getScene().getWindow();
			stage.close();
		}

	}

	public void carregaComboBox() {
		ArrayList<String> lista = new ArrayList<>();
		ObservableList<String> observa = null;

		String recebimento = new String("RECEBIMENTO");
		lista.add(recebimento);

		observa = FXCollections.observableArrayList(lista);
		cmbTipo.setItems(observa);

	}

	public Float filtroValor(Integer id) {
		Float valor = null;

		String sql = "SELECT * FROM contas_receber WHERE id = '" + id + "' ";
		try {

			PreparedStatement cmd = conexao.getConexao().prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getFloat("valor_receber");
			}

			conexao.fecharConexao();

		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("valor recebido do banco de dados : " + valor);
		return valor;
	}

}

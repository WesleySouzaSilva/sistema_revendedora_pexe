package br.com.sistema.controll;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import br.com.sistema.conexao.Conexao;
import br.com.sistema.model.Relatorio;
import br.com.sistema.model.RelatorioDre;
import br.com.sistema.model.dao.ContasReceberDAO;
import br.com.sistema.model.dao.DespesaDAO;
import br.com.sistema.model.dao.PagamentoVeiculoDAO;
import br.com.sistema.model.dao.VeiculoDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

public class TelaRelatorioDre {

	@FXML
	private DatePicker txtDataInicial;

	@FXML
	private DatePicker txtDataFinal;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnConfirmar;

	private Conexao conexao;
	private DecimalFormat decimal = new DecimalFormat("###,###,###,##0.00");
	private DespesaDAO despesaDAO = null;
	private ContasReceberDAO contasReceberDAO = null;
	private PagamentoVeiculoDAO pagamentoVeiculoDAO = null;
	private VeiculoDAO veiculoDAO = null;

	public void initialize() {

		btnCancelar.setOnAction(e -> {
			cancelar();
		});

		btnConfirmar.setOnAction(e -> {
			try {
				confirmar();
			} catch (JRException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

	}

	public void confirmar() throws JRException {
		String dataInicialFormatada = null, dataFinalFormatada = null, faturamento = null, receitaTotal = null,
				receitaLiquida = null, despesaVeiculo = null, despesaFuncionario = null, despesaEmpresa = null,
				despesaTotal = null, compraVeiculo = null, contasPagarPago = null, contasPagarPendente = null,
				contasReceberPago = null, contasReceberPendente = null, mediaLucroVeiculo = null, mediaDespesa = null,
				mediaCompraVeiculo = null, qtdeVeiculos = null, valorVeiculos = null, despesaDiversas = null;
		String garantiaVeiculos = null, contasPagarTotal = null, contasReceberTotal = null, lucroLiquidoVeiculos = null,
				qtdeVeiculosVendidos = null;

		String valorDespesasTotal = null, despesaTotalVeiculos = null;

		Date dataInicial = null;
		Date dataFinal = null;
		SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<RelatorioDre> lista = new ArrayList<RelatorioDre>();

		if (txtDataInicial.getValue() == null) {
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione a Data Inicial !");
			dlg.showAndWait();
			txtDataInicial.requestFocus();
			return;
		} else {
			dataInicial = java.sql.Date.valueOf(txtDataInicial.getValue());
			dataInicialFormatada = formata.format(dataInicial);
		}

		if (txtDataFinal.getValue() == null) {
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione a Data Final !");
			dlg.showAndWait();
			txtDataFinal.requestFocus();
			return;
		} else {
			dataFinal = java.sql.Date.valueOf(txtDataFinal.getValue());
			dataFinalFormatada = formata.format(dataFinal);
		}

		this.conexao = new Conexao();
		this.pagamentoVeiculoDAO = new PagamentoVeiculoDAO(conexao);
		lucroLiquidoVeiculos = decimal.format(pagamentoVeiculoDAO.getValorTotalLucro(dataInicial, dataFinal));

		BigDecimal receitLiquid = pagamentoVeiculoDAO.getValorTotalLucro(dataInicial, dataFinal);
		BigDecimal qtdeVenda = pagamentoVeiculoDAO.getQtdeVendaVeiculos(dataInicial, dataFinal);
		if (qtdeVenda.compareTo(new BigDecimal(0)) == 0) {
			mediaLucroVeiculo = new String("0,00");
		} else {
			mediaLucroVeiculo = decimal.format(receitLiquid.divide(qtdeVenda, 2, RoundingMode.HALF_UP));

		}
		
		this.despesaDAO = new DespesaDAO(conexao);
		despesaEmpresa = decimal.format(despesaDAO.getTotalDespesasBigDecimalEmpresa(dataInicial, dataFinal));
		System.out.println("DESPESA EMPRESA : " + despesaEmpresa);
		despesaDiversas = decimal.format(despesaDAO.getTotalDespesasBigDecimalDiversas(dataInicial, dataFinal));
		despesaFuncionario = decimal.format(despesaDAO.getTotalDespesasBigDecimalFuncionario(dataInicial, dataFinal));
		despesaVeiculo = decimal.format(despesaDAO.getTotalDespesasBigDecimalVeiculo(dataInicial, dataFinal));
		despesaTotalVeiculos = decimal.format(despesaDAO.getTotalDespesasVeiculosEstoque(dataInicial, dataFinal));
		despesaTotal = decimal.format(despesaDAO.getTotalDespesasBigDecimal(dataInicial, dataFinal));
		valorDespesasTotal = decimal.format(despesaDAO.getTotalDespesasBigDecimal(dataInicial, dataFinal));
		BigDecimal despe = despesaDAO.getTotalDespesasBigDecimal(dataInicial, dataFinal);
		BigDecimal contaQtdeDespesa = despesaDAO.getQtdeTotalDespesasBigDecimal(dataInicial, dataFinal);
		if (contaQtdeDespesa.compareTo(new BigDecimal(0)) == 0) {
			mediaDespesa = new String("0");
		} else {
			mediaDespesa = decimal.format(despe.divide(contaQtdeDespesa, 2, RoundingMode.HALF_UP));
		}
		contasPagarPendente = decimal.format(despesaDAO.getPagarDespesasContasPagar(dataInicial, dataFinal));
		contasPagarPago = decimal.format(despesaDAO.getPagoDespesasContasPagar(dataInicial, dataFinal));
		contasPagarTotal = decimal.format(despesaDAO.getTotalDespesasContasPagar(dataInicial, dataFinal));
		compraVeiculo = decimal.format(despesaDAO.getTotalDespesasCompraVeiculos(dataInicial, dataFinal));
		garantiaVeiculos = decimal.format(despesaDAO.getTotalDespesasGarantiaVeiculos(dataInicial, dataFinal));
		BigDecimal comprVeicul = despesaDAO.getTotalDespesasCompraVeiculos(dataInicial, dataFinal);
		BigDecimal qtdeCompra = despesaDAO.getQtdeCompraVeiculos(dataInicial, dataFinal);
		if (qtdeCompra.compareTo(new BigDecimal(0)) == 0) {
			mediaCompraVeiculo = new String("0");
		} else {
			mediaCompraVeiculo = decimal.format(comprVeicul.divide(qtdeCompra, 2, RoundingMode.HALF_UP));
		}

		this.contasReceberDAO = new ContasReceberDAO(conexao);
		receitaTotal = decimal.format(contasReceberDAO.getValorContasReceber(dataInicial, dataFinal, "valor_total"));
		contasReceberPago = decimal
				.format(contasReceberDAO.getValorContasReceber(dataInicial, dataFinal, "valor_pago"));
		contasReceberPendente = decimal
				.format(contasReceberDAO.getValorContasReceber(dataInicial, dataFinal, "valor_receber"));
		contasReceberTotal = decimal
				.format(contasReceberDAO.getValorContasReceber(dataInicial, dataFinal, "valor_total"));
		BigDecimal contasReceber = contasReceberDAO.getValorContasReceber(dataInicial, dataFinal, "valor_total");

		faturamento = decimal.format(contasReceber.subtract(despe));

		this.veiculoDAO = new VeiculoDAO(conexao);
		qtdeVeiculos = veiculoDAO.getQtdeTotalEstoque();
		valorVeiculos = decimal.format(veiculoDAO.getValorTotalEstoque(dataInicial, dataFinal, "valor_venda"));
		qtdeVeiculosVendidos = veiculoDAO.getQtdeTotalVeiculosVendidos(dataInicial, dataFinal);
		conexao.fecharConexao();

		RelatorioDre re = new RelatorioDre(dataInicialFormatada, dataFinalFormatada, faturamento, receitaTotal,
				receitaLiquida, despesaVeiculo, despesaFuncionario, despesaEmpresa, despesaTotal, compraVeiculo,
				contasPagarPago, contasPagarPendente, contasReceberPago, contasReceberPendente, mediaLucroVeiculo,
				mediaDespesa, mediaCompraVeiculo, qtdeVeiculos, valorVeiculos, despesaDiversas, garantiaVeiculos,
				contasPagarTotal, contasReceberTotal, lucroLiquidoVeiculos, qtdeVeiculosVendidos, valorDespesasTotal,
				despesaTotalVeiculos);

		lista.add(re);

		Relatorio relatorioImprimir = new Relatorio();
		relatorioImprimir.gerarRelatorio(lista, "RelatorioDRE");

	}

	public void cancelar() {
		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.setTitle("Confirmação de Cancelamento!");
		alerta.setHeaderText("Você quer mesmo Cancelar ? ");
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

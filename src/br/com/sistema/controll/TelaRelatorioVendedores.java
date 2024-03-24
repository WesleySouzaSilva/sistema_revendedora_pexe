package br.com.sistema.controll;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import br.com.sistema.conexao.Conexao;
import br.com.sistema.model.Funcionario;
import br.com.sistema.model.Relatorio;
import br.com.sistema.model.RelatorioVenda;
import br.com.sistema.model.dao.FuncionarioDAO;
import br.com.sistema.model.dao.RelatorioVendaDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

public class TelaRelatorioVendedores {

	@FXML
	private DatePicker txtDataInicial;

	@FXML
	private DatePicker txtDataFinal;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnConfirmar;

	@FXML
	private ComboBox<Funcionario> cmbFuncionario;

	private Conexao conexao = Principal.getConexao();
	private RelatorioVendaDAO relatorioVendaDAO = null;
	private FuncionarioDAO funcionarioDAO = null;
	private DecimalFormat decimal = new DecimalFormat("###,###,###,##0.00");

	public void initialize() {

		carregaComboBoxVendedor();
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

		conexao.fecharConexao();

	}

	public void confirmar() throws JRException {
		String vendedor = null, veiculo = null, dataCompra = null, dataVenda = null, valor = null,
				dataInicialFormatada = null, dataFinalFormatada = null, valorTotalFormatado = null, placa = null,
				qtdeVendas = null;

		String valorTotalLucro = null, valorLucro = null, valorDespesas = null, valorCompra = null;

		Date dataInicial = null;
		Date dataFinal = null;
		ArrayList<RelatorioVenda> lista = new ArrayList<>();
		SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");

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

		if (cmbFuncionario.getValue() == null) {
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione o Funcionario !");
			dlg.showAndWait();
			cmbFuncionario.requestFocus();
			return;
		} else {
			vendedor = cmbFuncionario.getValue().toString();
		}

		if (dataInicial != null && dataFinal != null && cmbFuncionario.getValue() != null) {
			this.relatorioVendaDAO = Principal.getRelatorioVendaDAO();
			for (RelatorioVenda re : relatorioVendaDAO.listarTodos(dataInicial, dataFinal, vendedor)) {
				veiculo = re.getVeiculo();
				vendedor = re.getVendedor();
				dataCompra = re.getDataCompraFormatada();
				dataVenda = re.getDataVendaFormatada();
				valor = re.getValorFormatado();
				placa = re.getPlaca();
				valorLucro = re.getValorLucro();
				valorCompra = re.getValorCompra();
				valorTotalLucro = decimal.format(relatorioVendaDAO.getValorTotalLucro(dataInicial, dataFinal));
				valorDespesas = re.getValorDespesas();
				valorTotalFormatado = decimal.format(relatorioVendaDAO.getTotalVendas(dataInicial, dataFinal));
				qtdeVendas = relatorioVendaDAO.getQtdeVendas(dataInicial, dataFinal);
				RelatorioVenda relatorio = new RelatorioVenda(vendedor, veiculo, placa, dataVenda, dataCompra, valor,
						dataInicialFormatada, dataFinalFormatada, valorTotalFormatado, qtdeVendas, valorTotalLucro,
						valorLucro, valorDespesas, valorCompra);

				lista.add(relatorio);
			}

			if (lista.isEmpty()) {
				String vazia = new String("------");
				veiculo = valor;
				vendedor = vazia;
				dataCompra = vazia;
				dataVenda = vazia;
				valor = vazia;
				placa = vazia;
				valorTotalFormatado = new String("0.0");
				qtdeVendas = new String("0");
				valorTotalLucro = new String("0.0");
				valorLucro = new String("0.0");
				valorDespesas = new String("0.0");
				valorCompra = new String("0.0");
				RelatorioVenda relatorio = new RelatorioVenda(vendedor, veiculo, placa, dataVenda, dataCompra, valor,
						dataInicialFormatada, dataFinalFormatada, valorTotalFormatado, qtdeVendas, valorTotalLucro,
						valorLucro, valorDespesas, valorCompra);

				lista.add(relatorio);
			}

			conexao.fecharConexao();

			Relatorio relatorioImprimir = new Relatorio();
			relatorioImprimir.gerarRelatorio(lista, "RelatorioVendaVendedores");
			voltarTela();
		}

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

	public void carregaComboBoxVendedor() {
		this.funcionarioDAO = Principal.getFuncionarioDAO();
		cmbFuncionario.getItems().addAll(funcionarioDAO.listarTodos());
		conexao.fecharConexao();

	}

}

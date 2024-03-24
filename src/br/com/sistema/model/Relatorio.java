package br.com.sistema.model;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class Relatorio {

	public void gerarRelatorioFuncionario(List<Funcionario> lista) throws JRException {

		InputStream jasperStream = this.getClass()
				.getResourceAsStream("/br/com/sistema/relatorios/RelatorioFuncionarios.jrxml");

		JasperReport report = JasperCompileManager.compileReport(jasperStream);

		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(lista));

		JasperViewer.viewReport(print, false);

	}

	public void gerarRelatorio(List<?> lista, String nomeRelatorio) throws JRException {

		InputStream jasperStream = this.getClass()
				.getResourceAsStream("/br/com/sistema/relatorios/" + nomeRelatorio + ".jrxml");

		JasperReport report = JasperCompileManager.compileReport(jasperStream);

		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(lista));

		JasperViewer.viewReport(print, false);

	}

	public void gerarRelatorioDadosVeiculo(HashMap<?, ?> lista, String nomeRelatorio) throws JRException {

		InputStream jasperStream = this.getClass()
				.getResourceAsStream("/br/com/sistema/relatorios/" + nomeRelatorio + ".jrxml");

		JasperReport report = JasperCompileManager.compileReport(jasperStream);

		JasperPrint print = JasperFillManager.fillReport(report, null,
				new JRBeanCollectionDataSource((Collection<?>) lista));

		JasperViewer.viewReport(print, false);

	}

	public void gerarRelatorioContasReceber(List<ContasReceber> lista, String nomeRelatorio) throws JRException {

		InputStream jasperStream = this.getClass()
				.getResourceAsStream("/br/com/sistema/relatorios/" + nomeRelatorio + ".jrxml");

		JasperReport report = JasperCompileManager.compileReport(jasperStream);

		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(lista));

		JasperViewer.viewReport(print, false);

	}

	public void gerarRelatorioVeiculos(List<Veiculo> lista) throws JRException {

		InputStream jasperStream = this.getClass()
				.getResourceAsStream("/br/com/sistema/relatorios/RelatorioVeiculos.jrxml");

		JasperReport report = JasperCompileManager.compileReport(jasperStream);

		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(lista));

		JasperViewer.viewReport(print, false);

	}

	public void gerarRelatorioConsumoVeiculo(List<Despesa> lista) throws JRException {

		InputStream jasperStream = this.getClass()
				.getResourceAsStream("/br/com/sistema/relatorios/RelatorioVeiculoConsumo.jrxml");

		JasperReport report = JasperCompileManager.compileReport(jasperStream);

		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(lista));

		JasperViewer.viewReport(print, false);

	}

	public void gerarRelatorioManutencaoVeiculo(List<Despesa> lista) throws JRException {

		InputStream jasperStream = this.getClass()
				.getResourceAsStream("/br/com/sistema/relatorios/RelatorioVeiculoManutencao.jrxml");

		JasperReport report = JasperCompileManager.compileReport(jasperStream);

		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(lista));

		JasperViewer.viewReport(print, false);

	}

	public void gerarRelatorioDespesaEmpresa(List<DespesaEmpresa> lista) throws JRException {

		InputStream jasperStream = this.getClass()
				.getResourceAsStream("/br/com/sistema/relatorios/RelatorioEmpresaDespesa.jrxml");

		JasperReport report = JasperCompileManager.compileReport(jasperStream);

		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(lista));

		JasperViewer.viewReport(print, false);

	}

	public void gerarRelatorioDespesaFuncionario(List<DespesaFuncionario> lista) throws JRException {

		InputStream jasperStream = this.getClass()
				.getResourceAsStream("/br/com/sistema/relatorios/RelatorioFuncionariosDespesa.jrxml");

		JasperReport report = JasperCompileManager.compileReport(jasperStream);

		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(lista));

		JasperViewer.viewReport(print, false);

	}

	public void gerarRelatorioTotalDespesas(List<Despesa> lista) throws JRException {

		InputStream jasperStream = this.getClass()
				.getResourceAsStream("/br/com/sistema/relatorios/RelatorioDespesas.jrxml");

		JasperReport report = JasperCompileManager.compileReport(jasperStream);

		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(lista));

		JasperViewer.viewReport(print, false);

	}

	public void gerarRelatorioDespesas(List<ContasReceber> lista) throws JRException {

		InputStream jasperStream = this.getClass()
				.getResourceAsStream("/br/com/sistema/relatorios/RelatorioContasReceber.jrxml");

		JasperReport report = JasperCompileManager.compileReport(jasperStream);

		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(lista));

		JasperViewer.viewReport(print, false);

	}

}

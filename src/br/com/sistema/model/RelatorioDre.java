package br.com.sistema.model;

public class RelatorioDre {

	private String dataInicial, dataFinal, faturamento, receitaTotal, receitaLiquida, despesaVeiculo,
			despesaFuncionario, despesaEmpresa, despesaTotal, compraVeiculo, contasPagarPago, contasPagarPendente,
			contasReceberPago, contasReceberPendente, mediaLucroVeiculo, mediaDespesa, mediaCompraVeiculo, qtdeVeiculos,
			valorVeiculos, despesaDiversas;
	String garantiaVeiculos, contasPagarTotal, contasReceberTotal, lucroLiquidoVeiculos, qtdeVeiculosVendidos,
			valorDespesasTotal, despesaTotalVeiculos;

	public RelatorioDre() {

	}

	public RelatorioDre(String dataInicial, String dataFinal, String faturamento, String receitaTotal,
			String receitaLiquida, String despesaVeiculo, String despesaFuncionario, String despesaEmpresa,
			String despesaTotal, String compraVeiculo, String contasPagarPago, String contasPagarPendente,
			String contasReceberPago, String contasReceberPendente, String mediaLucroVeiculo, String mediaDespesa,
			String mediaCompraVeiculo, String qtdeVeiculos, String valorVeiculos, String despesaDiversas,
			String garantiaVeiculos, String contasPagarTotal, String contasReceberTotal, String lucroLiquidoVeiculos,
			String qtdeVeiculosVendidos, String valorDespesasTotal, String despesaTotalVeiculos) {
		super();
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
		this.faturamento = faturamento;
		this.receitaTotal = receitaTotal;
		this.receitaLiquida = receitaLiquida;
		this.despesaVeiculo = despesaVeiculo;
		this.despesaFuncionario = despesaFuncionario;
		this.despesaEmpresa = despesaEmpresa;
		this.despesaTotal = despesaTotal;
		this.compraVeiculo = compraVeiculo;
		this.contasPagarPago = contasPagarPago;
		this.contasPagarPendente = contasPagarPendente;
		this.contasReceberPago = contasReceberPago;
		this.contasReceberPendente = contasReceberPendente;
		this.mediaLucroVeiculo = mediaLucroVeiculo;
		this.mediaDespesa = mediaDespesa;
		this.mediaCompraVeiculo = mediaCompraVeiculo;
		this.qtdeVeiculos = qtdeVeiculos;
		this.valorVeiculos = valorVeiculos;
		this.despesaDiversas = despesaDiversas;
		this.garantiaVeiculos = garantiaVeiculos;
		this.contasPagarTotal = contasPagarTotal;
		this.contasReceberTotal = contasReceberTotal;
		this.lucroLiquidoVeiculos = lucroLiquidoVeiculos;
		this.qtdeVeiculosVendidos = qtdeVeiculosVendidos;
		this.valorDespesasTotal = valorDespesasTotal;
		this.despesaTotalVeiculos = despesaTotalVeiculos;
	}

	public String getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}

	public String getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getFaturamento() {
		return faturamento;
	}

	public void setFaturamento(String faturamento) {
		this.faturamento = faturamento;
	}

	public String getReceitaTotal() {
		return receitaTotal;
	}

	public void setReceitaTotal(String receitaTotal) {
		this.receitaTotal = receitaTotal;
	}

	public String getReceitaLiquida() {
		return receitaLiquida;
	}

	public void setReceitaLiquida(String receitaLiquida) {
		this.receitaLiquida = receitaLiquida;
	}

	public String getDespesaVeiculo() {
		return despesaVeiculo;
	}

	public void setDespesaVeiculo(String despesaVeiculo) {
		this.despesaVeiculo = despesaVeiculo;
	}

	public String getDespesaFuncionario() {
		return despesaFuncionario;
	}

	public void setDespesaFuncionario(String despesaFuncionario) {
		this.despesaFuncionario = despesaFuncionario;
	}

	public String getDespesaEmpresa() {
		return despesaEmpresa;
	}

	public void setDespesaEmpresa(String despesaEmpresa) {
		this.despesaEmpresa = despesaEmpresa;
	}

	public String getDespesaTotal() {
		return despesaTotal;
	}

	public void setDespesaTotal(String despesaTotal) {
		this.despesaTotal = despesaTotal;
	}

	public String getCompraVeiculo() {
		return compraVeiculo;
	}

	public void setCompraVeiculo(String compraVeiculo) {
		this.compraVeiculo = compraVeiculo;
	}

	public String getContasPagarPago() {
		return contasPagarPago;
	}

	public void setContasPagarPago(String contasPagarPago) {
		this.contasPagarPago = contasPagarPago;
	}

	public String getContasPagarPendente() {
		return contasPagarPendente;
	}

	public void setContasPagarPendente(String contasPagarPendente) {
		this.contasPagarPendente = contasPagarPendente;
	}

	public String getContasReceberPago() {
		return contasReceberPago;
	}

	public void setContasReceberPago(String contasReceberPago) {
		this.contasReceberPago = contasReceberPago;
	}

	public String getContasReceberPendente() {
		return contasReceberPendente;
	}

	public void setContasReceberPendente(String contasReceberPendente) {
		this.contasReceberPendente = contasReceberPendente;
	}

	public String getMediaLucroVeiculo() {
		return mediaLucroVeiculo;
	}

	public void setMediaLucroVeiculo(String mediaLucroVeiculo) {
		this.mediaLucroVeiculo = mediaLucroVeiculo;
	}

	public String getMediaDespesa() {
		return mediaDespesa;
	}

	public void setMediaDespesa(String mediaDespesa) {
		this.mediaDespesa = mediaDespesa;
	}

	public String getMediaCompraVeiculo() {
		return mediaCompraVeiculo;
	}

	public void setMediaCompraVeiculo(String mediaCompraVeiculo) {
		this.mediaCompraVeiculo = mediaCompraVeiculo;
	}

	public String getQtdeVeiculos() {
		return qtdeVeiculos;
	}

	public void setQtdeVeiculos(String qtdeVeiculos) {
		this.qtdeVeiculos = qtdeVeiculos;
	}

	public String getValorVeiculos() {
		return valorVeiculos;
	}

	public void setValorVeiculos(String valorVeiculos) {
		this.valorVeiculos = valorVeiculos;
	}

	public String getDespesaDiversas() {
		return despesaDiversas;
	}

	public void setDespesaDiversas(String despesaDiversas) {
		this.despesaDiversas = despesaDiversas;
	}

	public String getGarantiaVeiculos() {
		return garantiaVeiculos;
	}

	public void setGarantiaVeiculos(String garantiaVeiculos) {
		this.garantiaVeiculos = garantiaVeiculos;
	}

	public String getContasPagarTotal() {
		return contasPagarTotal;
	}

	public void setContasPagarTotal(String contasPagarTotal) {
		this.contasPagarTotal = contasPagarTotal;
	}

	public String getContasReceberTotal() {
		return contasReceberTotal;
	}

	public void setContasReceberTotal(String contasReceberTotal) {
		this.contasReceberTotal = contasReceberTotal;
	}

	public String getLucroLiquidoVeiculos() {
		return lucroLiquidoVeiculos;
	}

	public void setLucroLiquidoVeiculos(String lucroLiquidoVeiculos) {
		this.lucroLiquidoVeiculos = lucroLiquidoVeiculos;
	}

	public String getQtdeVeiculosVendidos() {
		return qtdeVeiculosVendidos;
	}

	public void setQtdeVeiculosVendidos(String qtdeVeiculosVendidos) {
		this.qtdeVeiculosVendidos = qtdeVeiculosVendidos;
	}

	public String getValorDespesasTotal() {
		return valorDespesasTotal;
	}

	public void setValorDespesasTotal(String valorDespesasTotal) {
		this.valorDespesasTotal = valorDespesasTotal;
	}

	public String getDespesaTotalVeiculos() {
		return despesaTotalVeiculos;
	}

	public void setDespesaTotalVeiculos(String despesaTotalVeiculos) {
		this.despesaTotalVeiculos = despesaTotalVeiculos;
	}

}

package br.com.sistema.model;

public class RelatorioVenda {

	private String vendedor, veiculo, dataVendaFormatada, dataCompraFormatada, valorFormatado, dataInicialFormatada,
			dataFinalFormatada, valorTotalFormatado, placa, qtdeVenda;
	private String valorTotalLucro, valorLucro, valorDespesas, valorCompra;

	public RelatorioVenda() {

	}

	public RelatorioVenda(String vendedor, String veiculo, String placa, String dataVendaFormatada,
			String dataCompraFormatada, String valorFormatado, String dataInicialFormatada, String dataFinalFormatada,
			String valorTotalFormatado, String qtdeVenda, String valorTotalLucro, String valorLucro,
			String valorDespesas, String valorCompra) {
		super();
		this.vendedor = vendedor;
		this.veiculo = veiculo;
		this.dataVendaFormatada = dataVendaFormatada;
		this.dataCompraFormatada = dataCompraFormatada;
		this.valorFormatado = valorFormatado;
		this.dataInicialFormatada = dataInicialFormatada;
		this.dataFinalFormatada = dataFinalFormatada;
		this.valorTotalFormatado = valorTotalFormatado;
		this.placa = placa;
		this.qtdeVenda = qtdeVenda;
		this.valorTotalLucro = valorTotalLucro;
		this.valorLucro = valorLucro;
		this.valorDespesas = valorDespesas;
		this.valorCompra = valorCompra;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public String getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(String veiculo) {
		this.veiculo = veiculo;
	}

	public String getDataVendaFormatada() {
		return dataVendaFormatada;
	}

	public void setDataVendaFormatada(String dataVendaFormatada) {
		this.dataVendaFormatada = dataVendaFormatada;
	}

	public String getDataCompraFormatada() {
		return dataCompraFormatada;
	}

	public void setDataCompraFormatada(String dataCompraFormatada) {
		this.dataCompraFormatada = dataCompraFormatada;
	}

	public String getValorFormatado() {
		return valorFormatado;
	}

	public void setValorFormatado(String valorFormatado) {
		this.valorFormatado = valorFormatado;
	}

	public String getDataInicialFormatada() {
		return dataInicialFormatada;
	}

	public void setDataInicialFormatada(String dataInicialFormatada) {
		this.dataInicialFormatada = dataInicialFormatada;
	}

	public String getDataFinalFormatada() {
		return dataFinalFormatada;
	}

	public void setDataFinalFormatada(String dataFinalFormatada) {
		this.dataFinalFormatada = dataFinalFormatada;
	}

	public String getValorTotalFormatado() {
		return valorTotalFormatado;
	}

	public void setValorTotalFormatado(String valorTotalFormatado) {
		this.valorTotalFormatado = valorTotalFormatado;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getQtdeVenda() {
		return qtdeVenda;
	}

	public void setQtdeVenda(String qtdeVenda) {
		this.qtdeVenda = qtdeVenda;
	}

	public String getValorTotalLucro() {
		return valorTotalLucro;
	}

	public void setValorTotalLucro(String valorTotalLucro) {
		this.valorTotalLucro = valorTotalLucro;
	}

	public String getValorLucro() {
		return valorLucro;
	}

	public void setValorLucro(String valorLucro) {
		this.valorLucro = valorLucro;
	}

	public String getValorDespesas() {
		return valorDespesas;
	}

	public void setValorDespesas(String valorDespesas) {
		this.valorDespesas = valorDespesas;
	}

	public String getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(String valorCompra) {
		this.valorCompra = valorCompra;
	}

}

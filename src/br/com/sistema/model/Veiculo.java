package br.com.sistema.model;

import java.math.BigDecimal;
import java.util.Date;

public class Veiculo {
	private Integer id;
	private String marca;
	private String ano_modelo;
	private String veiculo, renavam, placa, cor, situacao;
	private String dataEntradaFormatada, valorEntradaFormatado, valorVendaFormatado, dataVendaFormatada;
	private Date dataEntrada, dataVenda;
	private BigDecimal valorEntrada, valorVenda;
	private Pessoa pessoa;
	private String vendedor;
	private String sinistroRs, financiamento;
	private String categoria;
	private String km;
	private BigDecimal valorFipe;
	private String valorFipeFormatado;
	private String qtdeVeiculos;
	private String valorTotalSomaFormatado, valorTotalCompraVeiculos, valorTotalFipeVeiculos;

	public Veiculo() {

	}

	public Veiculo(Integer id) {
		this.id = id;
	}

	public Veiculo(Integer id, String situacao, Date dataVenda, String vendedor) {
		super();
		this.id = id;
		this.situacao = situacao;
		this.dataVenda = dataVenda;
		this.vendedor = vendedor;
	}

	public Veiculo(String qtdeVeiculos, String valorTotalSomaFormatado, String valorTotalCompraVeiculos,
			String valorTotalFipeVeiculos) {
		this.qtdeVeiculos = qtdeVeiculos;
		this.valorTotalSomaFormatado = valorTotalSomaFormatado;
		this.valorTotalCompraVeiculos = valorTotalCompraVeiculos;
		this.valorTotalFipeVeiculos = valorTotalFipeVeiculos;
	}

	public Veiculo(Integer id, String veiculo, String marca, String ano_modelo, String renavam, String placa,
			String cor, String situacao, String dataEntradaFormatada, String valorEntradaFormatado,
			String valorVendaFormatado, String dataVendaFormatada, Date dataEntrada, Date dataVenda,
			BigDecimal valorEntrada, BigDecimal valorVenda, Pessoa pessoa, String sinistroRs, String financiamento,
			String categoria, String km, BigDecimal valorFipe, String valorFipeFormatado,
			String valorTotalCompraVeiculos, String valorTotalFipeVeiculos) {
		super();
		this.id = id;
		this.veiculo = veiculo;
		this.renavam = renavam;
		this.placa = placa;
		this.cor = cor;
		this.situacao = situacao;
		this.dataEntradaFormatada = dataEntradaFormatada;
		this.valorEntradaFormatado = valorEntradaFormatado;
		this.valorVendaFormatado = valorVendaFormatado;
		this.dataVendaFormatada = dataVendaFormatada;
		this.dataEntrada = dataEntrada;
		this.dataVenda = dataVenda;
		this.valorEntrada = valorEntrada;
		this.valorVenda = valorVenda;
		this.pessoa = pessoa;
		this.marca = marca;
		this.ano_modelo = ano_modelo;
		this.sinistroRs = sinistroRs;
		this.financiamento = financiamento;
		this.categoria = categoria;
		this.km = km;
		this.valorFipe = valorFipe;
		this.valorFipeFormatado = valorFipeFormatado;
		this.valorTotalCompraVeiculos = valorTotalCompraVeiculos;
		this.valorTotalFipeVeiculos = valorTotalFipeVeiculos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(String veiculo) {
		this.veiculo = veiculo;
	}

	public String getRenavam() {
		return renavam;
	}

	public void setRenavam(String renavam) {
		this.renavam = renavam;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getDataEntradaFormatada() {
		return dataEntradaFormatada;
	}

	public void setDataEntradaFormatada(String dataEntradaFormatada) {
		this.dataEntradaFormatada = dataEntradaFormatada;
	}

	public String getValorEntradaFormatado() {
		return valorEntradaFormatado;
	}

	public void setValorEntradaFormatado(String valorEntradaFormatado) {
		this.valorEntradaFormatado = valorEntradaFormatado;
	}

	public String getValorVendaFormatado() {
		return valorVendaFormatado;
	}

	public void setValorVendaFormatado(String valorVendaFormatado) {
		this.valorVendaFormatado = valorVendaFormatado;
	}

	public String getDataVendaFormatada() {
		return dataVendaFormatada;
	}

	public void setDataVendaFormatada(String dataVendaFormatada) {
		this.dataVendaFormatada = dataVendaFormatada;
	}

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public Date getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}

	public BigDecimal getValorEntrada() {
		return valorEntrada;
	}

	public void setValorEntrada(BigDecimal valorEntrada) {
		this.valorEntrada = valorEntrada;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getAno_modelo() {
		return ano_modelo;
	}

	public void setAno_modelo(String ano_modelo) {
		this.ano_modelo = ano_modelo;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public String getFinanciamento() {
		return financiamento;
	}

	public void setFinanciamento(String financiamento) {
		this.financiamento = financiamento;
	}

	public String getSinistroRs() {
		return sinistroRs;
	}

	public void setSinistroRs(String sinistroRs) {
		this.sinistroRs = sinistroRs;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getKm() {
		return km;
	}

	public void setKm(String km) {
		this.km = km;
	}

	public BigDecimal getValorFipe() {
		return valorFipe;
	}

	public void setValorFipe(BigDecimal valorFipe) {
		this.valorFipe = valorFipe;
	}

	public String getValorFipeFormatado() {
		return valorFipeFormatado;
	}

	public void setValorFipeFormatado(String valorFipeFormatado) {
		this.valorFipeFormatado = valorFipeFormatado;
	}

	public String getQtdeVeiculos() {
		return qtdeVeiculos;
	}

	public void setQtdeVeiculos(String qtdeVeiculos) {
		this.qtdeVeiculos = qtdeVeiculos;
	}

	public String getValorTotalSomaFormatado() {
		return valorTotalSomaFormatado;
	}

	public void setValorTotalSomaFormatado(String valorTotalSomaFormatado) {
		this.valorTotalSomaFormatado = valorTotalSomaFormatado;
	}

	public String getValorTotalCompraVeiculos() {
		return valorTotalCompraVeiculos;
	}

	public void setValorTotalCompraVeiculos(String valorTotalCompraVeiculos) {
		this.valorTotalCompraVeiculos = valorTotalCompraVeiculos;
	}

	public String getValorTotalFipeVeiculos() {
		return valorTotalFipeVeiculos;
	}

	public void setValorTotalFipeVeiculos(String valorTotalFipeVeiculos) {
		this.valorTotalFipeVeiculos = valorTotalFipeVeiculos;
	}

}

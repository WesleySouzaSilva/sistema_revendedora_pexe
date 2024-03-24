package br.com.sistema.model;

import java.math.BigDecimal;
import java.util.Date;

public class PagamentoVeiculo {

	private Integer id;
	private Date dataVenda;
	private BigDecimal lucroVenda, valorParcela;
	private String tipoPagamento, parcela, observacao, garantia;
	private Veiculo veiculo;
	private Pessoa pessoa;
	private String dataVendaFormatada, lucroVendaFormatado, valorParcelaFormatado, nomeCliente;
	private Date dataGarantia;
	private String dataGarantiaFormatada, placa, nomeVeiculo;

	public PagamentoVeiculo() {

	}

	public PagamentoVeiculo(Integer id, Date dataVenda, BigDecimal lucroVenda, BigDecimal valorParcela,
			String tipoPagamento, String parcela, String observacao, String garantia, Veiculo veiculo, Pessoa pessoa,
			Date dataGarantia) {
		super();
		this.id = id;
		this.dataVenda = dataVenda;
		this.lucroVenda = lucroVenda;
		this.valorParcela = valorParcela;
		this.tipoPagamento = tipoPagamento;
		this.parcela = parcela;
		this.veiculo = veiculo;
		this.pessoa = pessoa;
		this.observacao = observacao;
		this.garantia = garantia;
		this.dataGarantia = dataGarantia;
	}

	public PagamentoVeiculo(Integer id, String tipoPagamento, String parcela, Veiculo veiculo, Pessoa pessoa,
			String dataVendaFormatada, String lucroVendaFormatado, String valorParcelaFormatado, String nomeCliente,
			String observacao, String garantia, String dataGarantiaFormatada, String placa, String nomeVeiculo) {
		super();
		this.id = id;
		this.tipoPagamento = tipoPagamento;
		this.parcela = parcela;
		this.veiculo = veiculo;
		this.pessoa = pessoa;
		this.dataVendaFormatada = dataVendaFormatada;
		this.lucroVendaFormatado = lucroVendaFormatado;
		this.valorParcelaFormatado = valorParcelaFormatado;
		this.nomeCliente = nomeCliente;
		this.observacao = observacao;
		this.garantia = garantia;
		this.dataGarantiaFormatada = dataGarantiaFormatada;
		this.placa = placa;
		this.nomeVeiculo = nomeVeiculo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}

	public BigDecimal getLucroVenda() {
		return lucroVenda;
	}

	public void setLucroVenda(BigDecimal lucroVenda) {
		this.lucroVenda = lucroVenda;
	}

	public BigDecimal getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(BigDecimal valorParcela) {
		this.valorParcela = valorParcela;
	}

	public String getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public String getParcela() {
		return parcela;
	}

	public void setParcela(String parcela) {
		this.parcela = parcela;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getDataVendaFormatada() {
		return dataVendaFormatada;
	}

	public void setDataVendaFormatada(String dataVendaFormatada) {
		this.dataVendaFormatada = dataVendaFormatada;
	}

	public String getLucroVendaFormatado() {
		return lucroVendaFormatado;
	}

	public void setLucroVendaFormatado(String lucroVendaFormatado) {
		this.lucroVendaFormatado = lucroVendaFormatado;
	}

	public String getValorParcelaFormatado() {
		return valorParcelaFormatado;
	}

	public void setValorParcelaFormatado(String valorParcelaFormatado) {
		this.valorParcelaFormatado = valorParcelaFormatado;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getGarantia() {
		return garantia;
	}

	public void setGarantia(String garantia) {
		this.garantia = garantia;
	}

	public Date getDataGarantia() {
		return dataGarantia;
	}

	public String getDataGarantiaFormatada() {
		return dataGarantiaFormatada;
	}

	public void setDataGarantia(Date dataGarantia) {
		this.dataGarantia = dataGarantia;
	}

	public void setDataGarantiaFormatada(String dataGarantiaFormatada) {
		this.dataGarantiaFormatada = dataGarantiaFormatada;
	}

	public String getNomeVeiculo() {
		return nomeVeiculo;
	}

	public void setNomeVeiculo(String nomeVeiculo) {
		this.nomeVeiculo = nomeVeiculo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}
}

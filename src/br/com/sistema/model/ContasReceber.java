package br.com.sistema.model;

import java.math.BigDecimal;
import java.util.Date;

public class ContasReceber {

	private Integer id;
	private Date dataVencimento;
	private BigDecimal valorTotal, valorPago, valorPendente;
	private Integer qtdeParcela, numeroParcela;
	private Pessoa pessoa;
	private Veiculo veiculo;
	private String situacao;
	private String dataVencimentoFormatada, valorTotalFormatado, valorPagoFormatado, valorPendenteFormatado,
			nomeCliente, descricao;
	private String saldo, dataInicio, dataFim;
	private String valorTotalTxt;

	public ContasReceber() {

	}

	public ContasReceber(Integer id) {
		this.id = id;
	}

	public ContasReceber(Integer id, Date dataVencimento, BigDecimal valorTotal, BigDecimal valorPago,
			BigDecimal valorPendente, Integer qtdeParcela, Integer numeroParcela, Pessoa pessoa, Veiculo veiculo,
			String situacao, String dataVencimentoFormatada, String valorTotalFormatado, String valorPagoFormatado,
			String valorPendenteFormatado, String nomeCliente, String descricao, String saldo, String dataInicio,
			String dataFim) {
		super();
		this.id = id;
		this.dataVencimento = dataVencimento;
		this.valorTotal = valorTotal;
		this.valorPago = valorPago;
		this.valorPendente = valorPendente;
		this.qtdeParcela = qtdeParcela;
		this.numeroParcela = numeroParcela;
		this.pessoa = pessoa;
		this.veiculo = veiculo;
		this.situacao = situacao;
		this.dataVencimentoFormatada = dataVencimentoFormatada;
		this.valorTotalFormatado = valorTotalFormatado;
		this.valorPagoFormatado = valorPagoFormatado;
		this.valorPendenteFormatado = valorPendenteFormatado;
		this.nomeCliente = nomeCliente;
		this.descricao = descricao;
		this.saldo = saldo;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
	}

	public ContasReceber(Integer id, Date dataVencimento, BigDecimal valorTotal, BigDecimal valorPago,
			BigDecimal valorPendente, Integer qtdeParcela, Integer numeroParcela, Pessoa pessoa, Veiculo veiculo,
			String situacao, String dataVencimentoFormatada, String valorTotalFormatado, String valorPagoFormatado,
			String valorPendenteFormatado, String nomeCliente, String descricao, String saldo, String dataInicio,
			String dataFim, String valorTotalTxt) {
		super();
		this.id = id;
		this.dataVencimento = dataVencimento;
		this.valorTotal = valorTotal;
		this.valorPago = valorPago;
		this.valorPendente = valorPendente;
		this.qtdeParcela = qtdeParcela;
		this.numeroParcela = numeroParcela;
		this.pessoa = pessoa;
		this.veiculo = veiculo;
		this.situacao = situacao;
		this.dataVencimentoFormatada = dataVencimentoFormatada;
		this.valorTotalFormatado = valorTotalFormatado;
		this.valorPagoFormatado = valorPagoFormatado;
		this.valorPendenteFormatado = valorPendenteFormatado;
		this.nomeCliente = nomeCliente;
		this.descricao = descricao;
		this.saldo = saldo;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.valorTotalTxt = valorTotalTxt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public BigDecimal getValorPendente() {
		return valorPendente;
	}

	public void setValorPendente(BigDecimal valorPendente) {
		this.valorPendente = valorPendente;
	}

	public Integer getQtdeParcela() {
		return qtdeParcela;
	}

	public void setQtdeParcela(Integer qtdeParcela) {
		this.qtdeParcela = qtdeParcela;
	}

	public Integer getNumeroParcela() {
		return numeroParcela;
	}

	public void setNumeroParcela(Integer numeroParcela) {
		this.numeroParcela = numeroParcela;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getDataVencimentoFormatada() {
		return dataVencimentoFormatada;
	}

	public void setDataVencimentoFormatada(String dataVencimentoFormatada) {
		this.dataVencimentoFormatada = dataVencimentoFormatada;
	}

	public String getValorTotalFormatado() {
		return valorTotalFormatado;
	}

	public void setValorTotalFormatado(String valorTotalFormatado) {
		this.valorTotalFormatado = valorTotalFormatado;
	}

	public String getValorPagoFormatado() {
		return valorPagoFormatado;
	}

	public void setValorPagoFormatado(String valorPagoFormatado) {
		this.valorPagoFormatado = valorPagoFormatado;
	}

	public String getValorPendenteFormatado() {
		return valorPendenteFormatado;
	}

	public void setValorPendenteFormatado(String valorPendenteFormatado) {
		this.valorPendenteFormatado = valorPendenteFormatado;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSaldo() {
		return saldo;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getDataFim() {
		return dataFim;
	}

	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}

	public String getValorTotalTxt() {
		return valorTotalTxt;
	}

	public void setValorTotalTxt(String valorTotalTxt) {
		this.valorTotalTxt = valorTotalTxt;
	}
}

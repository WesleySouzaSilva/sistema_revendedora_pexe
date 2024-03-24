package br.com.sistema.model;

import java.math.BigDecimal;
import java.util.Date;

public class Despesa {
	private Integer id;
	private String tipo;
	private String descricao;
	private Date dataVencimento, dataPagamento;
	private BigDecimal valor;
	private String valorFormatado;
	private String responsavel;
	private String dataVencimentoFormatada, dataPagamentoFormatada;
	private Veiculo veiculo;
	private Funcionario funcionario;
	private Pessoa pessoa;
	private String situacao;

	public Despesa(Integer id, String tipo, String descricao, Date dataVencimento, Date dataPagamento, BigDecimal valor,
			String valorFormatado, String responsavel, String dataVencimentoFormatada, String dataPagamentoFormatada,
			Veiculo veiculo, Funcionario funcionario, Pessoa pessoa, String situacao) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.descricao = descricao;
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.valorFormatado = valorFormatado;
		this.responsavel = responsavel;
		this.dataVencimentoFormatada = dataVencimentoFormatada;
		this.dataPagamentoFormatada = dataPagamentoFormatada;
		this.veiculo = veiculo;
		this.funcionario = funcionario;
		this.pessoa = pessoa;
		this.situacao = situacao;
	}

	public Despesa(Integer id) {
		this.id = id;
	}

	public Despesa() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getValorFormatado() {
		return valorFormatado;
	}

	public void setValorFormatado(String valorFormatado) {
		this.valorFormatado = valorFormatado;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getDataVencimentoFormatada() {
		return dataVencimentoFormatada;
	}

	public void setDataVencimentoFormatada(String dataVencimentoFormatada) {
		this.dataVencimentoFormatada = dataVencimentoFormatada;
	}

	public String getDataPagamentoFormatada() {
		return dataPagamentoFormatada;
	}

	public void setDataPagamentoFormatada(String dataPagamentoFormatada) {
		this.dataPagamentoFormatada = dataPagamentoFormatada;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

}

package br.com.sistema.model;

import java.math.BigDecimal;
import java.util.Date;

public class HistoricoVeiculo {

	private Integer id;
	private String tipo;
	private String descricao;
	private Date data;
	private BigDecimal valor;
	private String responsavel;
	private String nomeCliente, cpfCnpj;
	private String dataFormatada, valorFormatado;
	private Veiculo veiculo;
	private Pessoa pessoa;

	public HistoricoVeiculo() {

	}

	public HistoricoVeiculo(String nomeCliente, String cpfCnpj) {
		super();
		this.nomeCliente = nomeCliente;
		this.cpfCnpj = cpfCnpj;
	}

	public HistoricoVeiculo(Integer id, String tipo, String descricao, Date data, BigDecimal valor, String responsavel,
			String nomeCliente, String cpfCnpj, String dataFormatada, String valorFormatado, Veiculo veiculo,
			Pessoa pessoa) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.descricao = descricao;
		this.data = data;
		this.valor = valor;
		this.responsavel = responsavel;
		this.nomeCliente = nomeCliente;
		this.cpfCnpj = cpfCnpj;
		this.dataFormatada = dataFormatada;
		this.valorFormatado = valorFormatado;
		this.veiculo = veiculo;
		this.pessoa = pessoa;
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getDataFormatada() {
		return dataFormatada;
	}

	public void setDataFormatada(String dataFormatada) {
		this.dataFormatada = dataFormatada;
	}

	public String getValorFormatado() {
		return valorFormatado;
	}

	public void setValorFormatado(String valorFormatado) {
		this.valorFormatado = valorFormatado;
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

}

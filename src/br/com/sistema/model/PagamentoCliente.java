package br.com.sistema.model;

import java.math.BigDecimal;
import java.util.Date;

public class PagamentoCliente {

	private Integer id;
	private BigDecimal mensalidade;
	private String dia;
	private String ativo;
	private Pessoa pessoa;
	private Date data;
	private String nomeCliente, rua, bairro, valorFormatado, dataFormatada;
	private String responsavel;

	public PagamentoCliente() {

	}

	public PagamentoCliente(Integer id, BigDecimal mensalidade, String dia, String ativo, Pessoa pessoa) {
		super();
		this.id = id;
		this.mensalidade = mensalidade;
		this.dia = dia;
		this.ativo = ativo;
		this.pessoa = pessoa;
	}

	public PagamentoCliente(Integer id, BigDecimal mensalidade, String dia, Date data, String responsavel,
			Pessoa pessoa) {
		super();
		this.id = id;
		this.mensalidade = mensalidade;
		this.dia = dia;
		this.responsavel = responsavel;
		this.pessoa = pessoa;
		this.data = data;
	}

	public PagamentoCliente(Integer id, String dia, String ativo, String nomeCliente, String rua, String bairro,
			String valorFormatado) {
		super();
		this.id = id;
		this.dia = dia;
		this.ativo = ativo;
		this.nomeCliente = nomeCliente;
		this.rua = rua;
		this.bairro = bairro;
		this.valorFormatado = valorFormatado;
	}

	public PagamentoCliente(Integer id, String dia, String valorFormatado, String dataFormatada, String responsavel,
			Pessoa pessoa, Date data, BigDecimal mensalidade) {
		super();
		this.id = id;
		this.dia = dia;
		this.valorFormatado = valorFormatado;
		this.dataFormatada = dataFormatada;
		this.responsavel = responsavel;
		this.pessoa = pessoa;
		this.data = data;
		this.mensalidade = mensalidade;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getMensalidade() {
		return mensalidade;
	}

	public void setMensalidade(BigDecimal mensalidade) {
		this.mensalidade = mensalidade;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getValorFormatado() {
		return valorFormatado;
	}

	public void setValorFormatado(String valorFormatado) {
		this.valorFormatado = valorFormatado;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getDataFormatada() {
		return dataFormatada;
	}

	public void setDataFormatada(String dataFormatada) {
		this.dataFormatada = dataFormatada;
	}
}

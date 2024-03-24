package br.com.sistema.model;

import java.math.BigDecimal;
import java.util.Date;

public class DetalhesPagamento {

	private Integer id;
	private String tipo;
	private BigDecimal valor;
	private Date data;
	private String dataFormatada;
	private ContasReceber contasReceber;
	private Despesa despesa;

	public DetalhesPagamento() {

	}

	public DetalhesPagamento(Integer id, String tipo, BigDecimal valor, Date data, String dataFormatada,
			ContasReceber contasReceber) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.valor = valor;
		this.data = data;
		this.dataFormatada = dataFormatada;
		this.contasReceber = contasReceber;
	}

	public DetalhesPagamento(Integer id, String tipo, BigDecimal valor, Date data, String dataFormatada,
			ContasReceber contasReceber, Despesa despesa) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.valor = valor;
		this.data = data;
		this.dataFormatada = dataFormatada;
		this.contasReceber = contasReceber;
		this.despesa = despesa;
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

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDataFormatada() {
		return dataFormatada;
	}

	public void setDataFormatada(String dataFormatada) {
		this.dataFormatada = dataFormatada;
	}

	public ContasReceber getContasReceber() {
		return contasReceber;
	}

	public void setContasReceber(ContasReceber contasReceber) {
		this.contasReceber = contasReceber;
	}

	public Despesa getDespesa() {
		return despesa;
	}

	public void setDespesa(Despesa despesa) {
		this.despesa = despesa;
	}
}

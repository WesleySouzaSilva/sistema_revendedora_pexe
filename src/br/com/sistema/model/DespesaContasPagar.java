package br.com.sistema.model;

public class DespesaContasPagar {

	private Integer id;
	private String tipo, descricao, responsavel, dataVencimentoFormatada, dataPagamentoFormatada, valor, situacao,
			dataInicio, dataFim, valorPagar, valorPago, valorTotal;

	public DespesaContasPagar() {

	}

	public DespesaContasPagar(Integer id, String tipo, String descricao, String responsavel,
			String dataVencimentoFormatada, String dataPagamentoFormatada, String valor, String situacao,
			String dataInicio, String dataFim, String valorPagar, String valorPago, String valorTotal) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.descricao = descricao;
		this.responsavel = responsavel;
		this.dataVencimentoFormatada = dataVencimentoFormatada;
		this.valor = valor;
		this.situacao = situacao;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.valorPagar = valorPagar;
		this.valorPago = valorPago;
		this.valorTotal = valorTotal;
		this.dataPagamentoFormatada = dataPagamentoFormatada;
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

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
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

	public String getValorPagar() {
		return valorPagar;
	}

	public void setValorPagar(String valorPagar) {
		this.valorPagar = valorPagar;
	}

	public String getValorPago() {
		return valorPago;
	}

	public void setValorPago(String valorPago) {
		this.valorPago = valorPago;
	}

	public String getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}

}

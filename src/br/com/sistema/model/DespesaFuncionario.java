package br.com.sistema.model;

public class DespesaFuncionario {

	private Integer id;
	private String descricao, responsavel, valor, data, dataInicio, dataFim;
	private String totalDespesa;

	public DespesaFuncionario(String totalDespesa) {
		this.totalDespesa = totalDespesa;
	}

	public DespesaFuncionario(Integer id, String descricao, String responsavel, String valor, String data,
			String dataInicio, String dataFim) {

		this.id = id;
		this.descricao = descricao;
		this.responsavel = responsavel;
		this.valor = valor;
		this.data = data;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
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

	public String getTotalDespesa() {
		return totalDespesa;
	}

	public void setTotalDespesa(String totalDespesa) {
		this.totalDespesa = totalDespesa;
	}

}

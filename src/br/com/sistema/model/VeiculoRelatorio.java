package br.com.sistema.model;

public class VeiculoRelatorio {

	private Integer id;
	private String nomeEmpresa, cnpjEmpresa, enderecoEmpresa, bairroEmpresa, numeroEmpresa, estadoEmpresa,
			cidadeEmpresa, telefoneEmpresa, emailEmpresa, cepEmpresa;
	private String diaPatio, totalDespesas, valorEntrada, custoTotalVeiculo, veiculo, placa, renavam, cor, dataEntrada,
			valorVenda;
	private String vendaDataVenda, vendaLucroVenda, vendaCliente, vendaTipoPagamento, vendaParcela, vendaValorParcela,
			observacao, garantia, dataGarantiaFormatada;

	public VeiculoRelatorio(Integer id, String nomeEmpresa, String cnpjEmpresa, String enderecoEmpresa,
			String bairroEmpresa, String numeroEmpresa, String estadoEmpresa, String cidadeEmpresa,
			String telefoneEmpresa, String emailEmpresa, String cepEmpresa) {
		super();
		this.id = id;
		this.nomeEmpresa = nomeEmpresa;
		this.cnpjEmpresa = cnpjEmpresa;
		this.enderecoEmpresa = enderecoEmpresa;
		this.bairroEmpresa = bairroEmpresa;
		this.numeroEmpresa = numeroEmpresa;
		this.estadoEmpresa = estadoEmpresa;
		this.cidadeEmpresa = cidadeEmpresa;
		this.telefoneEmpresa = telefoneEmpresa;
		this.emailEmpresa = emailEmpresa;
		this.cepEmpresa = cepEmpresa;

	}

	public VeiculoRelatorio(String diaPatio, String totalDespesas, String valorEntrada, String custoTotalVeiculo) {
		super();
		this.diaPatio = diaPatio;
		this.totalDespesas = totalDespesas;
		this.valorEntrada = valorEntrada;
		this.custoTotalVeiculo = custoTotalVeiculo;
	}

	public VeiculoRelatorio(Integer id, String valorEntrada, String veiculo, String placa, String renavam, String cor,
			String dataEntrada, String valorVenda) {
		super();
		this.id = id;
		this.valorEntrada = valorEntrada;
		this.veiculo = veiculo;
		this.placa = placa;
		this.renavam = renavam;
		this.cor = cor;
		this.dataEntrada = dataEntrada;
		this.valorVenda = valorVenda;
	}

	public VeiculoRelatorio(String vendaDataVenda, String vendaLucroVenda, String vendaCliente,
			String vendaTipoPagamento, String vendaParcela, String vendaValorParcela, String garantia, String obervacao,
			String dataGarantiaFormatada) {
		super();
		this.vendaDataVenda = vendaDataVenda;
		this.vendaLucroVenda = vendaLucroVenda;
		this.vendaCliente = vendaCliente;
		this.vendaTipoPagamento = vendaTipoPagamento;
		this.vendaParcela = vendaParcela;
		this.vendaValorParcela = vendaValorParcela;
		this.garantia = garantia;
		this.observacao = obervacao;
		this.dataGarantiaFormatada = dataGarantiaFormatada;
	}

	public VeiculoRelatorio(Integer id, String nomeEmpresa, String cnpjEmpresa, String enderecoEmpresa,
			String bairroEmpresa, String numeroEmpresa, String estadoEmpresa, String cidadeEmpresa,
			String telefoneEmpresa, String emailEmpresa, String cepEmpresa, String veiculo, String placa,
			String renavam, String cor, String dataEntrada, String valorVenda) {
		super();
		this.id = id;
		this.nomeEmpresa = nomeEmpresa;
		this.cnpjEmpresa = cnpjEmpresa;
		this.enderecoEmpresa = enderecoEmpresa;
		this.bairroEmpresa = bairroEmpresa;
		this.numeroEmpresa = numeroEmpresa;
		this.estadoEmpresa = estadoEmpresa;
		this.cidadeEmpresa = cidadeEmpresa;
		this.telefoneEmpresa = telefoneEmpresa;
		this.emailEmpresa = emailEmpresa;
		this.cepEmpresa = cepEmpresa;
		this.veiculo = veiculo;
		this.placa = placa;
		this.renavam = renavam;
		this.cor = cor;
		this.dataEntrada = dataEntrada;
		this.valorVenda = valorVenda;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public String getCnpjEmpresa() {
		return cnpjEmpresa;
	}

	public void setCnpjEmpresa(String cnpjEmpresa) {
		this.cnpjEmpresa = cnpjEmpresa;
	}

	public String getEnderecoEmpresa() {
		return enderecoEmpresa;
	}

	public void setEnderecoEmpresa(String enderecoEmpresa) {
		this.enderecoEmpresa = enderecoEmpresa;
	}

	public String getBairroEmpresa() {
		return bairroEmpresa;
	}

	public void setBairroEmpresa(String bairroEmpresa) {
		this.bairroEmpresa = bairroEmpresa;
	}

	public String getNumeroEmpresa() {
		return numeroEmpresa;
	}

	public void setNumeroEmpresa(String numeroEmpresa) {
		this.numeroEmpresa = numeroEmpresa;
	}

	public String getEstadoEmpresa() {
		return estadoEmpresa;
	}

	public void setEstadoEmpresa(String estadoEmpresa) {
		this.estadoEmpresa = estadoEmpresa;
	}

	public String getCidadeEmpresa() {
		return cidadeEmpresa;
	}

	public void setCidadeEmpresa(String cidadeEmpresa) {
		this.cidadeEmpresa = cidadeEmpresa;
	}

	public String getTelefoneEmpresa() {
		return telefoneEmpresa;
	}

	public void setTelefoneEmpresa(String telefoneEmpresa) {
		this.telefoneEmpresa = telefoneEmpresa;
	}

	public String getEmailEmpresa() {
		return emailEmpresa;
	}

	public void setEmailEmpresa(String emailEmpresa) {
		this.emailEmpresa = emailEmpresa;
	}

	public String getCepEmpresa() {
		return cepEmpresa;
	}

	public void setCepEmpresa(String cepEmpresa) {
		this.cepEmpresa = cepEmpresa;
	}

	public String getDiaPatio() {
		return diaPatio;
	}

	public void setDiaPatio(String diaPatio) {
		this.diaPatio = diaPatio;
	}

	public String getTotalDespesas() {
		return totalDespesas;
	}

	public void setTotalDespesas(String totalDespesas) {
		this.totalDespesas = totalDespesas;
	}

	public String getValorEntrada() {
		return valorEntrada;
	}

	public void setValorEntrada(String valorEntrada) {
		this.valorEntrada = valorEntrada;
	}

	public String getCustoTotalVeiculo() {
		return custoTotalVeiculo;
	}

	public void setCustoTotalVeiculo(String custoTotalVeiculo) {
		this.custoTotalVeiculo = custoTotalVeiculo;
	}

	public String getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(String veiculo) {
		this.veiculo = veiculo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getRenavam() {
		return renavam;
	}

	public void setRenavam(String renavam) {
		this.renavam = renavam;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(String dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public String getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(String valorVenda) {
		this.valorVenda = valorVenda;
	}

	public String getVendaDataVenda() {
		return vendaDataVenda;
	}

	public void setVendaDataVenda(String vendaDataVenda) {
		this.vendaDataVenda = vendaDataVenda;
	}

	public String getVendaLucroVenda() {
		return vendaLucroVenda;
	}

	public void setVendaLucroVenda(String vendaLucroVenda) {
		this.vendaLucroVenda = vendaLucroVenda;
	}

	public String getVendaCliente() {
		return vendaCliente;
	}

	public void setVendaCliente(String vendaCliente) {
		this.vendaCliente = vendaCliente;
	}

	public String getVendaTipoPagamento() {
		return vendaTipoPagamento;
	}

	public void setVendaTipoPagamento(String vendaTipoPagamento) {
		this.vendaTipoPagamento = vendaTipoPagamento;
	}

	public String getVendaParcela() {
		return vendaParcela;
	}

	public void setVendaParcela(String vendaParcela) {
		this.vendaParcela = vendaParcela;
	}

	public String getVendaValorParcela() {
		return vendaValorParcela;
	}

	public void setVendaValorParcela(String vendaValorParcela) {
		this.vendaValorParcela = vendaValorParcela;
	}

	public String getGarantia() {
		return garantia;
	}

	public void setGarantia(String garantia) {
		this.garantia = garantia;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getDataGarantiaFormatada() {
		return dataGarantiaFormatada;
	}

	public void setDataGarantiaFormatada(String dataGarantiaFormatada) {
		this.dataGarantiaFormatada = dataGarantiaFormatada;
	}
}

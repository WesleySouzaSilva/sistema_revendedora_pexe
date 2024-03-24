package br.com.sistema.model;

import java.math.BigDecimal;

public class ClienteCrm {

	private Integer id;
	private String nomeCliente, telefone, tipo, marca, modelo;
	private BigDecimal valorInicial, valorFinal;
	private String veiculo, categoria, marcaVeiculo, anoModelo, valorVenda, cor, placa, km;

	public ClienteCrm() {

	}

	public ClienteCrm(Integer id, String nomeCliente, String telefone, String tipo, String marca, String modelo,
			BigDecimal valorInicial, BigDecimal valorFinal) {
		this.id = id;
		this.nomeCliente = nomeCliente;
		this.telefone = telefone;
		this.tipo = tipo;
		this.marca = marca;
		this.modelo = modelo;
		this.valorInicial = valorInicial;
		this.valorFinal = valorFinal;
	}

	public ClienteCrm(Integer id, String nomeCliente, String telefone, String tipo, String marca, String modelo,
			BigDecimal valorInicial, BigDecimal valorFinal, String veiculo, String categoria, String marcaVeiculo,
			String anoModelo, String valorVenda, String cor, String placa, String km) {
		super();
		this.id = id;
		this.nomeCliente = nomeCliente;
		this.telefone = telefone;
		this.tipo = tipo;
		this.marca = marca;
		this.modelo = modelo;
		this.valorInicial = valorInicial;
		this.valorFinal = valorFinal;
		this.veiculo = veiculo;
		this.categoria = categoria;
		this.marcaVeiculo = marcaVeiculo;
		this.anoModelo = anoModelo;
		this.valorVenda = valorVenda;
		this.cor = cor;
		this.placa = placa;
		this.km = km;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public BigDecimal getValorInicial() {
		return valorInicial;
	}

	public void setValorInicial(BigDecimal valorInicial) {
		this.valorInicial = valorInicial;
	}

	public BigDecimal getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(BigDecimal valorFinal) {
		this.valorFinal = valorFinal;
	}

	public String getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(String veiculo) {
		this.veiculo = veiculo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getMarcaVeiculo() {
		return marcaVeiculo;
	}

	public void setMarcaVeiculo(String marcaVeiculo) {
		this.marcaVeiculo = marcaVeiculo;
	}

	public String getAnoModelo() {
		return anoModelo;
	}

	public void setAnoModelo(String anoModelo) {
		this.anoModelo = anoModelo;
	}

	public String getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(String valorVenda) {
		this.valorVenda = valorVenda;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getKm() {
		return km;
	}

	public void setKm(String km) {
		this.km = km;
	}

}

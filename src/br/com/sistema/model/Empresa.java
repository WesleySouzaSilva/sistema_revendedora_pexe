package br.com.sistema.model;

public class Empresa {

	private Integer id;
	private String nome, cnpj;
	private Endereco endereco;
	private Telefone telefone;
	private Email email;
	private String rua, bairro, cep, numero, cidade, estado, telCelular, telComercial, telResidencial, telWhatsapp,
			emailFormatado;

	public Empresa() {

	}

	public Empresa(Integer id, String nome, String cnpj, Endereco endereco, Telefone telefone, Email email) {
		super();
		this.id = id;
		this.nome = nome;
		this.cnpj = cnpj;
		this.endereco = endereco;
		this.telefone = telefone;
		this.email = email;
	}

	public Empresa(Integer id, String nome, String cnpj, String cidade, String estado) {
		super();
		this.id = id;
		this.nome = nome;
		this.cnpj = cnpj;
		this.cidade = cidade;
		this.estado = estado;
	}

	public Empresa(Integer id, String nome, String cnpj, String rua, String bairro, String cep, String numero,
			String cidade, String estado, String telCelular, String telComercial, String telResidencial,
			String telWhatsapp, String emailFormatado) {
		super();
		this.id = id;
		this.nome = nome;
		this.cnpj = cnpj;
		this.rua = rua;
		this.bairro = bairro;
		this.cep = cep;
		this.numero = numero;
		this.cidade = cidade;
		this.estado = estado;
		this.telCelular = telCelular;
		this.telComercial = telComercial;
		this.telResidencial = telResidencial;
		this.telWhatsapp = telWhatsapp;
		this.emailFormatado = emailFormatado;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
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

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTelCelular() {
		return telCelular;
	}

	public void setTelCelular(String telCelular) {
		this.telCelular = telCelular;
	}

	public String getTelComercial() {
		return telComercial;
	}

	public void setTelComercial(String telComercial) {
		this.telComercial = telComercial;
	}

	public String getTelResidencial() {
		return telResidencial;
	}

	public void setTelResidencial(String telResidencial) {
		this.telResidencial = telResidencial;
	}

	public String getTelWhatsapp() {
		return telWhatsapp;
	}

	public void setTelWhatsapp(String telWhatsapp) {
		this.telWhatsapp = telWhatsapp;
	}

	public String getEmailFormatado() {
		return emailFormatado;
	}

	public void setEmailFormatado(String emailFormatado) {
		this.emailFormatado = emailFormatado;
	}

}

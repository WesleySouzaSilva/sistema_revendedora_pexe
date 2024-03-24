package br.com.sistema.model;

import java.util.Date;

public class Funcionario {

	private Integer id;
	private String nome;
	private String profissao;
	private String ativo;
	private Endereco endereco;
	private Telefone telefone;
	private Email email;
	private String salarioFormatado, dataAdmissaoFormatado, dataDemissaoFormatado;
	private Date dataAdmissao, demissao;
	private String rua, bairro, cidade, estado, cep, numero, telCelular, telComercial, telResidencial, telWhatsapp,
			emailFormatado;
	private String somaSalarios;

	public Funcionario() {

	}

	public Funcionario(Integer id, String nome, String profissao, String ativo, String salarioFormatado,
			String dataAdmissaoFormatado, String dataDemissaoFormatado) {
		super();
		this.id = id;
		this.nome = nome;
		this.profissao = profissao;
		this.ativo = ativo;
		this.salarioFormatado = salarioFormatado;
		this.dataAdmissaoFormatado = dataAdmissaoFormatado;
		this.dataDemissaoFormatado = dataDemissaoFormatado;
	}

	public Funcionario(Integer id, String nome, String profissao, String ativo, Endereco endereco, Telefone telefone,
			Email email, Date dataAdmissao, Date demissao) {
		super();
		this.id = id;
		this.nome = nome;
		this.profissao = profissao;
		this.ativo = ativo;
		this.endereco = endereco;
		this.telefone = telefone;
		this.email = email;
		this.dataAdmissao = dataAdmissao;
		this.demissao = demissao;
	}

	public Funcionario(Integer id, String rua, String bairro, String cidade, String estado, String cep, String numero,
			String telCelular, String telComercial, String telResidencial, String telWhatsapp, String emailFormatado) {
		super();
		this.id = id;
		this.rua = rua;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.cep = cep;
		this.numero = numero;
		this.telCelular = telCelular;
		this.telComercial = telComercial;
		this.telResidencial = telResidencial;
		this.telWhatsapp = telWhatsapp;
		this.emailFormatado = emailFormatado;
	}

	public Funcionario(Integer id, String nome, String profissao, String ativo, Endereco endereco, Telefone telefone,
			Email email, String salarioFormatado, Date dataAdmissao, String dataAdmissaoFormatado, String rua,
			String bairro, String cidade, String estado, String cep, String numero, String telCelular,
			String telComercial, String telResidencial, String telWhatsapp, String emailFormatado) {
		super();
		this.id = id;
		this.nome = nome;
		this.profissao = profissao;
		this.ativo = ativo;
		this.endereco = endereco;
		this.telefone = telefone;
		this.email = email;
		this.salarioFormatado = salarioFormatado;
		this.dataAdmissao = dataAdmissao;
		this.dataAdmissaoFormatado = dataAdmissaoFormatado;
		this.rua = rua;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.cep = cep;
		this.numero = numero;
		this.telCelular = telCelular;
		this.telComercial = telComercial;
		this.telResidencial = telResidencial;
		this.telWhatsapp = telWhatsapp;
		this.emailFormatado = emailFormatado;
	}

	public Funcionario(String nome, String profissao, String ativo, String salarioFormatado,
			String dataAdmissaoFormatado, String dataDemissaoFormatado, String somaSalarios) {
		super();
		this.nome = nome;
		this.profissao = profissao;
		this.ativo = ativo;
		this.salarioFormatado = salarioFormatado;
		this.dataAdmissaoFormatado = dataAdmissaoFormatado;
		this.dataDemissaoFormatado = dataDemissaoFormatado;
		this.somaSalarios = somaSalarios;
	}

	public Funcionario(String somaSalarios) {
		super();
		this.somaSalarios = somaSalarios;
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

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
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

	public String getSalarioFormatado() {
		return salarioFormatado;
	}

	public void setSalarioFormatado(String salarioFormatado) {
		this.salarioFormatado = salarioFormatado;
	}

	public String getDataAdmissaoFormatado() {
		return dataAdmissaoFormatado;
	}

	public void setDataAdmissaoFormatado(String dataAdmissaoFormatado) {
		this.dataAdmissaoFormatado = dataAdmissaoFormatado;
	}

	public String getDataDemissaoFormatado() {
		return dataDemissaoFormatado;
	}

	public void setDataDemissaoFormatado(String dataDemissaoFormatado) {
		this.dataDemissaoFormatado = dataDemissaoFormatado;
	}

	public Date getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public Date getDemissao() {
		return demissao;
	}

	public void setDemissao(Date demissao) {
		this.demissao = demissao;
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

	public String getSomaSalarios() {
		return somaSalarios;
	}

	public void setSomaSalarios(String somaSalarios) {
		this.somaSalarios = somaSalarios;
	}

	@Override
	public String toString() {
		return this.getNome();
	}

}

package br.com.sistema.model;

import java.util.Date;

public class Pessoa {

	private Integer id;
	private String nome;
	private String cpfcnpj;
	private String rg;
	private String sexo;
	private Date dataNascimento;
	private String Iestadual;
	private Endereco endereco;
	private Telefone telefone;
	private Email email;
	private String tipo;
	private String ativo;
	private String dataNascimentoFormatada;
	private String rua, bairro;

	public Pessoa() {
		this(null, null, null, null, null, null, null, null, null, null, null, null);
	}

	public Pessoa(Integer id, String nome, String cpfcnpj, String rg, String tipo, String ativo) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpfcnpj = cpfcnpj;
		this.rg = rg;
		this.ativo = ativo;
		this.tipo = tipo;
	}

	public Pessoa(Integer id, String nome, String cpfcnpj, String rg, String sexo, Date dataNascimento,
			String iestadual, Endereco endereco, Telefone telefone, Email email) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpfcnpj = cpfcnpj;
		this.rg = rg;
		this.sexo = sexo;
		this.dataNascimento = dataNascimento;
		Iestadual = iestadual;
		this.endereco = endereco;
		this.telefone = telefone;
		this.email = email;
	}

	public Pessoa(Integer id, String nome, String cpfcnpj, String rg, String sexo, Date dataNascimento,
			String iestadual, Endereco endereco, Telefone telefone, Email email, String ativo, String tipo) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpfcnpj = cpfcnpj;
		this.rg = rg;
		this.sexo = sexo;
		this.dataNascimento = dataNascimento;
		this.Iestadual = iestadual;
		this.endereco = endereco;
		this.telefone = telefone;
		this.email = email;
		this.tipo = tipo;
		this.ativo = ativo;
	}

	public Pessoa(Integer id, String nome, String cpfcnpj, String rua, String bairro) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpfcnpj = cpfcnpj;
		this.rua = rua;
		this.bairro = bairro;
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

	public String getCpfcnpj() {
		return cpfcnpj;
	}

	public void setCpfcnpj(String cpfcnpj) {
		this.cpfcnpj = cpfcnpj;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getIestadual() {
		return Iestadual;
	}

	public void setIestadual(String iestadual) {
		Iestadual = iestadual;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public String getDataNascimentoFormatada() {
		return dataNascimentoFormatada;
	}

	public void setDataNascimentoFormatada(String dataNascimentoFormatada) {
		this.dataNascimentoFormatada = dataNascimentoFormatada;
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

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", cpfcnpj=" + cpfcnpj + ", rg=" + rg + ", sexo=" + sexo
				+ ", dataNascimento=" + dataNascimento + ", Iestadual=" + Iestadual + ", endereco=" + endereco
				+ ", telefone=" + telefone + ", email=" + email + "]";
	}

}
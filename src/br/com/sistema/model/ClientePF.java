package br.com.sistema.model;

import java.util.Date;

public class ClientePF {

	private Integer id;
	private Date dataNascimento;
	private String rg, sexo;
	private Endereco endereco;
	private Telefone telefone;
	private Email email;

	public ClientePF() {
		this(null, null, null, null, null, null, null);
	}

	public ClientePF(Integer id, Date dataNascimento, String rg, String sexo, Endereco endereco, Telefone telefone,
			Email email) {
		super();
		this.id = id;
		this.dataNascimento = dataNascimento;

		this.rg = rg;
		this.sexo = sexo;
		this.endereco = endereco;
		this.telefone = telefone;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
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

}

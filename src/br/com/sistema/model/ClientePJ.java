package br.com.sistema.model;

public class ClientePJ {

	private Integer id;
	private String Iestadual;
	private Endereco endereco;
	private Telefone telefone;
	private Email email;

	public ClientePJ() {
		this(null, null, null, null, null);
	}

	public ClientePJ(Integer id, String iestadual, Endereco endereco, Telefone telefone, Email email) {
		super();
		this.id = id;

		Iestadual = iestadual;
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

}

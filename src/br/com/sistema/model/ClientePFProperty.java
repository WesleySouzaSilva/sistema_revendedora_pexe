package br.com.sistema.model;

public class ClientePFProperty {

	private Integer id;
	private String nome;
	private String cpfcnpj;
	private String rg;
	private String sexo;
	private String dataNascimento;
	private String Iestadual;
	private Endereco endereco;
	private Telefone telefone;
	private Email email;

	public ClientePFProperty() {
		this(null, null, null, null, null, null, null, null, null, null);
	}

	public ClientePFProperty(Integer id, String nome, String cpfcnpj, String rg, String sexo, String dataNascimento,
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

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
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

}

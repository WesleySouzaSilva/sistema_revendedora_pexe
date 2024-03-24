package br.com.sistema.model;

public class ClienteSerasa {

	private Integer id;
	private String email;
	private String senha;

	private String documento;

	public ClienteSerasa() {
		super();
	}

	public ClienteSerasa(Integer id, String email, String senha, String documento) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.documento = documento;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

}

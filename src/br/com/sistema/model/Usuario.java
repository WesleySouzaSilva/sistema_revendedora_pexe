package br.com.sistema.model;

public class Usuario {

	private Integer id;
	private String nome;
	private String senha;
	private String Permissao;

	public Usuario() {
		this(null, null, null, null);
	}

	public Usuario(Integer id, String nome, String senha, String permissao) {
		super();
		this.id = id;
		this.nome = nome;
		this.senha = senha;
		Permissao = permissao;

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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getPermissao() {
		return Permissao;
	}

	public String setPermissao(String Permissao) {
		this.Permissao = Permissao;
		return Permissao;
	}

	@Override
	public String toString() {
		return String.format("%d, %s, %s, %s", id, nome, senha, Permissao);
	}
}

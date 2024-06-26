package br.com.sistema.model;

public class Email {

	private Integer id;
	private String email;

	public Email() {
		this(null, null);
	}

	public Email(Integer id, String email) {
		super();
		this.id = id;
		this.email = email;
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

}

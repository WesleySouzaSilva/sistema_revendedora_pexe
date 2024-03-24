package br.com.sistema.model;

public class MarcaVeiculo {

	private String id;
	private String name;
	private String fipe_name;
	private String order;
	private String key;

	public MarcaVeiculo(String id, String name, String fipe_name, String order, String key) {
		super();
		this.id = id;
		this.name = name;
		this.fipe_name = fipe_name;
		this.order = order;
		this.key = key;
	}

	public MarcaVeiculo() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFipe_name() {
		return fipe_name;
	}

	public void setFipe_name(String fipe_name) {
		this.fipe_name = fipe_name;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}
}

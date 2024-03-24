package br.com.sistema.model;

public class ModeloVeiculo {
	private String id;
	private String name;
	private String key;
	private String fipe_name;
	private String fipe_marca;
	private String marca;

	public ModeloVeiculo() {

	}

	public ModeloVeiculo(String id, String name, String key, String fipe_name, String fipe_marca, String marca) {
		super();
		this.id = id;
		this.name = name;
		this.key = key;
		this.fipe_name = fipe_name;
		this.fipe_marca = fipe_marca;
		this.marca = marca;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getFipe_name() {
		return fipe_name;
	}

	public void setFipe_name(String fipe_name) {
		this.fipe_name = fipe_name;
	}

	public String getFipe_marca() {
		return fipe_marca;
	}

	public void setFipe_marca(String fipe_marca) {
		this.fipe_marca = fipe_marca;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}

}

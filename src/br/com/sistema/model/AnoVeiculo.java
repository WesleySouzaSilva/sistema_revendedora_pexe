package br.com.sistema.model;

public class AnoVeiculo {

	private String id;
	private String ano_modelo;
	private String fipe_marca;
	private String fipe_codigo;
	private String name;
	private String marca;
	private String key;
	private String veiculo;

	public AnoVeiculo() {

	}

	public AnoVeiculo(String id, String ano_modelo, String fipe_marca, String fipe_codigo, String name, String marca,
			String key, String veiculo) {
		super();
		this.id = id;
		this.ano_modelo = ano_modelo;
		this.fipe_marca = fipe_marca;
		this.fipe_codigo = fipe_codigo;
		this.name = name;
		this.marca = marca;
		this.key = key;
		this.veiculo = veiculo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAno_modelo() {
		return ano_modelo;
	}

	public void setAno_modelo(String ano_modelo) {
		this.ano_modelo = ano_modelo;
	}

	public String getFipe_marca() {
		return fipe_marca;
	}

	public void setFipe_marca(String fipe_marca) {
		this.fipe_marca = fipe_marca;
	}

	public String getFipe_codigo() {
		return fipe_codigo;
	}

	public void setFipe_codigo(String fipe_codigo) {
		this.fipe_codigo = fipe_codigo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(String veiculo) {
		this.veiculo = veiculo;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}

}

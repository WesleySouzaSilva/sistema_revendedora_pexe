package br.com.sistema.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JOptionPane;

public class BuscaVeiculos {

	public String buscarMarca(String urlAPI) {
		String json;

		try {

			URL url = new URL(urlAPI);
			URLConnection connection = url.openConnection();
			connection.connect();
			System.out.println("conectou no servidor : " + connection);

			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			StringBuilder jsonSb = new StringBuilder();

			br.lines().forEach(l -> jsonSb.append(l.trim()));

			json = jsonSb.toString();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro de conexão com o servidor.\nVerifique a sua conexão com a internet!");

			throw new RuntimeException(e);
		}

		return json;
	}

	public String buscarModelo(String urlAPI) {
		String json;

		try {

			URL url = new URL(urlAPI);
			URLConnection connection = url.openConnection();
			connection.connect();
			System.out.println("conectou no servidor : " + connection);

			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			StringBuilder jsonSb = new StringBuilder();

			br.lines().forEach(l -> jsonSb.append(l.trim()));

			json = jsonSb.toString();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro de conexão com o servidor.\nVerifique a sua conexão com a internet!");

			throw new RuntimeException(e);
		}

		return json;
	}

	public String buscarAnoModelo(String urlAPI) {
		String json;

		try {

			URL url = new URL(urlAPI);
			URLConnection connection = url.openConnection();
			connection.connect();
			System.out.println("conectou no servidor : " + connection);

			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			StringBuilder jsonSb = new StringBuilder();

			br.lines().forEach(l -> jsonSb.append(l.trim()));

			json = jsonSb.toString();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro de conexão com o servidor.\nVerifique a sua conexão com a internet!");

			throw new RuntimeException(e);
		}

		return json;
	}

	public String buscarVeiculo(String urlAPI, String tipo, String modelo) {
		String json;

		try {

			URL url = new URL(urlAPI + tipo + "/" + modelo + ".json");
			URLConnection connection = url.openConnection();
			connection.connect();
			System.out.println("conectou no servidor : " + connection);

			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			StringBuilder jsonSb = new StringBuilder();

			br.lines().forEach(l -> jsonSb.append(l.trim()));

			json = jsonSb.toString();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro de conexão com o servidor.\nVerifique a sua conexão com a internet!");

			throw new RuntimeException(e);
		}

		return json;
	}

	// public static void main(String[] args) throws IOException {
	// String json = buscarMarca("http://fipeapi.appspot.com/api/1/","carros",
	// "marcas");
	// System.out.println(json);
	//
	// Map<String, String> mapa = new HashMap<>();
	//
	// Matcher matcher = Pattern.compile("\"\\D.*?\": \".*?\"").matcher(json);
	// while (matcher.find()) {
	// String[] group = matcher.group().split(":");
	// mapa.put(group[0].replaceAll("\"", "").trim(), group[1].replaceAll("\"",
	// "").trim());
	// }
	//
	// if (mapa == null || mapa.isEmpty()) {
	// System.out.println("não achou as informações......");
	// } else {
	// System.out.println(mapa);
	// }
	// }

}

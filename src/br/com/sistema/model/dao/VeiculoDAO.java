package br.com.sistema.model.dao;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.sistema.conexao.AbstractGenericDAO;
import br.com.sistema.conexao.Conexao;
import br.com.sistema.model.Veiculo;
import br.com.sistema.model.VeiculoRelatorio;

public class VeiculoDAO extends AbstractGenericDAO<Veiculo> {

	public VeiculoDAO(Conexao conexao) {
		super(conexao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean inserir(Veiculo pojo) {
		String sql = "INSERT INTO veiculo(categoria, veiculo, marca, ano_modelo, data_entrada, valor_entrada, valor_venda, renavam, placa, cor, km, pessoa_id, situacao, leilao_rs, financiamento, valor_fipe) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, pojo.getCategoria());
			cmd.setString(2, pojo.getVeiculo());
			cmd.setString(3, pojo.getMarca());
			cmd.setString(4, pojo.getAno_modelo());
			cmd.setDate(5, (Date) pojo.getDataEntrada());
			cmd.setBigDecimal(6, pojo.getValorEntrada());
			cmd.setBigDecimal(7, pojo.getValorVenda());
			cmd.setString(8, pojo.getRenavam());
			cmd.setString(9, pojo.getPlaca());
			cmd.setString(10, pojo.getCor());
			cmd.setString(11, pojo.getKm());
			cmd.setInt(12, pojo.getPessoa().getId());
			cmd.setString(13, pojo.getSituacao());
			cmd.setString(14, pojo.getSinistroRs());
			cmd.setString(15, pojo.getFinanciamento());
			cmd.setBigDecimal(16, pojo.getValorFipe());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no prÃ³prio objeto
				pojo.setId(ultimoID("veiculo"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean apagar(Veiculo pojo) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean apagarVeiculo(Integer id) {
		String sqlPessoa = "DELETE FROM veiculo WHERE id =?";

		try {

			PreparedStatement cmdPessoa = dbConnection.prepareStatement(sqlPessoa);
			cmdPessoa.setInt(1, id);
			int retornoPessoa = cmdPessoa.executeUpdate();
			cmdPessoa.close();
			return retornoPessoa > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean apagarDespesaVeiculo(Integer id) {
		String sqlPessoa = "DELETE FROM despesas WHERE veiculo_id =?";

		try {

			PreparedStatement cmdPessoa = dbConnection.prepareStatement(sqlPessoa);
			cmdPessoa.setInt(1, id);
			int retornoPessoa = cmdPessoa.executeUpdate();
			cmdPessoa.close();
			return retornoPessoa > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean atualizar(Veiculo novo) {
		String sql = "UPDATE veiculo SET categoria = ?, marca = ?, veiculo = ?, placa = ?, renavam = ?, cor = ?, km = ?, ano_modelo = ?, valor_entrada = ? , valor_venda = ?, leilao_rs = ?, financiamento = ?, situacao = ?, valor_fipe = ? WHERE id = ?";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, novo.getCategoria());
			cmd.setString(2, novo.getMarca());
			cmd.setString(3, novo.getVeiculo());
			cmd.setString(4, novo.getPlaca());
			cmd.setString(5, novo.getRenavam());
			cmd.setString(6, novo.getCor());
			cmd.setString(7, novo.getKm());
			cmd.setString(8, novo.getAno_modelo());
			cmd.setBigDecimal(9, novo.getValorEntrada());
			cmd.setBigDecimal(10, novo.getValorVenda());
			cmd.setString(11, novo.getSinistroRs());
			cmd.setString(12, novo.getFinanciamento());
			cmd.setString(13, novo.getSituacao());
			cmd.setBigDecimal(14, novo.getValorFipe());
			cmd.setInt(15, novo.getId());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no prÃ³prio objeto
				novo.setId(ultimoID("veiculo"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean atualizarSituacao(Veiculo novo) {
		String sql = "UPDATE veiculo SET situacao = ?, data_venda = ?, vendedor = ? WHERE id = ?";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, novo.getSituacao());
			cmd.setDate(2, new java.sql.Date(novo.getDataVenda().getTime()));
			cmd.setString(3, novo.getVendedor());
			cmd.setInt(4, novo.getId());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no prÃ³prio objeto
				novo.setId(ultimoID("veiculo"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Veiculo> listarTodos() {
		return null;
	}

	public List<Veiculo> listarTodosId(Integer id) {
		String sql = "SELECT v.id, v.categoria, v.veiculo, v.marca, v.ano_modelo, DATE_FORMAT(v.data_entrada, '%d/%m/%Y'), DATE_FORMAT(v.data_venda, '%d/%m/%Y'), FORMAT(v.valor_entrada,2,'de_DE'), FORMAT(v.valor_venda,2,'de_DE'), v.renavam, v.placa, v.cor, v.km, v.situacao, v.leilao_rs, v.financiamento, v.valor_fipe, FORMAT(v.valor_fipe,2,'de_DE') FROM veiculo AS v WHERE v.id = "
				+ id + "";
		Veiculo veiculo = null;
		ArrayList<Veiculo> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				Integer ids = rs.getInt("v.id");
				String veicul = rs.getString("v.veiculo");
				String marca = rs.getString("v.marca");
				String ano_modelo = rs.getString("v.ano_modelo");
				String data_entrada = rs.getString("DATE_FORMAT(v.data_entrada, '%d/%m/%Y')");
				String data_venda = rs.getString("DATE_FORMAT(v.data_venda, '%d/%m/%Y')");
				String valor_entrada = rs.getString("FORMAT(v.valor_entrada,2,'de_DE')");
				String valor_venda = rs.getString("FORMAT(v.valor_venda,2,'de_DE')");
				String renavam = rs.getString("v.renavam");
				String placa = rs.getString("v.placa");
				String cor = rs.getString("v.cor");
				String situacao = rs.getString("v.situacao");
				String sinistroRs = rs.getString("v.leilao_rs");
				String financiamento = rs.getString("v.financiamento");
				String categoria = rs.getString("v.categoria");
				String km = rs.getString("v.km");
				BigDecimal valorFipe = rs.getBigDecimal("v.valor_fipe");
				String valorFipeFormatado = rs.getString("FORMAT(v.valor_fipe,2,'de_DE')");

				veiculo = new Veiculo(ids, veicul, marca, ano_modelo, renavam, placa, cor, situacao, data_entrada,
						valor_entrada, valor_venda, data_venda, null, null, null, null, null, sinistroRs, financiamento,
						categoria, km, valorFipe, valorFipeFormatado, null, null);

				lista.add(veiculo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<VeiculoRelatorio> listarDadosVenda(Integer veiculo_id) {
		String sql = "SELECT v.id, DATE_FORMAT(v.data_venda, '%d/%m/%Y'), FORMAT(v.lucro_venda,2,'de_DE'), DATE_FORMAT(v.data_garantia, '%d/%m/%Y'), v.tipo_pagamento, v.parcela, v.observacao, v.garantia, FORMAT(v.valor_parcela,2,'de_DE'), p.nome FROM pagamento_veiculo AS v, pessoa AS p WHERE v.veiculo_id = "
				+ veiculo_id + " AND p.id = v.pessoa_id";
		VeiculoRelatorio veiculo = null;
		ArrayList<VeiculoRelatorio> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				String data_venda = rs.getString("DATE_FORMAT(v.data_venda, '%d/%m/%Y')");
				String lucro_venda = rs.getString("FORMAT(v.lucro_venda,2,'de_DE')");
				String tipoPagamento = rs.getString("v.tipo_pagamento");
				String parcela = rs.getString("v.parcela");
				String valor_parcela = rs.getString("FORMAT(v.valor_parcela,2,'de_DE')");
				String nomeCliente = rs.getString("p.nome");
				String garantia = rs.getString("v.garantia");
				String observacao = rs.getString("v.observacao");
				String dataGarantia = rs.getString("DATE_FORMAT(v.data_garantia, '%d/%m/%Y')");

				veiculo = new VeiculoRelatorio(data_venda, lucro_venda, nomeCliente, tipoPagamento, parcela,
						valor_parcela, garantia, observacao, dataGarantia);

				lista.add(veiculo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<Veiculo> listarTodosNome(String buscar) {
		String sql = "SELECT v.id, v.categoria, v.veiculo, v.marca, v.ano_modelo, DATE_FORMAT(v.data_entrada, '%d/%m/%Y'), DATE_FORMAT(v.data_venda, '%d/%m/%Y'), FORMAT(v.valor_entrada,2,'de_DE'), FORMAT(v.valor_venda,2,'de_DE'), v.renavam, v.placa, v.cor, v.km, v.situacao, v.leilao_rs, v.financiamento, v.valor_fipe, FORMAT(v.valor_fipe,2,'de_DE') FROM veiculo AS v WHERE "
				+ buscar + " ORDER BY " + buscar + "";
		Veiculo veiculo = null;
		ArrayList<Veiculo> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				Integer id = rs.getInt("v.id");
				String veicul = rs.getString("v.veiculo");
				String marca = rs.getString("v.marca");
				String ano_modelo = rs.getString("v.ano_modelo");
				String data_entrada = rs.getString("DATE_FORMAT(v.data_entrada, '%d/%m/%Y')");
				String data_venda = rs.getString("DATE_FORMAT(v.data_venda, '%d/%m/%Y')");
				String valor_entrada = rs.getString("FORMAT(v.valor_entrada,2,'de_DE')");
				String valor_venda = rs.getString("FORMAT(v.valor_venda,2,'de_DE')");
				String renavam = rs.getString("v.renavam");
				String placa = rs.getString("v.placa");
				String cor = rs.getString("v.cor");
				String situacao = rs.getString("v.situacao");
				String sinistroRs = rs.getString("v.leilao_rs");
				String financiamento = rs.getString("v.financiamento");
				String categoria = rs.getString("v.categoria");
				String km = rs.getString("v.km");
				BigDecimal valorFipe = rs.getBigDecimal("v.valor_fipe");
				String valorFipeFormatado = rs.getString("FORMAT(v.valor_fipe,2,'de_DE')");

				veiculo = new Veiculo(id, veicul, marca, ano_modelo, renavam, placa, cor, situacao, data_entrada,
						valor_entrada, valor_venda, data_venda, null, null, null, null, null, sinistroRs, financiamento,
						categoria, km, valorFipe, valorFipeFormatado, null, null);

				lista.add(veiculo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<Veiculo> listarTodosNome(String tipo, String buscar) {
		String sql = "SELECT v.id, v.categoria, v.veiculo, v.marca, v.ano_modelo, DATE_FORMAT(v.data_entrada, '%d/%m/%Y'), DATE_FORMAT(v.data_venda, '%d/%m/%Y'), v.valor_venda, FORMAT(v.valor_entrada,2,'de_DE'), FORMAT(v.valor_venda,2,'de_DE'), v.renavam, v.placa, v.cor, v.km, v.situacao, v.leilao_rs, v.financiamento, v.valor_fipe, FORMAT(v.valor_fipe,2,'de_DE') FROM veiculo AS v WHERE v.situacao = '"
				+ tipo + "' ORDER BY " + buscar + "";
		Veiculo veiculo = null;
		ArrayList<Veiculo> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				Integer id = rs.getInt("v.id");
				String veicul = rs.getString("v.veiculo");
				String marca = rs.getString("v.marca");
				String ano_modelo = rs.getString("v.ano_modelo");
				String data_entrada = rs.getString("DATE_FORMAT(v.data_entrada, '%d/%m/%Y')");
				String data_venda = rs.getString("DATE_FORMAT(v.data_venda, '%d/%m/%Y')");
				String valor_entrada = rs.getString("FORMAT(v.valor_entrada,2,'de_DE')");
				String valor_venda = rs.getString("FORMAT(v.valor_venda,2,'de_DE')");
				String renavam = rs.getString("v.renavam");
				String placa = rs.getString("v.placa");
				String cor = rs.getString("v.cor");
				String situacao = rs.getString("v.situacao");
				String sinistroRs = rs.getString("v.leilao_rs");
				String financiamento = rs.getString("v.financiamento");
				String categoria = rs.getString("v.categoria");
				String km = rs.getString("v.km");
				BigDecimal valorFipe = rs.getBigDecimal("v.valor_fipe");
				BigDecimal valorVen = rs.getBigDecimal("v.valor_venda");
				String valorFipeFormatado = rs.getString("FORMAT(v.valor_fipe,2,'de_DE')");

				veiculo = new Veiculo(id, veicul, marca, ano_modelo, renavam, placa, cor, situacao, data_entrada,
						valor_entrada, valor_venda, data_venda, null, null, null, valorVen, null, sinistroRs,
						financiamento, categoria, km, valorFipe, valorFipeFormatado, null, null);

				lista.add(veiculo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<Veiculo> listarTodosEstoque() {
		String sql = "SELECT v.id, v.categoria, v.veiculo, v.marca, v.ano_modelo, DATE_FORMAT(v.data_entrada, '%d/%m/%Y'), DATE_FORMAT(v.data_venda, '%d/%m/%Y'), FORMAT(v.valor_entrada,2,'de_DE'), FORMAT(v.valor_venda,2,'de_DE'), v.renavam, v.placa, v.cor, v.km, v.situacao, v.leilao_rs, v.financiamento, v.valor_fipe, FORMAT(v.valor_fipe,2,'de_DE') FROM veiculo AS v WHERE v.situacao = 'ESOQUE' ORDER BY v.data_entrada";
		Veiculo veiculo = null;
		ArrayList<Veiculo> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				Integer id = rs.getInt("v.id");
				String veicul = rs.getString("v.veiculo");
				String marca = rs.getString("v.marca");
				String ano_modelo = rs.getString("v.ano_modelo");
				String data_entrada = rs.getString("DATE_FORMAT(v.data_entrada, '%d/%m/%Y')");
				String data_venda = rs.getString("DATE_FORMAT(v.data_venda, '%d/%m/%Y')");
				String valor_entrada = rs.getString("FORMAT(v.valor_entrada,2,'de_DE')");
				String valor_venda = rs.getString("FORMAT(v.valor_venda,2,'de_DE')");
				String renavam = rs.getString("v.renavam");
				String placa = rs.getString("v.placa");
				String cor = rs.getString("v.cor");
				String situacao = rs.getString("v.situacao");
				String sinistroRs = rs.getString("v.leilao_rs");
				String financiamento = rs.getString("v.financiamento");
				String categoria = rs.getString("v.categoria");
				String km = rs.getString("v.km");
				BigDecimal valorFipe = rs.getBigDecimal("v.valor_fipe");
				String valorFipeFormatado = rs.getString("FORMAT(v.valor_fipe,2,'de_DE')");

				veiculo = new Veiculo(id, veicul, marca, ano_modelo, renavam, placa, cor, situacao, data_entrada,
						valor_entrada, valor_venda, data_venda, null, null, null, null, null, sinistroRs, financiamento,
						categoria, km, valorFipe, valorFipeFormatado, null, null);
				lista.add(veiculo);
				System.out.println("lista pegou situacao : " + lista.get(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<Veiculo> listarTodos(String buscar) {
		String sql = "SELECT v.id, v.categoria, v.veiculo, v.marca, v.ano_modelo, DATE_FORMAT(v.data_entrada, '%d/%m/%Y'), DATE_FORMAT(v.data_venda, '%d/%m/%Y'), FORMAT(v.valor_entrada,2,'de_DE'), FORMAT(v.valor_venda,2,'de_DE'), v.renavam, v.placa, v.cor, v.km, v.situacao, v.leilao_rs, v.financiamento, v.valor_fipe, FORMAT(v.valor_fipe,2,'de_DE') FROM veiculo AS v ORDER BY "
				+ buscar + "";
		Veiculo veiculo = null;
		ArrayList<Veiculo> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				Integer id = rs.getInt("v.id");
				String veicul = rs.getString("v.veiculo");
				String marca = rs.getString("v.marca");
				String ano_modelo = rs.getString("v.ano_modelo");
				String data_entrada = rs.getString("DATE_FORMAT(v.data_entrada, '%d/%m/%Y')");
				String data_venda = rs.getString("DATE_FORMAT(v.data_venda, '%d/%m/%Y')");
				String valor_entrada = rs.getString("FORMAT(v.valor_entrada,2,'de_DE')");
				String valor_venda = rs.getString("FORMAT(v.valor_venda,2,'de_DE')");
				String renavam = rs.getString("v.renavam");
				String placa = rs.getString("v.placa");
				String cor = rs.getString("v.cor");
				String situacao = rs.getString("v.situacao");
				String sinistroRs = rs.getString("v.leilao_rs");
				String financiamento = rs.getString("v.financiamento");
				String categoria = rs.getString("v.categoria");
				String km = rs.getString("v.km");
				BigDecimal valorFipe = rs.getBigDecimal("v.valor_fipe");
				String valorFipeFormatado = rs.getString("FORMAT(v.valor_fipe,2,'de_DE')");

				veiculo = new Veiculo(id, veicul, marca, ano_modelo, renavam, placa, cor, situacao, data_entrada,
						valor_entrada, valor_venda, data_venda, null, null, null, null, null, sinistroRs, financiamento,
						categoria, km, valorFipe, valorFipeFormatado, null, null);

				lista.add(veiculo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<Veiculo> listarTodos(String buscar, String pesquisa) {
		String sql = "SELECT v.id, v.categoria, v.veiculo, v.marca, v.ano_modelo, DATE_FORMAT(v.data_entrada, '%d/%m/%Y'), DATE_FORMAT(v.data_venda, '%d/%m/%Y'), FORMAT(v.valor_entrada,2,'de_DE'), FORMAT(v.valor_venda,2,'de_DE'), v.renavam, v.placa, v.cor, v.km, v.situacao, v.leilao_rs, v.financiamento, v.valor_fipe, FORMAT(v.valor_fipe,2,'de_DE') FROM veiculo AS v WHERE "
				+ buscar + " LIKE '" + pesquisa + "%' ORDER BY " + buscar + "";
		Veiculo veiculo = null;
		ArrayList<Veiculo> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				Integer id = rs.getInt("v.id");
				String veicul = rs.getString("v.veiculo");
				String marca = rs.getString("v.marca");
				String ano_modelo = rs.getString("v.ano_modelo");
				String data_entrada = rs.getString("DATE_FORMAT(v.data_entrada, '%d/%m/%Y')");
				String data_venda = rs.getString("DATE_FORMAT(v.data_venda, '%d/%m/%Y')");
				String valor_entrada = rs.getString("FORMAT(v.valor_entrada,2,'de_DE')");
				String valor_venda = rs.getString("FORMAT(v.valor_venda,2,'de_DE')");
				String renavam = rs.getString("v.renavam");
				String placa = rs.getString("v.placa");
				String cor = rs.getString("v.cor");
				String situacao = rs.getString("v.situacao");
				String sinistroRs = rs.getString("v.leilao_rs");
				String financiamento = rs.getString("v.financiamento");
				String categoria = rs.getString("v.categoria");
				String km = rs.getString("v.km");
				BigDecimal valorFipe = rs.getBigDecimal("v.valor_fipe");
				String valorFipeFormatado = rs.getString("FORMAT(v.valor_fipe,2,'de_DE')");

				veiculo = new Veiculo(id, veicul, marca, ano_modelo, renavam, placa, cor, situacao, data_entrada,
						valor_entrada, valor_venda, data_venda, null, null, null, null, null, sinistroRs, financiamento,
						categoria, km, valorFipe, valorFipeFormatado, null, null);

				lista.add(veiculo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<Veiculo> listarTodosNome(String tipo, String buscar, String pesquisa) {
		String sql = "SELECT v.id, v.categoria, v.veiculo, v.marca, v.ano_modelo, DATE_FORMAT(v.data_entrada, '%d/%m/%Y'), DATE_FORMAT(v.data_venda, '%d/%m/%Y'), FORMAT(v.valor_entrada,2,'de_DE'), FORMAT(v.valor_venda,2,'de_DE'), v.renavam, v.placa, v.cor, v.km, v.situacao, v.leilao_rs, v.financiamento, v.valor_fipe, FORMAT(v.valor_fipe,2,'de_DE') FROM veiculo AS v WHERE "
				+ buscar + " like '" + pesquisa + "%' AND v.situacao = '" + tipo + "' ORDER BY " + buscar + "";
		Veiculo veiculo = null;
		ArrayList<Veiculo> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				Integer id = rs.getInt("v.id");
				String veicul = rs.getString("v.veiculo");
				String marca = rs.getString("v.marca");
				String ano_modelo = rs.getString("v.ano_modelo");
				String data_entrada = rs.getString("DATE_FORMAT(v.data_entrada, '%d/%m/%Y')");
				String data_venda = rs.getString("DATE_FORMAT(v.data_venda, '%d/%m/%Y')");
				String valor_entrada = rs.getString("FORMAT(v.valor_entrada,2,'de_DE')");
				String valor_venda = rs.getString("FORMAT(v.valor_venda,2,'de_DE')");
				String renavam = rs.getString("v.renavam");
				String placa = rs.getString("v.placa");
				String cor = rs.getString("v.cor");
				String situacao = rs.getString("v.situacao");
				String sinistroRs = rs.getString("v.leilao_rs");
				String financiamento = rs.getString("v.financiamento");
				String categoria = rs.getString("v.categoria");
				String km = rs.getString("v.km");
				BigDecimal valorFipe = rs.getBigDecimal("v.valor_fipe");
				String valorFipeFormatado = rs.getString("FORMAT(v.valor_fipe,2,'de_DE')");

				veiculo = new Veiculo(id, veicul, marca, ano_modelo, renavam, placa, cor, situacao, data_entrada,
						valor_entrada, valor_venda, data_venda, null, null, null, null, null, sinistroRs, financiamento,
						categoria, km, valorFipe, valorFipeFormatado, null, null);
				lista.add(veiculo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<String> listarCategoriaMarca() {
		String sql = "SELECT DISTINCT(categoria) FROM marcas ";
		String dados = null;
		ArrayList<String> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				dados = rs.getString("categoria");

				lista.add(dados);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<String> listarModeloMarca(String categoria) {
		String sql = "SELECT * FROM marcas WHERE categoria = '" + categoria + "' ORDER BY nome ";
		String dados = null;
		ArrayList<String> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				dados = rs.getString("nome");

				lista.add(dados);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public BigDecimal getValorEntrada(Integer veiculo_id) {
		String sql = "SELECT * FROM veiculo WHERE id = '" + veiculo_id + "'";
		BigDecimal valor = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getBigDecimal("valor_entrada");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public BigDecimal getValorTotalEstoque(java.util.Date dataInicial, java.util.Date dataFinal, String campo) {
		String sql = "SELECT COALESCE(SUM(" + campo + "),0) FROM veiculo WHERE data_entrada BETWEEN '" + dataInicial
				+ "' AND '" + dataFinal + "' AND situacao = 'ESTOQUE' ";
		BigDecimal valor = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getBigDecimal("COALESCE(SUM(" + campo + "),0)");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public String getQtdeTotalEstoque(java.util.Date dataInicial, java.util.Date dataFinal) {
		String sql = "SELECT COUNT(data_entrada) FROM veiculo WHERE data_entrada BETWEEN '" + dataInicial + "' AND '"
				+ dataFinal + "' AND situacao = 'ESTOQUE' ";
		String valor = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getString("COUNT(data_entrada)");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public String getQtdeTotalEstoque() {
		String sql = "SELECT COUNT(veiculo) FROM veiculo WHERE situacao = 'ESTOQUE' ";
		String valor = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getString("COUNT(veiculo)");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public String getQtdeTotalVeiculosVendidos(java.util.Date dataInicial, java.util.Date dataFinal) {
		String sql = "SELECT COUNT(data_venda) FROM veiculo WHERE data_venda BETWEEN '" + dataInicial + "' AND '"
				+ dataFinal + "' AND situacao = 'VENDA' ";
		String valor = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getString("COUNT(data_venda)");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public BigDecimal getTotalDespesaVeiculo(Integer veiculo_id) {
		String sql = "SELECT COALESCE(SUM(valor),0) FROM despesas WHERE veiculo_id = " + veiculo_id
				+ " AND tipo LIKE 'DESPESA VEICULO%'";
		BigDecimal valor = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getBigDecimal("COALESCE(SUM(valor),0)");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public BigDecimal getValorVendaVeiculo(Integer veiculo_id) {
		String sql = "SELECT * FROM veiculo WHERE id = " + veiculo_id + "";
		BigDecimal valor = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getBigDecimal("valor_venda");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public String getQtdeVeiculos(String tipo, String buscar, String pesquisa) {
		String sql = "SELECT COUNT(id) FROM veiculo WHERE " + buscar + " like '" + pesquisa + "%' AND situacao = '"
				+ tipo + "'";
		String valor = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getString("COUNT(id)");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public String getQtdeVeiculos(String tipo) {
		String sql = "SELECT COUNT(id) FROM veiculo WHERE situacao = '" + tipo + "'";
		String valor = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getString("COUNT(id)");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public String getQtdeVeiculos() {
		String sql = "SELECT COUNT(id) FROM veiculo";
		String valor = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getString("COUNT(id)");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public BigDecimal getValorTotalSoma(String tipo, String buscar, String pesquisa) {
		String sql = "SELECT COALESCE(SUM(valor_venda),0) FROM veiculo WHERE " + buscar + " like '" + pesquisa
				+ "%' AND situacao = '" + tipo + "'";
		BigDecimal valor = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getBigDecimal("COALESCE(SUM(valor_venda),0)");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public BigDecimal getValorTotalSoma(String tipo, String campo) {
		String sql = "SELECT COALESCE(SUM(" + campo + "),0) FROM veiculo WHERE situacao = '" + tipo + "'";
		BigDecimal valor = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getBigDecimal("COALESCE(SUM(" + campo + "),0)");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public BigDecimal getValorTotalSoma(String campo) {
		String sql = "SELECT COALESCE(SUM(" + campo + "),0) FROM veiculo";
		BigDecimal valor = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getBigDecimal("COALESCE(SUM(" + campo + "),0)");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public Date getDataVeiculo(Integer veiculo_id, String campo) {
		String sql = "SELECT * FROM veiculo WHERE id = " + veiculo_id + "";
		Date valor = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getDate(campo);
			}
		} catch (Exception e) {
		}
		return valor;
	}
}

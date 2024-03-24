package br.com.sistema.model.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.sistema.conexao.AbstractGenericDAO;
import br.com.sistema.conexao.Conexao;
import br.com.sistema.model.PagamentoVeiculo;

public class PagamentoVeiculoDAO extends AbstractGenericDAO<PagamentoVeiculo> {

	public PagamentoVeiculoDAO(Conexao conexao) {
		super(conexao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean inserir(PagamentoVeiculo pojo) {
		String sql = "INSERT INTO pagamento_veiculo(data_venda, lucro_venda, tipo_pagamento, parcela, valor_parcela, observacao, garantia, data_garantia, veiculo_id, pessoa_id) VALUES (?, ? , ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);

			cmd.setDate(1, new java.sql.Date(pojo.getDataVenda().getTime()));
			cmd.setBigDecimal(2, pojo.getLucroVenda());
			cmd.setString(3, pojo.getTipoPagamento());
			cmd.setString(4, pojo.getParcela());
			cmd.setBigDecimal(5, pojo.getValorParcela());
			cmd.setString(6, pojo.getObservacao());
			cmd.setString(7, pojo.getGarantia());
			cmd.setDate(8, new java.sql.Date(pojo.getDataGarantia().getTime()));
			cmd.setInt(9, pojo.getVeiculo().getId());
			cmd.setInt(10, pojo.getPessoa().getId());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no prÃ³prio objeto
				pojo.setId(ultimoID("pagamento_veiculo"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean apagar(PagamentoVeiculo pojo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean atualizar(PagamentoVeiculo novo) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean atualizarValor(BigDecimal valor, Integer id) {
		String sql = "UPDATE pagamento_veiculo SET lucro_venda = ? WHERE id = ?";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setBigDecimal(1, valor);
			cmd.setInt(2, id);

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {

			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public List<PagamentoVeiculo> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PagamentoVeiculo> listarTodosId(Integer id) {
		String sql = "SELECT p.id, DATE_FORMAT(p.data_venda, '%d/%m/%Y'), FORMAT(p.lucro_venda,2,'de_DE'), p.tipo_pagamento, p.parcela, p.observacao, p.garantia, DATE_FORMAT(p.data_garantia, '%d/%m/%Y'), FORMAT(p.valor_parcela,2,'de_DE'), v.placa, v.veiculo, pe.nome FROM pagamento_veiculo AS p, pessoa AS pe, veiculo AS v WHERE p.id = "
				+ id + " AND pe.id = p.pessoa_id AND v.id = p.veiculo_id";
		PagamentoVeiculo veiculo = null;
		ArrayList<PagamentoVeiculo> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				Integer ids = rs.getInt("p.id");
				String data_venda = rs.getString("DATE_FORMAT(p.data_venda, '%d/%m/%Y')");
				String lucro_venda = rs.getString("FORMAT(p.lucro_venda,2,'de_DE')");
				String tipoPagamento = rs.getString("p.tipo_pagamento");
				String parcela = rs.getString("p.parcela");
				String valor_parcela = rs.getString("FORMAT(p.valor_parcela,2,'de_DE')");
				String nomeCliente = rs.getString("pe.nome");
				String observacao = rs.getString("p.observacao");
				String garantia = rs.getString("p.garantia");
				String dataGarantia = rs.getString("DATE_FORMAT(p.data_garantia, '%d/%m/%Y')");
				String placa = rs.getString("v.placa");
				String nomeVeiculo = rs.getString("v.veiculo");

				veiculo = new PagamentoVeiculo(ids, tipoPagamento, parcela, null, null, data_venda, lucro_venda,
						valor_parcela, nomeCliente, observacao, garantia, dataGarantia, placa, nomeVeiculo);
				lista.add(veiculo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<PagamentoVeiculo> listarTodosGarantia(String data) {
		String sql = "SELECT p.id, DATE_FORMAT(p.data_venda, '%d/%m/%Y'), FORMAT(p.lucro_venda,2,'de_DE'), p.tipo_pagamento, p.parcela, p.observacao, p.garantia, DATE_FORMAT(p.data_garantia, '%d/%m/%Y'), FORMAT(p.valor_parcela,2,'de_DE'), v.placa, v.veiculo, pe.nome FROM pagamento_veiculo AS p, pessoa AS pe, veiculo AS v WHERE p.data_garantia >= '"
				+ data + "' AND pe.id = p.pessoa_id AND v.id = p.veiculo_id";
		PagamentoVeiculo veiculo = null;
		ArrayList<PagamentoVeiculo> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				Integer ids = rs.getInt("p.id");
				String data_venda = rs.getString("DATE_FORMAT(p.data_venda, '%d/%m/%Y')");
				String lucro_venda = rs.getString("FORMAT(p.lucro_venda,2,'de_DE')");
				String tipoPagamento = rs.getString("p.tipo_pagamento");
				String parcela = rs.getString("p.parcela");
				String valor_parcela = rs.getString("FORMAT(p.valor_parcela,2,'de_DE')");
				String nomeCliente = rs.getString("pe.nome");
				String observacao = rs.getString("p.observacao");
				String garantia = rs.getString("p.garantia");
				String dataGarantia = rs.getString("DATE_FORMAT(p.data_garantia, '%d/%m/%Y')");
				String placa = rs.getString("v.placa");
				String nomeVeiculo = rs.getString("v.veiculo");

				veiculo = new PagamentoVeiculo(ids, tipoPagamento, parcela, null, null, data_venda, lucro_venda,
						valor_parcela, nomeCliente, observacao, garantia, dataGarantia, placa, nomeVeiculo);
				lista.add(veiculo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public BigDecimal getValorTotalLucro(java.util.Date dataInicial, java.util.Date dataFinal) {
		String sql = "SELECT COALESCE(SUM(lucro_venda),0) FROM pagamento_veiculo WHERE data_venda BETWEEN '"
				+ dataInicial + "' AND '" + dataFinal + "'";
		BigDecimal valor = null;
		try {
			Statement cmd = dbConnection.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			if (rs.next()) {
				valor = rs.getBigDecimal("COALESCE(SUM(lucro_venda),0)");

			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valor;
	}

	public BigDecimal getQtdeVendaVeiculos(java.util.Date dataInicial, java.util.Date dataFinal) {
		String sql = "SELECT COUNT(veiculo) FROM veiculo WHERE data_venda BETWEEN '" + dataInicial + "' AND '"
				+ dataFinal + "'";
		BigDecimal valor = null;
		try {
			Statement cmd = dbConnection.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			if (rs.next()) {
				valor = rs.getBigDecimal("COUNT(veiculo)");

			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valor;
	}

	public BigDecimal getLucroVendaVeiculo(Integer pagamentoId) {
		String sql = "SELECT * FROM pagamento_veiculo WHERE id =  " + pagamentoId + "";
		BigDecimal valor = null;
		try {
			Statement cmd = dbConnection.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			if (rs.next()) {
				valor = rs.getBigDecimal("lucro_venda");

			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valor;
	}

	public Integer getQtdeVeiculosGarantia(String data) {
		String sql = "SELECT COUNT(data_garantia) FROM pagamento_veiculo WHERE data_garantia >= '" + data + "'";
		Integer valor = 0;
		try {
			Statement cmd = dbConnection.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			if (rs.next()) {
				valor = rs.getInt("COUNT(data_garantia)");
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valor;
	}

	public Integer getIdVeiculo(Integer pagamentoId) {
		String sql = "SELECT * FROM pagamento_veiculo WHERE id = " + pagamentoId + "";
		Integer valor = 0;
		try {
			Statement cmd = dbConnection.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			if (rs.next()) {
				valor = rs.getInt("veiculo_id");
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valor;
	}

}

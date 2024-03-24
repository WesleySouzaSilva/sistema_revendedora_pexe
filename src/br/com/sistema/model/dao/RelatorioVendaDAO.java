package br.com.sistema.model.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.sistema.conexao.AbstractGenericDAO;
import br.com.sistema.conexao.Conexao;
import br.com.sistema.model.RelatorioVenda;

public class RelatorioVendaDAO extends AbstractGenericDAO<RelatorioVenda> {

	public RelatorioVendaDAO(Conexao conexao) {
		super(conexao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean inserir(RelatorioVenda pojo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean apagar(RelatorioVenda pojo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean atualizar(RelatorioVenda novo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<RelatorioVenda> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<RelatorioVenda> listarTodos(java.util.Date dataInicial, java.util.Date dataFinal, String vendedr) {
		String sql = "SELECT v.vendedor, v.veiculo, v.placa, DATE_FORMAT(v.data_entrada, '%d/%m/%Y'), DATE_FORMAT(v.data_venda, '%d/%m/%Y'), FORMAT(v.valor_venda,2,'de_DE') FROM veiculo AS v WHERE v.data_venda BETWEEN '"
				+ dataInicial + "' AND '" + dataFinal + "' AND v.vendedor = '" + vendedr
				+ "' AND v.situacao = 'VENDA' ORDER BY v.data_venda";
		RelatorioVenda relatorioVenda = null;
		ArrayList<RelatorioVenda> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				String veiculo = rs.getString("v.veiculo");
				String vendedor = rs.getString("v.vendedor");
				String data_entrada = rs.getString("DATE_FORMAT(v.data_entrada, '%d/%m/%Y')");
				String data_venda = rs.getString("DATE_FORMAT(v.data_venda, '%d/%m/%Y')");
				String valor_venda = rs.getString("FORMAT(v.valor_venda,2,'de_DE')");
				String placa = rs.getString("v.placa");

				relatorioVenda = new RelatorioVenda(vendedor, veiculo, placa, data_venda, data_entrada, valor_venda,
						null, null, null, null, null, null, null, null);

				lista.add(relatorioVenda);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<RelatorioVenda> listarTodosResumo(java.util.Date dataInicial, java.util.Date dataFinal) {
		String sql = "SELECT v.id, v.vendedor, v.veiculo, v.placa, DATE_FORMAT(v.data_entrada, '%d/%m/%Y'), DATE_FORMAT(v.data_venda, '%d/%m/%Y'), FORMAT(v.valor_venda,2,'de_DE'), FORMAT(v.valor_entrada,2,'de_DE') FROM veiculo AS v WHERE v.data_venda BETWEEN '"
				+ dataInicial + "' AND '" + dataFinal + "' AND v.situacao = 'VENDA' ORDER BY v.data_venda ";
		RelatorioVenda relatorioVenda = null;
		ArrayList<RelatorioVenda> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				String veiculo = rs.getString("v.veiculo");
				String vendedor = rs.getString("v.vendedor");
				String data_entrada = rs.getString("DATE_FORMAT(v.data_entrada, '%d/%m/%Y')");
				String data_venda = rs.getString("DATE_FORMAT(v.data_venda, '%d/%m/%Y')");
				String valor_venda = rs.getString("FORMAT(v.valor_venda,2,'de_DE')");
				Integer idVeiculo = rs.getInt("v.id");
				String valorLucro = getValorLucro(idVeiculo);
				String valorDespesa = getValorTotalDespesas(idVeiculo);
				String valor_entrada = rs.getString("FORMAT(v.valor_entrada,2,'de_DE')");
				String placa = rs.getString("v.placa");

				relatorioVenda = new RelatorioVenda(vendedor, veiculo, placa, data_venda, data_entrada, valor_venda,
						null, null, null, null, null, valorLucro, valorDespesa, valor_entrada);

				lista.add(relatorioVenda);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public BigDecimal getValorTotalLucro(java.util.Date dataInicial, java.util.Date dataFinal) {
		BigDecimal total = null;
		String sql = "SELECT COALESCE(SUM(lucro_venda),2) FROM pagamento_veiculo WHERE data_venda BETWEEN '2024-02-01' AND '2024-02-29'";
		;

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				total = rs.getBigDecimal("COALESCE(SUM(lucro_venda),2)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;
	}

	public String getValorLucro(Integer veiculoId) {
		String total = null;
		String sql = "SELECT FORMAT(p.lucro_venda,2,'de_DE') FROM pagamento_veiculo AS p WHERE p.veiculo_id = "
				+ veiculoId + "";

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				total = rs.getString("FORMAT(p.lucro_venda,2,'de_DE')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;
	}

	public String getValorTotalDespesas(Integer veiculoId) {
		String total = null;
		String sql = "SELECT d.id, COALESCE(FORMAT(SUM(d.valor), 2, 'de_DE'), '0.00') AS total_despesas	FROM despesas AS d WHERE d.veiculo_id = "
				+ veiculoId + " AND d.tipo LIKE 'DESPESA VEICULO%'";

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				total = rs.getString("total_despesas");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;
	}

	public BigDecimal getTotalVendas(java.util.Date dataInicial, java.util.Date dataFinal) {
		BigDecimal total = null;
		String sql = "SELECT COALESCE(SUM(valor_venda),0) FROM veiculo WHERE data_venda BETWEEN '" + dataInicial
				+ "' AND '" + dataFinal + "'";

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				total = rs.getBigDecimal("COALESCE(SUM(valor_venda),0)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;
	}

	public BigDecimal getTotalVendas(java.util.Date dataInicial, java.util.Date dataFinal, String vendedor) {
		BigDecimal total = null;
		String sql = "SELECT COALESCE(SUM(valor_venda),0) FROM veiculo WHERE data_venda BETWEEN '" + dataInicial
				+ "' AND '" + dataFinal + "' AND vendedor = '" + vendedor + "'";

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				total = rs.getBigDecimal("COALESCE(SUM(valor_venda),0)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;
	}

	public String getQtdeVendas(java.util.Date dataInicial, java.util.Date dataFinal, String vendedor) {
		String total = null;
		String sql = "SELECT COUNT(id) FROM veiculo WHERE data_venda BETWEEN '" + dataInicial + "' AND '" + dataFinal
				+ "' AND vendedor = '" + vendedor + "' AND situacao = 'VENDA'";

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				total = rs.getString("COUNT(id)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;
	}

	public String getQtdeVendas(java.util.Date dataInicial, java.util.Date dataFinal) {
		String total = null;
		String sql = "SELECT COUNT(id) FROM veiculo WHERE data_venda BETWEEN '" + dataInicial + "' AND '" + dataFinal
				+ "' AND situacao = 'VENDA'";

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				total = rs.getString("COUNT(id)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;
	}
}

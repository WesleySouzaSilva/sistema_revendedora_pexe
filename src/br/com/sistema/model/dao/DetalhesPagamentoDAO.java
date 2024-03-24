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
import br.com.sistema.model.DetalhesPagamento;

public class DetalhesPagamentoDAO extends AbstractGenericDAO<DetalhesPagamento> {

	public DetalhesPagamentoDAO(Conexao conexao) {
		super(conexao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean inserir(DetalhesPagamento pojo) {
		String sql = "INSERT INTO detalhes_pagamento(tipo, valor, data_pagamento, contas_receber_id) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, pojo.getTipo());
			cmd.setBigDecimal(2, pojo.getValor());
			cmd.setDate(3, (Date) pojo.getData());
			cmd.setInt(4, pojo.getContasReceber().getId());
			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no pr贸prio objeto
				pojo.setId(ultimoID("detalhes_pagamento"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean apagar(DetalhesPagamento pojo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean atualizar(DetalhesPagamento novo) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean atualizarValor(DetalhesPagamento novo) {
		String sql = "UPDATE contas_receber SET valor_receber = ? WHERE id = ?";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setBigDecimal(1, novo.getValor());
			cmd.setInt(2, novo.getContasReceber().getId());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no pr贸prio objeto
				novo.setId(ultimoID("contas_receber"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean atualizarValorRecebido(DetalhesPagamento novo) {
		String sql = "UPDATE contas_receber SET valor_pago = ? WHERE id = ?";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setBigDecimal(1, novo.getValor());
			cmd.setInt(2, novo.getContasReceber().getId());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no pr贸prio objeto
				novo.setId(ultimoID("contas_receber"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean atualizarValorDespesa(DetalhesPagamento novo) {
		String sql = "UPDATE despesas SET valor_descontar = ? WHERE id = ?";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setBigDecimal(1, novo.getValor());
			cmd.setInt(2, novo.getContasReceber().getId());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no pr贸prio objeto
				novo.setId(ultimoID("despesas"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<DetalhesPagamento> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DetalhesPagamento> buscarIdPagamento(Integer id) {
		String sql = "SELECT d.id, d.tipo, d.valor, DATE_FORMAT(d.data_pagamento, '%d/%m/%Y') FROM detalhes_pagamento AS d WHERE d.contas_receber_id = "
				+ id + "";
		ArrayList<DetalhesPagamento> lista = new ArrayList<>();
		DetalhesPagamento detalhes = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				Integer ids = rs.getInt("d.id");
				String tipo = rs.getString("d.tipo");
				BigDecimal valor = rs.getBigDecimal("d.valor");
				String data = rs.getString("DATE_FORMAT(d.data_pagamento, '%d/%m/%Y')");

				detalhes = new DetalhesPagamento(ids, tipo, valor, null, data, null);
				lista.add(detalhes);

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return lista;
	}

	public List<DetalhesPagamento> buscarIdPagamentoDespesa(Integer id) {
		String sql = "SELECT d.id, d.tipo, d.valor, DATE_FORMAT(d.data_pagamento, '%d/%m/%Y') FROM detalhes_pagamento AS d WHERE d.despesa_id = "
				+ id + "";
		ArrayList<DetalhesPagamento> lista = new ArrayList<>();
		DetalhesPagamento detalhes = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				Integer ids = rs.getInt("d.id");
				String tipo = rs.getString("d.tipo");
				BigDecimal valor = rs.getBigDecimal("d.valor");
				String data = rs.getString("DATE_FORMAT(d.data_pagamento, '%d/%m/%Y')");

				detalhes = new DetalhesPagamento(ids, tipo, valor, null, data, null);
				lista.add(detalhes);

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return lista;
	}

	public BigDecimal getTotal(Integer id) {
		BigDecimal total = null;
		String sql = "SELECT SUM(valor) FROM detalhes_pagamento WHERE contas_receber_id = ?";

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setInt(1, id);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				total = rs.getBigDecimal("SUM(valor)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;
	}

	public BigDecimal getTotalDespesaDetalhes(Integer id) {
		BigDecimal total = null;
		String sql = "SELECT COALESCE(SUM(valor),0) FROM detalhes_pagamento WHERE despesa_id = ?";

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setInt(1, id);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				total = rs.getBigDecimal("COALESCE(SUM(valor),0)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;
	}

	public BigDecimal getTotalDespesa(Integer id) {
		BigDecimal total = null;
		String sql = "SELECT SUM(valor) FROM despesas WHERE id = ?";

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setInt(1, id);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				total = rs.getBigDecimal("SUM(valor)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;
	}

	public BigDecimal getTotalReceber(Integer id) {
		BigDecimal total = null;
		String sql = "SELECT SUM(valor) FROM contas_receber WHERE id = ?";

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setInt(1, id);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				total = rs.getBigDecimal("SUM(valor)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;
	}

	public BigDecimal getTotalPagarDespesa(Integer id) {
		BigDecimal total = null;
		String sql = "SELECT COALESCE(SUM(valor),0) FROM despesas WHERE id = ? ";

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setInt(1, id);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				total = rs.getBigDecimal("COALESCE(SUM(valor),0)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;
	}

	public BigDecimal getTotalReceberDespesa(Integer id) {
		BigDecimal total = null;
		String sql = "SELECT SUM(valor) FROM despesas WHERE id = ?";

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setInt(1, id);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				total = rs.getBigDecimal("SUM(valor)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;
	}

	public BigDecimal getTotalRecebido(Integer id) {
		BigDecimal total = null;
		String sql = "SELECT COALESCE(SUM(valor),0) FROM detalhes_pagamento WHERE contas_receber_id = ?";

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setInt(1, id);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				total = rs.getBigDecimal("COALESCE(SUM(valor),0)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;
	}

	public BigDecimal getTotalRecebidoDespesa(Integer id) {
		BigDecimal total = null;
		String sql = "SELECT SUM(valor) FROM detalhes_pagamento WHERE despesa_id = ?";

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setInt(1, id);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				total = rs.getBigDecimal("SUM(valor)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;
	}

	public BigDecimal getTotalPagamentoReceber(Integer id) {
		BigDecimal total = null;
		String sql = "SELECT * FROM contas_receber WHERE id = ?";

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setInt(1, id);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				total = rs.getBigDecimal("valor_receber");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;
	}

	public BigDecimal getTotalPagamentoReceberDespesa(Integer id) {
		BigDecimal total = null;
		String sql = "SELECT * FROM despesas WHERE id = ?";

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setInt(1, id);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				total = rs.getBigDecimal("valor");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;
	}

}

package br.com.sistema.model.dao;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.sistema.conexao.AbstractGenericDAO;
import br.com.sistema.conexao.Conexao;
import br.com.sistema.model.ContasReceber;

public class ContasReceberDAO extends AbstractGenericDAO<ContasReceber> {

	public ContasReceberDAO(Conexao conexao) {
		super(conexao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean inserir(ContasReceber pojo) {
		String sql = "INSERT INTO contas_receber(descricao, data, qtde_parcela, numero_parcela, valor_total, valor_pago, valor_receber, pessoa_id, veiculo_id, situacao) VALUES (?, ? , ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);

			cmd.setString(1, pojo.getDescricao());
			cmd.setDate(2, (Date) pojo.getDataVencimento());
			cmd.setInt(3, pojo.getQtdeParcela());
			cmd.setInt(4, pojo.getNumeroParcela());
			cmd.setBigDecimal(5, pojo.getValorTotal());
			cmd.setBigDecimal(6, pojo.getValorPago());
			cmd.setBigDecimal(7, pojo.getValorPendente());
			cmd.setInt(8, pojo.getPessoa().getId());
			cmd.setInt(9, pojo.getVeiculo().getId());
			cmd.setString(10, pojo.getSituacao());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no pr贸prio objeto
				pojo.setId(ultimoID("contas_receber"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean inserirContas(ContasReceber pojo) {
		String sql = "INSERT INTO contas_receber(descricao, data, qtde_parcela, numero_parcela, valor_total, valor_receber, valor_pago, pessoa_id, situacao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);

			cmd.setString(1, pojo.getDescricao());
			cmd.setDate(2, (Date) pojo.getDataVencimento());
			cmd.setInt(3, pojo.getQtdeParcela());
			cmd.setInt(4, pojo.getNumeroParcela());
			cmd.setBigDecimal(5, pojo.getValorTotal());
			cmd.setBigDecimal(6, pojo.getValorPendente());
			cmd.setBigDecimal(7, pojo.getValorPago());
			cmd.setInt(8, pojo.getPessoa().getId());
			cmd.setString(9, pojo.getSituacao());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no pr贸prio objeto
				pojo.setId(ultimoID("contas_receber"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean inserirPagamentoParcelado(ContasReceber pojo) {
		String sql = "INSERT INTO detalhes_pagamento(tipo, valor, data_pagamento, contas_receber_id) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, pojo.getSituacao());
			cmd.setBigDecimal(2, pojo.getValorTotal());
			cmd.setDate(3, (Date) pojo.getDataVencimento());
			cmd.setInt(4, pojo.getId());
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

	public boolean inserirPagamentoTemporario(ContasReceber pojo) {
		String sql = "INSERT INTO pagamento_temporario(numero_parcela, qtde_parcela, data_vencimento, valor_total, veiculo_id) VALUES(?, ?, ?, ?, ?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setInt(1, pojo.getNumeroParcela());
			cmd.setInt(2, pojo.getQtdeParcela());
			cmd.setDate(3, (Date) pojo.getDataVencimento());
			cmd.setBigDecimal(4, pojo.getValorTotal());
			cmd.setInt(5, pojo.getVeiculo().getId());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no pr贸prio objeto
				pojo.setId(ultimoID("pagamento_temporario"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean apagar(ContasReceber pojo) {
		return false;
	}

	public boolean apagarPagamento(ContasReceber pojo) {
		String sql = "DELETE FROM contas_receber WHERE id = ?";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setInt(1, pojo.getId());
			int retorno = cmd.executeUpdate();
			cmd.close();
			if (retorno > 0) {
				// salva o id gerado pelo banco no pr贸prio objeto
				pojo.setId(ultimoID("contas_receber"));
			}

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean apagarHistoricoVeiculo(String tipo, Integer idVeiculo) {
		String sql = "DELETE FROM historico_veiculo WHERE veiculo_id = ? AND tipo = ? ";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setInt(1, idVeiculo);
			cmd.setString(2, tipo);

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

	public boolean apagarPagamentoTemporario() {
		String sql = "DELETE FROM pagamento_temporario";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			int retorno = cmd.executeUpdate();
			cmd.close();
			if (retorno > 0) {
			}

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean atualizar(ContasReceber novo) {
		String sql = "UPDATE contas_receber SET situacao = ?, valor_total = ?, data = ?  WHERE id = ?";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, novo.getSituacao());
			cmd.setBigDecimal(2, novo.getValorTotal());
			cmd.setDate(3, (Date) novo.getDataVencimento());
			cmd.setInt(4, novo.getId());
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

	public boolean atualizarSituacaoDespesa(ContasReceber novo) {
		String sql = "UPDATE despesas SET situacao = ?  WHERE id = ?";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, novo.getSituacao());
			cmd.setInt(2, novo.getId());
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

	public boolean atualizarSituacaoVeiculo(String situacao, Integer idVeiculo) {
		String sql = "UPDATE veiculo SET situacao = ?  WHERE id = ?";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, situacao);
			cmd.setInt(2, idVeiculo);
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

	public boolean atualizarValor(Integer id, String campo, BigDecimal valor) {
		String sql = "UPDATE contas_receber SET " + campo + " = ? WHERE id = ?";
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
	public List<ContasReceber> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ContasReceber> listarTodos(Integer ids) {
		String sql = "SELECT c.id, c.descricao, FORMAT(c.valor_total,2,'de_DE'), c.valor_total, DATE_FORMAT(c.data, '%d/%m/%Y'), c.situacao, c.pessoa_id, p.id, p.nome  FROM contas_receber AS c, pessoa AS p  WHERE c.id = "
				+ ids + " AND p.id = c.pessoa_id ";
		ContasReceber contas = null;
		ArrayList<ContasReceber> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				Integer id = rs.getInt("c.id");
				String nomeCliente = rs.getString("p.nome");
				String descricao = rs.getString("c.descricao");
				String valorFormatado = rs.getString("FORMAT(c.valor_total,2,'de_DE')");
				BigDecimal valorTotal = rs.getBigDecimal("c.valor_total");
				String dataFormatada = rs.getString("DATE_FORMAT(c.data, '%d/%m/%Y')");
				String situacao = rs.getString("c.situacao");

				contas = new ContasReceber(id, null, valorTotal, null, null, null, null, null, null, situacao,
						dataFormatada, valorFormatado, null, null, nomeCliente, descricao, null, null, null);
				lista.add(contas);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<ContasReceber> listarTodos(java.util.Date dataInicial, java.util.Date dataFinal) {
		String sql = "SELECT c.id, c.descricao, FORMAT(c.valor_total,2,'de_DE'), DATE_FORMAT(c.data, '%d/%m/%Y'), c.qtde_parcela, c.numero_parcela, c.situacao, c.pessoa_id, p.id, p.nome  FROM contas_receber AS c, pessoa AS p  WHERE c.data BETWEEN '"
				+ dataInicial + "' AND '" + dataFinal + "' AND p.id = c.pessoa_id ORDER BY c.data";
		ContasReceber contas = null;
		ArrayList<ContasReceber> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				Integer id = rs.getInt("c.id");
				String nomeCliente = rs.getString("p.nome");
				String descricao = rs.getString("c.descricao");
				String valorFormatado = rs.getString("FORMAT(c.valor_total,2,'de_DE')");
				String dataFormatada = rs.getString("DATE_FORMAT(c.data, '%d/%m/%Y')");
				String situacao = rs.getString("c.situacao");
				Integer qtdeParcela = rs.getInt("c.qtde_parcela");
				Integer numeroParcela = rs.getInt("c.numero_parcela");

				contas = new ContasReceber(id, null, null, null, null, qtdeParcela, numeroParcela, null, null, situacao,
						dataFormatada, valorFormatado, null, null, nomeCliente, descricao, null, null, null);
				lista.add(contas);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<ContasReceber> listarTodos(java.util.Date dataInicial, java.util.Date dataFinal, String busca) {
		String sql = "SELECT c.id, c.descricao, FORMAT(c.valor_total,2,'de_DE'), DATE_FORMAT(c.data, '%d/%m/%Y'), c.situacao, c.pessoa_id, p.id, p.nome  FROM contas_receber AS c, pessoa AS p  WHERE c.data BETWEEN '"
				+ dataInicial + "' AND '" + dataFinal + "' AND p.id = c.pessoa_id ORDER BY " + busca + "";
		ContasReceber contas = null;
		ArrayList<ContasReceber> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				Integer id = rs.getInt("c.id");
				String nomeCliente = rs.getString("p.nome");
				String descricao = rs.getString("c.descricao");
				String valorFormatado = rs.getString("FORMAT(c.valor_total,2,'de_DE')");
				String dataFormatada = rs.getString("DATE_FORMAT(c.data, '%d/%m/%Y')");
				String situacao = rs.getString("c.situacao");

				contas = new ContasReceber(id, null, null, null, null, null, null, null, null, situacao, dataFormatada,
						valorFormatado, null, null, nomeCliente, descricao, null, null, null);
				lista.add(contas);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<ContasReceber> listarTodos(java.util.Date dataInicial, java.util.Date dataFinal, String busca,
			String pesquisa) {
		String sql = "SELECT c.id, c.descricao, FORMAT(c.valor_total,2,'de_DE'), DATE_FORMAT(c.data, '%d/%m/%Y'), c.qtde_parcela, c.numero_parcela, c.situacao, c.pessoa_id, p.id, p.nome  FROM contas_receber AS c, pessoa AS p  WHERE c.data BETWEEN '"
				+ dataInicial + "' AND '" + dataFinal + "' AND " + busca + " LIKE '" + pesquisa
				+ "%'AND p.id = c.pessoa_id ORDER BY c.data";
		ContasReceber contas = null;
		ArrayList<ContasReceber> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				Integer id = rs.getInt("c.id");
				String nomeCliente = rs.getString("p.nome");
				String descricao = rs.getString("c.descricao");
				String valorFormatado = rs.getString("FORMAT(c.valor_total,2,'de_DE')");
				String dataFormatada = rs.getString("DATE_FORMAT(c.data, '%d/%m/%Y')");
				String situacao = rs.getString("c.situacao");
				Integer qtdeParcela = rs.getInt("c.qtde_parcela");
				Integer numeroParcela = rs.getInt("c.numero_parcela");

				contas = new ContasReceber(id, null, null, null, null, qtdeParcela, numeroParcela, null, null, situacao,
						dataFormatada, valorFormatado, null, null, nomeCliente, descricao, null, null, null);
				lista.add(contas);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<ContasReceber> listarTodosPagamentoTemporarioParcelas(Integer veiculoId) {
		String sql = "SELECT * FROM pagamento_temporario WHERE veiculo_id = '" + veiculoId + "' ORDER BY id";
		ContasReceber contas = null;
		ArrayList<ContasReceber> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				Integer id = rs.getInt("id");
				BigDecimal valorTotal = rs.getBigDecimal("valor_total");
				BigDecimal valorPendente = rs.getBigDecimal("valor_total");
				Date dataVencimento = rs.getDate("data_vencimento");
				Integer qtdeParcela = rs.getInt("qtde_parcela");
				Integer numeroParcela = rs.getInt("numero_parcela");

				contas = new ContasReceber(id, dataVencimento, valorTotal, null, valorPendente, qtdeParcela,
						numeroParcela, null, null, null, null, null, null, null, null, null, null, null, null);
				lista.add(contas);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public BigDecimal getValorTotalDespesas(java.util.Date dataInicial, java.util.Date dataFinal) {
		String sql = "SELECT COALESCE(SUM(valor),0) FROM despesas WHERE data BETWEEN '" + dataInicial + "' AND '"
				+ dataFinal + "'";
		BigDecimal valor = null;
		try {
			Statement cmd = dbConnection.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			if (rs.next()) {
				valor = rs.getBigDecimal("COALESCE(SUM(valor),0)");

			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valor;
	}

	public BigDecimal getValorTotal(java.util.Date dataInicial, java.util.Date dataFinal, String busca,
			String pesquisa) {
		String sql = "SELECT COALESCE(SUM(valor),0) FROM contas_receber WHERE data BETWEEN '" + dataInicial + "' AND '"
				+ dataFinal + "' AND " + busca + " LIKE '" + pesquisa + "%'";
		BigDecimal valor = null;
		try {
			Statement cmd = dbConnection.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			if (rs.next()) {
				valor = rs.getBigDecimal("COALESCE(SUM(valor),0)");

			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valor;
	}

	public BigDecimal getValorPago(java.util.Date dataInicial, java.util.Date dataFinal, String busca,
			String pesquisa) {
		String sql = "SELECT COALESCE(SUM(valor),0) FROM contas_receber WHERE data BETWEEN '" + dataInicial + "' AND '"
				+ dataFinal + "' AND " + busca + " LIKE '" + pesquisa + "%'";
		BigDecimal valor = null;
		try {
			Statement cmd = dbConnection.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			if (rs.next()) {
				valor = rs.getBigDecimal("COALESCE(SUM(valor),0)");

			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valor;
	}

	public BigDecimal getValorPagoDespesa(java.util.Date dataInicial, java.util.Date dataFinal) {
		String sql = "SELECT COALESCE(SUM(valor),0) FROM despesas WHERE data BETWEEN '" + dataInicial + "' AND '"
				+ dataFinal + "' AND situacao = 'PAGO'";
		BigDecimal valor = null;
		try {
			Statement cmd = dbConnection.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			if (rs.next()) {
				valor = rs.getBigDecimal("COALESCE(SUM(valor),0)");

			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valor;
	}

	public BigDecimal getValorContasReceber(java.util.Date dataInicial, java.util.Date dataFinal) {
		String sql = "SELECT COALESCE(SUM(valor),0) FROM contas_receber WHERE data BETWEEN '" + dataInicial + "' AND '"
				+ dataFinal + "'";
		BigDecimal valor = null;
		try {
			Statement cmd = dbConnection.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			if (rs.next()) {
				valor = rs.getBigDecimal("COALESCE(SUM(valor),0)");

			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valor;
	}

	public BigDecimal getValorContasReceber(java.util.Date dataInicial, java.util.Date dataFinal, String situacao,
			String campoTabela) {
		String sql = "SELECT COALESCE(SUM(" + campoTabela + "),0) FROM contas_receber WHERE data BETWEEN '"
				+ dataInicial + "' AND '" + dataFinal + "' AND situacao = '" + situacao + "'";
		BigDecimal valor = null;
		try {
			Statement cmd = dbConnection.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			if (rs.next()) {
				valor = rs.getBigDecimal("COALESCE(SUM(valor),0)");

			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valor;
	}

	public BigDecimal getValorContasReceber(Integer id, String campo) {
		String sql = "SELECT COALESCE(SUM(" + campo + "),0) FROM contas_receber WHERE id = " + id + "";
		BigDecimal valor = null;
		try {
			Statement cmd = dbConnection.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			if (rs.next()) {
				valor = rs.getBigDecimal("COALESCE(SUM(" + campo + "),0)");

			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valor;
	}

	public BigDecimal getValorContasReceberCliente(java.util.Date dataInicial, java.util.Date dataFinal,
			String campoTabela, String busca, String pesquisa) {
		String sql = "SELECT COALESCE(SUM(" + campoTabela
				+ "),0) FROM contas_receber AS c, pessoa AS p WHERE c.data BETWEEN '" + dataInicial + "' AND '"
				+ dataFinal + "' AND " + busca + " LIKE '" + pesquisa + "%' AND p.id = c.pessoa_id ";
		BigDecimal valor = null;
		try {
			Statement cmd = dbConnection.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			if (rs.next()) {
				valor = rs.getBigDecimal("COALESCE(SUM(" + campoTabela + "),0)");

			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valor;
	}

	public BigDecimal getValorContasReceber(java.util.Date dataInicial, java.util.Date dataFinal, String campoTabela,
			String busca, String pesquisa) {
		String sql = "SELECT COALESCE(SUM(" + campoTabela + "),0) FROM contas_receber WHERE data BETWEEN '"
				+ dataInicial + "' AND '" + dataFinal + "' AND " + busca + " LIKE '" + pesquisa + "%' ";
		BigDecimal valor = null;
		try {
			Statement cmd = dbConnection.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			if (rs.next()) {
				valor = rs.getBigDecimal("COALESCE(SUM(" + campoTabela + "),0)");

			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valor;
	}

	public BigDecimal getValorContasReceber(java.util.Date dataInicial, java.util.Date dataFinal, String campoTabela) {
		String sql = "SELECT COALESCE(SUM(" + campoTabela + "),0) FROM contas_receber WHERE data BETWEEN '"
				+ dataInicial + "' AND '" + dataFinal + "' ";
		BigDecimal valor = null;
		try {
			Statement cmd = dbConnection.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			if (rs.next()) {
				valor = rs.getBigDecimal("COALESCE(SUM(" + campoTabela + "),0)");

			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valor;
	}

	public BigDecimal getValorReceberDespesas(java.util.Date dataInicial, java.util.Date dataFinal) {
		String sql = "SELECT COALESCE(SUM(valor),0) FROM despesas WHERE data BETWEEN '" + dataInicial + "' AND '"
				+ dataFinal + "' AND situacao = 'PENDENTE'";
		BigDecimal valor = null;
		try {
			Statement cmd = dbConnection.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			if (rs.next()) {
				valor = rs.getBigDecimal("COALESCE(SUM(valor),0)");

			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valor;
	}

	public Integer getNumeroParcela(Integer veiculoId) {
		String sql = "SELECT COALESCE(MAX(numero_parcela),0) FROM pagamento_temporario WHERE veiculo_id = '" + veiculoId
				+ "'";
		Integer valor = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getInt("COALESCE(MAX(numero_parcela),0)");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public Integer getNumeroParcelaCliente(Integer id, String descricao, BigDecimal valor) {
		String sql = "SELECT COALESCE(MAX(numero_parcela),0) FROM contas_receber WHERE pessoa_id = '" + id
				+ "' AND descricao = '" + descricao + "' AND valor_total = '" + valor + "' AND situacao = 'PENDENTE'";
		Integer valo = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valo = rs.getInt("COALESCE(MAX(numero_parcela),0)");
			}
		} catch (Exception e) {
		}
		return valo;
	}

	public Date getDataParcelaCliente(Integer id, String descricao, BigDecimal valor) {
		String sql = "SELECT COALESCE(MAX(data),0) FROM contas_receber WHERE pessoa_id = '" + id + "' AND descricao = '"
				+ descricao + "' AND valor_total = '" + valor + "' AND situacao = 'PENDENTE'";
		Date valo = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valo = rs.getDate("COALESCE(MAX(data),0)");
			}
		} catch (Exception e) {
		}
		return valo;
	}

	public Date getDataContasReceberPorId(Integer id) {
		String sql = "SELECT * FROM contas_receber WHERE id = " + id + "";
		Date valor = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getDate("data");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public Integer getIdVeiculo(Integer contasReceberId) {
		String sql = "SELECT * FROM contas_receber WHERE id = " + contasReceberId + "";
		Integer valor = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getInt("veiculo_id");
			}
		} catch (Exception e) {
		}
		return valor;
	}

}

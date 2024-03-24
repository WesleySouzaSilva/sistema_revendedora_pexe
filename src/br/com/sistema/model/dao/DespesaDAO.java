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
import br.com.sistema.model.Despesa;
import br.com.sistema.model.Pessoa;

public class DespesaDAO extends AbstractGenericDAO<Despesa> {

	public DespesaDAO(Conexao conexao) {
		super(conexao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean inserir(Despesa pojo) {
		String sql = "INSERT INTO despesas(tipo, descricao, responsavel, valor, data_vencimento, situacao) VALUES(?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, pojo.getTipo());
			cmd.setString(2, pojo.getDescricao());
			cmd.setString(3, pojo.getResponsavel());
			cmd.setBigDecimal(4, pojo.getValor());
			cmd.setDate(5, (Date) pojo.getDataVencimento());
			cmd.setString(6, pojo.getSituacao());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no pr贸prio objeto
				pojo.setId(ultimoID("despesas"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean inserirDespesaFixa(String descricao) {
		String sql = "INSERT INTO despesa_fixa(descricao) VALUES(?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, descricao);

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

	public boolean inserirDespesaFuncionario(Despesa pojo) {
		String sql = "INSERT INTO despesas(tipo, descricao, responsavel, valor, data_vencimento, data_pagamento, funcionario_id, situacao) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, pojo.getTipo());
			cmd.setString(2, pojo.getDescricao());
			cmd.setString(3, pojo.getResponsavel());
			cmd.setBigDecimal(4, pojo.getValor());
			cmd.setDate(5, (Date) pojo.getDataVencimento());
			cmd.setDate(6, (Date) pojo.getDataPagamento());
			cmd.setInt(7, pojo.getFuncionario().getId());
			cmd.setString(8, pojo.getSituacao());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no pr贸prio objeto
				pojo.setId(ultimoID("despesas"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean inserirDespesaEmpresa(Despesa pojo) {
		String sql = "INSERT INTO despesas(tipo, descricao, responsavel, valor, data_vencimento, situacao) VALUES(?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, pojo.getTipo());
			cmd.setString(2, pojo.getDescricao());
			cmd.setString(3, pojo.getResponsavel());
			cmd.setBigDecimal(4, pojo.getValor());
			cmd.setDate(5, (Date) pojo.getDataVencimento());
			cmd.setString(6, pojo.getSituacao());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no pr贸prio objeto
				pojo.setId(ultimoID("despesas"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean inserirDespesaVeiculo(Despesa pojo) {
		String sql = "INSERT INTO despesas(tipo, descricao, responsavel, valor, data_vencimento, data_pagamento, veiculo_id, situacao) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, pojo.getTipo());
			cmd.setString(2, pojo.getDescricao());
			cmd.setString(3, pojo.getResponsavel());
			cmd.setBigDecimal(4, pojo.getValor());
			cmd.setDate(5, (Date) pojo.getDataVencimento());
			cmd.setDate(6, (Date) pojo.getDataPagamento());
			cmd.setInt(7, pojo.getVeiculo().getId());
			cmd.setString(8, pojo.getSituacao());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no pr贸prio objeto
				pojo.setId(ultimoID("despesas"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean inserirDespesaVeiculoCompra(Despesa pojo) {
		String sql = "INSERT INTO despesas(tipo, descricao, responsavel, valor, data_vencimento, data_pagamento, veiculo_id, pessoa_id, situacao) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, pojo.getTipo());
			cmd.setString(2, pojo.getDescricao());
			cmd.setString(3, pojo.getResponsavel());
			cmd.setBigDecimal(4, pojo.getValor());
			cmd.setDate(5, (Date) pojo.getDataVencimento());
			cmd.setDate(6, (Date) pojo.getDataPagamento());
			cmd.setInt(7, pojo.getVeiculo().getId());
			cmd.setInt(8, pojo.getPessoa().getId());
			cmd.setString(9, pojo.getSituacao());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no pr贸prio objeto
				pojo.setId(ultimoID("despesas"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean apagar(Despesa pojo) {
		String sql = "DELETE FROM despesas WHERE id = ?";

		try {

			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setInt(1, pojo.getId());
			int retorno = cmd.executeUpdate();
			cmd.close();
			return retorno > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean apagarDespesaFixa(Integer id) {
		String sql = "DELETE FROM despesa_fixa WHERE id = ?";

		try {

			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setInt(1, id);
			int retorno = cmd.executeUpdate();
			cmd.close();
			return retorno > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean apagarParcelas(String usuario) {
		String sql = "DELETE FROM pagamento_temporario WHERE usuario = '" + usuario + "'";

		try {

			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			int retorno = cmd.executeUpdate();
			cmd.close();
			return retorno > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean atualizar(Despesa novo) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean atualizarPagamentoSituacao(Despesa novo) {
		String sql = "UPDATE despesas SET data_pagamento = ?, situacao = ?  WHERE id = ?";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setDate(1, (Date) novo.getDataPagamento());
			cmd.setString(2, novo.getSituacao());
			cmd.setInt(3, novo.getId());

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

	public boolean atualizarDespesaFixa(Integer id, String descricao) {
		String sql = "UPDATE despesa_fixa SET descricao = ?  WHERE id = ?";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, descricao);
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
	public List<Despesa> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Despesa> listarTodosId(Integer id) {
		String sql = "SELECT d.id, d.tipo, d.descricao, d.responsavel, DATE_FORMAT(d.data_vencimento, '%d/%m/%Y'), DATE_FORMAT(d.data_pagamento, '%d/%m/%Y'), FORMAT(d.valor,2,'de_DE'), d.situacao FROM despesas AS d WHERE d.id = "
				+ id + "";
		Despesa despesa = null;
		ArrayList<Despesa> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				Integer ids = rs.getInt("d.id");
				String tipo = rs.getString("d.tipo");
				String descricao = rs.getString("d.descricao");
				String responsavel = rs.getString("d.responsavel");
				String dataVencimentoFormatada = rs.getString("DATE_FORMAT(d.data_vencimento, '%d/%m/%Y')");
				String dataPagamentoFormatada = rs.getString("DATE_FORMAT(d.data_pagamento, '%d/%m/%Y')");
				String valor = rs.getString("FORMAT(d.valor,2,'de_DE')");
				String situacao = rs.getString("d.situacao");

				despesa = new Despesa(ids, tipo, descricao, null, null, null, valor, responsavel,
						dataVencimentoFormatada, dataPagamentoFormatada, null, null, null, situacao);

				lista.add(despesa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<Despesa> listarTodos(java.util.Date dataInicial, java.util.Date dataFinal) {
		String sql = "SELECT d.id, d.tipo, d.descricao, d.responsavel, DATE_FORMAT(d.data_vencimento, '%d/%m/%Y'), DATE_FORMAT(d.data_pagamento, '%d/%m/%Y'), FORMAT(d.valor,2,'de_DE'), d.situacao FROM despesas AS d WHERE d.data_vencimento BETWEEN '"
				+ dataInicial + "' AND '" + dataFinal + "' ORDER BY d.data_vencimento";
		Despesa despesa = null;
		ArrayList<Despesa> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				Integer ids = rs.getInt("d.id");
				String tipo = rs.getString("d.tipo");
				String descricao = rs.getString("d.descricao");
				String responsavel = rs.getString("d.responsavel");
				String dataVencimentoFormatada = rs.getString("DATE_FORMAT(d.data_vencimento, '%d/%m/%Y')");
				String dataPagamentoFormatada = rs.getString("DATE_FORMAT(d.data_pagamento, '%d/%m/%Y')");
				String valor = rs.getString("FORMAT(d.valor,2,'de_DE')");
				String situacao = rs.getString("d.situacao");

				despesa = new Despesa(ids, tipo, descricao, null, null, null, valor, responsavel,
						dataVencimentoFormatada, dataPagamentoFormatada, null, null, null, situacao);

				lista.add(despesa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<Despesa> listarTodosFuncionario(java.util.Date dataInicial, java.util.Date dataFinal) {
		String sql = "SELECT id, tipo, descricao, responsavel, DATE_FORMAT(data_vencimento, '%d/%m/%Y'), DATE_FORMAT(data_pagamento, '%d/%m/%Y'), FORMAT(valor,2,'de_DE'), situacao FROM despesas  WHERE data_vencimento BETWEEN '"
				+ dataInicial + "' AND '" + dataFinal
				+ "' AND tipo LIKE 'DESPESA FUNCIONARI%' ORDER BY data_vencimento";
		Despesa despesa = null;
		ArrayList<Despesa> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				Integer ids = rs.getInt("id");
				String tipo = rs.getString("tipo");
				String descricao = rs.getString("descricao");
				String responsavel = rs.getString("responsavel");
				String dataVencimentoFormatada = rs.getString("DATE_FORMAT(data_vencimento, '%d/%m/%Y')");
				String dataPagamentoFormatada = rs.getString("DATE_FORMAT(data_pagamento, '%d/%m/%Y')");
				String valor = rs.getString("FORMAT(valor,2,'de_DE')");
				String situacao = rs.getString("situacao");

				despesa = new Despesa(ids, tipo, descricao, null, null, null, valor, responsavel,
						dataVencimentoFormatada, dataPagamentoFormatada, null, null, null, situacao);

				lista.add(despesa);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<Despesa> listarTodosEmpresa(java.util.Date dataInicial, java.util.Date dataFinal) {
		String sql = "SELECT d.id, d.tipo, d.descricao, d.responsavel, DATE_FORMAT(d.data_vencimento, '%d/%m/%Y'), DATE_FORMAT(d.data_pagamento, '%d/%m/%Y'), FORMAT(d.valor,2,'de_DE'), d.situacao FROM despesas AS d WHERE d.data_vencimento BETWEEN '"
				+ dataInicial + "' AND '" + dataFinal + "' AND d.tipo = 'DESPESA LOJA' ORDER BY d.data_vencimento";
		Despesa despesa = null;
		ArrayList<Despesa> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				Integer ids = rs.getInt("d.id");
				String tipo = rs.getString("d.tipo");
				String descricao = rs.getString("d.descricao");
				String responsavel = rs.getString("d.responsavel");
				String dataVencimentoFormatada = rs.getString("DATE_FORMAT(d.data_vencimento, '%d/%m/%Y')");
				String dataPagamentoFormatada = rs.getString("DATE_FORMAT(d.data_pagamento, '%d/%m/%Y')");
				String valor = rs.getString("FORMAT(d.valor,2,'de_DE')");
				String situacao = rs.getString("d.situacao");

				despesa = new Despesa(ids, tipo, descricao, null, null, null, valor, responsavel,
						dataVencimentoFormatada, dataPagamentoFormatada, null, null, null, situacao);

				lista.add(despesa);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<Despesa> listarTodosDiversas(java.util.Date dataInicial, java.util.Date dataFinal) {
		String sql = "SELECT d.id, d.tipo, d.descricao, d.responsavel, DATE_FORMAT(d.data_vencimento, '%d/%m/%Y'), DATE_FORMAT(d.data_pagamento, '%d/%m/%Y'), FORMAT(d.valor,2,'de_DE'), d.situacao FROM despesas AS d WHERE d.data_vencimento BETWEEN '"
				+ dataInicial + "' AND '" + dataFinal + "' AND d.tipo = 'DESPESA DIVERSAS' ORDER BY d.data_vencimento";
		Despesa despesa = null;
		ArrayList<Despesa> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				Integer ids = rs.getInt("d.id");
				String tipo = rs.getString("d.tipo");
				String descricao = rs.getString("d.descricao");
				String responsavel = rs.getString("d.responsavel");
				String dataVencimentoFormatada = rs.getString("DATE_FORMAT(d.data_vencimento, '%d/%m/%Y')");
				String dataPagamentoFormatada = rs.getString("DATE_FORMAT(d.data_pagamento, '%d/%m/%Y')");
				String valor = rs.getString("FORMAT(d.valor,2,'de_DE')");
				String situacao = rs.getString("d.situacao");

				despesa = new Despesa(ids, tipo, descricao, null, null, null, valor, responsavel,
						dataVencimentoFormatada, dataPagamentoFormatada, null, null, null, situacao);

				lista.add(despesa);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<Despesa> listarTodosVeiculo(java.util.Date dataInicial, java.util.Date dataFinal) {
		String sql = "SELECT d.id, d.tipo, d.descricao, d.responsavel, d.veiculo_id, DATE_FORMAT(d.data_vencimento, '%d/%m/%Y'), DATE_FORMAT(d.data_pagamento, '%d/%m/%Y'), FORMAT(d.valor,2,'de_DE'), d.situacao, v.id FROM despesas AS d, veiculo AS v WHERE d.data_vencimento BETWEEN '"
				+ dataInicial + "' AND '" + dataFinal
				+ "' AND d.tipo LIKE '%VEICULO%' AND v.id = d.veiculo_id ORDER BY d.data_vencimento";
		Despesa despesa = null;
		ArrayList<Despesa> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				Integer ids = rs.getInt("d.id");
				String tipo = rs.getString("d.tipo");
				String descricao = rs.getString("d.descricao");
				String responsavel = rs.getString("d.responsavel");
				String dataVencimentoFormatada = rs.getString("DATE_FORMAT(d.data_vencimento, '%d/%m/%Y')");
				String dataPagamentoFormatada = rs.getString("DATE_FORMAT(d.data_pagamento, '%d/%m/%Y')");
				String valor = rs.getString("FORMAT(d.valor,2,'de_DE')");
				String situacao = rs.getString("d.situacao");

				despesa = new Despesa(ids, tipo, descricao, null, null, null, valor, responsavel,
						dataVencimentoFormatada, dataPagamentoFormatada, null, null, null, situacao);

				lista.add(despesa);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<Pessoa> listarDadosCliente(Integer pessoa_id) {
		String sql = "SELECT * FROM pessoa WHERE id = " + pessoa_id + "";
		Pessoa pessoa = null;
		ArrayList<Pessoa> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				Integer ids = rs.getInt("id");
				String nome = rs.getString("nome");
				String cpfCnpj = rs.getString("cpf_cnpj");

				pessoa = new Pessoa(ids, nome, cpfCnpj, null, null);

				lista.add(pessoa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<Despesa> listarDadosDespesaFixa() {
		String sql = "SELECT * FROM despesa_fixa ORDER BY id ";

		ArrayList<Despesa> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				Integer ids = rs.getInt("id");
				String descricao = rs.getString("descricao");

				Despesa despesa = new Despesa(ids, null, descricao, null, null, null, null, null, null, null, null,
						null, null, null);

				lista.add(despesa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<Despesa> listarDadosDespesaFixa(Integer id) {
		String sql = "SELECT * FROM despesa_fixa WHERE id = " + id + " ";

		ArrayList<Despesa> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				Integer ids = rs.getInt("id");
				String descricao = rs.getString("descricao");

				Despesa despesa = new Despesa(ids, null, descricao, null, null, null, null, null, null, null, null,
						null, null, null);

				lista.add(despesa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public String getEmpresa() {
		String valor = null;
		String sql = "SELECT * FROM empresa";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getString("nome");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public String getTotalDespesas(java.util.Date dataInicial, java.util.Date dataFinal) {
		String valor = null;
		String sql = "SELECT FORMAT(SUM(valor),2,'de_DE') FROM despesas WHERE data BETWEEN '" + dataInicial + "' AND '"
				+ dataFinal + "'";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getString("FORMAT(SUM(valor),2,'de_DE')");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public String getTotalDespesasVeiculo(java.util.Date dataInicial, java.util.Date dataFinal) {
		String valor = null;
		String sql = "SELECT FORMAT(SUM(s.valor),2,'de_DE'), s.veiculo_id, v.id FROM despesas AS s, veiculo AS v WHERE s.data BETWEEN '"
				+ dataInicial + "' AND '" + dataFinal + "' AND v.id = s.veiculo_id";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getString("FORMAT(SUM(s.valor),2,'de_DE')");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public String getTotalDespesasFuncionario(java.util.Date dataInicial, java.util.Date dataFinal) {
		String valor = null;
		String sql = "SELECT FORMAT(SUM(valor),2,'de_DE'), tipo FROM despesas WHERE data_vencimento BETWEEN '"
				+ dataInicial + "' AND '" + dataFinal + "' AND tipo = 'DESPESA FUNCIONARIO'";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getString("FORMAT(SUM(valor),2,'de_DE')");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public BigDecimal getTotalDespesasBigDecimal(java.util.Date dataInicial, java.util.Date dataFinal) {
		BigDecimal valor = null;
		String sql = "SELECT COALESCE(SUM(valor),0) FROM despesas WHERE data_vencimento BETWEEN '" + dataInicial
				+ "' AND '" + dataFinal + "'";
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

	public BigDecimal getQtdeTotalDespesasBigDecimal(java.util.Date dataInicial, java.util.Date dataFinal) {
		BigDecimal valor = null;
		String sql = "SELECT COUNT(responsavel) FROM despesas WHERE data_vencimento BETWEEN '" + dataInicial + "' AND '"
				+ dataFinal + "'";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getBigDecimal("COUNT(responsavel)");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public BigDecimal getTotalDespesasVeiculoContasPagar(java.util.Date dataInicial, java.util.Date dataFinal) {
		BigDecimal valor = null;
		String sql = "SELECT COALESCE(SUM(d.valor),0), d.veiculo_id, v.id FROM despesas AS d, veiculo AS v WHERE d.data_vencimento BETWEEN '"
				+ dataInicial + "' AND '" + dataFinal + "' AND v.id = d.veiculo_id ";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getBigDecimal("COALESCE(SUM(d.valor),0)");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public BigDecimal getPagarDespesasVeiculoContasPagar(java.util.Date dataInicial, java.util.Date dataFinal) {
		BigDecimal valor = null;
		String sql = "SELECT COALESCE(SUM(d.valor),0), d.veiculo_id, v.id FROM despesas AS d, veiculo AS v WHERE d.data_vencimento BETWEEN '"
				+ dataInicial + "' AND '" + dataFinal + "' AND d.situacao = 'PENDENTE' AND v.id = d.veiculo_id";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getBigDecimal("COALESCE(SUM(d.valor),0)");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public BigDecimal getPagoDespesasVeiculoContasPagar(java.util.Date dataInicial, java.util.Date dataFinal) {
		BigDecimal valor = null;
		String sql = "SELECT COALESCE(SUM(d.valor),0), d.veiculo_id, v.id FROM despesas AS d, veiculo AS v WHERE d.data_vencimento BETWEEN '"
				+ dataInicial + "' AND '" + dataFinal + "' AND d.situacao = 'PAGO' AND v.id = d.veiculo_id";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getBigDecimal("COALESCE(SUM(d.valor),0)");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public BigDecimal getTotalDespesasContasPagar(java.util.Date dataInicial, java.util.Date dataFinal) {
		BigDecimal valor = null;
		String sql = "SELECT COALESCE(SUM(valor),0) FROM despesas WHERE data_vencimento BETWEEN '" + dataInicial
				+ "' AND '" + dataFinal + "'";
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

	public BigDecimal getPagarDespesasContasPagar(java.util.Date dataInicial, java.util.Date dataFinal) {
		BigDecimal valor = null;
		String sql = "SELECT COALESCE(SUM(valor),0) FROM despesas WHERE data_vencimento BETWEEN '" + dataInicial
				+ "' AND '" + dataFinal + "' AND situacao = 'PENDENTE'";
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

	public BigDecimal getPagoDespesasContasPagar(java.util.Date dataInicial, java.util.Date dataFinal) {
		BigDecimal valor = null;
		String sql = "SELECT COALESCE(SUM(valor),0) FROM despesas WHERE data_vencimento BETWEEN '" + dataInicial
				+ "' AND '" + dataFinal + "' AND situacao = 'PAGO'";
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

	public BigDecimal getTotalDespesasCompraVeiculos(java.util.Date dataInicial, java.util.Date dataFinal) {
		BigDecimal valor = null;
		String sql = "SELECT COALESCE(SUM(valor),0) FROM despesas WHERE data_vencimento BETWEEN '" + dataInicial
				+ "' AND '" + dataFinal + "' AND tipo = 'DESPESA COMPRA VEICULO'";
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

	public BigDecimal getQtdeCompraVeiculos(java.util.Date dataInicial, java.util.Date dataFinal) {
		BigDecimal valor = null;
		String sql = "SELECT COUNT(tipo) FROM despesas WHERE data_vencimento BETWEEN '" + dataInicial + "' AND '"
				+ dataFinal + "' AND tipo = 'DESPESA COMPRA VEICULO'";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getBigDecimal("COUNT(tipo)");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public BigDecimal getTotalDespesasContasPagar(java.util.Date dataInicial, java.util.Date dataFinal, String tipo) {
		BigDecimal valor = null;
		String sql = "SELECT COALESCE(SUM(valor),0) FROM despesas WHERE data_vencimento BETWEEN '" + dataInicial
				+ "' AND '" + dataFinal + "' AND tipo LIKE '" + tipo + "'";
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

	public BigDecimal getPagarDespesasContasPagar(java.util.Date dataInicial, java.util.Date dataFinal, String tipo) {
		BigDecimal valor = null;
		String sql = "SELECT COALESCE(SUM(valor),0) FROM despesas WHERE data_vencimento BETWEEN '" + dataInicial
				+ "' AND '" + dataFinal + "' AND situacao = 'PENDENTE' AND tipo LIKE '" + tipo + "'";
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

	public BigDecimal getPagoDespesasContasPagar(java.util.Date dataInicial, java.util.Date dataFinal, String tipo) {
		BigDecimal valor = null;
		String sql = "SELECT COALESCE(SUM(valor),0) FROM despesas WHERE data_vencimento BETWEEN '" + dataInicial
				+ "' AND '" + dataFinal + "' AND situacao = 'PAGO' AND tipo LIKE '" + tipo + "'";
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

	public BigDecimal getTotalDespesasBigDecimalEmpresa(java.util.Date dataInicial, java.util.Date dataFinal) {
		BigDecimal valor = null;
		String sql = "SELECT COALESCE(SUM(valor),0) FROM despesas WHERE data_vencimento BETWEEN '" + dataInicial
				+ "' AND '" + dataFinal + "' AND tipo = 'DESPESA LOJA'";
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

	public BigDecimal getTotalDespesasBigDecimalDiversas(java.util.Date dataInicial, java.util.Date dataFinal) {
		BigDecimal valor = null;
		String sql = "SELECT COALESCE(SUM(valor),0) FROM despesas WHERE data_vencimento BETWEEN '" + dataInicial
				+ "' AND '" + dataFinal + "' AND tipo = 'DESPESA DIVERSAS'";
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

	public BigDecimal getTotalDespesasBigDecimalFuncionario(java.util.Date dataInicial, java.util.Date dataFinal) {
		BigDecimal valor = null;
		String sql = "SELECT COALESCE(SUM(valor),0) FROM despesas WHERE data_vencimento BETWEEN '" + dataInicial
				+ "' AND '" + dataFinal + "' AND tipo = 'DESPESA FUNCIONARIO%' ";
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

	public BigDecimal getTotalDespesasBigDecimalVeiculo(java.util.Date dataInicial, java.util.Date dataFinal) {
		BigDecimal valor = null;
		String sql = "SELECT COALESCE(SUM(valor),0) FROM despesas WHERE data_vencimento BETWEEN '" + dataInicial
				+ "' AND '" + dataFinal + "' AND tipo = 'DESPESA VEICULO'";
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

	public BigDecimal getTotalEmpresaBigDecimal(java.util.Date dataInicial, java.util.Date dataFinal) {
		BigDecimal valor = null;
		String sql = "SELECT SUM(valor) FROM despesas WHERE data_vencimento BETWEEN '" + dataInicial + "' AND '"
				+ dataFinal + "'";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getBigDecimal("SUM(valor)");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public String getTotalSalarios() {
		String valor = null;
		String sql = "SELECT FORMAT(SUM(s.valor),2,'de_DE'), f.id, f.salario_id, f.ativo ,s.id FROM funcionario AS f, salario_funcionario AS s WHERE f.ativo = 'SIM'  AND s.id = f.salario_id";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getString("FORMAT(SUM(s.valor),2,'de_DE')");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public BigDecimal getTotalSalariosBigDecimal() {
		BigDecimal valor = null;
		String sql = "SELECT SUM(s.valor), f.id, f.salario_id, f.ativo ,s.id FROM funcionario AS f, salario_funcionario AS s WHERE f.ativo = 'SIM'  AND s.id = f.salario_id";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getBigDecimal("SUM(s.valor)");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public Integer getNumeroParcela(String usuario) {
		String sql = "SELECT COALESCE(MAX(numero),0) FROM pagamento_temporario WHERE usuario = '" + usuario + "'";
		Integer valor = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getInt("COALESCE(MAX(numero),0)");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public String getDataPagamento(Integer id) {
		String sql = "SELECT p.id, DATE_FORMAT(p.data, '%d/%m/%Y') FROM pagamento_temporario AS p WHERE p.id = '" + id
				+ "'";
		String valor = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getString("DATE_FORMAT(p.data, '%d/%m/%Y')");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public BigDecimal getValorPagamento(Integer id) {
		String sql = "SELECT * FROM pagamento_temporario WHERE id = '" + id + "'";
		BigDecimal valor = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getBigDecimal("valor");
			}
		} catch (Exception e) {
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

	public BigDecimal getValorPagoDespesa(java.util.Date dataInicial, java.util.Date dataFinal, String campo) {
		String sql = "SELECT COALESCE(SUM(valor),0) FROM despesas WHERE data BETWEEN '" + dataInicial + "' AND '"
				+ dataFinal + "' AND situacao = 'PAGO' AND tipo LIKE '%" + campo + "%'";
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

	public BigDecimal getValorReceberDespesas(java.util.Date dataInicial, java.util.Date dataFinal, String campo) {
		String sql = "SELECT COALESCE(SUM(valor),0) FROM despesas WHERE data BETWEEN '" + dataInicial + "' AND '"
				+ dataFinal + "' AND situacao = 'PENDENTE' AND tipo LIKE '%" + campo + "%'";
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

	public BigDecimal getValorTotalDespesas(java.util.Date dataInicial, java.util.Date dataFinal, String campo) {
		String sql = "SELECT COALESCE(SUM(valor),0) FROM despesas WHERE data BETWEEN '" + dataInicial + "' AND '"
				+ dataFinal + "' AND tipo LIKE '%" + campo + "%'";
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

	public BigDecimal getTotalDespesasGarantiaVeiculos(java.util.Date dataInicial, java.util.Date dataFinal) {
		BigDecimal valor = null;
		String sql = "SELECT COALESCE(SUM(valor),0) FROM despesas WHERE data_vencimento BETWEEN '" + dataInicial
				+ "' AND '" + dataFinal + "' AND tipo = 'DESPESA VEICULO GARANTIA'";
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

	public BigDecimal getTotalDespesasVeiculosEstoque(java.util.Date dataInicial, java.util.Date dataFinal) {
		BigDecimal valor = null;
		String sql = "SELECT COALESCE(SUM(s.valor),2,'de_DE'), s.veiculo_id, v.id FROM despesas AS s, veiculo AS v WHERE s.data_pagamento BETWEEN '"
				+ dataInicial + "' AND '" + dataFinal + "' AND v.id = s.veiculo_id AND s.tipo LIKE 'DESPESA VEICULO%'";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getBigDecimal("COALESCE(SUM(s.valor),2,'de_DE')");
			}
		} catch (Exception e) {
		}
		return valor;
	}

}

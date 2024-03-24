package br.com.sistema.model.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import br.com.sistema.conexao.AbstractGenericDAO;
import br.com.sistema.conexao.Conexao;
import br.com.sistema.model.RelatorioDre;

public class RelatorioDreDAO extends AbstractGenericDAO<RelatorioDre> {

	public RelatorioDreDAO(Conexao conexao) {
		super(conexao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean inserir(RelatorioDre pojo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean apagar(RelatorioDre pojo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean atualizar(RelatorioDre novo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<RelatorioDre> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getTotalDespesasContasPagarTipo(java.util.Date dataInicial, java.util.Date dataFinal,
			String tipo) {
		BigDecimal valor = null;
		String sql = "SELECT COALESCE(SUM(valor),0) FROM despesas WHERE data BETWEEN '" + dataInicial + "' AND '"
				+ dataFinal + "' AND tipo = '" + tipo + "' AND ativo = 'SIM'";
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

}

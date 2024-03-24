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
import br.com.sistema.model.HistoricoVeiculo;

public class HistoricoVeiculoDAO extends AbstractGenericDAO<HistoricoVeiculo> {

	public HistoricoVeiculoDAO(Conexao conexao) {
		super(conexao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean inserir(HistoricoVeiculo pojo) {
		String sql = "INSERT INTO historico_veiculo(tipo, descricao, data, valor, responsavel, veiculo_id, pessoa_id) VALUES(?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, pojo.getTipo());
			cmd.setString(2, pojo.getDescricao());
			cmd.setDate(3, new java.sql.Date(pojo.getData().getTime()));
			cmd.setBigDecimal(4, pojo.getValor());
			cmd.setString(5, pojo.getResponsavel());
			cmd.setInt(6, pojo.getVeiculo().getId());
			cmd.setInt(7, pojo.getPessoa().getId());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no prÃ³prio objeto
				pojo.setId(ultimoID("historico_veiculo"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean inserirHistorico(HistoricoVeiculo pojo) {
		String sql = "INSERT INTO historico_veiculo(tipo, descricao, data, valor, responsavel, veiculo_id) VALUES(?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, pojo.getTipo());
			cmd.setString(2, pojo.getDescricao());
			cmd.setDate(3, (Date) pojo.getData());
			cmd.setBigDecimal(4, pojo.getValor());
			cmd.setString(5, pojo.getResponsavel());
			cmd.setInt(6, pojo.getVeiculo().getId());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no prÃ³prio objeto
				pojo.setId(ultimoID("historico_veiculo"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean apagar(HistoricoVeiculo pojo) {
		String sql = "DELETE FROM historico_veiculo WHERE id = ? ";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setInt(1, pojo.getId());

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

	public boolean apagarDespesaContasPagar(String tipo, String descricao, BigDecimal valor, Integer idVeiculo) {
		String sql = "DELETE FROM despesa WHERE id = ? AND tipo = ? AND descricao AND valor = ? AND veiculo_id = ? ";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, tipo);
			cmd.setString(1, descricao);
			cmd.setBigDecimal(1, valor);
			cmd.setInt(1, idVeiculo);

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
	public boolean atualizar(HistoricoVeiculo novo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<HistoricoVeiculo> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<HistoricoVeiculo> listarDadosCliente(Integer id) {
		String sql = "SELECT h.id, h.pessoa_id, p.id, p.nome, p.cpf_cnpj FROM historico_veiculo AS h, pessoa AS p WHERE h.id = "
				+ id + " AND p.id = h.pessoa_id";
		HistoricoVeiculo historico = null;
		ArrayList<HistoricoVeiculo> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				String nomeCliente = rs.getString("p.nome");
				String cpfCnpj = rs.getString("p.cpf_cnpj");

				historico = new HistoricoVeiculo(nomeCliente, cpfCnpj);

				lista.add(historico);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public ArrayList<HistoricoVeiculo> listarTodosIdVeiculo(Integer veiculo_id) {
		String sql = "SELECT h.id, h.tipo, h.descricao, DATE_FORMAT(h.data, '%d/%m/%Y'), FORMAT(h.valor,2,'de_DE'), h.responsavel FROM historico_veiculo AS h WHERE h.veiculo_id = "
				+ veiculo_id + " ORDER BY h.id";
		HistoricoVeiculo historico = null;
		ArrayList<HistoricoVeiculo> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				Integer id = rs.getInt("h.id");
				String tipo = rs.getString("h.tipo");
				String descricao = rs.getString("h.descricao");
				String data = rs.getString("DATE_FORMAT(h.data, '%d/%m/%Y')");
				String valor = rs.getString("FORMAT(h.valor,2,'de_DE')");
				// String situacao = rs.getString("h.situacao");
				String responsavel = rs.getString("h.responsavel");

				historico = new HistoricoVeiculo(id, tipo, descricao, null, null, responsavel, null, null, data, valor,
						null, null);

				lista.add(historico);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public String getHistoricoVeiculo(Integer id) {
		String resultado = null;
		String sql = "SELECT * FROM historico_veiculo WHERE id = ?";

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setInt(1, id);
			ResultSet rs = cmd.executeQuery();

			if (rs.next()) {
				resultado = rs.getString("tipo");
			}
			cmd.close();

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return resultado;

	}

}

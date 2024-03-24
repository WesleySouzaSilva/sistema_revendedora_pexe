package br.com.sistema.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import br.com.sistema.conexao.AbstractGenericDAO;
import br.com.sistema.conexao.Conexao;
import br.com.sistema.model.Endereco;

public class EnderecoDAO extends AbstractGenericDAO<Endereco> {

	public EnderecoDAO(Conexao conexao) {
		super(conexao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean inserir(Endereco pojo) {
		String sqlEndereco = "INSERT INTO endereco(rua, bairro, numero, cidade, estado, cep) VALUES (?, ?, ?, ?, ?, ?) ";
		try {
			PreparedStatement stmt = dbConnection.prepareStatement(sqlEndereco);
			stmt.setString(1, pojo.getRua());
			stmt.setString(2, pojo.getBairro());
			stmt.setString(3, pojo.getNumero());
			stmt.setString(4, pojo.getCidade());
			stmt.setString(5, pojo.getUf());
			stmt.setString(6, pojo.getCep());
			int retornoEnd = stmt.executeUpdate();

			if (retornoEnd > 0) {
				// salva o id gerado pelo banco no próprio objeto
				pojo.setId(ultimoID("endereco"));
			}

			return retornoEnd > 0;

		} catch (SQLException e) {
			e.printStackTrace();

			return false;
		}
	}

	@Override
	public boolean apagar(Endereco pojo) {
		String sqlEndereco = "DELETE FROM endereco WHERE id =?";
		try {

			PreparedStatement cmdEndereco = dbConnection.prepareStatement(sqlEndereco);
			cmdEndereco.setInt(1, pojo.getId());
			int retorno = cmdEndereco.executeUpdate();
			cmdEndereco.close();

			return retorno > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean atualizar(Endereco novo) {
		String sql = "UPDATE endereco SET rua = ?, bairro = ?, numero = ?, cidade = ?, estado = ?, cep = ? WHERE id = ?";

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, novo.getRua());
			cmd.setString(2, novo.getBairro());
			cmd.setString(3, novo.getNumero());
			cmd.setString(4, novo.getCidade());
			cmd.setString(5, novo.getUf());
			cmd.setString(6, novo.getCep());
			cmd.setInt(7, novo.getId());
			int retorno = cmd.executeUpdate();
			cmd.close();
			return retorno > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public Endereco buscar(Integer id) {
		String sql = "SELECT e.id, e.rua, e.bairro, e.numero, e.cidade, e.estado, e.cep, p.id, p.endereco_id FROM endereco AS e, pessoa as p WHERE p.id = '"
				+ id + "' AND e.id = p.endereco_id";
		Endereco endereco = null;
		try {
			Statement cmd = dbConnection.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			// enquanto houver um pr�ximo registro, leia-os
			while (rs.next()) {
				int ids = rs.getInt("e.id");
				String rua = rs.getString("e.rua");
				String bairro = rs.getString("e.bairro");
				String numero = rs.getString("e.numero");
				String cidade = rs.getString("e.cidade");
				String estado = rs.getString("e.estado");
				String cep = rs.getString("e.cep");

				endereco = new Endereco(ids, rua, bairro, numero, cidade, estado, cep);

			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return endereco;
	}

	public Integer getEndereco(String id) {
		String sql = "SELECT * FROM endereco WHERE id = " + id + "";
		Endereco endereco = null;
		try {
			Statement cmd = dbConnection.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			// enquanto houver um pr�ximo registro, leia-os
			while (rs.next()) {
				int ids = rs.getInt("id");

				endereco = new Endereco(ids, null, null, null, null, null, null);

			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return endereco.getId();
	}

	@Override
	public List<Endereco> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
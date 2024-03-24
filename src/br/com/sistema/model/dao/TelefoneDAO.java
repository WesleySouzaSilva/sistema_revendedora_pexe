package br.com.sistema.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import br.com.sistema.conexao.AbstractGenericDAO;
import br.com.sistema.conexao.Conexao;
import br.com.sistema.model.Telefone;

public class TelefoneDAO extends AbstractGenericDAO<Telefone> {

	public TelefoneDAO(Conexao conexao) {
		super(conexao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean inserir(Telefone pojo) {
		String sqlTelefone = "INSERT INTO telefone(telCelular, telComercial, telResidencial, telWhatsapp) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement s = dbConnection.prepareStatement(sqlTelefone);
			s.setString(1, pojo.getTelCelular());
			s.setString(2, pojo.getTelComercial());
			s.setString(3, pojo.getTelResidencial());
			s.setString(4, pojo.getTelWhatsapp());

			int retornaTel = s.executeUpdate();
			if (retornaTel > 0) {
				// salva o id gerado pelo banco no prÃ³prio objeto
				pojo.setId(ultimoID("telefone"));
			}

			return retornaTel > 0;

		} catch (SQLException e) {
			e.printStackTrace();

			return false;
		}
	}

	@Override
	public boolean apagar(Telefone pojo) {
		String sqlTelefone = "DELETE FROM telefone WHERE id =?";
		try {

			PreparedStatement cmdTelefone = dbConnection.prepareStatement(sqlTelefone);
			cmdTelefone.setInt(1, pojo.getId());
			int retorno = cmdTelefone.executeUpdate();
			cmdTelefone.close();

			return retorno > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean atualizar(Telefone novo) {
		String sql = "UPDATE telefone SET telCelular = ?, telComercial = ?, telResidencial = ?, telWhatsapp = ? WHERE id = ?";

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, novo.getTelCelular());
			cmd.setString(2, novo.getTelComercial());
			cmd.setString(3, novo.getTelResidencial());
			cmd.setString(4, novo.getTelWhatsapp());
			cmd.setInt(5, novo.getId());
			int retorno = cmd.executeUpdate();
			cmd.close();
			return retorno > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public Telefone buscar(Integer id) {
		String sql = "SELECT t.id, t.telCelular, t.telComercial, t.telResidencial, t.telWhatsapp, p.id, p.telefone_id FROM telefone AS t, pessoa as p WHERE p.id = '"
				+ id + "' AND t.id = p.telefone_id";
		Telefone telefone = null;
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();

			if (rs.next()) {
				String telCelular = rs.getString("t.telCelular");
				String telComercial = rs.getString("t.telComercial");
				String telResidencial = rs.getString("t.telResidencial");
				String telWhatsapp = rs.getString("t.telWhatsapp");

				telefone = new Telefone(null, telComercial, telCelular, telResidencial, telWhatsapp);
			}

			cmd.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return telefone;
	}

	public Integer getTelefone(String id) {
		String sql = "SELECT * FROM email WHERE id = " + id + "";
		Telefone telefone = null;
		try {
			Statement cmd = dbConnection.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				int ids = rs.getInt("id");

				telefone = new Telefone(ids, null, null, null, null);

			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return telefone.getId();
	}

	@Override
	public List<Telefone> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}

package br.com.sistema.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.sistema.conexao.AbstractGenericDAO;
import br.com.sistema.conexao.Conexao;
import br.com.sistema.model.Pessoa;

public class PessoaDAOPJ extends AbstractGenericDAO<Pessoa> {

	final private EnderecoDAO enderecoDAO;
	final private TelefoneDAO telefoneDAO;
	final private EmailDAO emailDAO;

	public PessoaDAOPJ(Conexao conexao, EnderecoDAO enderecoDAO, TelefoneDAO telefoneDAO, EmailDAO emailDAO) {
		super(conexao);
		this.emailDAO = emailDAO;
		this.enderecoDAO = enderecoDAO;
		this.telefoneDAO = telefoneDAO;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean inserir(Pessoa novo) {
		String sql = "INSERT INTO pessoa(Nome, Cpf_Cnpj, Iestadual, endereco_id, email_id, telefone_id, ativo, tipo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);

			cmd.setString(1, novo.getNome());
			cmd.setString(2, novo.getCpfcnpj());
			cmd.setString(3, novo.getIestadual());
			cmd.setInt(4, novo.getEndereco().getId());
			cmd.setInt(5, novo.getEmail().getId());
			cmd.setInt(6, novo.getTelefone().getId());
			cmd.setString(7, novo.getAtivo());
			cmd.setString(8, novo.getTipo());
			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no próprio objeto
				novo.setId(ultimoID("pessoa"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean apagar(Pessoa novo) {
		String sqlPessoa = "DELETE FROM pessoa WHERE id =?";
		String sqlEndereco = "DELETE FROM endereco WHERE id =?";
		String sqlTelefone = "DELETE FROM telefone WHERE id = ?";
		String sqlEmail = "DELETE FROM email WHERE id = ?";

		try {
			PreparedStatement cmdEmail = dbConnection.prepareStatement(sqlEmail);
			cmdEmail.setInt(1, novo.getEmail().getId());
			cmdEmail.executeUpdate();
			cmdEmail.close();

			PreparedStatement cmdTelefone = dbConnection.prepareStatement(sqlTelefone);
			cmdTelefone.setInt(1, novo.getTelefone().getId());
			cmdTelefone.executeUpdate();
			cmdTelefone.close();

			PreparedStatement cmdEndereco = dbConnection.prepareStatement(sqlEndereco);
			cmdEndereco.setInt(1, novo.getEndereco().getId());
			cmdEndereco.executeUpdate();
			cmdEndereco.close();

			PreparedStatement cmdPessoa = dbConnection.prepareStatement(sqlPessoa);
			cmdPessoa.setInt(1, novo.getId());
			int retornoPessoa = cmdPessoa.executeUpdate();
			cmdPessoa.close();
			return retornoPessoa > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean atualizar(Pessoa novo) {
		String sql = "UPDATE pessoa SET Nome = ?, Cpf_Cnpj = ?, endereco_id = ?, email_id = ?, telefone_id = ? WHERE id = ?";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);

			cmd.setString(1, novo.getNome());
			cmd.setString(2, novo.getCpfcnpj());
			cmd.setInt(3, novo.getEndereco().getId());
			cmd.setInt(4, novo.getEmail().getId());
			cmd.setInt(5, novo.getTelefone().getId());
			cmd.setInt(6, novo.getId());

			int retorno = cmd.executeUpdate();
			cmd.close();
			if (retorno > 0) {
				// salva o id gerado pelo banco no próprio objeto
				novo.setId(ultimoID("pessoa"));
			}

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Pessoa> buscarCpfCnpj(String campo) {
		String sql = "SELECT * FROM pessoa WHERE Cpf_Cnpj ='" + campo + "'";
		Pessoa pessoa = null;
		ArrayList<Pessoa> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr�ximo registro, leia-os
			if (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("Nome");
				String cpfcnpj = rs.getString("Cpf_Cnpj");
//				Integer clientepf_id = rs.getInt("clientePF_id");
//				ClientePF clientepf = clientePFDAO.buscarNome(clientepf_id);
//				Integer clientepj_id = rs.getInt("clientePJ_id");
//				ClientePJ clientepj = clientePJDAO.buscarNome(clientepj_id);

				pessoa = new Pessoa(id, nome, cpfcnpj, null, null, null, null, null, null, null);

				System.out.println("id: " + id);
				System.out.println("nome: " + nome);
				System.out.println("cpf_cnpj: " + cpfcnpj);

				lista.add(pessoa);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public EmailDAO getEmailDAO() {
		return emailDAO;
	}

	public EnderecoDAO getEnderecoDAO() {
		return enderecoDAO;
	}

	public TelefoneDAO getTelefoneDAO() {
		return telefoneDAO;
	}

	@Override
	public List<Pessoa> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}

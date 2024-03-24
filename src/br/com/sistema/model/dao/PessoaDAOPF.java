package br.com.sistema.model.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.sistema.conexao.AbstractGenericDAO;
import br.com.sistema.conexao.Conexao;
import br.com.sistema.model.Pessoa;

public class PessoaDAOPF extends AbstractGenericDAO<Pessoa> {

	final private EnderecoDAO enderecoDAO;
	final private TelefoneDAO telefoneDAO;
	final private EmailDAO emailDAO;

	public PessoaDAOPF(Conexao conexao, EnderecoDAO enderecoDAO, TelefoneDAO telefoneDAO, EmailDAO emailDAO) {
		super(conexao);
		this.emailDAO = emailDAO;
		this.enderecoDAO = enderecoDAO;
		this.telefoneDAO = telefoneDAO;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean inserir(Pessoa pojo) {
		String sql = "INSERT INTO pessoa(Nome, Cpf_Cnpj, rg, dataNascimento, sexo, endereco_id, email_id, telefone_id, ativo, tipo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);

			cmd.setString(1, pojo.getNome());
			cmd.setString(2, pojo.getCpfcnpj());
			cmd.setString(3, pojo.getRg());
			cmd.setDate(4, (Date) pojo.getDataNascimento());
			cmd.setString(5, pojo.getSexo());
			cmd.setInt(6, pojo.getEndereco().getId());
			cmd.setInt(7, pojo.getEmail().getId());
			cmd.setInt(8, pojo.getTelefone().getId());
			cmd.setString(9, pojo.getAtivo());
			cmd.setString(10, pojo.getTipo());

			int retorno = cmd.executeUpdate();
			cmd.close();
			if (retorno > 0) {
				// salva o id gerado pelo banco no próprio objeto
				pojo.setId(ultimoID("pessoa"));
			}

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean apagar(Pessoa pojo) {
		// melhor usar ON DELETE CASCADE durante a criação da tabela no
		// constraint
		String sqlPessoa = "DELETE FROM pessoa WHERE id =?";
		String sqlEndereco = "DELETE FROM endereco WHERE id =?";
		String sqlTelefone = "DELETE FROM telefone WHERE id = ?";
		String sqlEmail = "DELETE FROM email WHERE id = ?";

		try {
			PreparedStatement cmdEmail = dbConnection.prepareStatement(sqlEmail);
			cmdEmail.setInt(1, pojo.getEmail().getId());
			cmdEmail.executeUpdate();
			cmdEmail.close();

			PreparedStatement cmdTelefone = dbConnection.prepareStatement(sqlTelefone);
			cmdTelefone.setInt(1, pojo.getTelefone().getId());
			cmdTelefone.executeUpdate();
			cmdTelefone.close();

			PreparedStatement cmdEndereco = dbConnection.prepareStatement(sqlEndereco);
			cmdEndereco.setInt(1, pojo.getEndereco().getId());
			cmdEndereco.executeUpdate();
			cmdEndereco.close();

			PreparedStatement cmdPessoa = dbConnection.prepareStatement(sqlPessoa);
			cmdPessoa.setInt(1, pojo.getId());
			int retornoPessoa = cmdPessoa.executeUpdate();
			cmdPessoa.close();
			return retornoPessoa > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean atualizar(Pessoa pojo) {
		String sql = "UPDATE pessoa SET Nome = ?, Cpf_Cnpj = ?, rg = ?, dataNascimento = ?, sexo = ?, endereco_id = ?, email_id = ?, telefone_id = ? WHERE id = ?";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);

			cmd.setString(1, pojo.getNome());
			cmd.setString(2, pojo.getCpfcnpj());
			cmd.setString(3, pojo.getRg());
			cmd.setDate(4, (Date) pojo.getDataNascimento());
			cmd.setString(5, pojo.getSexo());
			cmd.setInt(6, pojo.getEndereco().getId());
			cmd.setInt(7, pojo.getEmail().getId());
			cmd.setInt(8, pojo.getTelefone().getId());
			cmd.setInt(9, pojo.getId());

			int retorno = cmd.executeUpdate();
			cmd.close();
			if (retorno > 0) {
				// salva o id gerado pelo banco no próprio objeto
				pojo.setId(ultimoID("pessoa"));
			}

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Pessoa> buscarCpfCnpj(String campo) {
		String sql = "SELECT * FROM pessoa WHERE Cpf_Cnpj ='" + campo + "'";
		String cpf = "SELECT formata_cpf('" + campo + "') FROM pessoa";
		Pessoa pessoa = null;
		ArrayList<Pessoa> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			PreparedStatement cmdCpf = dbConnection.prepareStatement(cpf);
			ResultSet rs = cmd.executeQuery();
			ResultSet rsCpf = cmdCpf.executeQuery();
			// enquanto houver um pr�ximo registro, leia-os
			if (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("Nome");
				String cpfcnpj = rsCpf.getString("formata_cpf('" + campo + "')");
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

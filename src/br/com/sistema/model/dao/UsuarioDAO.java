package br.com.sistema.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.sistema.conexao.AbstractGenericDAO;
import br.com.sistema.conexao.Conexao;
import br.com.sistema.model.Usuario;

public class UsuarioDAO extends AbstractGenericDAO<Usuario> {

	public UsuarioDAO(Conexao conexao) {
		super(conexao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean inserir(Usuario pojo) {
		String sql = "INSERT INTO usuario(nome, senha, permissao) VALUES(?, ?, ?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, pojo.getNome());
			cmd.setString(2, pojo.getSenha());
			cmd.setString(3, pojo.getPermissao());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no prÃ³prio objeto
				pojo.setId(ultimoID("usuario"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean apagar(Usuario pojo) {
		String sqlPessoa = "DELETE FROM usuario WHERE id =?";

		try {

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
	public boolean atualizar(Usuario novo) {
		String sql = "UPDATE usuario SET nome = ?, senha = ?, permissao = ? WHERE id = ?";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);

			cmd.setString(1, novo.getNome());
			cmd.setString(2, novo.getSenha());
			cmd.setString(3, novo.getPermissao());
			cmd.setInt(4, novo.getId());

			int retorno = cmd.executeUpdate();
			cmd.close();
			if (retorno > 0) {
				// salva o id gerado pelo banco no prÃ³prio objeto
				novo.setId(ultimoID("usuario"));
			}

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean criarTabela(String sql) {
		String sqlAlteracao = sql;
		boolean retorno = false;
		PreparedStatement cmd = null;
		try {
			cmd = dbConnection.prepareStatement(sqlAlteracao);
			int linhasAfetadas = cmd.executeUpdate();

			if (linhasAfetadas > 0) {
				System.out.println("Tabela adicionada com sucesso.");
				retorno = true;
			} else {
				System.out.println("A tabela já existe no BD.");
			}

		} catch (SQLException e) {
			System.err.println("Erro ao executar a operação: " + e.getMessage());
		} finally {

			try {
				if (cmd != null) {
					cmd.close();
				}
			} catch (SQLException e) {
				System.err.println("Erro ao fechar o PreparedStatement: " + e.getMessage());
			}
		}

		return retorno;
	}

	public boolean atualizarSql(String tabela, String coluna, String sql) {
		String sqlAlteracao = sql;
		boolean retorno = false;

		try {
			if (!verificarSeColunaExiste(tabela, coluna)) {
				PreparedStatement cmd = dbConnection.prepareStatement(sqlAlteracao);
				int linhasAfetadas = cmd.executeUpdate();
				cmd.close();

				if (linhasAfetadas > 0) {
					System.out.println("Coluna adicionada com sucesso.");
					retorno = true;
				} else {
					System.out.println("A coluna já existe na tabela.");
				}
			} else {
				System.out.println("A coluna já existe na tabela.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Erro ao executar a operação: " + e.getMessage());
		}

		return retorno;
	}

	private boolean verificarSeColunaExiste(String tabela, String coluna) {
		String sql = "SELECT COUNT(*) FROM information_schema.columns WHERE table_name = ? AND column_name = ?";

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, tabela);
			cmd.setString(2, coluna);
			ResultSet resultSet = cmd.executeQuery();

			if (resultSet.next()) {
				int retorno = resultSet.getInt(1);
				return retorno > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public List<Usuario> listarTodos() {
		String sql = "SELECT * FROM usuario ORDER BY nome";

		ArrayList<Usuario> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String permissao = rs.getString("permissao");

				Usuario usuario = new Usuario(id, nome, null, permissao);

				lista.add(usuario);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;

	}

	public List<Usuario> buscarNome(String campo) {
		String sql = "SELECT * FROM usuario WHERE nome like '%" + campo + "' ORDER BY nome";
		Usuario usuario = null;
		ArrayList<Usuario> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			if (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String permissao = rs.getString("permissao");

				usuario = new Usuario(id, nome, null, permissao);

				lista.add(usuario);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public boolean logar(Conexao conexao, String usuario, String senha) {
		boolean logado = false;
		String sql = "SELECT COUNT(*) AS quant FROM usuario WHERE upper(nome)=? AND senha=?";

		try {
			PreparedStatement cmd = conexao.getConexao().prepareStatement(sql);
			cmd.setString(1, usuario);
			cmd.setString(2, senha);
			ResultSet rs = cmd.executeQuery();

			if (rs.next() && rs.getInt("quant") > 0) {
				logado = true;
			}

			cmd.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return logado;
	}

	public List<Usuario> buscarId(Integer campo) {
		String sql = "SELECT * FROM usuario WHERE id = " + campo + "";
		Usuario usuario = null;
		ArrayList<Usuario> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			if (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String senha = rs.getString("senha");
				String permissao = rs.getString("permissao");

				usuario = new Usuario(id, nome, senha, permissao);

				lista.add(usuario);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

}

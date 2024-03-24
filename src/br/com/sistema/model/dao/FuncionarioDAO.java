package br.com.sistema.model.dao;

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
import br.com.sistema.model.Funcionario;

public class FuncionarioDAO extends AbstractGenericDAO<Funcionario> {

	public FuncionarioDAO(Conexao conexao) {
		super(conexao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean inserir(Funcionario pojo) {
		String sql = "INSERT INTO funcionario(nome, profissao, data_admissao, endereco_id, telefone_id, email_id, ativo) VALUES(?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, pojo.getNome());
			cmd.setString(2, pojo.getProfissao());
			cmd.setDate(3, (Date) pojo.getDataAdmissao());
			cmd.setInt(4, pojo.getEndereco().getId());
			cmd.setInt(5, pojo.getTelefone().getId());
			cmd.setInt(6, pojo.getEmail().getId());
			cmd.setString(7, pojo.getAtivo());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no prÃ³prio objeto
				pojo.setId(ultimoID("funcionario"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean apagar(Funcionario pojo) {
		String sqlPessoa = "UPDATE funcionario SET ativo ? where id = ?";

		try {

			PreparedStatement cmdPessoa = dbConnection.prepareStatement(sqlPessoa);
			cmdPessoa.setString(1, pojo.getAtivo());
			cmdPessoa.setInt(2, pojo.getId());
			int retornoPessoa = cmdPessoa.executeUpdate();
			cmdPessoa.close();
			return retornoPessoa > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean atualizar(Funcionario novo) {
		String sql = "UPDATE funcionario SET nome = ?, profissao = ?, data_admissao = ?, ativo = ? WHERE id = ?";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, novo.getNome());
			cmd.setString(2, novo.getProfissao());
			cmd.setDate(3, (Date) novo.getDataAdmissao());
			cmd.setString(4, novo.getAtivo());
			cmd.setInt(5, novo.getId());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no prÃ³prio objeto
				novo.setId(ultimoID("funcionario"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean atualizarDemissao(Funcionario novo) {
		String sql = "UPDATE funcionario SET data_demissao = ?, ativo = ? WHERE id = ?";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);

			cmd.setDate(1, (Date) novo.getDemissao());
			cmd.setString(2, novo.getAtivo());
			cmd.setInt(3, novo.getId());

			int retorno = cmd.executeUpdate();
			cmd.close();
			if (retorno > 0) {
				// salva o id gerado pelo banco no prÃ³prio objeto
				novo.setId(ultimoID("funcionario"));
			}

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Funcionario buscar(String campo) {

		return null;
	}

	public List<Funcionario> listar() {
		String sql = "SELECT * FROM funcionario WHERE ativo = 'SIM' ORDER BY nome";
		List<Funcionario> lista = new ArrayList<>();

		try {
			Statement cmd = dbConnection.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				String nome = rs.getString("nome");

				Funcionario f = new Funcionario(null, nome, null, null, null, null, null, null, null);
				lista.add(f);

			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;

	}

	public List<Despesa> listarTodosDespesa(java.util.Date dataInicial, java.util.Date dataFinal) {
		String sql = "SELECT id, descricao, responsavel, DATE_FORMAT(data_vencimento, '%d/%m/%Y'), FORMAT(valor,2,'de_DE')  FROM despesas  WHERE data BETWEEN '"
				+ dataInicial + "' AND '" + dataFinal + "' AND tipo LIKE 'DESPESA FUNCIONARI%' ORDER BY data";
		Despesa despesa = null;
		ArrayList<Despesa> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String descricao = rs.getString("descricao");
				String responsavel = rs.getString("responsavel");
				String data = rs.getString("DATE_FORMAT(data_vencimento, '%d/%m/%Y')");
				String valor = rs.getString("FORMAT(valor,2,'de_DE')");

				despesa = new Despesa(id, null, descricao, null, null, null, valor, responsavel, data, null, null, null,
						null, null);
				lista.add(despesa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;

	}

	@Override
	public List<Funcionario> listarTodos() {
		String sql = "SELECT f.id, f.nome, f.profissao, DATE_FORMAT(f.data_admissao, '%d/%m/%Y'), DATE_FORMAT(f.data_demissao, '%d/%m/%Y'), f.ativo FROM funcionario AS f ORDER BY f.nome";
		Funcionario funcionario = null;
		ArrayList<Funcionario> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				Integer id = rs.getInt("f.id");
				String nome = rs.getString("f.nome");
				String profissao = rs.getString("f.profissao");
				String dataAdmissao = rs.getString("DATE_FORMAT(f.data_admissao, '%d/%m/%Y')");
				String dataDemissao = rs.getString("DATE_FORMAT(f.data_demissao, '%d/%m/%Y')");
				String ativo = rs.getString("f.ativo");

				funcionario = new Funcionario(id, nome, profissao, ativo, null, dataAdmissao, dataDemissao);

				lista.add(funcionario);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;

	}

	public List<Funcionario> listarTodosDadosId(Integer id) {
		String sql = "SELECT f.id, f.nome, f.profissao, DATE_FORMAT(f.data_admissao, '%d/%m/%Y'),f.endereco_id, f.telefone_id, f.email_id, e.id,e.rua, e.bairro, e.numero, e.cep, e.cidade, e.estado, t.id,t.telCelular, t.telComercial, t.telResidencial, t.telWhatsapp, em.id, em.email FROM funcionario AS f, endereco AS e, telefone AS t, email AS em WHERE f.id = "
				+ id + " AND e.id = f.endereco_id AND t.id = f.telefone_id AND em.id = f.email_id";

		Funcionario funcionario = null;
		ArrayList<Funcionario> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				Integer ids = rs.getInt("f.id");
				String nome = rs.getString("f.nome");
				String profissao = rs.getString("f.profissao");
				String dataAdmissao = rs.getString("DATE_FORMAT(f.data_admissao, '%d/%m/%Y')");
				String rua = rs.getString("e.rua");
				String bairro = rs.getString("e.bairro");
				String numero = rs.getString("e.numero");
				String cidade = rs.getString("e.cidade");
				String estado = rs.getString("e.estado");
				String cep = rs.getString("e.cep");
				String telCelular = rs.getString("t.telCelular");
				String telComercial = rs.getString("t.telComercial");
				String telResidencial = rs.getString("t.telResidencial");
				String telWhatsapp = rs.getString("t.telWhatsapp");
				String email = rs.getString("em.email");

				funcionario = new Funcionario(ids, nome, profissao, null, null, null, null, null, null, dataAdmissao,
						rua, bairro, cidade, estado, cep, numero, telCelular, telComercial, telResidencial, telWhatsapp,
						email);
				lista.add(funcionario);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;

	}

	public List<Funcionario> listarTodosNome(String nome) {
		String sql = "SELECT f.id, f.nome, f.profissao, DATE_FORMAT(f.data_admissao, '%d/%m/%Y'), DATE_FORMAT(f.data_demissao, '%d/%m/%Y'), f.ativo FROM funcionario AS f WHERE f.nome like '"
				+ nome + "%' ORDER BY f.nome";
		Funcionario funcionario = null;
		ArrayList<Funcionario> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				Integer id = rs.getInt("f.id");
				String nomes = rs.getString("f.nome");
				String profissao = rs.getString("f.profissao");
				String dataAdmissao = rs.getString("DATE_FORMAT(f.data_admissao, '%d/%m/%Y')");
				String dataDemissao = rs.getString("DATE_FORMAT(f.data_demissao, '%d/%m/%Y')");
				String ativo = rs.getString("f.ativo");

				funcionario = new Funcionario(id, nomes, profissao, ativo, null, dataAdmissao, dataDemissao);

				lista.add(funcionario);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;

	}

	public List<Funcionario> listarTodosAtivo(String ativo) {
		String sql = "SELECT f.id, f.nome, f.profissao, DATE_FORMAT(f.data_admissao, '%d/%m/%Y'), DATE_FORMAT(f.data_demissao, '%d/%m/%Y'), f.ativo FROM funcionario AS f WHERE f.ativo like '"
				+ ativo + "%' ORDER BY f.nome";
		Funcionario funcionario = null;
		ArrayList<Funcionario> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				Integer id = rs.getInt("f.id");
				String nome = rs.getString("f.nome");
				String profissao = rs.getString("f.profissao");
				String dataAdmissao = rs.getString("DATE_FORMAT(f.data_admissao, '%d/%m/%Y')");
				String dataDemissao = rs.getString("DATE_FORMAT(f.data_demissao, '%d/%m/%Y')");
				String ativos = rs.getString("f.ativo");

				funcionario = new Funcionario(id, nome, profissao, ativos, null, dataAdmissao, dataDemissao);

				lista.add(funcionario);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;

	}

	public List<Funcionario> listarEnderecoFuncionario(Integer id) {
		String sql = "SELECT f.id, f.nome, f.endereco_id, f.telefone_id, f.email_id, e.id,e.rua, e.bairro, e.numero, e.cep, e.cidade, e.estado, t.id,t.telCelular, t.telComercial, t.telResidencial, t.telWhatsapp, em.id, em.email FROM funcionario AS f, endereco AS e, telefone AS t, email AS em WHERE f.id = "
				+ id + " AND e.id = f.endereco_id AND t.id = f.telefone_id AND em.id = f.email_id";

		Funcionario funcionario = null;
		ArrayList<Funcionario> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				Integer ids = rs.getInt("f.id");
				String rua = rs.getString("e.rua");
				String bairro = rs.getString("e.bairro");
				String numero = rs.getString("e.numero");
				String cidade = rs.getString("e.cidade");
				String estado = rs.getString("e.estado");
				String cep = rs.getString("e.cep");
				String telCelular = rs.getString("t.telCelular");
				String telComercial = rs.getString("t.telComercial");
				String telResidencial = rs.getString("t.telResidencial");
				String telWhatsapp = rs.getString("t.telWhatsapp");
				String email = rs.getString("em.email");

				funcionario = new Funcionario(ids, rua, bairro, cidade, estado, cep, numero, telCelular, telComercial,
						telResidencial, telWhatsapp, email);

				lista.add(funcionario);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;

	}

	public String getTotalDespesa(java.util.Date dataInicial, java.util.Date dataFinal) {
		String valor = null;
		String sql = "SELECT FORMAT(SUM(valor),2,'de_DE') FROM despesas WHERE data BETWEEN '" + dataInicial + "' AND '"
				+ dataFinal + "' AND tipo LIKE 'DESPESA FUNCIONARI%'";
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

	public String getNome(Integer id) {
		String valor = null;
		String sql = "SELECT * FROM funcionario WHERE id = " + id + "";
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

	public Integer getIdNome(String nome) {
		Integer valor = null;
		String sql = "SELECT * FROM funcionario WHERE nome = '" + nome + "'";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getInt("id");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public Integer getIdEndereco(Integer id) {
		Integer valor = null;
		String sql = "SELECT * FROM funcionario WHERE id = " + id + "";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getInt("endereco_id");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public Integer getIdEmail(Integer id) {
		Integer valor = null;
		String sql = "SELECT * FROM funcionario WHERE id = " + id + "";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getInt("email_id");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public Integer getIdTelefone(Integer id) {
		Integer valor = null;
		String sql = "SELECT * FROM funcionario WHERE id = " + id + "";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getInt("telefone_id");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public String getStatusDemissao(Integer id) {
		String valor = null;
		String sql = "SELECT * FROM funcionario WHERE id = " + id + "";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getString("ativo");
			}
		} catch (Exception e) {
		}
		return valor;
	}
}

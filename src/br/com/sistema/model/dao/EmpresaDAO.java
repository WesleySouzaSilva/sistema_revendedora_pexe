package br.com.sistema.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.sistema.conexao.AbstractGenericDAO;
import br.com.sistema.conexao.Conexao;
import br.com.sistema.model.Despesa;
import br.com.sistema.model.Empresa;

public class EmpresaDAO extends AbstractGenericDAO<Empresa> {

	public EmpresaDAO(Conexao conexao) {
		super(conexao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean inserir(Empresa pojo) {
		String sql = "INSERT INTO empresa(nome, cnpj, endereco_id, telefone_id, email_id) VALUES(?, ?, ?, ?, ?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, pojo.getNome());
			cmd.setString(2, pojo.getCnpj());
			cmd.setInt(3, pojo.getEndereco().getId());
			cmd.setInt(4, pojo.getTelefone().getId());
			cmd.setInt(5, pojo.getEmail().getId());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no prÃ³prio objeto
				pojo.setId(ultimoID("empresa"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean apagar(Empresa pojo) {
		String sqlPessoa = "DELETE FROM empresa WHERE id =?";

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
	public boolean atualizar(Empresa novo) {
		String sql = "UPDATE empresa SET nome = ?, cnpj = ?, endereco_id = ?, telefone_id = ?, email_id = ? WHERE id = ?";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, novo.getNome());
			cmd.setString(2, novo.getCnpj());
			cmd.setInt(3, novo.getEndereco().getId());
			cmd.setInt(4, novo.getTelefone().getId());
			cmd.setInt(5, novo.getEmail().getId());
			cmd.setInt(6, novo.getId());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no prÃ³prio objeto
				novo.setId(ultimoID("empresa"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Empresa> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Empresa> listarEnderecoEmpresa(Integer id) {
		String sql = "SELECT f.id, f.nome, f.cnpj,f.endereco_id, f.telefone_id, f.email_id, e.id,e.rua, e.bairro, e.numero, e.cep, e.cidade, e.estado, t.id,t.telCelular, t.telComercial, t.telResidencial, t.telWhatsapp, em.id, em.email FROM empresa AS f, endereco AS e, telefone AS t, email AS em WHERE f.id = "
				+ id + " AND e.id = f.endereco_id AND t.id = f.telefone_id AND em.id = f.email_id";

		Empresa empresa = null;
		ArrayList<Empresa> lista = new ArrayList<>();

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

				empresa = new Empresa(ids, null, null, rua, bairro, cep, numero, cidade, estado, telCelular,
						telComercial, telResidencial, telWhatsapp, email);

				lista.add(empresa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<Empresa> listarEmpresaId(Integer id) {
		String sql = "SELECT f.id, f.nome, f.cnpj,f.endereco_id, f.telefone_id, f.email_id, e.id,e.rua, e.bairro, e.numero, e.cep, e.cidade, e.estado, t.id,t.telCelular, t.telComercial, t.telResidencial, t.telWhatsapp, em.id, em.email FROM empresa AS f, endereco AS e, telefone AS t, email AS em WHERE f.id = "
				+ id + " AND e.id = f.endereco_id AND t.id = f.telefone_id AND em.id = f.email_id";

		Empresa empresa = null;
		ArrayList<Empresa> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				Integer ids = rs.getInt("f.id");
				String nome = rs.getString("f.nome");
				String cnpj = rs.getString("f.cnpj");
				String rua = rs.getString("e.rua");
				String bairro = rs.getString("e.bairro");
				String numero = rs.getString("e.numero");
				String cidade = rs.getString("e.cidade");
				String estado = rs.getString("e.estado");
				String cep = rs.getString("e.cep");
				String telCelular = null;
				String telComercial = null;
				String telResidencial = null;
				String telWhatsapp = null;
				if (rs.getString("t.telCelular") == null) {
					telCelular = new String("");
				} else {
					telCelular = rs.getString("t.telCelular");
				}
				if (rs.getString("t.telComercial") == null) {
					telComercial = new String("");
				} else {
					telComercial = rs.getString("t.telComercial");
				}
				if (rs.getString("t.telResidencial") == null) {
					telResidencial = new String("");
				} else {
					telResidencial = rs.getString("t.telResidencial");
				}
				if (rs.getString("t.telWhatsapp") == null) {
					telWhatsapp = new String("");
				} else {
					telWhatsapp = rs.getString("t.telWhatsapp");
				}

				String email = rs.getString("em.email");

				empresa = new Empresa(ids, nome, cnpj, rua, bairro, cep, numero, cidade, estado, telCelular,
						telComercial, telResidencial, telWhatsapp, email);

				lista.add(empresa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<Empresa> listarTodosNome(String nome) {
		String sql = "SELECT f.id, f.nome, f.cnpj, f.endereco_id, e.id, e.cidade, e.estado FROM empresa AS f, endereco AS e WHERE f.nome like '"
				+ nome + "%' AND e.id = f.endereco_id ORDER BY f.nome";
		Empresa empresa = null;
		ArrayList<Empresa> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				Integer id = rs.getInt("f.id");
				String nomes = rs.getString("f.nome");
				String cnpj = rs.getString("f.cnpj");
				String cidade = rs.getString("e.cidade");
				String estado = rs.getString("e.estado");

				empresa = new Empresa(id, nomes, cnpj, cidade, estado);

				lista.add(empresa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;

	}

	public List<Empresa> listarTodosCnpj(String cnpj) {
		String sql = "SELECT f.id, f.nome, f.cnpj, f.endereco_id, e.id, e.cidade, e.estado FROM empresa AS f, endereco AS e WHERE f.cnpj = '"
				+ cnpj + "' AND e.id = f.endereco_id ORDER BY f.nome";
		Empresa empresa = null;
		ArrayList<Empresa> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				Integer id = rs.getInt("f.id");
				String nomes = rs.getString("f.nome");
				String cnpjs = rs.getString("f.cnpj");
				String cidade = rs.getString("e.cidade");
				String estado = rs.getString("e.estado");

				empresa = new Empresa(id, nomes, cnpjs, cidade, estado);

				lista.add(empresa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;

	}

	public List<Despesa> listarTodosDespesa(java.util.Date dataInicial, java.util.Date dataFinal) {
		String sql = "SELECT id, descricao, responsavel, DATE_FORMAT(data_vencimento, '%d/%m/%Y'), FORMAT(valor,2,'de_DE')  FROM despesas  WHERE data BETWEEN '"
				+ dataInicial + "' AND '" + dataFinal + "' AND tipo = 'DESPESA EMPRESA' ORDER BY data";
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

	public String getTotalDespesa(java.util.Date dataInicial, java.util.Date dataFinal) {
		String valor = null;
		String sql = "SELECT FORMAT(SUM(valor),2,'de_DE') FROM despesas WHERE data BETWEEN '" + dataInicial + "' AND '"
				+ dataFinal + "' AND tipo = 'DESPESA EMPRESA'";
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
		String sql = "SELECT * FROM empresa WHERE id = " + id + "";
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

	public Integer getIdEndereco(Integer id) {
		Integer valor = null;
		String sql = "SELECT * FROM empresa WHERE id = " + id + "";
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
		String sql = "SELECT * FROM empresa WHERE id = " + id + "";
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
		String sql = "SELECT * FROM empresa WHERE id = " + id + "";
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

	public Integer getIdEmpresa() {
		Integer valor = null;
		String sql = "SELECT MAX(id) FROM empresa";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getInt("MAX(id)");
			}
		} catch (Exception e) {
		}
		return valor;
	}

}

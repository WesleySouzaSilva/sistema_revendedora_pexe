package br.com.sistema.model.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.sistema.conexao.AbstractGenericDAO;
import br.com.sistema.conexao.Conexao;
import br.com.sistema.model.ClienteCrm;
import br.com.sistema.model.Email;
import br.com.sistema.model.Endereco;
import br.com.sistema.model.Pessoa;
import br.com.sistema.model.Telefone;

public class PessoaDAO extends AbstractGenericDAO<Pessoa> {

	public PessoaDAO(Conexao conexao) {
		super(conexao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean inserir(Pessoa pojo) {
		String sql = "INSERT INTO empresa(nome, cnpj, endereco_id, email_id, telefone_id) VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);

			cmd.setString(1, pojo.getNome());
			cmd.setString(2, pojo.getCpfcnpj());
			cmd.setInt(3, pojo.getEndereco().getId());
			cmd.setInt(4, pojo.getEmail().getId());
			cmd.setInt(5, pojo.getTelefone().getId());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no pr贸prio objeto
				pojo.setId(ultimoID("empresa"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean inserirClienteCrm(ClienteCrm pojo) {
		String sql = "INSERT INTO clientes_interesse_compra(nome, telefone, tipo, marca, modelo, valor_inicial, valor_final) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);

			cmd.setString(1, pojo.getNomeCliente());
			cmd.setString(2, pojo.getTelefone());
			cmd.setString(3, pojo.getTipo());
			cmd.setString(4, pojo.getMarca());
			cmd.setString(5, pojo.getModelo());
			cmd.setBigDecimal(6, pojo.getValorInicial());
			cmd.setBigDecimal(7, pojo.getValorFinal());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no pr贸prio objeto
				pojo.setId(ultimoID("clientes_interesse_compra"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean apagar(Pessoa pojo) {
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

	public boolean apagarClienteCrm(Integer id) {
		String sqlPessoa = "DELETE FROM clientes_interesse_compra WHERE id =?";

		try {

			PreparedStatement cmdPessoa = dbConnection.prepareStatement(sqlPessoa);
			cmdPessoa.setInt(1, id);
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
		String sql = "UPDATE pessoa SET ativo = ? WHERE id = ?";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);

			cmd.setString(1, novo.getTipo());
			cmd.setInt(2, novo.getId());

			int retorno = cmd.executeUpdate();
			cmd.close();
			if (retorno > 0) {
				// salva o id gerado pelo banco no pr贸prio objeto
				novo.setId(ultimoID("pessoa"));
			}

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean atualizarClienteCrm(ClienteCrm pojo) {
		String sql = "UPDATE clientes_interesse_compra SET telefone = ?, tipo = ?, marca = ?, modelo = ?, valor_inicial = ?, valor_final = ? WHERE id = ?";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setString(1, pojo.getTelefone());
			cmd.setString(2, pojo.getTipo());
			cmd.setString(3, pojo.getMarca());
			cmd.setString(4, pojo.getModelo());
			cmd.setBigDecimal(5, pojo.getValorInicial());
			cmd.setBigDecimal(6, pojo.getValorFinal());
			cmd.setInt(7, pojo.getId());

			int retorno = cmd.executeUpdate();
			cmd.close();
			if (retorno > 0) {
				// salva o id gerado pelo banco no pr贸prio objeto
				pojo.setId(ultimoID("pessoa"));
			}

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Pessoa buscar(Integer ids) {
		String sql = "SELECT * FROM pessoa WHERE id ='" + ids + "'";
		Pessoa pessoa = null;

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			if (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("Nome");
				String cpfcnpj = rs.getString("Cpf_Cnpj");
				pessoa = new Pessoa(id, nome, cpfcnpj, null, null, null, null, null, null, null);

				System.out.println("id: " + id);
				System.out.println("nome: " + nome);
				System.out.println("cpf_cnpj: " + cpfcnpj);

				// lista.add(pessoa);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pessoa;
	}

	public Pessoa buscar(String campo) {
		String sql = "SELECT * FROM pessoa WHERE Cpf_Cnpj ='" + campo + "'";
		Pessoa pessoa = null;

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			if (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("Nome");
				String cpfcnpj = rs.getString("Cpf_Cnpj");
				pessoa = new Pessoa(id, nome, cpfcnpj, null, null, null, null, null, null, null);

				System.out.println("id: " + id);
				System.out.println("nome: " + nome);
				System.out.println("cpf_cnpj: " + cpfcnpj);

				// lista.add(pessoa);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pessoa;
	}

	@Override
	public List<Pessoa> listarTodos() {
		String sql = "SELECT * FROM pessoa WHERE ativo = 'SIM' ORDER BY Nome";
		Pessoa pessoa = null;
		ArrayList<Pessoa> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("Nome");
				String cpfcnpj = rs.getString("Cpf_Cnpj");
				String ativo = rs.getString("ativo");
				String tipo = rs.getString("tipo");
				String rg = rs.getString("rg");

				pessoa = new Pessoa(id, nome, cpfcnpj, rg, tipo, ativo);

				lista.add(pessoa);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<Pessoa> listarTodos(Integer id) {
		String sql = "SELECT p.id, p.nome, p.cpf_cnpj,  p.endereco_id, e.id, e.rua, e.bairro FROM pessoa AS p, endereco AS e WHERE p.id = "
				+ id + " AND e.id = p.endereco_id ORDER BY p.nome";
		Pessoa pessoa = null;
		ArrayList<Pessoa> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				int ids = rs.getInt("p.id");
				String nome = rs.getString("p.nome");
				String cpfcnpj = rs.getString("p.cpf_cnpj");
				String rua = rs.getString("e.rua");
				String bairro = rs.getString("e.bairro");

				pessoa = new Pessoa(ids, nome, cpfcnpj, rua, bairro);

				lista.add(pessoa);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<Pessoa> buscarNome(String campo) {
		String sql = "SELECT * FROM pessoa WHERE Nome like '" + campo + "%' and ATIVO = 'SIM' ORDER BY Nome";
		Pessoa pessoa = null;
		ArrayList<Pessoa> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("Nome");
				String cpfcnpj = rs.getString("Cpf_Cnpj");
				String ativo = rs.getString("ativo");
				String tipo = rs.getString("tipo");
				String rg = rs.getString("rg");

				pessoa = new Pessoa(id, nome, cpfcnpj, rg, tipo, ativo);

				lista.add(pessoa);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<Pessoa> buscarNomeEmpresa(String campo) {
		String sql = "SELECT * FROM empresa WHERE nome like '" + campo + "%' ORDER BY nome";
		Pessoa pessoa = null;
		ArrayList<Pessoa> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String cpfcnpj = rs.getString("cnpj");

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

	public List<Pessoa> buscarCpfCnpj(String campo) {
		String sql = "SELECT * FROM empresa WHERE cnpj ='" + campo + "' AND ativo = 'SIM'";
		Pessoa pessoa = null;
		ArrayList<Pessoa> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			if (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String cpfcnpj = rs.getString("cnpj");
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

	public List<Pessoa> buscarCpfCnpjCliente(String campo) {
		String sql = "SELECT * FROM pessoa WHERE Cpf_Cnpj ='" + campo + "' AND ativo = 'SIM'";
		Pessoa pessoa = null;
		ArrayList<Pessoa> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			if (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String cpfcnpj = rs.getString("cnpj");
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

	public List<Pessoa> buscarIdServico(Integer id) {
		String sql = "SELECT p.id, p.Nome, p.Cpf_Cnpj, p.rg, p.dataNascimento, p.sexo, p.Iestadual, e.rua, e.bairro, e.numero, e.cidade, e.estado, e.cep, t.telComercial, t.telCelular, t.telResidencial, t.telWhatsapp, m.email FROM pessoa AS p INNER JOIN endereco e INNER JOIN telefone t INNER JOIN email m ON e.id = p.id AND t.id = p.id AND m.id = p.id WHERE p.id = '"
				+ id + "'";
		List<Pessoa> lista = new ArrayList<>();
		Pessoa pessoa = new Pessoa();
		Endereco endereco = new Endereco();
		Telefone telefone = new Telefone();
		Email email = new Email();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				pessoa.setId(rs.getInt("p.id"));
				pessoa.setNome(rs.getString("p.nome"));
				pessoa.setCpfcnpj(rs.getString("p.Cpf_Cnpj"));
				pessoa.setRg(rs.getString("p.rg"));
				pessoa.setDataNascimento(rs.getDate("p.dataNascimento"));
				pessoa.setSexo(rs.getString("p.sexo"));
				pessoa.setIestadual(rs.getString("p.Iestadual"));
				endereco.setCidade(rs.getString("e.cidade"));
				endereco.setBairro(rs.getString("e.bairro"));
				endereco.setCep(rs.getString("e.cep"));
				endereco.setNumero(rs.getString("e.numero"));
				endereco.setRua(rs.getString("e.rua"));
				endereco.setUf(rs.getString("e.estado"));
				pessoa.setEndereco(endereco);
				telefone.setTelCelular(rs.getString("t.telCelular"));
				telefone.setTelComercial(rs.getString("t.telComercial"));
				telefone.setTelResidencial(rs.getString("t.telResidencial"));
				telefone.setTelWhatsapp(rs.getString("t.telWhatsapp"));
				pessoa.setTelefone(telefone);
				email.setEmail(rs.getString("m.email"));
				pessoa.setEmail(email);
				lista.add(pessoa);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<Pessoa> buscarEmpresa(Integer id) {
		String sql = "SELECT p.id, p.nome, p.cnpj, e.rua, e.bairro, e.numero, e.cidade, e.estado, e.cep, t.telComercial, t.telCelular, t.telResidencial, t.telWhatsapp, m.email FROM empresa AS p INNER JOIN endereco e INNER JOIN telefone t INNER JOIN email m ON e.id = p.endereco_id AND t.id = p.telefone_id AND m.id = p.email_id WHERE p.id = '"
				+ id + "'";
		List<Pessoa> lista = new ArrayList<>();
		Pessoa pessoa = new Pessoa();
		Endereco endereco = new Endereco();
		Telefone telefone = new Telefone();
		Email email = new Email();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				pessoa.setId(rs.getInt("p.id"));
				pessoa.setNome(rs.getString("p.nome"));
				pessoa.setCpfcnpj(rs.getString("p.cnpj"));
				endereco.setCidade(rs.getString("e.cidade"));
				endereco.setBairro(rs.getString("e.bairro"));
				endereco.setCep(rs.getString("e.cep"));
				endereco.setNumero(rs.getString("e.numero"));
				endereco.setRua(rs.getString("e.rua"));
				endereco.setUf(rs.getString("e.estado"));
				pessoa.setEndereco(endereco);
				telefone.setTelCelular(rs.getString("t.telCelular"));
				telefone.setTelComercial(rs.getString("t.telComercial"));
				telefone.setTelResidencial(rs.getString("t.telResidencial"));
				telefone.setTelWhatsapp(rs.getString("t.telWhatsapp"));
				pessoa.setTelefone(telefone);
				email.setEmail(rs.getString("m.email"));
				pessoa.setEmail(email);
				lista.add(pessoa);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<Pessoa> buscarCidade(Integer id) {
		String sql = "SELECT p.id, p.nome, p.Cpf_Cnpj, p.rg , DATE_FORMAT(p.dataNascimento, '%d/%m/%Y'),e.rua, e.bairro, e.numero, e.cidade, e.estado, e.cep, t.telComercial, t.telCelular, t.telResidencial, t.telWhatsapp, m.email FROM pessoa AS p INNER JOIN endereco e INNER JOIN telefone t INNER JOIN email m ON e.id = p.endereco_id AND t.id = p.telefone_id AND m.id = p.email_id WHERE p.id = '"
				+ id + "'";
		List<Pessoa> lista = new ArrayList<>();
		Pessoa pessoa = new Pessoa();
		Endereco endereco = new Endereco();
		Telefone telefone = new Telefone();
		Email email = new Email();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				pessoa.setId(rs.getInt("p.id"));
				pessoa.setNome(rs.getString("p.nome"));
				pessoa.setCpfcnpj(rs.getString("p.Cpf_Cnpj"));
				pessoa.setRg(rs.getString("p.rg"));
				pessoa.setDataNascimentoFormatada(rs.getString("DATE_FORMAT(p.dataNascimento, '%d/%m/%Y')"));
				endereco.setCidade(rs.getString("e.cidade"));
				endereco.setBairro(rs.getString("e.bairro"));
				endereco.setCep(rs.getString("e.cep"));
				endereco.setNumero(rs.getString("e.numero"));
				endereco.setRua(rs.getString("e.rua"));
				endereco.setUf(rs.getString("e.estado"));
				pessoa.setEndereco(endereco);
				telefone.setTelCelular(rs.getString("t.telCelular"));
				telefone.setTelComercial(rs.getString("t.telComercial"));
				telefone.setTelResidencial(rs.getString("t.telResidencial"));
				telefone.setTelWhatsapp(rs.getString("t.telWhatsapp"));
				pessoa.setTelefone(telefone);
				email.setEmail(rs.getString("m.email"));
				pessoa.setEmail(email);
				lista.add(pessoa);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<ClienteCrm> listarTodosClienteCrm(Integer id) {
		String sql = "SELECT * FROM clientes_interesse_compra ORDER BY nome";
		ClienteCrm clienteCrm = null;
		ArrayList<ClienteCrm> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				int ids = rs.getInt("id");
				String nome = rs.getString("nome");
				String telefone = rs.getString("telefone");
				String tipo = rs.getString("tipo");
				String marca = rs.getString("marca");
				String modelo = rs.getString("modelo");
				BigDecimal valorInicial = rs.getBigDecimal("valor_inicial");
				BigDecimal valorFinal = rs.getBigDecimal("valor_final");

				clienteCrm = new ClienteCrm(ids, nome, telefone, tipo, marca, modelo, valorInicial, valorFinal);

				lista.add(clienteCrm);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<ClienteCrm> listarTodosClienteCrm() {
		String sql = "SELECT * FROM clientes_interesse_compra ORDER BY nome";
		ClienteCrm clienteCrm = null;
		ArrayList<ClienteCrm> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String telefone = rs.getString("telefone");
				String tipo = rs.getString("tipo");
				String marca = rs.getString("marca");
				String modelo = rs.getString("modelo");
				BigDecimal valorInicial = rs.getBigDecimal("valor_inicial");
				BigDecimal valorFinal = rs.getBigDecimal("valor_final");

				clienteCrm = new ClienteCrm(id, nome, telefone, tipo, marca, modelo, valorInicial, valorFinal);

				lista.add(clienteCrm);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<ClienteCrm> listarTodosClienteCrm(String campo, String pesquisa) {
		String sql = "SELECT * FROM clientes_interesse_compra WHERE " + campo + " LIKE = '" + pesquisa + "%' ORDER BY "
				+ campo + "";
		ClienteCrm clienteCrm = null;
		ArrayList<ClienteCrm> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String telefone = rs.getString("telefone");
				String tipo = rs.getString("tipo");
				String marca = rs.getString("marca");
				String modelo = rs.getString("modelo");
				BigDecimal valorInicial = rs.getBigDecimal("valor_inicial");
				BigDecimal valorFinal = rs.getBigDecimal("valor_final");

				clienteCrm = new ClienteCrm(id, nome, telefone, tipo, marca, modelo, valorInicial, valorFinal);

				lista.add(clienteCrm);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<ClienteCrm> listarTodosClienteCrmTipo(String tipo, String marca, String modelo) {
		String sql = "SELECT * FROM clientes_interesse_compra WHERE tipo = '" + tipo + "' AND marca = '" + marca
				+ "' AND (modelo IS NULL OR modelo = '" + modelo + "')";
		ClienteCrm clienteCrm = null;
		ArrayList<ClienteCrm> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String telefone = rs.getString("telefone");
				String tipos = rs.getString("tipo");
				String marcas = rs.getString("marca");
				String modelos = rs.getString("modelo");
				BigDecimal valorInicial = rs.getBigDecimal("valor_inicial");
				BigDecimal valorFinal = rs.getBigDecimal("valor_final");

				clienteCrm = new ClienteCrm(id, nome, telefone, tipos, marcas, modelos, valorInicial, valorFinal);

				lista.add(clienteCrm);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<ClienteCrm> listarTodosClienteCrmValores(String tipo, String marca, String modelo) {
		String sql = "SELECT * FROM clientes_interesse_compra WHERE tipo = '" + tipo + "' AND marca = '" + marca
				+ "' AND modelo LIKE '%" + modelo + "%'";
		ClienteCrm clienteCrm = null;
		ArrayList<ClienteCrm> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String telefone = rs.getString("telefone");
				String tipos = rs.getString("tipo");
				String marcas = rs.getString("marca");
				String modelos = rs.getString("modelo");
				BigDecimal valorInicia = rs.getBigDecimal("valor_inicial");
				BigDecimal valorFina = rs.getBigDecimal("valor_final");

				clienteCrm = new ClienteCrm(id, nome, telefone, tipos, marcas, modelos, valorInicia, valorFina);

				lista.add(clienteCrm);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<ClienteCrm> listarTodosClienteCrmValores(String tipo, BigDecimal valorInicial, BigDecimal valorFinal) {
		String sql = "SELECT * FROM clientes_interesse_compra WHERE tipo = '" + tipo + "' AND valor_inicial BETWEEN "
				+ valorInicial + " AND " + valorFinal + " OR valor_final BETWEEN " + valorInicial + " AND " + valorFinal
				+ "";
		ClienteCrm clienteCrm = null;
		ArrayList<ClienteCrm> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String telefone = rs.getString("telefone");
				String tipos = rs.getString("tipo");
				String marcas = rs.getString("marca");
				String modelos = rs.getString("modelo");
				BigDecimal valorInicia = rs.getBigDecimal("valor_inicial");
				BigDecimal valorFina = rs.getBigDecimal("valor_final");

				clienteCrm = new ClienteCrm(id, nome, telefone, tipos, marcas, modelos, valorInicia, valorFina);

				lista.add(clienteCrm);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public Integer getQtdeClienteCrmValores(String tipo, BigDecimal valorInicial, BigDecimal valorFinal) {
		String sql = "SELECT COUNT(*) FROM clientes_interesse_compra WHERE tipo = ? "
				+ "AND (valor_inicial BETWEEN ? AND ? OR valor_final BETWEEN ? AND ?)";
		Integer valor = 0;

		try (PreparedStatement cmd = dbConnection.prepareStatement(sql)) {
			cmd.setString(1, tipo);
			cmd.setBigDecimal(2, valorInicial);
			cmd.setBigDecimal(3, valorFinal);
			cmd.setBigDecimal(4, valorInicial);
			cmd.setBigDecimal(5, valorFinal);

			try (ResultSet rs = cmd.executeQuery()) {
				if (rs.next()) {
					valor = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return valor;
	}

	public Integer getQtdeClienteCrmCategorias(String categoria, String marca, String modelo) {
		String sql = "SELECT COUNT(DISTINCT ci.id) " + "FROM clientes_interesse_compra ci "
				+ "INNER JOIN veiculo v ON ci.categoria = v.categoria AND ci.marca = v.marca AND ci.modelo = v.veiculo "
				+ "WHERE ci.categoria = ? AND ci.marca = ? AND ci.modelo = ? " + "AND NOT EXISTS ("
				+ "    SELECT 1 FROM veiculo v2 "
				+ "    WHERE ci.valor_inicial BETWEEN v2.valor_venda AND v2.valor_venda "
				+ "       OR ci.valor_final BETWEEN v2.valor_venda AND v2.valor_venda " + ")";

		Integer valor = 0;

		try (PreparedStatement cmd = dbConnection.prepareStatement(sql)) {
			cmd.setString(1, categoria);
			cmd.setString(2, marca);
			cmd.setString(3, modelo);

			try (ResultSet rs = cmd.executeQuery()) {
				if (rs.next()) {
					valor = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return valor;
	}

	public List<ClienteCrm> obterClientesEncontrados(String tipo) {
		List<ClienteCrm> clientesEncontrados = new ArrayList<>();

		String sql = "SELECT DISTINCT ci.id, ci.nome, ci.telefone, ci.tipo, ci.marca, ci.modelo, ci.valor_inicial, ci.valor_final, v.id, v.categoria, v.veiculo, v.ano_modelo, v.marca, v.placa, v.cor, v.km, v.valor_venda FROM clientes_interesse_compra ci "
				+ "INNER JOIN veiculo v ON (ci.tipo = v.categoria AND ci.marca = v.marca AND ci.modelo = v.veiculo) "
				+ "OR (v.valor_venda BETWEEN ci.valor_inicial AND ci.valor_final) " + "WHERE v.situacao = ?";

		try (PreparedStatement cmd = dbConnection.prepareStatement(sql)) {
			cmd.setString(1, tipo);

			try (ResultSet rs = cmd.executeQuery()) {
				while (rs.next()) {
					ClienteCrm cliente = new ClienteCrm(rs.getInt("ci.id"), rs.getString("ci.nome"),
							rs.getString("ci.telefone"), rs.getString("ci.tipo"), rs.getString("ci.marca"),
							rs.getString("ci.modelo"), rs.getBigDecimal("ci.valor_inicial"),
							rs.getBigDecimal("ci.valor_final"), rs.getString("v.veiculo"), rs.getString("v.categoria"),
							rs.getString("v.marca"), rs.getString("v.ano_modelo"), rs.getString("v.valor_venda"),
							rs.getString("v.cor"), rs.getString("v.placa"), rs.getString("v.km"));

					clientesEncontrados.add(cliente);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return clientesEncontrados;
	}

	public List<ClienteCrm> obterClientesEncontrados(String categoria, String marca, BigDecimal valorVenda) {
		List<ClienteCrm> clientesEncontrados = new ArrayList<>();

		String sql = "SELECT DISTINCT ci.id, ci.nome, ci.telefone, ci.tipo, ci.marca, ci.modelo, ci.valor_inicial, ci.valor_final FROM clientes_interesse_compra ci WHERE (ci.tipo = ? AND ci.marca = ?) OR (? BETWEEN ci.valor_inicial AND ci.valor_final);\r\n"
				+ "";

		try (PreparedStatement cmd = dbConnection.prepareStatement(sql)) {
			cmd.setString(1, categoria);
			cmd.setString(2, marca);
			cmd.setBigDecimal(3, valorVenda);

			try (ResultSet rs = cmd.executeQuery()) {
				while (rs.next()) {
					ClienteCrm cliente = new ClienteCrm(rs.getInt("ci.id"), rs.getString("ci.nome"),
							rs.getString("ci.telefone"), rs.getString("ci.tipo"), rs.getString("ci.marca"),
							rs.getString("ci.modelo"), rs.getBigDecimal("ci.valor_inicial"),
							rs.getBigDecimal("ci.valor_final"));

					clientesEncontrados.add(cliente);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return clientesEncontrados;
	}

	public Integer getEnderecoId(Integer id) {
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

	public Integer getEmailId(Integer id) {
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

	public Integer getTelefoneId(Integer id) {
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

	public String getNomeiD(Integer id) {
		String valor = null;
		String sql = "SELECT * FROM pessoa WHERE id = " + id + "";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getString("Nome");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public Integer getEmail(Integer id) {
		String sql = "SELECT * FROM pessoa WHERE id = " + id + "";
		Pessoa pessoa = null;
		Email email = null;
		try {
			Statement cmd = dbConnection.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				int ids = rs.getInt("email_id");
				email = new Email(ids, null);
				pessoa = new Pessoa(null, null, null, null, null, null, null, null, null, email, null, null);

			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pessoa.getEmail().getId();
	}

	public Integer getEndereco(Integer id) {
		String sql = "SELECT * FROM pessoa WHERE id = " + id + "";
		Pessoa pessoa = null;
		Endereco endereco = null;
		try {
			Statement cmd = dbConnection.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				int ids = rs.getInt("endereco_id");
				endereco = new Endereco(ids, null, null, null, null, null, null);
				pessoa = new Pessoa(null, null, null, null, null, null, null, endereco, null, null, null, null);

			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pessoa.getEndereco().getId();
	}

	public Integer getTelefone(Integer id) {
		String sql = "SELECT * FROM pessoa WHERE id = " + id + "";
		Pessoa pessoa = null;
		Telefone telefone = null;
		try {
			Statement cmd = dbConnection.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				int ids = rs.getInt("telefone_id");
				telefone = new Telefone(ids, null, null, null, null);
				pessoa = new Pessoa(null, null, null, null, null, null, null, null, telefone, null, null, null);

			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pessoa.getTelefone().getId();
	}
}

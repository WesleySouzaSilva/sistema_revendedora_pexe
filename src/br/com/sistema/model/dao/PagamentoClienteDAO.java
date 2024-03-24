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
import br.com.sistema.model.Email;
import br.com.sistema.model.Endereco;
import br.com.sistema.model.PagamentoCliente;
import br.com.sistema.model.Pessoa;
import br.com.sistema.model.Telefone;

public class PagamentoClienteDAO extends AbstractGenericDAO<PagamentoCliente> {

	public PagamentoClienteDAO(Conexao conexao) {
		super(conexao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean inserir(PagamentoCliente pojo) {
		String sql = "INSERT INTO pagamento_cliente(mensalidade, dia, ativo, pessoa_id) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);

			cmd.setBigDecimal(1, pojo.getMensalidade());
			cmd.setString(2, pojo.getDia());
			cmd.setString(3, pojo.getAtivo());
			cmd.setInt(4, pojo.getPessoa().getId());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no pr贸prio objeto
				pojo.setId(ultimoID("pagamento_cliente"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean inserirPagamentoClienteTemporaio(PagamentoCliente pojo) {
		String sql = "INSERT INTO pagamento_cliente_temporario(mensalidade, dia, data, pessoa_id, responsavel) VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);

			cmd.setBigDecimal(1, pojo.getMensalidade());
			cmd.setString(2, pojo.getDia());
			cmd.setDate(3, (Date) pojo.getData());
			cmd.setInt(4, pojo.getPessoa().getId());
			cmd.setString(5, pojo.getResponsavel());

			int retorno = cmd.executeUpdate();
			if (retorno > 0) {
				// salva o id gerado pelo banco no pr贸prio objeto
				pojo.setId(ultimoID("pagamento_cliente_temporario"));
			}
			cmd.close();

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean apagar(PagamentoCliente pojo) {
		String sql = "DELETE FROM pagamento_cliente WHERE id = ?";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setInt(1, pojo.getId());
			int retorno = cmd.executeUpdate();
			cmd.close();
			if (retorno > 0) {
				// salva o id gerado pelo banco no pr贸prio objeto
				pojo.setId(ultimoID("pagamento_cliente"));
			}

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean apagarPagamentoCliente(Integer pessoa_id) {
		String sql = "DELETE FROM contas_receber WHERE pessoa_id = ?";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setInt(1, pessoa_id);
			int retorno = cmd.executeUpdate();
			cmd.close();
			if (retorno > 0) {

			}

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean apagarDatastemporarias(Integer cliente_id, String usuario) {
		String sql = "DELETE FROM pagamento_cliente_temporario WHERE pessoa_id = " + cliente_id + " AND responsavel = '"
				+ usuario + "' ";

		try {

			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			int retorno = cmd.executeUpdate();
			cmd.close();
			return retorno > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean atualizar(PagamentoCliente novo) {
		String sql = "UPDATE pagamento_cliente SET mensalidade = ?, dia = ? WHERE id = ?";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			cmd.setBigDecimal(1, novo.getMensalidade());
			cmd.setString(2, novo.getDia());
			cmd.setInt(3, novo.getId());
			int retorno = cmd.executeUpdate();
			cmd.close();
			if (retorno > 0) {
				// salva o id gerado pelo banco no pr贸prio objeto
				novo.setId(ultimoID("pagamento_cliente"));
			}

			return retorno > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<PagamentoCliente> listarTodos() {
		String sql = "SELECT p.id, p.dia, p.ativo, FORMAT(p.mensalidade,2,'de_DE'), p.pessoa_id, pe.id, pe.nome, pe.endereco_id, e.id, e.rua, e.bairro FROM pagamento_cliente AS p, pessoa AS pe, endereco AS e WHERE pe.id = p.pessoa_id AND e.id = pe.endereco_id AND p.ativo = 'SIM' ORDER BY pe.nome";
		PagamentoCliente pagamentoCliente = null;
		ArrayList<PagamentoCliente> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				Integer id = rs.getInt("p.id");
				String dia = rs.getString("p.dia");
				String ativo = rs.getString("p.ativo");
				String mensalidade = rs.getString("FORMAT(p.mensalidade,2,'de_DE')");
				String nomeCliente = rs.getString("pe.nome");
				String rua = rs.getString("e.rua");
				String bairro = rs.getString("e.bairro");

				pagamentoCliente = new PagamentoCliente(id, dia, ativo, nomeCliente, rua, bairro, mensalidade);

				lista.add(pagamentoCliente);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<PagamentoCliente> listarTodos(Integer id) {
		String sql = "SELECT p.id, p.dia, p.ativo, FORMAT(p.mensalidade,2,'de_DE'), p.pessoa_id, pe.id, pe.nome, pe.endereco_id, e.id, e.rua, e.bairro FROM pagamento_cliente AS p, pessoa AS pe, endereco AS e WHERE p.id = "
				+ id + " AND pe.id = p.pessoa_id AND e.id = pe.endereco_id ORDER BY pe.nome";
		PagamentoCliente pagamentoCliente = null;
		ArrayList<PagamentoCliente> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				Integer ids = rs.getInt("p.id");
				String dia = rs.getString("p.dia");
				String ativo = rs.getString("p.ativo");
				String mensalidade = rs.getString("FORMAT(p.mensalidade,2,'de_DE')");
				String nomeCliente = rs.getString("pe.nome");
				String rua = rs.getString("e.rua");
				String bairro = rs.getString("e.bairro");

				pagamentoCliente = new PagamentoCliente(ids, dia, ativo, nomeCliente, rua, bairro, mensalidade);

				lista.add(pagamentoCliente);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<PagamentoCliente> listarTodos(String busca) {
		String sql = "SELECT p.id, p.dia, p.ativo, FORMAT(p.mensalidade,2,'de_DE'), p.pessoa_id, pe.id, pe.nome, pe.endereco_id, e.id, e.rua, e.bairro FROM pagamento_cliente AS p, pessoa AS pe, endereco AS e WHERE pe.id = p.pessoa_id AND e.id = pe.endereco_id AND p.ativo = 'SIM' ORDER BY "
				+ busca + "";
		PagamentoCliente pagamentoCliente = null;
		ArrayList<PagamentoCliente> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				Integer id = rs.getInt("p.id");
				String dia = rs.getString("p.dia");
				String ativo = rs.getString("p.ativo");
				String mensalidade = rs.getString("FORMAT(p.mensalidade,2,'de_DE')");
				String nomeCliente = rs.getString("pe.nome");
				String rua = rs.getString("e.rua");
				String bairro = rs.getString("e.bairro");

				pagamentoCliente = new PagamentoCliente(id, dia, ativo, nomeCliente, rua, bairro, mensalidade);

				lista.add(pagamentoCliente);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<PagamentoCliente> listarTodos(String busca, String pesquisa) {
		String sql = "SELECT p.id, p.dia, p.ativo, FORMAT(p.mensalidade,2,'de_DE'), p.pessoa_id, pe.id, pe.nome, pe.endereco_id, e.id, e.rua, e.bairro FROM pagamento_cliente AS p, pessoa AS pe, endereco AS e WHERE "
				+ busca + " LIKE '" + pesquisa
				+ "&' AND pe.id = p.pessoa_id AND e.id = pe.endereco_id AND p.ativo = 'SIM' ORDER BY " + busca + "";
		PagamentoCliente pagamentoCliente = null;
		ArrayList<PagamentoCliente> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um pr髕imo registro, leia-os
			while (rs.next()) {
				Integer id = rs.getInt("p.id");
				String dia = rs.getString("p.dia");
				String ativo = rs.getString("p.ativo");
				String mensalidade = rs.getString("FORMAT(p.mensalidade,2,'de_DE')");
				String nomeCliente = rs.getString("pe.nome");
				String rua = rs.getString("e.rua");
				String bairro = rs.getString("e.bairro");

				pagamentoCliente = new PagamentoCliente(id, dia, ativo, nomeCliente, rua, bairro, mensalidade);

				lista.add(pagamentoCliente);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<PagamentoCliente> listaTodosTemporarios(Integer cliente_id, String responsavel) {
		ArrayList<PagamentoCliente> lista = new ArrayList<>();
		String sql = "SELECT p.id, p.dia, p.responsavel, p.data, p.mensalidade,  DATE_FORMAT(p.data, '%d/%m/%Y'), FORMAT(p.mensalidade,2,'de_DE'), p.pessoa_id FROM pagamento_cliente_temporario AS p WHERE p.pessoa_id = "
				+ cliente_id + " AND p.responsavel = '" + responsavel + "' ORDER BY p.id ";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			while (rs.next()) {
				Integer id = rs.getInt("p.id");
				String dia = rs.getString("p.dia");
				String dataFormatada = rs.getString("DATE_FORMAT(p.data, '%d/%m/%Y')");
				String responsavels = rs.getString("p.responsavel");
				String valorFormatado = rs.getString("FORMAT(p.mensalidade,2,'de_DE')");
				Integer pessoaId = rs.getInt("p.pessoa_id");
				Date data = rs.getDate("p.data");
				BigDecimal valo = rs.getBigDecimal("p.mensalidade");

				Pessoa p = new Pessoa(pessoaId, null, null, null, null, null, null, null, null, null);

				PagamentoCliente pa = new PagamentoCliente(id, dia, valorFormatado, dataFormatada, responsavels, p,
						data, valo);

				lista.add(pa);

			}

		} catch (Exception e) {
			// TODO: handle exception
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

	public Integer getIdClientePagamento(Integer id) {
		Integer valor = null;
		String sql = "SELECT * FROM pagamento_cliente WHERE id = " + id + "";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getInt("pessoa_id");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public Integer getIdRotaCliente(Integer cliente_id) {
		Integer valor = null;
		String sql = "SELECT * FROM rota_cliente WHERE pessoa_id = " + cliente_id + "";
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

	public Integer getUltimaDatatemporaria(Integer cliente_id, String responsavel) {
		Integer valor = null;
		String sql = "SELECT MAX(MONTH(data)) FROM pagamento_cliente_temporario WHERE pessoa_id = " + cliente_id
				+ "  AND responsavel = '" + responsavel + "'";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getInt("MAX(MONTH(data))");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public Date getUltimaDataContasReceber(Integer cliente_id, String descricao) {
		Date valor = null;
		String sql = "SELECT MAX(data) FROM contas_receber WHERE pessoa_id = " + cliente_id + "  AND descricao = '"
				+ descricao + "'";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getDate("MAX(data)");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public String getDiaPagamentoCliente(Integer cliente_id) {
		String valor = null;
		String sql = "SELECT * FROM pagamento_cliente WHERE pessoa_id = " + cliente_id + " AND ativo = 'SIM'";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getString("dia");
			}
		} catch (Exception e) {
		}
		return valor;
	}

	public BigDecimal getMensalidadeCliente(Integer cliente_id) {
		BigDecimal valor = null;
		String sql = "SELECT * FROM pagamento_cliente WHERE pessoa_id = " + cliente_id + " AND ativo = 'SIM'";
		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				valor = rs.getBigDecimal("mensalidade");
			}
		} catch (Exception e) {
		}
		return valor;
	}

}

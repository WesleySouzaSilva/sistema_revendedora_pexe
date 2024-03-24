package br.com.sistema.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.sistema.conexao.AbstractGenericDAO;
import br.com.sistema.conexao.Conexao;
import br.com.sistema.model.ClientePFProperty;
import br.com.sistema.model.Email;
import br.com.sistema.model.Endereco;
import br.com.sistema.model.Telefone;

public class ClientePFPropertyDAO extends AbstractGenericDAO<ClientePFProperty> {

	public ClientePFPropertyDAO(Conexao conexao) {
		super(conexao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean inserir(ClientePFProperty pojo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean apagar(ClientePFProperty pojo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean atualizar(ClientePFProperty novo) {
		// TODO Auto-generated method stub
		return false;
	}

	public ClientePFProperty buscar(Integer id) {
		String sql = "SELECT p.id, p.Nome, p.Cpf_Cnpj, p.rg, DATE_FORMAT(p.dataNascimento, '%d/%m/%Y'), p.sexo, p.Iestadual, e.rua, e.bairro, e.numero, e.cidade, e.estado, e.cep, t.telComercial, t.telCelular, t.telResidencial, t.telWhatsapp, m.email FROM pessoa AS p INNER JOIN endereco e INNER JOIN telefone t INNER JOIN email m ON e.id = p.id AND t.id = p.id AND m.id = p.id WHERE p.id ='"
				+ id + "'";
		ClientePFProperty ClientePFProperty = new ClientePFProperty();
		Endereco endereco = new Endereco();
		Telefone telefone = new Telefone();
		Email email = new Email();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				int ids = rs.getInt("p.id");
				String nome = rs.getString("p.nome");
				String CpfCnpj = rs.getString("p.Cpf_Cnpj");
				String rg = rs.getString("p.rg");
				String dataNascimento = rs.getString("DATE_FORMAT(p.dataNascimento, '%d/%m/%Y')");
				String sexo = rs.getString("p.sexo");
				String IEstadual = rs.getString("p.Iestadual");
				String cidade = rs.getString("e.cidade");
				String bairro = rs.getString("e.bairro");
				String cep = rs.getString("e.cep");
				String numero = rs.getString("e.numero");
				String rua = rs.getString("e.rua");
				String estado = rs.getString("e.estado");

				String telCelular = rs.getString("t.telCelular");
				String telComercial = rs.getString("t.telComercial");
				String telResidencial = rs.getString("t.telResidencial");
				String telWhatsapp = rs.getString("t.telWhatsapp");

				String emails = rs.getString("m.email");

				endereco = new Endereco(null, rua, bairro, numero, cidade, estado, cep);
				telefone = new Telefone(null, telComercial, telCelular, telResidencial, telWhatsapp);
				email = new Email(null, emails);

				ClientePFProperty = new ClientePFProperty(ids, nome, CpfCnpj, rg, sexo, dataNascimento, IEstadual,
						endereco, telefone, email);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ClientePFProperty;
	}

	public List<ClientePFProperty> buscarCidade(Integer id) {
		String sql = "SELECT p.id, p.Nome, p.Cpf_Cnpj, p.rg, DATE_FORMAT(p.dataNascimento, '%d/%m/%Y'), p.sexo, p.Iestadual, p.telefone_id, p.endereco_id, p.email_id, e.rua, e.bairro, e.numero, e.cidade, e.estado, e.cep, t.telComercial, t.telCelular, t.telResidencial, t.telWhatsapp, m.email FROM pessoa AS p INNER JOIN endereco e INNER JOIN telefone t INNER JOIN email m ON e.id = p.endereco_id AND t.id = p.telefone_id AND m.id = p.email_id WHERE p.id ='"
				+ id + "'";
		ClientePFProperty ClientePFProperty = new ClientePFProperty();
		Endereco endereco = new Endereco();
		Telefone telefone = new Telefone();
		Email email = new Email();
		ArrayList<ClientePFProperty> lista = new ArrayList<>();

		try {
			PreparedStatement cmd = dbConnection.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			// enquanto houver um próximo registro, leia-os
			while (rs.next()) {
				int ids = rs.getInt("p.id");
				String nome = rs.getString("p.nome");
				String CpfCnpj = rs.getString("p.Cpf_Cnpj");
				String rg = rs.getString("p.rg");
				String dataNascimento = rs.getString("DATE_FORMAT(p.dataNascimento, '%d/%m/%Y')");
				String sexo = rs.getString("p.sexo");
				String IEstadual = rs.getString("p.Iestadual");
				String cidade = rs.getString("e.cidade");
				String bairro = rs.getString("e.bairro");
				String cep = rs.getString("e.cep");
				String numero = rs.getString("e.numero");
				String rua = rs.getString("e.rua");
				String estado = rs.getString("e.estado");

				String telCelular = rs.getString("t.telCelular");
				String telComercial = rs.getString("t.telComercial");
				String telResidencial = rs.getString("t.telResidencial");
				String telWhatsapp = rs.getString("t.telWhatsapp");

				String emails = rs.getString("m.email");

				endereco = new Endereco(null, rua, bairro, numero, cidade, estado, cep);
				telefone = new Telefone(null, telComercial, telCelular, telResidencial, telWhatsapp);
				email = new Email(null, emails);

				ClientePFProperty = new ClientePFProperty(ids, nome, CpfCnpj, rg, sexo, dataNascimento, IEstadual,
						endereco, telefone, email);
				lista.add(ClientePFProperty);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	@Override
	public List<ClientePFProperty> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}

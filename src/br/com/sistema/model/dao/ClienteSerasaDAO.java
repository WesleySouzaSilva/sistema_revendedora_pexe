package br.com.sistema.model.dao;

import java.util.List;

import br.com.sistema.conexao.AbstractGenericDAO;
import br.com.sistema.conexao.Conexao;
import br.com.sistema.model.ClienteSerasa;

public class ClienteSerasaDAO extends AbstractGenericDAO<ClienteSerasa> {

	public ClienteSerasaDAO(Conexao conexao) {
		super(conexao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean inserir(ClienteSerasa pojo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean apagar(ClienteSerasa pojo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean atualizar(ClienteSerasa novo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ClienteSerasa> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}

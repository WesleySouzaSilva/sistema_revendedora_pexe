package br.com.sistema.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexao {

	private Connection conexao = null;
	private String usuario = "sistema_revendedora";
	private String senha = "bd_revendedora486231";

	// "sistema_revenda", "sistema_revenda", "Bd-@4862*"
	public Conexao() {
		if (conexao == null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				// Conexao com ip 192.168.1.253
//				conexao = DriverManager.getConnection(
//						// ip 192.168.1.253
//						"jdbc:mysql://sistema-revenda.mysql.uhserver.com/" + nomeBanco + "?autoReconnect=true", usuario,
//						senha);
				conexao = DriverManager.getConnection(
						// ip 192.168.1.253
						"jdbc:mysql://localhost:3306/sistema_revendedora?autoReconnect=true&useSSL=false&serverTimezone=UTC", usuario,
						senha);

				System.out.println("Conectou!!");

			} catch (ClassNotFoundException e) {
				System.out.println("Não foi encontrado o Driver de conexão");
				e.printStackTrace();

			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null,
						"Falha de conexao com o BANCO DE DADOS.\n\nVerifique usuario e senha\n\nVerifique se o servidor do banco esta ligado\n\nTente novamente!");
				e.printStackTrace();
			}
		} else {
			System.out.println("conexao aberta, nao será aberta outra conexao");
		}
	}

	public Connection getConexao() {
		return conexao;
	}

	public void fecharConexao() {
		try {
			conexao.close();
			System.out.println("Desconectou!!");
		} catch (Exception e) {
			System.out.println("Falha ao fechar a conexão com o banco");
			e.printStackTrace();
		}
	}
}

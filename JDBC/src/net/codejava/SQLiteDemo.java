package net.codejava;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import main.Pessoa;

public class SQLiteDemo {
	
	private Connection conexao;
	
	public boolean conectar() {
		
		try {
			String url = "jdbc:sqlite:bancoDados/bancoSqlite.db";
			
			this.conexao = DriverManager.getConnection(url);
		}catch(SQLException e) {
			return false;
		}
		System.out.println("Conectado ao Banco de Dados");
		return true;
	}
	
	public boolean desconectar() {
		
		try {
			if(this.conexao.isClosed() == false) {
				this.conexao.close();
			}
		}catch(SQLException e) {
			return false;
		}
		System.out.println("Desconectado do Banco de Dados");
		return true;
	}
	
	public Statement criarStatement() {
		try {
			return this.conexao.createStatement();
		}catch(SQLException e) {
			return null;
		}
	}
	
	public PreparedStatement criarPreparedStatement(String sql) {
		try {
			return this.conexao.prepareStatement(sql);
		}catch(SQLException e) {
			return null;
		}
	}
	
	public Connection getConexao() {
		return this.conexao;
	}
}
package main;
import net.codejava.SQLiteDemo;
import java.sql.Statement;
import java.sql.SQLException;

public class CriarBancoSQL {
	
	private final SQLiteDemo sqLiteDemo;
	
	public CriarBancoSQL(SQLiteDemo pSQLiteDemo) {
		this.sqLiteDemo = pSQLiteDemo;
	}
	
	public void criarTabelaPessoa(){
		String sql = "CREATE TABLE IF NOT EXISTS tbl_estudantes"
				+"("
				+"matrícula text NOT NULL PRIMARY KEY,"
				+"nome text NOT NULL,"
				+"idade integer,"
				+"curso text NOT NULL,"
				+"semestre integer"
				+")";
		
		boolean conectou = false;
		
		try {
			conectou = this.sqLiteDemo.conectar();
			
			Statement stmt = this.sqLiteDemo.criarStatement();
			
			stmt.execute(sql);
			
			System.out.println("Tabela criada ou acessada!");
			
		}catch(SQLException e) {
			System.out.println("Erro ao criar ou acessar tabela.");
		}
	}
}

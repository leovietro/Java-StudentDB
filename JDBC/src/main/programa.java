package main;

import net.codejava.SQLiteDemo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class programa {
	public static void main(String[] args) {
		SQLiteDemo sqLiteDemo = new SQLiteDemo();
		
		CriarBancoSQL criarBancoSQL = new CriarBancoSQL(sqLiteDemo);
		criarBancoSQL.criarTabelaPessoa();
		
		//ESCOLHER OPÇAO
		Object opcao = "";
		
		while(opcao != null) {
		
			JDialog.setDefaultLookAndFeelDecorated(true);
		    Object[] opcaoValues = { "Buscar aluno", "Visualizar banco de dados", "Adicionar aluno", "Modificar aluno", "Deletar aluno"};
		    String opcaoInitialSelection = "Selecione a opção desejada";
		    opcao = JOptionPane.showInputDialog(null, "Qual ação executar no banco de dados?",
		        "Banco de Dados de Estudantes", JOptionPane.QUESTION_MESSAGE, null, opcaoValues, opcaoInitialSelection);
			
		    
		    
		    //BUSCAR UM ALUNO
		    if(opcao == "Buscar aluno") {
		    	JDialog.setDefaultLookAndFeelDecorated(true);
			    Object[] filtroValues = { "Matrícula", "Nome", "Idade", "Curso", "Semestre"};
			    String filtroInitialSelection = "Selecione a opção desejada";
			    Object filtro = JOptionPane.showInputDialog(null, "Filtrar alunos por qual valor?",
			        "Banco de Dados de Estudantes", JOptionPane.QUESTION_MESSAGE, null, filtroValues, filtroInitialSelection);
			    
			    if(filtro == null) {
			    	opcao = null;
			    }
			    
			    else {
			    	String filtrotext = filtro.toString();
			  
			    
				    String valorFiltro = JOptionPane.showInputDialog("Insira o " + filtrotext + " a ser filtrado: ");
				    
			    	ResultSet resultSet = null;
			    	Statement statement = null;
			    	
			    	String query = "SELECT *" 
		    		    	+ " FROM tbl_estudantes"
		    		    	+ " WHERE ";
			    	
			    	if(filtrotext == "Nome" || filtrotext == "Curso" || filtrotext == "Matrícula") {
			    		query = query + filtrotext.toLowerCase() + " = " + "'" + valorFiltro + "'";
			    	}
			    	else {
			    		query = query + filtrotext.toLowerCase() + " = " + Integer.parseInt(valorFiltro);
			    	}
			    	
			    	statement = sqLiteDemo.criarStatement();
			    	
			    	try {
			    		resultSet = statement.executeQuery(query);
			    		
			    		String queryResult = "<html>";
			    		
			    		if(resultSet.next() == true) {
			    			queryResult = queryResult 
					    			+ "(Matrícula: " + resultSet.getString("matrícula") 
					    			+ "; Nome: " + resultSet.getString("nome")
					    			+ "; Idade: " + resultSet.getInt("idade")
					    			+ "; Curso: " + resultSet.getString("curso")
					    			+ "; Semestre: " + resultSet.getInt("semestre")
					    			+ ") <br><br>";
			    			while(resultSet.next()) {
				    			queryResult = queryResult 
				    			+ "(Matrícula: " + resultSet.getString("matrícula") 
				    			+ "; Nome: " + resultSet.getString("nome")
				    			+ "; Idade: " + resultSet.getInt("idade")
				    			+ "; Curso: " + resultSet.getString("curso")
				    			+ "; Semestre: " + resultSet.getInt("semestre")
				    			+ ") <br><br>";
				    			System.out.println(resultSet.getString("nome"));
				    		}
			    		}
			    		else {
			    			queryResult = queryResult + "Aluno(s) não encontrado(s)!<br>Tente novamente.";
			    		}
			    		
				        JOptionPane optionPane = new NarrowOptionPane();
				        optionPane.setMessage(queryResult);
				        optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
				        JDialog dialog = optionPane.createDialog(null, "Banco de Dados de Estudantes");
				        dialog.setVisible(true);
			    	}
			    	catch (SQLException e) {
			    		System.out.println("Erro no query: " + e);
			    	}
			    
			    }
		    }
		    
		    
		    
		    //VISUALIZAR O BANCO DE DADOS
		    if(opcao == "Visualizar banco de dados") {
		    	ResultSet resultSet = null;
		    	Statement statement = null;
		    	
		    	String query = "SELECT * FROM tbl_estudantes";
		    	
		    	statement = sqLiteDemo.criarStatement();
		    	
		    	try {
		    		resultSet = statement.executeQuery(query);
		    		
		    		String queryResult = "<html>";
		    		
		    		if(resultSet.next() == true) {
		    			queryResult = queryResult 
				    			+ "(Matrícula: " + resultSet.getString("matrícula") 
				    			+ "; Nome: " + resultSet.getString("nome")
				    			+ "; Idade: " + resultSet.getInt("idade")
				    			+ "; Curso: " + resultSet.getString("curso")
				    			+ "; Semestre: " + resultSet.getInt("semestre")
				    			+ ") <br><br>";
		    			while(resultSet.next()) {
			    			queryResult = queryResult 
			    			+ "(Matrícula: " + resultSet.getString("matrícula") 
			    			+ "; Nome: " + resultSet.getString("nome")
			    			+ "; Idade: " + resultSet.getInt("idade")
			    			+ "; Curso: " + resultSet.getString("curso")
			    			+ "; Semestre: " + resultSet.getInt("semestre")
			    			+ ") <br><br>";
			    		}
		    		}
		    		else {
		    			queryResult = queryResult + "Não há alunos cadastrados no banco de dados.";
		    		}
		    		
			        JOptionPane optionPane = new NarrowOptionPane();
			        optionPane.setMessage(queryResult);
			        optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
			        JDialog dialog = optionPane.createDialog(null, "Banco de Dados de Estudantes");
			        dialog.setVisible(true);
		    	}
		    	catch (SQLException e) {
		    		System.out.println("Erro no query: " + e);
		    	}
		    }
		    
			
		    
			//INSERT DE UM ALUNO
		    if(opcao == "Adicionar aluno") {
				int verificacao = 0;
				String matrColetado = "";
				String nomeColetado = "";
				String idadeColetado = "";
				String cursoColetado = "";
				String semestreColetado = "";
				
				JFrame frame = new JFrame();
				String[] options = new String[2];
				options[0] = new String("Sim");
				options[1] = new String("Não");
				
				while(verificacao == 0) {
					matrColetado = (JOptionPane.showInputDialog("Insira a matrícula do aluno: "));
					nomeColetado = JOptionPane.showInputDialog("Insira o nome completo do aluno: ");
					idadeColetado = JOptionPane.showInputDialog("Insira a idade do aluno: ");
					cursoColetado = JOptionPane.showInputDialog("Insira o curso do aluno: ");
					semestreColetado = JOptionPane.showInputDialog("Insira o semestre do aluno: ");
					if(matrColetado == null ||nomeColetado == null || idadeColetado == null || cursoColetado == null || semestreColetado == null ) {
				    	verificacao = 1;
				    }
				    
				    else {
					    	
					    int intIdadeColetado = Integer.parseInt(idadeColetado);
					    int intSemestreColetado = Integer.parseInt(semestreColetado);
						
						Pessoa aluno = new Pessoa();
						aluno.setMatricula(matrColetado);
						aluno.setNome(nomeColetado);
						aluno.setIdade(intIdadeColetado);
						aluno.setCurso(cursoColetado);
						aluno.setSemestre(intSemestreColetado);
			
						String sqlInsert = "INSERT INTO tbl_estudantes ("
								+ "matrícula,"
								+ "nome,"
								+ "idade,"
								+ "curso,"
								+ "semestre"
								+ ") VALUES(?, ?, ?, ?, ?)"
								+ ";";
						
						PreparedStatement preparedStatement = sqLiteDemo.criarPreparedStatement(sqlInsert);
						
						try {
							preparedStatement.setString(1, aluno.getMatricula());
							preparedStatement.setString(2, aluno.getNome());
							preparedStatement.setInt(3, aluno.getIdade());
							preparedStatement.setString(4, aluno.getCurso());
							preparedStatement.setInt(5, aluno.getSemestre());
							
							int resultado = preparedStatement.executeUpdate();
							
							if (resultado == 1) {
								System.out.println("O aluno foi inserido.");
							} else {
								System.out.println("O aluno NAO  foi inserido.");
							}
						}
						catch(SQLException e){
							System.out.println("Erro no prepared statement: " + e);
						}
						finally {
							if (preparedStatement != null) {
								try {
									preparedStatement.close();
								} catch(SQLException ex) {
									System.out.println(ex);
								}
							}
					    }
						verificacao = JOptionPane.showOptionDialog(frame.getContentPane(),"Deseja inserir outro aluno?","Banco de Dados de Estudantes", 0,JOptionPane.INFORMATION_MESSAGE,null,options,null);
				    }
				 }
		    }
		    
		    
		    
		    //UPDATE DE UM ALUNO
		    if(opcao == "Modificar aluno") {
		    	PreparedStatement preparedStatement = null;
		    	
		    	String matrAlterado = JOptionPane.showInputDialog("Insira a matrícula do aluno a ser modificado: ");
		    	
		    	String update = "UPDATE tbl_estudantes"
		    			+ " SET"
		    			+ " nome = ?,"
		    			+ " idade = ?,"
		    			+ " curso = ?,"
		    			+ " semestre = ?"
		    			+ " WHERE matrícula = " + "'" + matrAlterado + "'";
		    					
		    	try {
		    		String nomeAlterado = JOptionPane.showInputDialog("Insira o nome completo do aluno: ");
					String StrIdadeAlterado = JOptionPane.showInputDialog("Insira a idade do aluno: ");
					String cursoAlterado = JOptionPane.showInputDialog("Insira o curso do aluno: ");
					String StrSemestreAlterado = JOptionPane.showInputDialog("Insira o semestre do aluno: ");
					
					if(matrAlterado == null || nomeAlterado == null || StrIdadeAlterado == null || cursoAlterado == null || StrSemestreAlterado == null) {
						opcao = null;
					}
					else {
						
						int idadeAlterado = Integer.parseInt(StrIdadeAlterado);
						int semestreAlterado = Integer.parseInt(StrSemestreAlterado);
						
						Pessoa aluno = new Pessoa();
						aluno.setNome(nomeAlterado);
						aluno.setIdade(idadeAlterado);
						aluno.setCurso(cursoAlterado);
						aluno.setSemestre(semestreAlterado);
						
			    		preparedStatement = sqLiteDemo.criarPreparedStatement(update);
			    		preparedStatement.setString(1, aluno.getNome());
			    		preparedStatement.setInt(2, aluno.getIdade());
			    		preparedStatement.setString(3, aluno.getCurso());
			    		preparedStatement.setInt(4, aluno.getSemestre());
			    		
			    		preparedStatement.executeUpdate();
					}
		    	}
		    	catch (SQLException e) {
		    		System.out.println("Erro no update: " + e);
		    		String queryResult = "Aluno não encontrado!";
	    			JOptionPane optionPane = new NarrowOptionPane();
			        optionPane.setMessage(queryResult);
			        optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
			        JDialog dialog = optionPane.createDialog(null, "Banco de Dados de Estudantes");
			        dialog.setVisible(true);
		    	}
		    }
		    
		    
		    
		    //DELETAR UM ALUNO
		    if(opcao == "Deletar aluno") {
		    	PreparedStatement preparedStatement = null;
		    	
		    	String matrDelete = JOptionPane.showInputDialog("Insira a matrícula do aluno a ser deletado do banco de dados: ");
		    	
		    	String delete = "DELETE FROM tbl_estudantes"
		    				+ " WHERE matrícula = ?;";
		    	
		    	try {
		    		preparedStatement = sqLiteDemo.criarPreparedStatement(delete);
		    		preparedStatement.setString(1, matrDelete);
		    		
		    		int delLines = preparedStatement.executeUpdate();
		    		String result = "";
		    		if (delLines > 0) {
		    			result = "Foram deletados " + delLines + " registros.";
		    		}
		    		else {
		    			result = "Não foi encontrado nenhum aluno com esta matrícula.";
		    		}
		    		
	    			JOptionPane optionPane = new NarrowOptionPane();
			        optionPane.setMessage(result);
			        optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
			        JDialog dialog = optionPane.createDialog(null, "Banco de Dados de Estudantes");
			        dialog.setVisible(true);
		    	}
		    	catch (SQLException e) {
		    		System.out.println("Erro ao deletar aluno:" + e);
		    	}
		    }
		    
		}
		
		sqLiteDemo.desconectar();
	}
}

class NarrowOptionPane extends JOptionPane {

	  NarrowOptionPane() {
	  }

	  public int getMaxCharactersPerLineCount() {
	    return 999999999;
	  }
}

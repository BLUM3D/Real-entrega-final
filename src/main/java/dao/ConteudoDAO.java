package dao;

import java.sql.*;

import model.Conteudo;

public class ConteudoDAO {
	private Connection conexao;
	
	private int maxId = 0;
	
	public int getMaxId() {
		return maxId;
	}
	
	
	public ConteudoDAO() {
		conexao = null;
	}
	
	public boolean conectar() {
		String driverName = "com.mysql.jdbc.Driver";                    
		String serverName = "localhost";
		String mydatabase = "user";
		int porta = 3306;
		String url = "jdbc:mysql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "root";
		String password = "1234";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("ConexÃ£o efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("ConexÃ£o NÃƒO efetuada com o postgres -- Driver nÃ£o encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("ConexÃ£o NÃƒO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	
	public boolean inserirConteudo(Conteudo conteudo) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO Conteudo (idConteudo, Nome, Data, Titulo, Url, Autor, Descrição, Conteúdo) "
					       + "VALUES ("+conteudo.getId() + ", '" + conteudo.getNome() + ", '" 
					       + conteudo.getTitulo() + ", '" + conteudo.getUrl() + ", '"
					       + conteudo.getAutor() + ", '" + conteudo.getDescricao() + ", '"
					       + conteudo.getConteudo() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean atualizarConteudo(Conteudo conteudo) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE Conteudo SET Nome = '" + conteudo.getNome() + "', Titulo = '"  
				       + conteudo.getTitulo() + "', Url = '" + conteudo.getUrl() + "', Autor = '" + conteudo.getAutor() 
				       + "', Descrição = '" + conteudo.getDescricao() + "', Conteúdo = '" + conteudo.getConteudo()
					   + " WHERE idConteudo = " + conteudo.getId();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean excluirConteudo(int idConteudo) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM Conteudo WHERE idConteudo = " + idConteudo);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public Conteudo[] getConteudo() {
		Conteudo[] conteudos = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM Conteudo");		
	         if(rs.next()){
	             rs.last();
	             conteudos = new Conteudo[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                conteudos[i] = new Conteudo(rs.getInt("idConteudo"), rs.getString("Nome"), rs.getString("Titulo"),
	                						  rs.getString("Url"), rs.getString("Autor"), rs.getString("Descrição"),
	                						  rs.getString("Conteúdo"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return conteudos;
	}

	/*
	public Conteudo[] getConteudosAutor() {
		Conteudo[] conteudos = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM usuario WHERE usuario.sexo LIKE 'M'");		
	         if(rs.next()){
	             rs.last();
	             conteudos = new Conteudo[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
		                conteudos[i] = newConteudos(rs.getInt("idUsuario"), rs.getString("Email"), 
      		                  rs.getString("Senha"), rs.getString("Nome"), 
      		                  rs.getString("Sobrenome"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuarios;
	}
	*/
}
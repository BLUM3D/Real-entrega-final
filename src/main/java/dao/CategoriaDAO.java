package dao;

import java.sql.*;

import model.Categoria;

public class CategoriaDAO {
	private Connection conexao;
	
	public CategoriaDAO() {
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
		/*
		String driverName = "org.postgresql.Driver ";                    
		String serverName = "localhost";
		String mydatabase = "user";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "postgres";
		String password = "1234";
		 */
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
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
	
	public boolean inserirCategoria(Categoria categoria) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO Categoria (idCategoria, Nome) "
					       + "VALUES ("+categoria.getIdCategoria() + ", '" + categoria.getNomeCategoria()  + "');");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean atualizarCategoria(Categoria categoria) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE Categoria SET Nome = '" + categoria.getNomeCategoria() 
											+ " WHERE idCategoria = " + categoria.getIdCategoria();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean excluirCategoria(int idCategoria) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM Categoria WHERE idCategoria = " + idCategoria);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public Categoria[] getCategoria() {
		Categoria[] categorias = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM Categoria");		
	         if(rs.next()){
	             rs.last();
	             categorias = new Categoria[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                categorias[i] = new Categoria(rs.getInt("idCategoria"), rs.getString("Nome"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return categorias;
	}
}
package dao;

import java.sql.*;

import model.Senha;
import model.Usuario;

import cripto.criptografia;

public class UsuarioDAO {
	private Connection conexao;
	public static int maxId = 0;
	
	public int getMaxId() {
		return maxId;
	}
	
	public UsuarioDAO() {
		conexao = null;
	}
	
	public boolean conectar() {
		
		String driverName = "org.postgresql.Driver";                    
		String serverName = "blum3d.postgres.database.azure.com";
		String mydatabase = "blum3d";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "administrador@blum3d";
		String password = "Blum3d1234";
		
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
	
	public boolean inserirUsuario(Usuario usuario) throws Exception {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			
			String paramEmail = usuario.getEmail().replace("'", "");
			String paramSenha = usuario.getSenha().replace("'", "");
			String paramNome = usuario.getNome().replace("'", "");
			String paramSobrenome = usuario.getSobrenome().replace("'", "");
			
			byte [] paramSenhaEncrypt = criptografia.encrypt(paramSenha);
			String SenhaEncrypt = new String(paramSenhaEncrypt);
			
			
			String sql = "INSERT INTO usuario (\"idUsuario\", \"Nome\", \"Sobrenome\", \"Email\", \"Senha\") VALUES ("+ usuario.getId() + ", '{"+paramNome+"}', '{"+paramSobrenome+"}', '{"+paramEmail+"}', '{"+SenhaEncrypt+"}');";
									      
			
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean atualizarUsuario(Usuario usuario) throws Exception {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String paramSenha = usuario.getSenha();
			
			byte [] paramSenhaEncrypt = criptografia.encrypt(paramSenha);
			String SenhaEncrypt = new String(paramSenhaEncrypt);
			
			String sql = "UPDATE usuario SET \"Nome\" = '{"+usuario.getNome()+"}' \"Sobrenome\" =  '{"+usuario.getSobrenome()+"}' \"Email\" = '{"+usuario.getEmail()+"}' \"Senha\" = '{"+SenhaEncrypt+"}' WHERE \"idUsuario\" = "+usuario.getId()+";";
		
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean excluirUsuario(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM usuario WHERE \"idUsuario\" = "+id+";");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public Usuario[] getUsuarios() {
		Usuario[] usuarios = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM usuario;");		
	         if(rs.next()){
	             rs.last();
	             usuarios = new Usuario[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                usuarios[i] = new Usuario(rs.getInt("idUsuario"), rs.getString("Nome"), rs.getString("Sobrenome"), rs.getString("Email"),  rs.getString("Senha"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuarios;
	}
	
	
	 public Usuario getUsuarioId(int id) {
		Usuario[] usuarios = null;
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM usuario WHERE \"idUsuario\" = " + id+";");		
	         if(rs.next()){
	             rs.last();
	             usuarios = new Usuario[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                usuarios[i] = new Usuario(rs.getInt("idUsuario"), rs.getString("Nome"), rs.getString("Sobrenome"), rs.getString("Email"),  rs.getString("Senha"));
	               return usuarios[i];

	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}
	 public Usuario getUsuarioV(String Email, String Senha) throws Exception {
			Usuario[] usuarios = null;
			String paramEmail = Email.replace("'", "");
			String paramSenha = Senha.replace("'", "");
			
			byte [] paramSenhaEncrypt = criptografia.encrypt(paramSenha);
			String SenhaEncrypt = new String(paramSenhaEncrypt);
			try {
				Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = st.executeQuery("SELECT * FROM usuario WHERE \"Email\" = '{" + paramEmail + "}' AND \"Senha\" = '{" + SenhaEncrypt+"}';");		
		         if(rs.next()){
		             rs.last();
		             usuarios = new Usuario[rs.getRow()];
		             rs.beforeFirst();

		             for(int i = 0; rs.next(); i++) {
		                usuarios[i] = new Usuario(rs.getInt("idUsuario"), rs.getString("Nome"), rs.getString("Sobrenome"), rs.getString("Email"),  rs.getString("Senha"));
		               return usuarios[i];

		             }
		           
		          }
		          st.close();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			return null;
		}
	 
	 
	 
	public boolean verificarUsuario(Usuario usuario) throws Exception {
		boolean status = false;
		String paramEmail = usuario.getEmail().replace("'", "");
		String paramSenha = usuario.getSenha().replace("'", "");
		
		byte [] paramSenhaEncrypt = criptografia.encrypt(paramSenha);
		String SenhaEncrypt = new String(paramSenhaEncrypt);
		
		
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("SELECT * FROM usuario WHERE \"Email\" = '{" + paramEmail +  "}' AND Senha = '{" + SenhaEncrypt+"}'");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public int getMaxIdUsuario() {
		Usuario[] usuarios = null;
		int MaxId = 0;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM usuario;");		
	         if(rs.next()){
	             rs.last();
	             usuarios = new Usuario[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                MaxId = rs.getInt("idUsuario") + 1;  
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		System.out.println(MaxId);
		return MaxId;
	}
    
	
}

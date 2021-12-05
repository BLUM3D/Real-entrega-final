package dao;

import java.io.UnsupportedEncodingException;
import java.lang.System.Logger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import cripto.criptografia;

import org.postgresql.core.Utils;

import model.Senha;
import model.Usuario;

public class SenhaDAO {
	private Connection conexao;
	
	public SenhaDAO() {
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
	
	
	public boolean inserirSenha(Senha senha) throws Exception {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String paramNome = senha.getNomeSenha().replace("'", "");
			String paramSenha = senha.getSenhaSenha().replace("'", "");
			
			byte [] paramSenhaEncrypt = criptografia.encrypt(paramSenha);
			String SenhaEncrypt = new String(paramSenhaEncrypt);
			
			String sql = "INSERT INTO senha (\"idSenha\", \"nomeSenha\", \"senhaSenha\", \"Usuario_idUsuario\") VALUES ("+senha.getIdSenha()+ ", '{"+paramNome+"}', '{"+SenhaEncrypt+"}', "+senha.getIdUsuario()+");";
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean atualizarSenha(Senha senha) throws Exception {
		boolean status = false;
		String paramNome = senha.getNomeSenha().replace("'", "");
		String paramSenha = senha.getSenhaSenha().replace("'", "");
		
		byte [] paramSenhaEncrypt = criptografia.encrypt(paramSenha);
		String SenhaEncrypt = new String(paramSenhaEncrypt);
		
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE senha SET \"Senha\" = '{" + SenhaEncrypt + "}' WHERE \"nomeSenha\" = '{" + paramNome + "}';";
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean excluirSenha(String nomeSenha, int idUsuario) {
		boolean status = false;
		String paramSenha = nomeSenha.replace("'", "");
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM senha WHERE \"nomeSenha\" = '{" + paramSenha +"}' AND \"Usuario_idUsuario\" = "+ idUsuario +";");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public Senha[] getSenhas() {
		Senha[] senhas = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM senha;");		
	         if(rs.next()){
	             rs.last();
	             senhas = new Senha[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                senhas[i] = new Senha(rs.getInt("idSenha"), rs.getString("nomeSenha"), 
	                		                  rs.getString("senhaSenha"), rs.getInt("Usuario_idUsuario"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return senhas;
	}
	
	public Senha[] getSenhasIdUsuario(int idUsuario) {
		Senha[] senhas = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM senha WHERE \"Usuario_idUsuario\" = "+ idUsuario + ";");		
	         if(rs.next()){
	             rs.last();
	             senhas = new Senha[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                senhas[i] = new Senha(rs.getInt("idSenha"), rs.getString("nomeSenha"), 
	                		                  rs.getString("senhaSenha"), rs.getInt("Usuario_idUsuario"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return senhas;
	}
	
	
	public Senha getSenha(String nomeSenha, int idUsuario) {
		Senha[] senhas = null;
		String paramSenha = nomeSenha.replace("'", "");
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM senha WHERE \"nomeSenha\" = '{"+paramSenha+"}' AND \"Usuario_idUsuario\" = "+idUsuario+";");		
	         if(rs.next()){
	             rs.last();
	             senhas = new Senha[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                senhas[i] = new Senha(rs.getInt("idSenha"), (rs.getString("nomeSenha")),
	                		                  rs.getString("senhaSenha"), rs.getInt("Usuario_idUsuario"));
	                
	               return senhas[i];

	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
		
	}

	public int getMaxIdSenha() {
		Senha[] senhas = null;
		int MaxId = 0;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM senha;");		
	         if(rs.next()){
	             rs.last();
	             senhas = new Senha[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                MaxId = rs.getInt("idSenha") + 1;  
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

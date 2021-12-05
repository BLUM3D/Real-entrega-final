package model;

import java.io.Serializable;



public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	protected int idUsuario;
	private String Email;
	private String Senha;
	private String Nome;
	private String Sobrenome;
	
	
	public Usuario() {
		idUsuario = -1;
		Email = "email@padrao.com";
		Senha = "senhapadrao123";
		Nome = "Fulano";
		Sobrenome = "Siclano";
	}
	
	public Usuario(int id, String email, String senha, String nome, String sobrenome) {
		setId(id);
		setEmail(email);
		setSenha(senha);
		setNome(nome);
		setSobrenome(sobrenome);
	}
	
	public void setId(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public void setEmail(String email) {
		this.Email = email;
	}
	public void setSenha(String senha) {
		this.Senha = senha;
	}
	public void setNome(String nome) {
		this.Nome = nome;
	}
	public void setSobrenome(String sobrenome) {
		this.Sobrenome = sobrenome;
	}
	public int getId() {
		return idUsuario;
	}
	public String getEmail() {
		return Email;
	}
	public String getSenha() {
		return Senha;
	}
	public String getNome() {
		return Nome;
	}
	public String getSobrenome() {
		return Sobrenome;
	}
	
	public String toString() {
		return "Usuario: "+ idUsuario + "Email: " + Email + "Senha: " + Senha + "Nome: " + Nome + "Sobrenome: " + Sobrenome;
	
	}
	
}

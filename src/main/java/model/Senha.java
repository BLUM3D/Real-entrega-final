package model;

public class Senha extends Usuario {
	
	public Senha(int idUsuario, String Email, String Senha, String Nome, String Sobrenome) {
		super(idUsuario, Email, Senha, Nome, Sobrenome);
	}
	
	private static final long serialVersionUID = 1L;
	protected int idSenha;
	private String nomeSenha;
	private String senhaSenha;
	private int idUsuario;
	
	public Senha() {
		idSenha = -1;
		nomeSenha = "Seila";
		senhaSenha = "12345abc";
		idUsuario = 0;
	}
	
	public Senha(int idSenha, String nomeSenha, String senhaSenha, int idUsuario) {
		setIdSenha(idSenha);
		setNomeSenha(nomeSenha);
		setSenhaSenha(senhaSenha);
		setIdUsuario(idUsuario);
	}
	
	public void setIdSenha(int idSenha) {
		this.idSenha = idSenha;
	}
	public void setNomeSenha(String nomeSenha) {
		this.nomeSenha = nomeSenha;
	}
	public void setSenhaSenha(String senhaSenha) {
		this.senhaSenha = senhaSenha;
	}
	public int getIdSenha() {
		return idSenha;
	}
	public String getNomeSenha() {
		return nomeSenha;
	}
	public String getSenhaSenha() {
		return senhaSenha;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	
	public String toString() {
		return  "Id Senha: " + idSenha + " Id Usuario: " + idUsuario +  " Nome: " + nomeSenha + " Senha: " + senhaSenha; 
	}

}

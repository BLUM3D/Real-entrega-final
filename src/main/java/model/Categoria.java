package model;

public class Categoria {
	protected int idCategoria;
	private String nomeCategoria;
	public int idSenha;
	
	public Categoria() {
		idCategoria = -1;
		nomeCategoria = "redessociais";
		idSenha = 0;
		
	}
	public Categoria(int idCategoria, String nomeCategoria, int idSenha) {
		setIdCategoria(idCategoria);
		setNomeCategoria(nomeCategoria);
		setIdSenha(idSenha);
	}
	
	
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}
	public int getIdCategoria() {
		return idCategoria;
	}
	public String getNomeCategoria() {
		return nomeCategoria;
	}
	public void setIdSenha(int idSenha) {
		this.idSenha = idSenha;
	}
	public int getIdSenha() {
		return idSenha;
	}
	
	public String toString() {
		return "Id Categoria: " + idCategoria + "Nome: " + nomeCategoria; 
	}

}

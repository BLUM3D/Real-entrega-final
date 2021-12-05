package model;

public class Categoria {
	protected int idCategoria;
	private String nomeCategoria;
	
	public Categoria() {
		idCategoria = -1;
		nomeCategoria = "redessociais";
		
	}
	public Categoria(int IdCategoria, String nomeCategoria) {
		setIdCategoria(IdCategoria);
		setNomeCategoria(nomeCategoria);
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
	
	public String toString() {
		return "Id Categoria: " + idCategoria + "Nome: " + nomeCategoria; 
	}

}

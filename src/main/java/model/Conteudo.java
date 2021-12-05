package model;


public class Conteudo {
	private int idConteudo;
	private String Nome;
	private String Titulo;
	private String Url;
	private String Autor;
	private String Descricao;
	private String Conteudo;

	public Conteudo() {
		idConteudo = -1;
		Nome = "";
		Titulo = "";
		Url = "";
		Autor = "";
		Descricao = "";
		Conteudo = "";
	}
	
	public Conteudo(int idConteudo, String Nome, 
			String Titulo, String Url, String Autor,
			String Descricao, String Conteudo) {
		setId(idConteudo);
		setNome(Nome);
		setTitulo(Titulo);
		setUrl(Url);
		setAutor(Autor);
		setDescricao(Descricao);
		setConteudo(Conteudo);
	}
	public void setId(int idConteudo) {
		this.idConteudo = idConteudo;
	}
	public void setNome(String Nome) {
		this.Nome = Nome;
	}
	public void setTitulo(String Titulo) {
		this.Titulo = Titulo;
	}
	public void setUrl(String Url) {
		this.Url = Url;
	}
	public void setAutor(String Autor) {
		this.Autor = Autor;
	}
	public void setDescricao(String Descricao) {
		this.Descricao = Descricao;
	}
	public void setConteudo(String Conteudo) {
		this.Conteudo = Conteudo;
	}
	public int getId() {
		return idConteudo;
	}
	public String getNome() {
		return Nome;
	}
	public String getTitulo() {
		return Titulo;
	}
	public String getUrl() {
		return Url;
	}
	public String getAutor() {
		return Autor;
	}
	public String getDescricao() {
		return Descricao;
	}
	public String getConteudo() {
		return Conteudo;
	}
	public String toString() {
		return "Conteudo: " + idConteudo + "Nome: " + Nome + "Titulo: " + Titulo + "Url: " + Url + "Autor: " + Autor + "Descricao: " + Descricao + "Conteudo: " + Conteudo;
	}
}

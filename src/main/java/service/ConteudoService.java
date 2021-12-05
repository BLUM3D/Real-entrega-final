package service;

import dao.ConteudoDAO;
import model.Conteudo;

import spark.Request;
import spark.Response;

public class ConteudoService {
	
	private ConteudoDAO conteudoDAO;
	
	public ConteudoService() {
		conteudoDAO = new ConteudoDAO();
	}

public Object inserirConteudo(Request request, Response response) {
        
	String NomeConteudo = request.queryParams("NomeConteudo");
    String Titulo = request.queryParams("Titulo");
    String Url = request.queryParams("Url");
    String Autor = request.queryParams("Autor");
    String Descricao = request.queryParams("Descricao");
    String Conteudo = request.queryParams("Conteudo");
			


	int idConteudo = conteudoDAO.getMaxId() + 1;

	Conteudo conteudo = new Conteudo(idConteudo, NomeConteudo, Titulo, Url, Autor, Descricao, Conteudo);

	conteudoDAO.inserirConteudo(conteudo);

	response.status(201); // 201 Created
	return idConteudo;

    }

public Object getConteudo(Request request, Response response) {
	int idConteudo = Integer.parseInt(request.params("idConteudo"));
	
	
	Conteudo conteudo = conteudoDAO.getConteudo(idConteudo);
	
	if (conteudo != null) {
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");

        return "<conteudos>\n" + 
        		"\t<id>" + conteudo.getId() + "</id>\n" +
        		"\t<nome>" + conteudo.getNome() + "</nome>\n" +
        		"\t<senha>" + conteudo.get() + "</senha>\n" +
        		"</conteudos>\n";
    } else {
        response.status(404); // 404 Not found
        return "Senha " + idSenha + " não encontrado.";
    }

}

public Object atualizarConteudo(Request request, Response response) {
	int idConteudo = Integer.parseInt(request.params("idConteudo"));
    
	Conteudo conteudo = conteudoDAO.getConteudo(idConteudo);

    if (conteudo != null) {
    	conteudo.setNome(request.queryParams("Nome"));
    	conteudo.setTitulo(request.queryParams("Titulo"));
    	conteudo.setTitulo(request.queryParams("Url"));
    	conteudo.setTitulo(request.queryParams("Autor"));
    	conteudo.setTitulo(request.queryParams("Descricao"));
    	conteudo.setTitulo(request.queryParams("Conteudo"));

    	conteudoDAO.atualizarConteudo(conteudo);
    	
        return idConteudo;
    } else {
        response.status(404); // 404 Not found
        return "Conteudo não encontrado.";
    }

}

public Object excluirConteudo(Request request, Response response) {
	Conteudo conteudo = conteudoDAO.getConteudo(idConteudo);
  
    if (NomeConteudo != null) {

        conteudoDAO.excluirConteudo(NomeConteudo);

        response.status(200); // success
    	return nomeConteudo;
    } else {
        response.status(404); // 404 Not found
        return "Senha não encontrado.";
    }
}

public Object getConteudos(Request request, Response response) {
	StringBuffer returnValue = new StringBuffer("<senhas type=\"array\">");
	for (Senha senha : senhaDAO.getSenhas()) {
		returnValue.append("\n<usuario>\n" + 
        		"\t<idSenha>" + senha.getIdSenha() + "</id>\n" +
        		"\t<nome>" + senha.getNomeSenha() + "</email>\n" +
        		"\t<senha>" + senha.getSenhaSenha() + "</senha>\n" +
        		"</usuarios>\n");
	}
	returnValue.append("</usuarios>");
    response.header("Content-Type", "application/xml");
    response.header("Content-Encoding", "UTF-8");
	return returnValue.toString();
}
}
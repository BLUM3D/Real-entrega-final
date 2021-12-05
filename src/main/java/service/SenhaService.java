package service;

import cripto.criptografia;
import dao.SenhaDAO;
import dao.UsuarioDAO;
import model.Senha;
import model.Usuario;
import service.UsuarioService;

import spark.Request;
import spark.Response;


public class SenhaService {
	
	private SenhaDAO senhaDAO;
	private UsuarioDAO usuarioDAO;
	public UsuarioService usuarioService;
	
	public static int maxId = 0;
	
	public int getMaxId() {
		return maxId;
	}
	
	public SenhaService() {
		senhaDAO = new SenhaDAO();
		usuarioDAO = new UsuarioDAO();
		
		senhaDAO.conectar();
		usuarioDAO.conectar();
	}

public Object inserirSenha(Request request, Response response) throws Exception {
        
	String nomeSenha = request.queryParams("nomeSenha");
	String senhaSenha = request.queryParams("senhaSenha");
	
	int idSenha = senhaDAO.getMaxIdSenha();
	System.out.println(idSenha);
	int idUsuario = usuarioService.IdUsuarioSessao;
	
	Senha senha = new Senha(idSenha, nomeSenha, senhaSenha, idUsuario);
	
	senhaDAO.inserirSenha(senha);
	Senha[] senhas = senhaDAO.getSenhas();

	response.status(201); // 201 Created
	return "<h1>Sua senha foi armazenada!</h1>"
    		+"<h1>Agora acesse o armazenamento de senhas!</h1>"
    		+ "<a href=\"file:///C:/Users/Leonardo%20de%20Oliveira/eclipse-workspace/sprint3-backend/src/main/resources/blumed-new-main%20(2)/blumed-new-main/page-senhas.html\">link do Armazenamento</a>";

    }

public Object getSenha(Request request, Response response) {
	int idSenha = Integer.parseInt(request.params("idSenha"));
	
	Senha senha = senhaDAO.getSenha(null, idSenha);
	
	if (senha != null) {
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");

        return "<senhas>\n" + 
        		"\t<id>" + senha.getIdSenha() + "</id>\n" +
        		"\t<nome>" + senha.getNomeSenha() + "</nome>\n" +
        		"\t<senha>" + senha.getSenhaSenha() + "</senha>\n" +
        		"</senhas>\n";
    } else {
        response.status(404); // 404 Not found
        return "Senha " + idSenha + " não encontrado.";
    }

}

public Object atualizarSenha(Request request, Response response) throws Exception {
	String nomeSenha = request.params("nomeSenha");
	int idUsuario = usuarioService.IdUsuarioSessao;
    
	Senha senha = (Senha) senhaDAO.getSenha(nomeSenha, idUsuario);

    if (senha != null) {
    	senha.setNomeSenha(request.queryParams("Nome"));
    	senha.setSenhaSenha(request.queryParams("Senha"));

    	senhaDAO.atualizarSenha(senha);
    	
        return "<h1>Sua senha foi atualizada!</h1>"
        		+"<h1>Agora acesse o armazenamento de senhas!</h1>"
        		+ "<a href=\"file:///C:/Users/Leonardo%20de%20Oliveira/eclipse-workspace/sprint3-backend/src/main/resources/blumed-new-main%20(2)/blumed-new-main/page-senhas.html\">link do Armazenamento</a>";;
    } else {
        response.status(404); // 404 Not found
        return "Senha não encontrado.";
    }

}

public Object excluirSenha(Request request, Response response) {
    String nomeSenha = (request.params(":nomeSenha"));
    int idUsuario = usuarioService.IdUsuarioSessao;
  
    if (nomeSenha != null) {

        senhaDAO.excluirSenha(nomeSenha, idUsuario);

        response.status(200); // success
    } else {
        response.status(404); // 404 Not found
        return "Senha não encontrado.";
    }
    return "<h1>Sua senha foi excluida!</h1>"
	+"<h1>Agora acesse o armazenamento de senhas!</h1>"
	+ "<a href=\"file:///C:/Users/Leonardo%20de%20Oliveira/eclipse-workspace/sprint3-backend/src/main/resources/blumed-new-main%20(2)/blumed-new-main/page-senhas.html\">link do Armazenamento</a>";
    
}
 
public Object getSenhasIdUsuario(Request request, Response response) {
	int idUsuario = usuarioService.IdUsuarioSessao;
	StringBuffer returnValue = new StringBuffer("<senhas type=\"array\">");
	for (Senha senha : senhaDAO.getSenhasIdUsuario(idUsuario)) {
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


public Object getSenhas(Request request, Response response) {
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

public Object getSenhaNome(Request request, Response response) throws Exception {
	
	String nomeSenha = request.queryParams("nomeSenha");
	
	int idUsuario = usuarioService.IdUsuarioSessao;
	
	Senha senha = senhaDAO.getSenha(nomeSenha, idUsuario);
	
	
	if (senha != null) {
	  
        return "<h1>Aqui est�o suas Senhas!</h1>"
        		+ "<h1>Nome: "+ nomeSenha + "</h1>"
        		+ "<h1>Nome: "+ senha.getSenhaSenha() + "</h1>"
        		+"<h1>Retorne ao armazenamento!</h1>"
        		+ "<a href=\"file:///C:/Users/Leonardo%20de%20Oliveira/eclipse-workspace/sprint3-backend/src/main/resources/blumed-new-main%20(2)/blumed-new-main/page-login.html\">link do Armazenamento</a>";
    } else {
        response.status(404); // 404 Not found
        return "Senha " + nomeSenha + " não encontrado.";
    }

}
}

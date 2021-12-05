package service;

import dao.UsuarioDAO;
import model.Senha;
import model.Usuario;
import spark.Request;
import spark.Response;

import cripto.criptografia;


public class UsuarioService {

	private UsuarioDAO usuarioDAO;
	
	public static int IdUsuarioSessao;

	public UsuarioService() {
		usuarioDAO = new UsuarioDAO();
		usuarioDAO.conectar();
	}

	public Object inserirUsuario(Request request, Response response) throws Exception {
		
		String nome = request.queryParams("nome");
		String sobrenome = request.queryParams("sobrenome");
		String email = request.queryParams("email");
		String senha = request.queryParams("senha");
		
		
		
		
		int id = usuarioDAO.getMaxIdUsuario();
		System.out.println(id);
		

		Usuario usuario = new Usuario(id, email, senha, nome, sobrenome);
		
		//Criptografia Senha FALTANDO

		usuarioDAO.inserirUsuario(usuario);
		Usuario[] usuarios = usuarioDAO.getUsuarios();

		response.status(201); // 201 Created
		return "<h1>Usuario foi criado!</h1>"
        		+"<h1>Agora acesse o tela de login!</h1>"
        		+ "<a href=\"file:///C:/Users/Leonardo%20de%20Oliveira/eclipse-workspace/sprint3-backend/src/main/resources/blumed-new-main%20(2)/blumed-new-main/page-login.html</a>";
	}

	public Object getUsuarioId(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		
		Usuario usuario = usuarioDAO.getUsuarioId(id);
		
		if (usuario != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<usuarios>\n" + 
            		"\t<id>" + usuario.getId() + "</id>\n" +
            		"\t<email>" + usuario.getEmail() + "</email>\n" +
            		"\t<senha>" + usuario.getSenha() + "</senha>\n" +
            		"\t<nome>" + usuario.getNome() + "</nome>\n" +
            		"\t<sobrenome>" + usuario.getSobrenome() + "</sobrenome>\n" +
            		"</usuarios>\n";
        } else {
            response.status(404); // 404 Not found
            return "Usuario " + id + " não encontrado.";
        }

	}

	public Object atualizarUsuario(Request request, Response response) throws Exception {
        int id = Integer.parseInt(request.params(":id"));
        
		Usuario usuario = (Usuario) usuarioDAO.getUsuarioId(id);

        if (usuario != null) {
        	usuario.setEmail(request.queryParams("Email"));
        	usuario.setSenha(request.queryParams("Senha"));
        	usuario.setNome(request.queryParams("Nome"));
        	usuario.setSobrenome(request.queryParams("Sobrenome"));

        	usuarioDAO.atualizarUsuario(usuario);
        	
            return id;
        } else {
            response.status(404); // 404 Not found
            return "Produto não encontrado.";
        }

	}

	public Object excluirUsuario(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
      
        if (id != 0) {

            usuarioDAO.excluirUsuario(id);

            response.status(200); // success
        	return id;
        } else {
            response.status(404); // 404 Not found
            return "Usuario não encontrado.";
        }
	}

	public Object getUsuarios(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<usuarios type=\"array\">");
		for (Usuario usuario : usuarioDAO.getUsuarios()) {
			returnValue.append("\n<usuario>\n" + 
            		"\t<id>" + usuario.getId() + "</id>\n" +
            		"\t<email>" + usuario.getEmail() + "</email>\n" +
            		"\t<senha>" + usuario.getSenha() + "</senha>\n" +
            		"\t<nome>" + usuario.getNome() + "</nome>\n" +
            		"\t<sobrenome>" + usuario.getSobrenome() + "</sobrenome>\n" +
            		"</usuarios>\n");
		}
		returnValue.append("</usuarios>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
	
	public Object verificarUsuario(Request request, Response response) throws Exception {
		boolean existe = false;
			
			String email = request.queryParams("email");
			String senha = request.queryParams("senha");
			
			Usuario usuario = usuarioDAO.getUsuarioV(email, senha);
			
			IdUsuarioSessao = usuario.getId();
			
			if (usuario != null) {
			
		        return "<h1>Usuario foi Logado!</h1>"
		        		+"<h1>Agora acesse o armazenamento de senhas!</h1>"
		        		+ "<a href=\"file:///C:/Users/Leonardo%20de%20Oliveira/eclipse-workspace/sprint3-backend/src/main/resources/blumed-new-main%20(2)/blumed-new-main/page-senhas.html\">link do Armazenamento</a>";
			}
			

		    if (existe = true) {
		    	
		    	//CODIGO PAGINA 
		    	
		    	
		    	return "Usuario encontrado.";
		    } else {
		        response.status(404); // 404 Not found
		        return "Usuario não encontrado.";
		    }
	}
}
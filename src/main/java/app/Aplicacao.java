package app;

import static spark.Spark.*;



import service.UsuarioService;
import service.SenhaService;
import service.CategoriaService;


public class Aplicacao {
	
	private static UsuarioService usuarioService = new UsuarioService();
	private static SenhaService senhaService = new SenhaService();
	private static CategoriaService categoriaService = new CategoriaService();

	
	
    public static void main(String[] args) {
        port(5467);
        
        
        //PARA SENHA
        post("/senha", (request, response) -> senhaService.inserirSenha(request, response));

        get("/senha/:nomeSenha", (request, response) -> senhaService.getSenhaNome(request, response));

        get("/senha/update/:nomeSenha", (request, response) -> senhaService.atualizarSenha(request, response));

        get("/senha/delete/:nomeSenha", (request, response) -> senhaService.excluirSenha(request, response));
        
        //PARA USUARIO
        post("/usuario", (request, response) -> usuarioService.inserirUsuario(request, response));
        
        get("/usuario/login", (request, response) -> usuarioService.verificarUsuario(request, response));
        
        get("/usuario/:idUsuario", (request, response) -> usuarioService.getUsuarios(request, response));
        
        get("/usuario/update/:idUsuario", (request, response) -> usuarioService.atualizarUsuario(request, response));
        
        get("/usuario/delete/:idUsuario", (request, response) -> usuarioService.excluirUsuario(request, response));
        
        //PARA CATEGORIA
        /*
        post("/categoria", (request, response) -> categoriaService.inserirCategoria(request, response));
        
        get("/categoria/:idCategoria", (request, response) -> categoriaService.getCategoria(request, response));
        
        get("/categoria/update/:idCategoria", (request, response) -> categoriaService.atualizarCategoria(request, response));
        
        get("/categoria/delete/:idCategoria", (request, response) -> categoriaService.excluirCategoria(request, response));
        */
        
        
        
               
    }
}
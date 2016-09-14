package br.com.alura.loja.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Projeto;

/**
 * @author Ramon Vieira
 *
 */

@Path("projetos")
public class ProjetoResource {
	
	// Retorna XML
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String buscaProjeto(@PathParam("id") long id){
		
		Projeto projeto = new ProjetoDAO().busca(id);
		
		return projeto.toXML();
		
	}
	
	// Retorna JSON
	
	//	@Path("{id}")
	//	@GET
	//	@Produces(MediaType.APPLICATION_JSON)
	//	public String buscaProjeto(@PathParam("id") long id){
	//		
	//		ProjetoDAO projetoDAO = new ProjetoDAO();
	//
	//		Projeto projeto = projetoDAO.busca(id);
	//		
	//		return projeto.toJSON();
	//		
	//	}
	
	@POST
	@Produces(MediaType.APPLICATION_XML)
	public String adiciona(String conteudo){
		
		Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
		
		new ProjetoDAO().adiciona(projeto);
	
		return "<status>Sucess</status>";
		
	}
	
}

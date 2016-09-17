package br.com.alura.loja.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	@Consumes(MediaType.APPLICATION_XML)
	public Response adiciona(String conteudo){
		
		Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
		
		new ProjetoDAO().adiciona(projeto);
		
		URI uri = URI.create("/projetos/" + projeto.getId());
	
		return Response.created(uri).build();
		
	}
	
	@DELETE
	@Path("{id}")
	public Response remove(@PathParam("id") long id){
		
		new ProjetoDAO().remove(id);
		
		return Response.ok().build();
	}
	
	@PUT
	@Path("{id}/ano")
	public Response altera(String conteudo, @PathParam("id") long id){
		
		Projeto projeto = new ProjetoDAO().busca(id);
		
		projeto.setAnoDeInicio(((Projeto) new XStream().fromXML(conteudo)).getAnoDeInicio());
	
		return Response.ok().build();
	}
	
}

package br.com.alura.loja.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;

/**
 * @author Ramon Vieira
 *
 */

@Path("carrinhos")
public class CarrinhoResource {

	// Retorna XML
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String busca(@PathParam("id") long id){
		
		CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
		
		Carrinho carrinho = carrinhoDAO.busca(id);
		
		return carrinho.toXML();
	}
	
	// Retorna JSON
	
//	@Path("{id}")
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public String busca(@PathParam("id") long id){
//		
//		CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
//		
//		Carrinho carrinho = carrinhoDAO.busca(id);
//		
//		return carrinho.toJSON();
//	}
	
}

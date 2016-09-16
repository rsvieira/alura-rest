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

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

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
		
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		
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
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response adiciona(String conteudo){
		
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		
		new CarrinhoDAO().adiciona(carrinho);
		
		URI uri = URI.create("/carrinhos/" + carrinho.getId());
		
		return Response.created(uri).build();
	}
	
	// OBS:
	// usando o curl tendo que passar o Content-Type
	//	curl -v -H "Content-Type: application/xml" -d "<br.com.alura.loja.modelo.Carrinho>  <produtos>    <br.com.alura.loja.modelo.Produto>      <preco>4000.0</preco>      <id>6237</id>      <nome>Videogame 4</nome>      <quantidade>1</quantidade>    </br.com.alura.loja.modelo.Produto>  </produtos>  <rua>Rua Vergueiro 3185, 8 andar</rua>  <cidade>São Paulo</cidade>  <id>1</id></br.com.alura.loja.modelo.Carrinho>" http://localhost:8080/carrinhos
	
	@DELETE
	@Path("{id}/produtos/{produtoId}")
	public Response remove(@PathParam("id") long id, @PathParam("produtoId") long produtoId){
		
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		
		carrinho.remove(produtoId);
	
		return Response.ok().build();
	}
	
	@PUT
	@Path("{id}/produtos/{produtoId}/quantidade")
	public Response altera(String conteudo, @PathParam("id") long id, @PathParam("produtoId") long produtoId){
		
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		
		carrinho.trocaQuantidade((Produto) new XStream().fromXML(conteudo));
	
		return Response.ok().build();
	}
	
}


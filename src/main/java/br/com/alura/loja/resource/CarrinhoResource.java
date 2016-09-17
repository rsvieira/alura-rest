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
	public Carrinho busca(@PathParam("id") long id){
		
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		
		return carrinho;
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
	
	
	/* OBS:
	 usando o curl tendo que passar o Content-Type

		#toXStream
			curl -v -H "Content-Type: application/xml" -d "<br.com.alura.loja.modelo.Carrinho>  <produtos>    <br.com.alura.loja.modelo.Produto>      <preco>4000.0</preco>      <id>6237</id>      <nome>Videogame 4</nome>      <quantidade>1</quantidade>    </br.com.alura.loja.modelo.Produto>  </produtos>  <rua>Rua Vergueiro 3185, 8 andar</rua>  <cidade>São Paulo</cidade>  <id>1</id></br.com.alura.loja.modelo.Carrinho>" http://localhost:8080/carrinhos
	
		#toJAXB
			curl -v -H "Content-Type: application/xml" -d "<carrinho>  <produtos>    <br.com.alura.loja.modelo.Produto>      <preco>4000.0</preco>      <id>6237</id>      <nome>Videogame 4</nome>      <quantidade>1</quantidade>    </br.com.alura.loja.modelo.Produto>  </produtos>  <rua>Rua Vergueiro 3185, 8 andar</rua>  <cidade>São Paulo</cidade>  <id>1</id></br.com.alura.loja.modelo.Carrinho>" http://localhost:8080/carrinhos
	
	 */
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response adiciona(Carrinho carrinho){
		
		new CarrinhoDAO().adiciona(carrinho);
		
		URI uri = URI.create("/carrinhos/" + carrinho.getId());
		
		return Response.created(uri).build();
	}
	
	/*
		Deletando usando curl
	 
	 # curl -v -X DELETE http://localhost:8080/carrinhos/1/produtos/6237
	
	*/
	
	@DELETE
	@Path("{id}/produtos/{produtoId}")
	public Response remove(@PathParam("id") long id, @PathParam("produtoId") long produtoId){
		
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		
		carrinho.remove(produtoId);
	
		return Response.ok().build();
	}
	
	/*
	PUT usando curl
 		
 		#curl -v -X PUT -H "Content-Type: application/xml" -d "<produto><preco>4000.0</preco><id>6237</id><nome>Videogame 4</nome><quantidade>69</quantidade></produto>" http://localhost:8080/carrinhos/1/produtos/6237/quantidade

	*/
	
	@PUT
	@Path("{id}/produtos/{produtoId}/quantidade")
	public Response altera(@PathParam("id") long id, @PathParam("produtoId") long produtoId, Produto produto){
		
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		
		carrinho.trocaQuantidade(produto);
	
		return Response.ok().build();
	}
	
}


package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;

/**
 * @author Ramon Vieira
 *
 */

public class ClienteTest {

	private HttpServer server;
	private Client client;
	private WebTarget target;
	private ClientConfig config;
	
	@Before
	public void startaServidor(){
		server = Servidor.inicializaServidor();
		config = new ClientConfig(new LoggingFilter());
		client = ClientBuilder.newClient(config);
		target = client.target("http://localhost:8080");
	}
	
	@After
	public void paraServidor(){
		System.out.println("parando o Servidor");
		server.stop();
	}
	
	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado () {
		String conteudo = target.path("/carrinhos/1").request().get(String.class);
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
		
	}
	
	@Test
	public void testandoPOST () {
		
		Carrinho carrinho = new Carrinho();
		carrinho.setId(3L);
		carrinho.setCidade("Salvador");
		carrinho.setRua("Rua Silveira Martins");
		
		String xml = carrinho.toXML();

		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
		Response response = target.path("/carrinhos").request().post(entity);
        
        Assert.assertEquals(201, response.getStatus());

        String location = response.getHeaderString("Location");
        String conteudo = client.target(location).request().get(String.class);
        
        Assert.assertTrue(conteudo.contains("Salvador"));
		
	}
	
}

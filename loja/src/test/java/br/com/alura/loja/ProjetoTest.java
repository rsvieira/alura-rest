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

import br.com.alura.loja.modelo.Projeto;

/**
 * @author Ramon Vieira
 *
 */

public class ProjetoTest {

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
		System.out.println("parando o Servidor...");
		server.stop();
	}
	
	@Test
	public void testaUrlProjetos(){
		String conteudo = target.path("/projetos/1").request().get(String.class);
		
		Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
		
		Assert.assertEquals("Minha loja", projeto.getNome());
	}
	
	@Test
	public void testandoPOST () {
		Projeto projeto = new Projeto();
		projeto.setId(3L);
		projeto.setNome("Alura");
		projeto.setAnoDeInicio(2016);
		
		String xml = projeto.toXML();

		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);

        Response response = target.path("/projetos").request().post(entity);
        
        Assert.assertEquals(201,response.getStatus());
        
        String location = response.getHeaderString("Location");
        String conteudo = client.target(location).request().get(String.class);
        
        Assert.assertTrue(conteudo.contains("Alura"));
		
	}
	
	
}

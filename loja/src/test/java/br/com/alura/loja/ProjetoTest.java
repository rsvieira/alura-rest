package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Projeto;

/**
 * @author Ramon Vieira
 *
 */

public class ProjetoTest {

	private HttpServer server;
	
	@Before
	public void startaServidor(){
		server = Servidor.inicializaServidor();
	}
	
	@After
	public void paraServidor(){
		System.out.println("parando o Servidor...");
		server.stop();
	}
	
	@Test
	public void testaUrlProjetos(){
		
		Client client = ClientBuilder.newClient();
		
		WebTarget target = client.target("http://localhost:8080");
		
		String conteudo = target.path("/projetos/1").request().get(String.class);
		
		Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
		
		Assert.assertEquals("Minha loja", projeto.getNome());
	}
	
	@Test
	public void testandoPOST () {
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		
		Projeto projeto = new Projeto();
		projeto.setId(3L);
		projeto.setNome("Alura");
		projeto.setAnoDeInicio(2016);
		
		String xml = projeto.toXML();

		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);

        Response response = target.path("/projetos").request().post(entity);
        Assert.assertEquals("<status>Sucess</status>", response.readEntity(String.class));
		
	}
	
	
}

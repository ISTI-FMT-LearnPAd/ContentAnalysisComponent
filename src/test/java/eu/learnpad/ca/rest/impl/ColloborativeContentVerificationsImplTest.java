package eu.learnpad.ca.rest.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Test;

import eu.learnpad.ca.gate.GateServletContextListener;
import eu.learnpad.ca.rest.data.collaborative.AnnotatedCollaborativeContentAnalyses;
import eu.learnpad.ca.rest.data.collaborative.AnnotatedCollaborativeContentAnalysis;
import eu.learnpad.ca.rest.data.collaborative.CollaborativeContentAnalysis;






public class ColloborativeContentVerificationsImplTest extends JerseyTest{


	@Override
	protected TestContainerFactory getTestContainerFactory()  {
		return new GrizzlyWebTestContainerFactory();
	}
	@Override
	protected DeploymentContext configureDeployment() {
		forceSet(TestProperties.CONTAINER_PORT, "0");
		//new ResourceConfig(ColloborativeContentVerificationsImpl.class)
		AbstractBinder binder = new AbstractBinder() {

			@Override
			protected void configure() {
				bindFactory(PersistenceMemory.class).to(TokenPersistence.class).in(Singleton.class);
				
			}
		};
		ResourceConfig r = new ResourceConfig(ColloborativeContentVerificationsImpl.class);
		r.register(binder);
		return ServletDeploymentContext.forServlet(new ServletContainer(r))
				.addListener(GateServletContextListener.class)
				.build();

	}

	@Override
	protected Application configure() {


		AbstractBinder binder = new AbstractBinder() {

			@Override
			protected void configure() {
					bindFactory(PersistenceMemory.class).to(TokenPersistence.class);//.in(Singleton.class);
			

			}
		};
		ResourceConfig r = new ResourceConfig(ColloborativeContentVerificationsImpl.class);
		r.register(binder);
		return r;
	}


	@Test
	public void checkCollaborativeContentAnalysis() throws JAXBException {
		//checkCollaborativeContentAnalysis("CollaborativeContentXMLITALIAN.xml");
		//checkCollaborativeContentAnalysis("CollaborativeContentXMLS_HTML_WC.xml");
		//checkCollaborativeContentAnalysis("CollaborativeContentXMLBi.xml");
		//checkCollaborativeContentAnalysis("CollaborativeContentXMLS_HTML_WC2.xml");
		checkCollaborativeContentAnalysis("CollaborativeContentXMLS_HTML.xml");
		checkCollaborativeContentAnalysis("CollaborativeContentXML_SUAP.xml");

	}



	public void checkCollaborativeContentAnalysis(String nameFilexml) throws JAXBException {

		InputStream is = ColloborativeContentVerificationsImplTest.class.getClassLoader().getResourceAsStream(nameFilexml);
		assertNotNull(is);
		JAXBContext jaxbContexti = JAXBContext.newInstance(CollaborativeContentAnalysis.class);

		Unmarshaller jaxbUnmarshaller1 = jaxbContexti.createUnmarshaller();
		CollaborativeContentAnalysis collaborativeContentInput = (CollaborativeContentAnalysis) jaxbUnmarshaller1.unmarshal(is);

		Entity<CollaborativeContentAnalysis> entity = Entity.entity(collaborativeContentInput,MediaType.APPLICATION_XML);
		Response response =  target("/learnpad/ca/bridge/validatecollaborativecontent").request(MediaType.APPLICATION_XML).post(entity);

		String id = response.readEntity(String.class);

		assertNotNull(response);
		assertTrue(id!="");
		String status = "IN PROGRESS";
		boolean flag = true;
		while(flag){
			status =  target("/learnpad/ca/bridge/validatecollaborativecontent/"+id+"/status").request().get(String.class);

			assertNotNull(status);
			if(status.equals("ERROR") | status.equals("OK")){
				flag = false;
			}
		}
		assertTrue(!status.equals("ERROR"));
		if(!status.equals("ERROR")){
			
			Response all =  target("/learnpad/ca/bridge/allid").request().get();

			List<String> g  = all.readEntity(new GenericType<List<String>>() {});
			
			Response annotatecontent =  target("/learnpad/ca/bridge/validatecollaborativecontent/"+id).request().get();

			//ArrayList<AnnotatedCollaborativeContentAnalysis> res =	annotatecontent.readEntity(new GenericType<ArrayList<AnnotatedCollaborativeContentAnalysis>>() {});

			AnnotatedCollaborativeContentAnalyses res =	annotatecontent.readEntity(new GenericType<AnnotatedCollaborativeContentAnalyses>() {});

			//AnnotatedCollaborativeContentAnalyses res =  annotatecontent.readEntity(AnnotatedCollaborativeContentAnalyses.class);

			for (AnnotatedCollaborativeContentAnalysis annotatedCollaborativeContentAnalysis : res.getAnnotateCollaborativeContentAnalysis()) {
				JAXBContext jaxbCtx;
				try {
					jaxbCtx = javax.xml.bind.JAXBContext.newInstance(AnnotatedCollaborativeContentAnalysis.class);

					Marshaller marshaller = jaxbCtx.createMarshaller();
					marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
					marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
					//marshaller.marshal(annotatedCollaborativeContentAnalysis, System.out);
					String type = annotatedCollaborativeContentAnalysis.getType();
					OutputStream os = new FileOutputStream( "nosferatu"+type+".xml" );
					marshaller.marshal( annotatedCollaborativeContentAnalysis, os );
				} catch (JAXBException | FileNotFoundException  e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					assertTrue(false);
				}
			}


			assertNotNull(res);
			assertNotNull(annotatecontent);
		}
	}



}

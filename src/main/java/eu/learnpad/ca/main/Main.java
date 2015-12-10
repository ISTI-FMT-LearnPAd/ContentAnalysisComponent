package eu.learnpad.ca.main;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

 
public class Main {

	 public static void main(String[] args) throws Exception {
	      
	        Server jettyServer = new Server(8082);
	       
	                WebAppContext webapp1 = new WebAppContext();
	        
	                webapp1.setResourceBase("src/main/webapp");
	        
	                webapp1.setContextPath("/webapp");
	        
	                webapp1.setDefaultsDescriptor("src/main/webapp/WEB-INF/web.xml");
	        
	                jettyServer.setHandler(webapp1);

	 
	        try {
	            jettyServer.start();
	            jettyServer.join();
	        } finally {
	            jettyServer.destroy();
	        }
	    }

	
}

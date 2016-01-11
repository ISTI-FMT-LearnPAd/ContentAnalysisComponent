package eu.learnpad.ca.main;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

 
public class Main {

	 public static void main(String[] args) throws Exception {
	      
	        Server jettyServer = new Server(8082);
	       
	                WebAppContext webapp = new WebAppContext();
	        
	                webapp.setContextPath("/lp-content-analysis");
	                webapp.setWar("lp-content-analysis.war");
	        
	                jettyServer.setHandler(webapp);

	 
	        try {
	            jettyServer.start();
	            jettyServer.join();
	        } finally {
	            jettyServer.destroy();
	        }
	    }

	
}

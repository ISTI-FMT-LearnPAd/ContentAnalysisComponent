package eu.learnpad.ca.rest.impl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;


public class MyApplication extends ResourceConfig {
    public MyApplication() {
    	 register(ColloborativeContentVerificationsImpl.class);
    	 packages(true, "eu.learnpad.ca.rest.impl");
    	 register(new AbstractBinder() {
             @Override
             protected void configure() {
                // bindFactory(HttpSessionFactory.class).to(TokenPersistence.class);
            	bindFactory(PersistenceDB.class).to(TokenPersistence.class);//.in(Singleton.class);
				// bind(new RelationDBPersistence()).to(TokenPersistence.class);
                /* bind(SessionInjectResolver.class)
                     .to(new TypeLiteral<InjectionResolver<SessionInject>>(){})
                     .in(Singleton.class);*/
             }
         });
    	
    }
}
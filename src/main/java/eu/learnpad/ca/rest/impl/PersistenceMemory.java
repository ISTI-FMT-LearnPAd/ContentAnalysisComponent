package eu.learnpad.ca.rest.impl;


import javax.inject.Singleton;
import javax.persistence.Persistence;

@Singleton
public class PersistenceMemory implements org.glassfish.hk2.api.Factory<TokenPersistence> {
	

	@Override
	 public TokenPersistence provide() {
		  RelationDBPersistence rdbp = new RelationDBPersistence();
		rdbp.setEntitymanagerFactory(Persistence.createEntityManagerFactory("annotatedcca-test"));
		  return rdbp;

	}

	@Override
	public void dispose(TokenPersistence instance) {
		// TODO Auto-generated method stub
		
	}
	
	



}

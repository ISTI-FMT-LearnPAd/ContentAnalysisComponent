package eu.learnpad.ca.rest.impl;


import javax.persistence.Persistence;


public class PersistenceDB implements org.glassfish.hk2.api.Factory<TokenPersistence> {

	@Override
	 public TokenPersistence provide() {
		  RelationDBPersistence rdbp = new RelationDBPersistence();
		rdbp.setEntitymanagerFactory(Persistence.createEntityManagerFactory("annotatedcca"));
		  return rdbp;

	}

	@Override
	public void dispose(TokenPersistence instance) {
		// TODO Auto-generated method stub
		
	}
	
	



}

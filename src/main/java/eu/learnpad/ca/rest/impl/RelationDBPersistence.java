package eu.learnpad.ca.rest.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import eu.learnpad.ca.rest.data.collaborative.AnnotatedCollaborativeContentAnalyses;

@Singleton
public class RelationDBPersistence implements TokenPersistence {

	 @Inject
	 private  EntityManagerFactory emFactory;
	 
	 private  EntityManager em;// = emFactory.createEntityManager();

	 @PostConstruct
	 public void init(){
		 if(emFactory!=null){
			 em = emFactory.createEntityManager();
		 }
		 
	 }

	 //ONLY FOR TEST PURPOSE
	 public void setEntitymanagerFactory(EntityManagerFactory emf){
		 this.emFactory = emf;
		 em = emFactory.createEntityManager();
	 }
	
	@Override
	public Query createNativeQuery(String string) {
		//em = emFactory.createEntityManager();
		return em.createNamedQuery(string);
	}

	@Override
	public EntityTransaction getTransaction() {
		return em.getTransaction();
	}

	@Override
	public void persist(AnnotatedCollaborativeContentAnalyses ar) {
		em.persist(ar);
	}

	@Override
	public AnnotatedCollaborativeContentAnalyses find(
			Class<AnnotatedCollaborativeContentAnalyses> class1, String value) {
	//	em = emFactory.createEntityManager();
		return em.find(class1, value);
	}

	@Override
	public TypedQuery<String> createNamedQuery(String string,
			Class<String> class1) {
		return em.createNamedQuery(string,class1);
	}

}

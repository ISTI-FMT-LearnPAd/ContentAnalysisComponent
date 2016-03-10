package eu.learnpad.ca.rest.impl;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import eu.learnpad.ca.rest.data.collaborative.AnnotatedCollaborativeContentAnalyses;


public interface TokenPersistence {




Query createNativeQuery(String string);

EntityTransaction getTransaction();

void persist(AnnotatedCollaborativeContentAnalyses ar);

AnnotatedCollaborativeContentAnalyses find(
		Class<AnnotatedCollaborativeContentAnalyses> class1, String value);

TypedQuery<String> createNamedQuery(String string, Class<String> class1);


}

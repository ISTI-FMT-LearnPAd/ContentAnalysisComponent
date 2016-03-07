package eu.learnpad.ca.rest.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import eu.learnpad.ca.rest.data.collaborative.AnnotatedCollaborativeContentAnalyses;

@Repository("contentRepository")
public interface ContentDao extends JpaRepository<AnnotatedCollaborativeContentAnalyses, Long>  {
	
	@Query("select s from Content s where s.id = :id")
	AnnotatedCollaborativeContentAnalyses findById(@Param("id") String id);
	
	
}

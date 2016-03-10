package eu.learnpad.ca.rest.data.collaborative;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity(name="ContentAnalyses")
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQuery(name="ContentAnalyses.findAll", query="SELECT b.id FROM ContentAnalyses b ORDER BY b.id")
@XmlRootElement(name = "AnnotatedCollaborativeContentAnalyses")
public class AnnotatedCollaborativeContentAnalyses implements Serializable{
	
	@Id
	@XmlTransient
	private Integer id;
	
	/**
	 * @OneToMany(mappedBy="Analyses")
	 */
	
	
	@XmlTransient
	private static final long serialVersionUID = 8605819018990855517L;
	
	
	@ElementCollection
	 @CollectionTable(
		        name="ASContentAnalysis"/*,
		        joinColumns=@JoinColumn(name="OWNER_ID")*/
		  )
	@XmlElement(required = true)
    protected List<AnnotatedCollaborativeContentAnalysis> AnnotatedCollaborativeContentAnalyses;

	
	
    /**
     * Gets the value of the annotateCollaborativeContentAnalysis property.
     * 
     * @return
     *     possible object is
     *     {@link AnnotatedCollaborativeContentAnalyses.AnnotateCollaborativeContentAnalysis }
     *     
     */
    public List<AnnotatedCollaborativeContentAnalysis> getAnnotateCollaborativeContentAnalysis() {
    	if(AnnotatedCollaborativeContentAnalyses==null){
    		AnnotatedCollaborativeContentAnalyses = new ArrayList<AnnotatedCollaborativeContentAnalysis>();
    	}
        return AnnotatedCollaborativeContentAnalyses;
    }

  
    public void setAnnotateCollaborativeContentAnalysis(AnnotatedCollaborativeContentAnalysis value) {
    	if(AnnotatedCollaborativeContentAnalyses==null){
    		AnnotatedCollaborativeContentAnalyses = new ArrayList<AnnotatedCollaborativeContentAnalysis>();
    	}
        this.AnnotatedCollaborativeContentAnalyses.add(value);
    }
    
    /**
     * get the value of id.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * set the value of id.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }
    
    

}

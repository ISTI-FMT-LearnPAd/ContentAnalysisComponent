package eu.learnpad.ca.rest.data.collaborative;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "AnnotatedCollaborativeContentAnalyses")
public class AnnotatedCollaborativeContentAnalyses implements Serializable{
	
	
	/**
	 * 
	 */
	@XmlTransient
	private static final long serialVersionUID = 8605819018990855517L;
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
    
    

}

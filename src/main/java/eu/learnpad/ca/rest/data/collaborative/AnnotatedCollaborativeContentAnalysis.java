package eu.learnpad.ca.rest.data.collaborative;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.learnpad.ca.rest.data.Annotation;
import eu.learnpad.ca.rest.data.stat.AnnotatedStaticContentAnalysis;

/**
 * This class contains the data structure with all the informations returned by
 * the API about a given Annotated Collaborative Content Analysis.
 *
 * @author ISTI CNR
 *
*/



@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "collaborativeContent",
    "annotations",
    "overallQuality",
    "overallQualityMeasure",
    "overallRecommendations"
})
@XmlRootElement(name = "annotatedCollaborativeContentAnalysis")
public class AnnotatedCollaborativeContentAnalysis implements Serializable {
	
	/**
	 * 
	 */
	@XmlTransient
	private static final long serialVersionUID = -4306190343830343967L;
	
	
	
	@JsonIgnore
	@XmlElement(name = "CollaborativeContent", required = true)
    protected CollaborativeContent collaborativeContent;
    @XmlElementWrapper(name = "Annotations", required = true)
    @XmlElement(name = "Annotation", required = true)
    protected List<Annotation> annotations;
    @XmlElement(name = "OverallQuality", required = true)
    protected String overallQuality;
    @XmlElement(name = "OverallQualityMeasure", required = true)
    protected String overallQualityMeasure;
    @Transient
    @XmlElement(name = "OverallRecommendations", required = true)
    protected String overallRecommendations;
    @XmlAttribute(name = "id", required = true)
    @Transient
    protected Integer id;
    @XmlAttribute(name = "type", required = true)
    protected String type;

    public AnnotatedCollaborativeContentAnalysis(){
    	
    }

    
    
    public AnnotatedCollaborativeContentAnalysis(Integer id, String type) {
		super();
		this.id = id;
		this.type = type;
	}



	/**
     * get the value of CollaborativeContent.
     * 
     * @return
     *     possible object is
     *     {@link AnnotatedStaticContentAnalysis.Content }
     *     
     */
    public CollaborativeContent getCollaborativeContent() {
        return collaborativeContent;
    }

    /**
     * set the value of collaborativeContent.
     * 
     * @param value
     *     allowed object is
     *     {@link AnnotatedStaticContentAnalysis.Content }
     *     
     */
    public void setCollaborativeContent(CollaborativeContent value) {
        this.collaborativeContent = value;
    }

    /**
     * get the value of annotations.
     * 
     * @return
     *     possible object is
     *     {@link AnnotatedStaticContentAnalysis.Annotations }
     *     
     */
    public List<Annotation> getAnnotations() {
    	if (annotations == null) {
    		annotations = new ArrayList<Annotation>();
        }
        return annotations;
    }

    /**
     * set the value of annotations.
     * 
     * @param value
     *     allowed object is
     *     {@link AnnotatedStaticContentAnalysis.Annotations }
     *     
     */
    public void setAnnotations(Annotation value) {
    	if (annotations == null) {
            annotations = new ArrayList<Annotation>();
            
        }
        annotations.add(value);
    }

    /**
     * get the value of overallQuality.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOverallQuality() {
        return overallQuality;
    }

    /**
     * set the value of overallQuality.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOverallQuality(String value) {
        this.overallQuality = value;
    }

    /**
     * get the value of overallQualityMeasure.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOverallQualityMeasure() {
        return overallQualityMeasure;
    }

    /**
     * set the value of overallQualityMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOverallQualityMeasure(String value) {
        this.overallQualityMeasure = value;
    }

    /**
     * get the value of overallRecommendations.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOverallRecommendations() {
        return overallRecommendations;
    }

    /**
     * set the value of overallRecommendations.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOverallRecommendations(String value) {
        this.overallRecommendations = value;
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

    /**
     * get the value of type.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Column
    public String getType() {
        return type;
    }

    /**
     * set the value of type.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    @Transient
	@Override
	public String toString() {
		return "ContentAnalysis [ type=" + type+ ", id=" + id 
				+ ", collaborativeContent=" + collaborativeContent + ", annotations=" + annotations
				+ ", overallQuality=" + overallQuality
				+ ", overallQualityMeasure=" + overallQualityMeasure
				+ ", overallRecommendations=" + overallRecommendations
				+  "]\n";
	}



	public void setAnnotations(List<Annotation> value) {
		if (annotations == null) {
            annotations = new ArrayList<Annotation>();
            
        }
        annotations.addAll(value);
		
	}

    

}

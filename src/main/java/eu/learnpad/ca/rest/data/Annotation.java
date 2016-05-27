package eu.learnpad.ca.rest.data;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonIgnore;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public  class Annotation implements Comparable<Object> , Serializable{

	/**
	 * 
	 */
	@XmlTransient
	private static final long serialVersionUID = -1802137579448233428L;
	@XmlAttribute(name = "id", required = true)
	protected Integer id;
	@XmlAttribute(name = "type", required = true)
	protected String type;
	@JsonIgnore
	@XmlAttribute(name = "StartNode", required = true)
	protected Integer startNode;
	@JsonIgnore
	@XmlAttribute(name = "EndNode", required = true)
	protected Integer endNode;
	@XmlAttribute(name = "recommendation", required = true)
	protected String recommendation;
	@XmlAttribute(name = "StartNode_Offset", required = true)
	protected Integer startNode_Offset;
	@XmlAttribute(name = "EndNode_Offset", required = true)
	protected Integer endNode_Offset;
	
	@XmlAttribute(name = "StartSentence_Offset", required = true)
	protected Integer startSentence_Offset;
	@XmlAttribute(name = "EndSentence_Offset", required = true)
	protected Integer endSentence_Offset;
	
	@XmlTransient
	protected Node NodeStart;
	@XmlTransient
	protected Node NodeEnd;
	
	
	public Annotation(){
		
	}
	
	public Annotation(int id, String type, int snode, int enode, String recom){
		this.id=id;
		this.type=type;
		this.startNode=snode;
		this.endNode=enode;
		this.recommendation=recom;
	}
	
	public Integer getStartSentence_Offset() {
        return startSentence_Offset;
    }

    public void setStarSenttence_Offset(Integer startSentence_Offset) {
        this.startSentence_Offset = startSentence_Offset;
    }

    public Integer getEndSentence_Offset() {
        return endSentence_Offset;
    }

    public void setEndSentence_Offset(Integer endSentence_Offset) {
        this.endSentence_Offset = endSentence_Offset;
    }
	
	
	public Integer getstartNode_Offset() {
		return startNode_Offset;
	}

	public void setstartNode_Offset(Integer startNode_Offset) {
		this.startNode_Offset = startNode_Offset;
	}

	public Integer getendNode_Offset() {
		return endNode_Offset;
	}

	public void setendNode_Offset(Integer endNode_Offset) {
		this.endNode_Offset = endNode_Offset;
	}

	public Node getNodeStart() {
		if(NodeStart==null){
			NodeStart = new Node();
		}
		return NodeStart;
	}

	public void setNodeStart(Node nodeStart) {
		NodeStart = nodeStart;
		startNode_Offset = NodeStart.getOffSet();
	}

	public Node getNodeEnd() {
		return NodeEnd;
	}

	public void setNodeEnd(Node nodeEnd) {
		NodeEnd = nodeEnd;
		endNode_Offset = NodeEnd.getOffSet();
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

	/**
	 * get the value of startNode.
	 * 
	 * @return
	 *     possible object is
	 *     {@link Integer }
	 *     
	 */
	public Integer getStartNode() {
		return startNode;
	}

	/**
	 * set the value of startNode.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link Integer }
	 *     
	 */
	public void setStartNode(Integer value) {
		this.startNode = value;
	}

	/**
	 * get the value of endNode.
	 * 
	 * @return
	 *     possible object is
	 *     {@link Integer }
	 *     
	 */
	public Integer getEndNode() {
		return endNode;
	}

	/**
	 * set the value of endNode.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link Integer }
	 *     
	 */
	public void setEndNode(Integer value) {
		this.endNode = value;
	}

	/**
	 * get the value of recommendation.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getRecommendation() {
		return recommendation;
	}

	/**
	 * set the value of recommendation.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setRecommendation(String value) {
		this.recommendation = value;
	}

	@Override
	public String toString() {
		return "Annotation_id=" + id + "[type=" + type + ", startNode="
				+ startNode + ", endNode=" + endNode + ", recommendation="
				+ recommendation + "]\n";
	}

	@Override
	public int compareTo(Object o) {
		Annotation other = (Annotation) o;
	    return startNode_Offset.compareTo(other.getstartNode_Offset());
	}
	
	

}


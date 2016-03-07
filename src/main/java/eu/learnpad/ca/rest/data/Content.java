package eu.learnpad.ca.rest.data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public  class Content  implements Serializable {

	/**
	 * 
	 */
	@XmlTransient
	private static final long serialVersionUID = 9202035221734096506L;
	
	@XmlMixed
	@XmlElementRef(type=Node.class, name="Node")
	protected List<Object> Content;

	/**
	 * get the value of Content
	 * @return List of Object
	 */
	public List<Object> getContent() {
		if (Content == null) {
			Content = new ArrayList<Object>();
		}
		return this.Content;
	}

	/***
	 * Set get the value of Content
	 * @param obj
	 */
	public void setContent(Object obj){
		if (Content == null) {
			Content = new ArrayList<Object>();
		}
		if(obj instanceof String | obj instanceof Node){
			this.Content.add(obj);
		}

	}

	@Override
	public String toString() {
		String string = "";
		for(Object obj: Content){
			string+=obj.toString();
		}
		return   string;
	}





}
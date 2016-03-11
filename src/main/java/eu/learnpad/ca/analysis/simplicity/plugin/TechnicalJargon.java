package eu.learnpad.ca.analysis.simplicity.plugin;


import java.util.List;
import java.util.Set;

import org.languagetool.Language;

import eu.learnpad.ca.analysis.Plugin;
import eu.learnpad.ca.analysis.localizzation.Messages;
import eu.learnpad.ca.gate.GateThread;
import eu.learnpad.ca.rest.data.Annotation;
import eu.learnpad.ca.rest.data.Node;
import gate.DocumentContent;
import gate.Factory;
import gate.FeatureMap;



public class TechnicalJargon extends Plugin {
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(TechnicalJargon.class);

	public TechnicalJargon(Language lang,  DocumentContent docContent, List<Node> listnode){
		this.language=lang;
		this.docContent = docContent;
		this.listnode = listnode;
	}


	public void checkTechnicalJargon(GateThread gateu, List<Annotation> listannotations, Set<gate.Annotation> listSentenceDefected,Set<gate.Annotation> listSentence){


		FeatureMap fe = Factory.newFeatureMap();

		//majorType lexicalambiguity
		fe.put("minorType", "technicaljargon");//  vagueness //$NON-NLS-1$ //$NON-NLS-2$
		fe.put("majorType", "simplicity"); //$NON-NLS-1$ //$NON-NLS-2$
		Set<gate.Annotation> tj = gateu.getAnnotationSet("Lookup" , fe); //$NON-NLS-1$


		String rac = Messages.getString("TechnicalJargon.Recomandation",language); //$NON-NLS-1$

		String type = "Technical Jargon"; //$NON-NLS-1$
		if(!tj.isEmpty())
			gatevsleanpadAnnotation(tj, listannotations,listSentenceDefected,listnode,docContent,type ,rac,log ,listSentence,null,null,null);
	}

	
}

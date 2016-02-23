package eu.learnpad.ca.analysis.contentclarity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.languagetool.Language;

import eu.learnpad.ca.analysis.AbstractAnalysisClass;
import eu.learnpad.ca.analysis.contentclarity.plugin.ActorUnclear;
import eu.learnpad.ca.analysis.contentclarity.plugin.UnclearAcronym;
import eu.learnpad.ca.gate.GateThread;
import eu.learnpad.ca.rest.data.Annotation;
import eu.learnpad.ca.rest.data.Content;
import eu.learnpad.ca.rest.data.Node;
import eu.learnpad.ca.rest.data.collaborative.AnnotatedCollaborativeContentAnalysis;
import eu.learnpad.ca.rest.data.collaborative.CollaborativeContent;
import eu.learnpad.ca.rest.data.collaborative.CollaborativeContentAnalysis;
import eu.learnpad.ca.rest.data.stat.AnnotatedStaticContentAnalysis;
import eu.learnpad.ca.rest.data.stat.StaticContent;
import eu.learnpad.ca.rest.data.stat.StaticContentAnalysis;
import gate.DocumentContent;
import gate.Factory;

public class ContentClarity extends AbstractAnalysisClass {
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ContentClarity.class);
	
	
	private long lStartTime;
	public ContentClarity(CollaborativeContentAnalysis collaborativeContentInput,Language lang, GateThread gate) {

		this.language = lang;
		this.collaborativeContentInput = collaborativeContentInput;
		this.gateu = gate;
	}

	public ContentClarity(StaticContentAnalysis staticContentInput, Language lang, GateThread gate) {

		this.language = lang;
		this.staticContentInput = staticContentInput;
		this.gateu = gate;
	}

	public void run() {
		 lStartTime = System.currentTimeMillis();
		//some tasks
		if (collaborativeContentInput != null) {
			check(collaborativeContentInput);
		}

		if (staticContentInput != null) {
			check(staticContentInput);
		}
		long lEndTime = System.currentTimeMillis();
		long difference = lEndTime - lStartTime;

		log.trace("ContentClarity Elapsed milliseconds: " + difference);


	}

	
	private AnnotatedStaticContentAnalysis check(StaticContentAnalysis staticContentInput) {
		String title = staticContentInput.getStaticContent().getTitle();
		String idc = staticContentInput.getStaticContent().getId();
		String content = staticContentInput.getStaticContent().getContentplain();

		annotatedStaticContent = new AnnotatedStaticContentAnalysis();
		StaticContent sc = new StaticContent();
		annotatedStaticContent.setStaticContent(sc);
		sc.setTitle(title);
		sc.setId(idc);
		Content c = new Content();
		sc.setContent(c);

		// AnnotationImpl i;

		
		List<Annotation> listannotation  =new ArrayList<Annotation>();
		int numSentence = execute(content,c,listannotation);
		annotatedStaticContent.setAnnotations(listannotation);
		double qualitymmeasure = calculateOverallQualityMeasure(numSentence);
		annotatedStaticContent.setOverallQuality(this.calculateOverallQuality(qualitymmeasure));
		annotatedStaticContent.setOverallQualityMeasure(new DecimalFormat("##.##").format(qualitymmeasure)+"%");
		annotatedStaticContent.setOverallRecommendations(this.calculateOverallRecommendations(qualitymmeasure));
		annotatedStaticContent.setType("Content Clarity");

		return annotatedStaticContent;
		
	}

	public AnnotatedCollaborativeContentAnalysis check(
			CollaborativeContentAnalysis collaborativeContentInput) {
		String title = collaborativeContentInput.getCollaborativeContent().getTitle();
		String idc = collaborativeContentInput.getCollaborativeContent().getId();
		String content = collaborativeContentInput.getCollaborativeContent().getContentplain();

		annotatedCollaborativeContent = new AnnotatedCollaborativeContentAnalysis();
		CollaborativeContent sc = new CollaborativeContent();
		annotatedCollaborativeContent.setCollaborativeContent(sc);
		sc.setTitle(title);
		sc.setId(idc);
		Content c = new Content();
		sc.setContent(c);

		// AnnotationImpl i;

		
		List<Annotation> listannotation  =new ArrayList<Annotation>();
		int numSentence = execute(content,c,listannotation);
		annotatedCollaborativeContent.setAnnotations(listannotation);
		double qualitymmeasure = calculateOverallQualityMeasure(numSentence);
		annotatedCollaborativeContent.setOverallQuality(this.calculateOverallQuality(qualitymmeasure));
		annotatedCollaborativeContent.setOverallQualityMeasure(new DecimalFormat("##.##").format(qualitymmeasure)+"%");
		annotatedCollaborativeContent.setOverallRecommendations(this.calculateOverallRecommendations(qualitymmeasure));
		annotatedCollaborativeContent.setType("Content Clarity");

		return annotatedCollaborativeContent;

	}

	private int execute(String content, Content c,
			List<Annotation> listannotation) {
		try {
			gateu.join();
		} catch (Exception e) {
			log.error(e);
		}
		 lStartTime = System.currentTimeMillis();
		DocumentContent docContent = gateu.getDocumentContent();
		Set<gate.Annotation> listSentence = gateu.getSentence();
		Set<gate.Annotation> listSentenceDefected = new HashSet<>();
		List<Node> listnode = new ArrayList<Node>();
		
		UnclearAcronym uclearac = new UnclearAcronym(language, docContent,listnode);
		uclearac.checkUnclearAcronym(listSentence, listSentenceDefected,listannotation);
		
		ActorUnclear ucleActor = new ActorUnclear(language, docContent, listnode);
		ucleActor.check(gateu, listannotation, listSentenceDefected, listSentence);
		addNodeInContent(listnode,c,docContent);
		
		numDefectiveSentences = listSentenceDefected.size();
		Factory.deleteResource(gateu.getCorpus());
		return listSentence.size();
	}

	

	
}

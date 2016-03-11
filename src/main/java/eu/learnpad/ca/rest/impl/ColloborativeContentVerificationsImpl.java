package eu.learnpad.ca.rest.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.languagetool.Language;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.language.BritishEnglish;
import org.languagetool.language.Italian;

import eu.learnpad.ca.analysis.AbstractAnalysisClass;
import eu.learnpad.ca.analysis.completeness.Completeness;
import eu.learnpad.ca.analysis.contentclarity.ContentClarity;
import eu.learnpad.ca.analysis.correctness.CorrectnessAnalysis;
import eu.learnpad.ca.analysis.non_ambiguity.NonAmbiguity;
import eu.learnpad.ca.analysis.presentation.PresentationClarity;
import eu.learnpad.ca.analysis.simplicity.Simplicity;
import eu.learnpad.ca.gate.GateThread;
import eu.learnpad.ca.rest.ColloborativeContentVerifications;
import eu.learnpad.ca.rest.data.collaborative.AnnotatedCollaborativeContentAnalyses;
import eu.learnpad.ca.rest.data.collaborative.AnnotatedCollaborativeContentAnalysis;
import eu.learnpad.ca.rest.data.collaborative.CollaborativeContentAnalysis;



@Path("/learnpad/ca/bridge")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class ColloborativeContentVerificationsImpl implements ColloborativeContentVerifications {

	@Inject 
	TokenPersistence em;

	// Setup the entity manager
	/*private static EntityManagerFactory factory =   Persistence.createEntityManagerFactory("annotatedcca");
	private static EntityManager em = factory.createEntityManager();*/


	private static Map<String,List<AbstractAnalysisClass>> map = new HashMap<String,List<AbstractAnalysisClass>>();
	private UUID id = UUID.randomUUID();
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ColloborativeContentVerificationsImpl.class);

	/*@PostConstruct
	void init() {
	    if(id<1){
			try{
				System.out.println("Entering connection.init()");
				//Query query = em.createNativeQuery("select ID FROM ANNOTATEDCOLLABORATIVECONTENTANALYSES order by ID");
				TypedQuery<String> query2 = em.createNamedQuery("ContentAnalyses.findAll",String.class);
				List<String> res = query2.getResultList();
				if(!res.isEmpty())
					id =  Integer.valueOf(res.get(res.size()-1));
				System.out.println("Exiting connection.init()");
			}catch(Exception e){
				log.fatal("db problem");
				log.error(e);
			}
		}
	}*/



	@Path("/validatecollaborativecontent")
	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String putValidateCollaborativeContent(CollaborativeContentAnalysis contentFile){
		try{
			if(contentFile!=null){
				String content = contentFile.getCollaborativeContent().getContentplain();
				if(content!=null && content.length()>0){
					GateThread gateu = new GateThread(content,contentFile.getQualityCriteria());
					gateu.start();

					Language lang = null;
					if(contentFile.getLanguage().toLowerCase().equals("english")){
						lang = new  BritishEnglish();
					}else{
						if(contentFile.getLanguage().toLowerCase().equals("italian")){
							lang = new Italian();
						}else
							if(contentFile.getLanguage().toLowerCase().equals("english uk")){
								lang = new BritishEnglish();
							}else
								if(contentFile.getLanguage().toLowerCase().equals("english us")){
									lang = new AmericanEnglish();
								}else
									lang = new BritishEnglish();
					}
					if(contentFile.getQualityCriteria().isCorrectness()){

						CorrectnessAnalysis threadcorre = new CorrectnessAnalysis(lang, contentFile);
						threadcorre.start();
						putAndCreate(id, threadcorre);

					}
					if(contentFile.getQualityCriteria().isSimplicity()){

						/*JuridicalJargon threadsimply = new JuridicalJargon (contentFile, lang);
					threadsimply.start();
					putAndCreate(id, threadsimply);

					DifficultJargon threadDF = new DifficultJargon (contentFile, lang);
					threadDF.start();
					putAndCreate(id, threadDF);



					ExcessiveLength threadEL = new ExcessiveLength(contentFile, lang);
					threadEL.start();
					putAndCreate(id, threadEL);*/
						Simplicity threadEL = new Simplicity(contentFile, lang, gateu);
						threadEL.start();
						putAndCreate(id, threadEL);


					}
					if(contentFile.getQualityCriteria().isNonAmbiguity()){

						NonAmbiguity threadNonAmbiguity = new NonAmbiguity (contentFile, lang, gateu);
						threadNonAmbiguity.start();
						putAndCreate(id, threadNonAmbiguity);

					}
					if(contentFile.getQualityCriteria().isContentClarity()){

						ContentClarity threadContentClarity = new ContentClarity (contentFile, lang, gateu);
						threadContentClarity.start();
						putAndCreate(id, threadContentClarity);

					}
					if(contentFile.getQualityCriteria().isPresentationClarity()){

						PresentationClarity threadPresentation = new PresentationClarity (contentFile, lang);
						threadPresentation.start();
						putAndCreate(id, threadPresentation);

					}
					if(contentFile.getQualityCriteria().isCompleteness()){

						Completeness threadCompleteness = new Completeness (contentFile, lang);
						threadCompleteness.start();
						putAndCreate(id, threadCompleteness);

					}


					return id.toString();
				}else{
					log.error("No Content send: "+content);
					return "No Content send";
				}
			}else{
				log.error("Null Element send");
				return "Null Element send";
			}

		}catch(Exception e){
			log.fatal("Fatal "+e.getMessage());
			return "FATAL ERROR";
		}
	}

	private void putAndCreate(UUID id, AbstractAnalysisClass ai){
		if(!map.containsKey(id.toString())){
			List<AbstractAnalysisClass> lai = new ArrayList<AbstractAnalysisClass>();
			lai.add(ai);
			map.put(id.toString(), lai);
		}else{
			List<AbstractAnalysisClass> lai = map.get(id.toString());
			lai.add(ai);
		}
	}

	@Path("/validatecollaborativecontent/{idAnnotatedCollaborativeContentAnalysis:.*}")
	@GET
	public AnnotatedCollaborativeContentAnalyses getCollaborativeContentVerifications(@PathParam("idAnnotatedCollaborativeContentAnalysis") String contentID, @Context HttpServletRequest request){
		try{
			if(map.containsKey(contentID)){
				String ip = request.getRemoteAddr();
				AnnotatedCollaborativeContentAnalyses ar = new AnnotatedCollaborativeContentAnalyses();
				ar.setIp(ip);
				ar.setDate(GetUTCdatetimeAsString());
				List<AbstractAnalysisClass> listanalysisInterface = map.get(contentID);


				for(AbstractAnalysisClass analysisInterface :listanalysisInterface){
					AnnotatedCollaborativeContentAnalysis annotatedCollaborativeContent = analysisInterface.getAnnotatedCollaborativeContentAnalysis();
					if(annotatedCollaborativeContent!=null){
						annotatedCollaborativeContent.setId(contentID);
						ar.setAnnotateCollaborativeContentAnalysis(annotatedCollaborativeContent);

					}
				}
				ar.setId(contentID);
				EntityTransaction trans = em.getTransaction();
				trans.begin();
				em.persist(ar);
				trans.commit();
				map.remove(contentID);


				return ar;
			}else{
				AnnotatedCollaborativeContentAnalyses	r = 	em.find(AnnotatedCollaborativeContentAnalyses.class, contentID);
				if(r!=null){
					return r;
				}else{
					log.error("Element not found: "+contentID+" map:"+map.keySet().toString());
					return null;
				}
			}
		}catch(Exception e){
			log.fatal("Fatal "+e.getMessage());
			return null;
		}
	}

	@Path("/validatecollaborativecontent/{idAnnotatedCollaborativeContentAnalysis:.*}/status")
	@GET
	public String getStatusCollaborativeContentVerifications(@PathParam("idAnnotatedCollaborativeContentAnalysis") String contentID){
		try{
			if(map.containsKey(contentID)){
				List<AbstractAnalysisClass> listanalysisInterface  = map.get(contentID);
				Integer progress = getProgress(listanalysisInterface);
				if(progress>99){
					clean(listanalysisInterface);
					return "OK";
				}
				else
					return "InProgress_"+progress+"%";
			}
			AnnotatedCollaborativeContentAnalyses	r = 	em.find(AnnotatedCollaborativeContentAnalyses.class, contentID);
			if(r!=null){
				return "OK";
			}
			log.error("Element not found: "+contentID+" map:"+map.keySet().toString());
			return "ERROR";
		}catch(Exception e){
			log.fatal("Fatal "+e.getMessage());
			return "FATAL ERROR";
		}
	}

	private Integer getProgress(List<AbstractAnalysisClass> listanalysisInterface){
		int size = listanalysisInterface.size();
		int i = 0;
		for(AbstractAnalysisClass analysisInterface :listanalysisInterface){
			if(analysisInterface.getStatus().equals("OK") ){
				i++;
			}
		}
		Integer p = (i*100/size);
		return p;

	}

	private void clean(List<AbstractAnalysisClass> listanalysisInterface){
		for(AbstractAnalysisClass analysisInterface :listanalysisInterface){
			if(analysisInterface.getGate()!=null){
				analysisInterface.getGate().cleanup();
			}
		}
	}

	@Path("/allid")
	@GET
	public String  getStatusCollaborativeContentVerifications(){
		String result = new String();
		try{
			if(!map.isEmpty()){
				for(String key :map.keySet()){
					result+=key.toString()+";";
				}

				return result;

			}
			log.error("Element not found");
			return "ERROR";
		}catch(Exception e){
			log.fatal("Fatal "+e.getMessage());

			return "FATAL ERROR";

		}


	}
	public static String GetUTCdatetimeAsString()
	{
		String DATEFORMAT = "dd-MM-yyyy HH:mm:ss";
		final SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		final String utcTime = sdf.format(new Date());

		return utcTime;
	}
}

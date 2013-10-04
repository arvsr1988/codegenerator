package com.arvind.codegenerator;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class POSTagger {
	
	public static final String NOUN_TAG = "NN";
	public static final List<String> NOUN_TAGS;
	static {
		List<String> nounTagsLocal = new ArrayList<String>();
		nounTagsLocal.add("NNP");
		nounTagsLocal.add("NN");
		nounTagsLocal.add("NNS");
		NOUN_TAGS = Collections.unmodifiableList(nounTagsLocal);
	}
	
	public static final List<String> PRONOUN_TAGS; 
	static {
		List<String> pronounTagsLocal = new ArrayList<String>();
		pronounTagsLocal.add("PRP");
		pronounTagsLocal.add("PRP$");
		pronounTagsLocal.add("POS");
		pronounTagsLocal.add("WP$");
		pronounTagsLocal.add("WP");
		pronounTagsLocal.add("WDT");
		PRONOUN_TAGS = Collections.unmodifiableList(pronounTagsLocal);
		
	}
	
	public static final List<String> SPECIAL_PRONOUN_TAGS;
	static {
		List<String> specialProunounTagsLocal = new ArrayList<String>();
		specialProunounTagsLocal.add("PRP");
		SPECIAL_PRONOUN_TAGS = Collections.unmodifiableList(specialProunounTagsLocal);
	}	
	

	public static String[] getTags(String[] inputString) throws Exception{
		
		InputStream modelIn = null;

		try {
		  modelIn = new FileInputStream("resources/en-pos-maxent.bin");
		  POSModel model = new POSModel(modelIn);
		  POSTaggerME tagger = new POSTaggerME(model);	  
		  String tags[] = tagger.tag(inputString);
		  return tags;

		} finally {
		  if (modelIn != null) {
		    try { modelIn.close(); }
		    catch (Exception e) {}
		  }
		}
	}	
	
	public static Map<String, Set<Integer>> getTagsByPOS(String[] tags, List<String> posList){
		Map<String, Set<Integer>> tagPositionMap = new HashMap<String, Set<Integer>>();
		if(posList == null || posList.isEmpty()){
			return tagPositionMap;
		}
		int tagArrayLength = tags.length;
		
		
		
		for(int currentIndex = 0; currentIndex < tagArrayLength ; currentIndex++){
			Set<Integer> tagPositionList = tagPositionMap.get(tags[currentIndex]);
			if(tagPositionList == null){
			}
			
			if(posList.contains(tags[currentIndex])){
				tagPositionMap.put(tags[currentIndex], tagPositionList);
			}
		}
		
		return tagPositionMap;
	}
	
}

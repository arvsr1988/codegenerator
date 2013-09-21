package com.arvind.codegenerator;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class POSTagger {
	
	public static final String NOUN_TAG = "NN";

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
	
	public static List<Integer> getTagsByPOS(String[] tags, String POS){
		List<Integer> positions = new ArrayList<Integer>();
		
		for(int counter = 0; counter < tags.length; counter++){
			if(tags[counter].equals(POS)){
				positions.add(counter);
			}
		}
		
		return positions;
	}
}

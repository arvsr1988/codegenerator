package com.arvind.codegenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class Main {
		
	public static void main(String[] args){
		
		InputStream modelIn = null;
		
		try {
			
		String inputFromFile = getInputFromFile();
		
		if(StringUtils.isEmpty(inputFromFile)){
			return;
		}
		
		modelIn = new FileInputStream("resources/en-token.bin");
		TokenizerModel model = new TokenizerModel(modelIn);
		Tokenizer tokenizer = new TokenizerME(model);
		String[] inputStringWords  = tokenizer.tokenize(inputFromFile);		
		String[] tags = POSTagger.getTags(inputStringWords);
		ClassGenerator.createClasses(inputStringWords, tags);
		
		} catch (Exception ex){
			ex.printStackTrace();
		} finally {
			  if (modelIn != null) {
				    try { modelIn.close(); }
				    catch (Exception e) {}
				  }
				}
		
	}

	private static String getInputFromFile() throws Exception {
		//TODO : move this to a property file
		return FileUtils.readFileToString(new File("/home/arvind/Desktop/input")).replaceAll("\n", " ");
	}
}
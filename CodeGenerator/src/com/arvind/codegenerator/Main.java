package com.arvind.codegenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class Main {
	
	
	
	public static void main(String[] args){
		
		try {
			
		String inputFromFile = getInputFromFile();
		
		if(StringUtils.isEmpty(inputFromFile)){
			return;
		}
		String[] inputStringWords = inputFromFile.toLowerCase().split(" ");
		
		String[] tags = POSTagger.getTags(inputStringWords);
		List<Integer> positions = POSTagger.getTagsByPOS(tags, POSTagger.NOUN_TAG);
		List<String> classNames = new ArrayList<String>();
		
		for(int position : positions){
			classNames.add(inputStringWords[position]);
		}
		
		ClassGenerator.generateClassFiles(classNames);
		
		} catch (Exception ex){
			ex.printStackTrace();
		}
		
	}

	private static String getInputFromFile() throws Exception {
		return FileUtils.readFileToString(new File("/home/arvind/Desktop/input")).replaceAll("\n", " ");
	}
}
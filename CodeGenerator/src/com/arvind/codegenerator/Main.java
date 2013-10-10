package com.arvind.codegenerator;

import java.io.File;

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
		ClassGenerator.createClasses(inputStringWords, tags);
		
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}

	private static String getInputFromFile() throws Exception {
		return FileUtils.readFileToString(new File("/home/arvind/Desktop/input")).replaceAll("\n", " ");
	}
}
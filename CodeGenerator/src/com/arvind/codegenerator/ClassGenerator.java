package com.arvind.codegenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class ClassGenerator {

	//TODO : Move this to a property file
	private static final String GENERATE_DIRECTORY = "/home/arvind/Desktop/CodeGenerator/src/com/codegenerator/";
	private static final String JAVA_EXTENSION = ".java";
	
	//input - array of words and array of POS tags for the array of words. 
	public static void createClassFiles() throws Exception {
		/* 
		 * approach : construct a list of nouns (class names) with the corresponding attributes (to be constructed based on the possessive words
		 * 	1) create a HashMap<Integer, Set<Integer> classesWithAttributes and add all the nouns into it - originally assuming that all nouns are classes
		 * 	2) keep the list of possessive tags. loop through the array of tags to get a Map<String, List<Intger>> (tag name with the positions of each occurrence of the tag)
		 *  3) for each tag, get the owner and the attribute position as a Map<Intger attributePosition, Integer ownerPosition>. remove attributePosition entry from classesWithAttributes and add attributePosition to classesWithAttributes.get(owernPosition)
		 */

	}
	
	public static void generateClassFiles(List<String> classNames) throws Exception{
		if(classNames == null || classNames.isEmpty()){
			return;
		}
		
		File targetFile = new File(getFullFilePath((classNames.get(0))));
		File parent = targetFile.getParentFile();
		
		if(!parent.exists() && !parent.mkdirs()){
		    throw new IllegalStateException("Couldn't create dir: " + parent);
		}
		
		for(String className : classNames){
			List<String> linesInFile = new ArrayList<String>();
			String formattedClassName = new StringBuilder(className.substring(0, 1).toUpperCase()).append(className.substring(1)).toString();
			linesInFile.add(new StringBuilder().append("public class ").append(formattedClassName).append("{").toString());
			linesInFile.add("}");
			FileUtils.writeLines(new File(getFullFilePath(formattedClassName)), linesInFile);
		}
	}
	
	private static String getFullFilePath(String fileName){
		return new StringBuilder(GENERATE_DIRECTORY).append(fileName).append(JAVA_EXTENSION).toString();
	}
	
}

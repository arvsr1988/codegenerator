package com.arvind.codegenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.arvind.codegenerator.domain.ClassDetails;
import com.arvind.codegenerator.domain.NounOwner;

public class ClassGenerator {

	//TODO : Move this to a property file
	private static final String GENERATE_DIRECTORY = "/home/arvind/Desktop/CodeGenerator/src/com/codegenerator/";
	private static final String JAVA_EXTENSION = ".java";
	
	/*
	 * input - array of words and array of POS tags for the array of words. 
	 * approach : construct a list of nouns (class names) with the corresponding attributes (to be constructed based on the possessive words
	 */
	
	public static void createClasses(String[] words, String[] posTags) throws Exception {
		if(words.length != posTags.length){
			return;
		}
		
		//1) create a HashMap<Integer, Set<Integer> classesWithAttributes and add all the nouns into it - originally assuming that all nouns are classes
		Map<Integer, Set<Integer>> classesWithAttributes = new HashMap<Integer, Set<Integer>>();
		
		//each noun is a class - so add each of these as a key in the classesWithAttributes map
		Map<String, Set<Integer>> nounTags = POSTagger.getTagsByPOS(posTags, POSTagger.NOUN_TAGS);
		for(Map.Entry<String, Set<Integer>>  nounTagPositions : nounTags.entrySet() ){
			for(int nounTagPosition : nounTagPositions.getValue()){
				classesWithAttributes.put(nounTagPosition, new HashSet<Integer>());
			}	
		}
		
		// 2) keep the list of possessive tags. loop through the array of tags to get a Map<String, List<Intger>> (tag name with the positions of each occurrence of the tag)
		Map<String, Set<Integer>> pronounTags = POSTagger.getTagsByPOS(posTags, POSTagger.PRONOUN_TAGS);
		 
	
		//3) for each tag, get the owner and the attribute position as a Map<Intger attributePosition, Integer ownerPosition>
		List<NounOwner> nounOwnershipList = new ArrayList<NounOwner>();
		
		for(Map.Entry<String, Set<Integer>> tag : pronounTags.entrySet()){
			for(int tagPosition : tag.getValue()){
				nounOwnershipList.add(NounOwner.getNounOwnership(tagPosition, posTags));
				
				// remove attributePosition entry from classesWithAttributes and add attributePosition to classesWithAttributes.get(owernPosition)
				
				
			}
		}
		
		NounOwner.getOwnersWithAttributes(nounOwnershipList, classesWithAttributes);
		
		//merge all the same nouns together. use Map<String, List<String>> for this. this can be passed to the class generator
		mergeNouns(classesWithAttributes, words);
		generateFiles(classesWithAttributes, words);

	}
	
	private static void mergeNouns(Map<Integer, Set<Integer>> classesWithAttributes, String[] inputStringArray){
		
		Map<ClassDetails, Set<Integer>> updatedClassAttributeMap = new HashMap<ClassDetails, Set<Integer>>();
		
		
		for(Map.Entry<Integer, Set<Integer>> classAttributes : classesWithAttributes.entrySet()){
			ClassDetails classDetails = new ClassDetails(inputStringArray[classAttributes.getKey()], classAttributes.getKey());
			Set<Integer> updateAttributeList = updatedClassAttributeMap.get(classDetails);
			if(updateAttributeList == null){
				updateAttributeList = new HashSet<Integer>();
			}
			updateAttributeList.addAll(classAttributes.getValue());
			
			updatedClassAttributeMap.put(classDetails, updateAttributeList);
		}
		
		
		classesWithAttributes = new HashMap<Integer, Set<Integer>>(updatedClassAttributeMap.size());
		for(Map.Entry<ClassDetails, Set<Integer>> updatedClassAttrs : updatedClassAttributeMap.entrySet() ){
			classesWithAttributes.put(updatedClassAttrs.getKey().getPosition(), updatedClassAttrs.getValue());
		}
	}
	
	
	private static void generateFiles(Map<Integer, Set<Integer>> classesWithAttributes, String[] inputStringArray) throws Exception{
		
		for(Map.Entry<Integer, Set<Integer>> classAttrs : classesWithAttributes.entrySet()){
			String className = inputStringArray[classAttrs.getKey()];
			//TODO : move this logic out of the loop. Its just to create the directory.
			File targetFile = new File(getFullFilePath(className));
			File parent = targetFile.getParentFile();
			
			if(!parent.exists() && !parent.mkdirs()){
			    throw new IllegalStateException("Couldn't create dir: " + parent);
			}
			
			
			List<String> linesInFile = new ArrayList<String>();
			String formattedClassName = getFormattedClassName(className);
			linesInFile.add(new StringBuilder().append("public class ").append(formattedClassName).append("{").toString());
			
			for(int attrPosition : classAttrs.getValue()){
				String prefix = "Object";
				Set<Integer> attrsOfAttribute = classesWithAttributes.get(attrPosition);
				if(attrsOfAttribute != null){
					prefix = getFormattedClassName(inputStringArray[attrPosition]);
				}
				
				linesInFile.add(new StringBuilder("private ").append(prefix).append(" ").append(inputStringArray[attrPosition].toLowerCase()).toString());
			}
			
			linesInFile.add("}");
			FileUtils.writeLines(new File(getFullFilePath(formattedClassName)), linesInFile);
		}	
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
	
	private static String getFormattedClassName(String className){
		return new StringBuilder(className.substring(0, 1).toUpperCase()).append(className.substring(1)).toString();
	}
	
	
}

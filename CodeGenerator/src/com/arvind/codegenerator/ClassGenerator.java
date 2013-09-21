package com.arvind.codegenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class ClassGenerator {

	//TODO : Move this to a property file
	private static final String GENERATE_DIRECTORY = "/home/arvind/Desktop/CodeGenerator/src/com/codegenerator/";
	private static final String JAVA_EXTENSION = ".java";
	
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

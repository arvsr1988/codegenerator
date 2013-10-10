package com.arvind.codegenerator.domain;

import org.apache.commons.lang3.StringUtils;

public class ClassDetails {
	private String className;
	private int position;
	
	public ClassDetails(String className, int position){
		this.className = className;
		this.position = position;
	}
	
	/*
	 * equality only compares the strings and not the positions for the purpose being used.
	 */
	@Override
	public boolean equals(Object o){
		if(!(o instanceof ClassDetails)){
			return false;
		}
		ClassDetails classDetailsToCompare = (ClassDetails)o;
		if(StringUtils.isEmpty(classDetailsToCompare.getClassName()) || StringUtils.isEmpty(this.getClassName())){
			return false;
		}
		
		return classDetailsToCompare.getClassName().equalsIgnoreCase(this.getClassName());	
	}
	
	@Override
	public int hashCode(){
		return position;
	}
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
}

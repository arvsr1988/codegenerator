package com.arvind.codegenerator.domain;

import com.arvind.codegenerator.POSTagger;

public class NounOwner {

	private int ownerPosition;
	private int attributePosition;
	
	public static NounOwner getNounOwnership(int pronounPosition, String[] tags){
		
		NounOwner nounOwner = new NounOwner();
		
		//find the attribute
		for(int index = pronounPosition + 1; index < tags.length; index++){
			if(POSTagger.NOUN_TAGS.contains(tags[index])){
				nounOwner.setAttributePosition(index);
			}
		}
		
		for(int index = pronounPosition - 1; index > 0; index --){
			if(POSTagger.NOUN_TAGS.contains(tags[index])){
				nounOwner.setOwnerPosition(index);
			}
		}
		
		return nounOwner;
	}	
	
	public int getOwnerPosition() {
		return ownerPosition;
	}
	public void setOwnerPosition(int ownerPosition) {
		this.ownerPosition = ownerPosition;
	}
	public int getAttributePosition() {
		return attributePosition;
	}
	public void setAttributePosition(int attributePosition) {
		this.attributePosition = attributePosition;
	}
	
}

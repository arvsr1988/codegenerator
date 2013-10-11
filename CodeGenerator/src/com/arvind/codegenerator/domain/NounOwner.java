package com.arvind.codegenerator.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
				break;
			}
		}
		
		for(int index = pronounPosition - 1; index > 0; index --){
			if(POSTagger.NOUN_TAGS.contains(tags[index])){
				nounOwner.setOwnerPosition(index);
				break;
			}
		}
		
		return nounOwner;
	}	
	
	public static void getOwnersWithAttributes(List<NounOwner> nounOwnerList, Map<Integer, Set<Integer>> classAttributeMap){
		for(NounOwner nounOwner : nounOwnerList){
			Set<Integer> attributeSet =  classAttributeMap.get(nounOwner.getOwnerPosition());
			if(attributeSet == null) {attributeSet = new HashSet<Integer>();}
			attributeSet.add(nounOwner.getAttributePosition());
			classAttributeMap.put(nounOwner.getOwnerPosition(), attributeSet);
		}
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

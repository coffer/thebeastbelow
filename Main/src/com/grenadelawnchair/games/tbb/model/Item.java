package com.grenadelawnchair.games.tbb.model;

/**
 * General Item class for items used by the player
 */
public class Item {

	private String name;
	
	public Item(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}
}

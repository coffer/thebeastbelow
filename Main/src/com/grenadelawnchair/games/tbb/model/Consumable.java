package com.grenadelawnchair.games.tbb.model;

public class Consumable extends Item {

	Effect effect;
	
	public Consumable(String name) {
		super(name, "data/items.xml");
		//XML
	}
	
	public Effect getEffect(){
		return effect;
	}
	

}

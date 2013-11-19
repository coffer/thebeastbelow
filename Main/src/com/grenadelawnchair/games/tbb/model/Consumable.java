package com.grenadelawnchair.games.tbb.model;

public class Consumable extends Item {

	Effect effect;
	
	public Consumable(String name) {
		super(name);
		//XML
	}
	
	public Effect getEffect(){
		return effect;
	}
	

}

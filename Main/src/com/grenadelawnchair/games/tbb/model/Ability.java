package com.grenadelawnchair.games.tbb.model;

public class Ability implements AbilityInterface {

	private Effect effect;
	private String name;
	private String desc;
	private boolean protective;
	
	public Ability(String name){
		// Search XML for ability
	}
	
	@Override
	public Effect getEffect() {
		return effect;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return desc;
	}

	@Override
	public boolean isProtective() {
		return protective;
	}

}

package com.grenadelawnchair.games.tbb.model;

public class AbilityPassive extends Ability {

	private boolean active;
	
	public AbilityPassive(String name){
		super(name);
		// Search XML for passiveability with said name
		active = false;
	}
	
	public boolean isActive(){
		return active;
	}

}

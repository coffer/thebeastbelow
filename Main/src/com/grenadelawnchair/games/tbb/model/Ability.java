package com.grenadelawnchair.games.tbb.model;

public class Ability {

	private Effect effect;
	private String name;
	private String desc;
	private boolean protective;
	private double cooldown;
	private boolean ready;
	
	public Ability(String name){
		// Search XML for ability
		this.name = name;
		ready = true;
	}
	
	public Effect getEffect() {
		return effect;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return desc;
	}

	public boolean isProtective() {
		return protective;
	}
	
	public double getCooldown(){
		return cooldown;
	}
	
	public boolean isReady(){
		return ready;
	}
	
	public void setReady(boolean b){
		ready = b;
	}

}

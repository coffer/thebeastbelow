package com.grenadelawnchair.games.tbb.model;

public class Effect {
	public enum Type {HEALTH, SPEED} //TODO add more!
	
	private String name;
	private String desc;
	private int duration;
	
	public Effect(String name){
		//Search XML for effect
	}

	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return desc;
	}
	
	public int getDuration(){
		return duration;
	}
}

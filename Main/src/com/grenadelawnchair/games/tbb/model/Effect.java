package com.grenadelawnchair.games.tbb.model;

public class Effect {
	public enum Type {HEALTH, SPEED}
	
	private boolean passive;	
	private String name;
	private String desc;
	private int duration;
	private Type type;
	private double amount;
	
	public Effect(String name){
		this.name = name;
		//Search XML for effect
	}
	
	public void update(){
		if(!isPassive()){
			duration--;
		}
	}
	
	public double getAmount(){
		return amount;
	}
	
	public Type getType(){
		return type;
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
	
	public boolean isPassive(){
		return passive;
	}
}

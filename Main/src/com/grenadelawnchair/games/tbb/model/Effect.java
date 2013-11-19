package com.grenadelawnchair.games.tbb.model;

public class Effect {
	public enum Affecting {HEALTH, SPEED, STRENGTH, AGILITY, INTELLIGENCE}
	
	private boolean passive;	
	private String name;
	private String desc;
	private int timeLeft;
	private Affecting type;
	private double amount;
	
	public Effect(String name){
		this.name = name;
		//Search XML for effect
	}
	
	public void update(){
		if(!isPassive()){
			timeLeft--;
		}
	}
	
	public double getAmount(){
		return amount;
	}
	
	public Affecting getAffecting(){
		return type;
	}

	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return desc;
	}
	
	public int getTimeLeft(){
		return timeLeft;
	}
	
	public boolean isPassive(){
		return passive;
	}
}

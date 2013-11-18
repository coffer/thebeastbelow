package com.grenadelawnchair.games.tbb.model;

public class Weapon {
	
	private String name;
	private int damage;
	private double atkSpeed;
	private Effect effect;
	
	public Weapon(String name){
		// XML stuff
	}
	
	public String getName(){
		return name;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public double getAtkSpeed(){
		return atkSpeed;
	}
	
	public Effect getEffect(){
		return effect;
	}

}

package com.grenadelawnchair.games.tbb.model;

public class Weapon extends Item {
	
	// Index 0 is base, the following are combos (double, triple, quad etc.)
	private int[] damage;
	// 0..1
	private double atkSpeed;
	// 0 = can't parry, 1 = excellent parrying 
	private double parry;
	
	private Effect effect;
	
	public Weapon(String name){
		super(name);
	}

	
	public int getDamage(int i){
		return damage[i];
	}
	
	public double getAtkSpeed(){
		return atkSpeed;
	}
	
	public Effect getEffect(){
		return effect;
	}

	public double getParry(){
		return parry;
	}
}

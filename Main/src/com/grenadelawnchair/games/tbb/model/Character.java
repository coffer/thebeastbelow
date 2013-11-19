package com.grenadelawnchair.games.tbb.model;

import java.util.List;

public abstract class Character {

	// Character Stats
	private String name;
	private double health;
	private double movementSpeed;
	private double attackMultiplyer;
	private double parryMultiplyer;
	private double magicMultiplyer;
	private double evasion;

	
	// Character Inventory and Abilities
	private Weapon weapon;
	private List<Ability> abilites;
	private List<Effect> activeEffects;
	
	public Character(String name){
		this.name = name;
		// XML stuff
	}
	
	public void update(){
		for(Effect e : activeEffects){
			if(!e.isPassive()){
				applyEffect(e);
			}
		}
	}
	
	/**
	 * Applies an effect to the character
	 * @param effect the effect to be applied
	 */
	public void applyEffect(Effect effect){
		activeEffects.add(effect);
		if(effect.isPassive()){
			affect(effect);
		}
	}
	
	/**
	 * The active effect affects the character
	 * @param effect the effect
	 */
	public void affect(Effect effect){
		switch(effect.getType()){
		case HEALTH:
			health += Math.round(effect.getAmount());
			break;
		case SPEED:
			movementSpeed += Math.round(effect.getAmount());
			break;
		default:
			effect.update();
			if(effect.getDuration() == 0){
				activeEffects.remove(effect);
			}
			break;
		}
	}
	
	// Getters and Setters
	public void setWeapon(Weapon w){
		weapon = w;
	}
	
	public List<Ability> getAbilities(){
		return abilites;
	}
	
	public Weapon getWeapon(){
		return weapon;
	}
	
	public String getName(){
		return name;
	}
	
	public double getHealth(){
		return health;
	}
	
	public double getMovementSpeed(){
		return movementSpeed;
	}
	
	
	public double getAtkMultiplyer(){
		return attackMultiplyer;
	}
	
	public double getPryMultiplyer(){
		return parryMultiplyer;
	}
	
	public double getMgcMultiplyer(){
		return magicMultiplyer;
	}
	
	public double getEvasion(){
		return evasion;
	}
}

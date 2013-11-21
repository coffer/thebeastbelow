package com.grenadelawnchair.games.tbb.model;

import java.util.List;

public abstract class Character {

	// Character Stats
	private String name;
	private int health;
	private float movementSpeed;
	private int strength;
	private int agility;
	private int intelligence;

	
	// Character Inventory and Abilities
	private Weapon weapon;
	private List<Ability> abilites;
	private List<Effect> activeEffects;
	
	private boolean offGuard;
	private int combo;
	
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
	 * The active effect affects the character in some way
	 * @param effect the effect
	 */
	public void affect(Effect effect){
		switch(effect.getAffecting()){
		case HEALTH:
			health += effect.getAmount();
			break;
		case SPEED:
			movementSpeed += Math.round(effect.getAmount());
			break;
		case STRENGTH:
			strength += effect.getAmount();
			break;
		case AGILITY:
			agility += effect.getAmount();
			break;
		case INTELLIGENCE:
			intelligence += effect.getAmount();
			break;
		default:
			effect.update();
			if(effect.getTimeLeft() == 0){
				activeEffects.remove(effect);
			}
			break;
		}
	}
	
	public void addEffect(Effect e){
		activeEffects.add(e);
	}
	
	public void affectHealth(int damage){
		health += damage;
	}	
	
	// Getters and Setters
	public void setHealth(int i){
		health = i;
	}
	
	public void setWeapon(Weapon w){
		weapon = w;
	}
	
	public void incrementCombo(){
		combo++;
	}
	
	public void decrementCombo(){
		combo--;
	}
	
	public void setCombo(int i){
		combo = i;
	}
	
	public int getDamage(){
		return weapon.getDamage(combo)+strength;
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
	
	public float getMovementSpeed(){
		return movementSpeed;
	}
	
	
	public int getStrenght(){
		return strength;
	}
		
	public int getAgility(){
		return agility;
	}
	
	public int getIntelligence(){
		return intelligence;
	}
	
	public int getCombo(){
		return combo;
	}
	
	public boolean isOffGuard(){
		return offGuard;
	}
}

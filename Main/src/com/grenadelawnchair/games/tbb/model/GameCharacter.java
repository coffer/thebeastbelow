package com.grenadelawnchair.games.tbb.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class GameCharacter {

	// Character Stats
	private String name;
	private int health;
	private float movementSpeed;
	private int strength;
	private int agility;
	private int intelligence;
	private int combo;
	
	// Character Inventory and Abilities
	private Weapon weapon;
	private List<Ability> abilities;
	private List<Effect> activeEffects; 
	private boolean parrying;
	
	private boolean offGuard;

	
	public GameCharacter(String name){
		abilities = new ArrayList<Ability>(3);
		try {
			Element root = new XmlReader().parse(Gdx.files.internal("data/characters.xml"));
			this.name = root.getChildByName(name).get("name");
			health = (int) root.getChildByName(name).getFloat("health");
			movementSpeed = root.getChildByName(name).getFloat("movementSpeed");
			strength = (int) root.getChildByName(name).getFloat("strength");
			agility = (int) root.getChildByName(name).getFloat("agility");
			intelligence = (int) root.getChildByName(name).getFloat("intelligence");
			weapon = new Weapon(root.getChildByName(name).get("weapon"));
			abilities.add(new Ability(root.getChildByName(name).getChildByName("abilities").get("first")));
			abilities.add(new Ability(root.getChildByName(name).getChildByName("abilities").get("second")));
			abilities.add(new Ability(root.getChildByName(name).getChildByName("abilities").get("third")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Debug
//		System.out.println(this.name);
//		System.out.println(health);
//		System.out.println(movementSpeed);
//		System.out.println(strength);
//		System.out.println(agility);
//		System.out.println(intelligence);
//		System.out.println(weapon.getName());
//		System.out.println("First ability: " + abilities.get(0));
//		System.out.println("Second ability: " + abilities.get(1));
//		System.out.println("Third ability: " + abilities.get(2));
		
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
			setMovementSpeed(getMovementSpeed() + Math.round(effect.getAmount()));
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
		health -= damage;
	}	
	
	// Getters and Setters
	public void setMovementSpeed(float movementSpeed) {
		this.movementSpeed = movementSpeed;
	}
	
	public void setHealth(int i){
		health = i;
	}
	
	public void setWeapon(Weapon w){
		weapon = w;
	}
	
	public int getDamage(){
		return weapon.getDamage(0)+strength;
	}
	
	public List<Ability> getAbilities(){
		return abilities;
	}
	
	public Weapon getWeapon(){
		return weapon;
	}
	
	public String getName(){
		return name;
	}
	
	public int getHealth(){
		return health;
	}
	
	public float getMovementSpeed(){
		return movementSpeed;
	}
	
	public int getCombo(){
		return combo;
	}
	
	public void incrementCombo(){
		if(combo < 3){
			combo++;
		}
	}
	
	public void decrementCombo(){
		if(combo > 1){
			combo--;
		}
	}
	
	public void setCombo(int i){
		combo = i;
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
	
	public boolean isOffGuard(){
		return offGuard;
	}

	public void setParry(boolean state){
		parrying = state;
	}
	
	public boolean isParrying(){
		return parrying;
	}
}

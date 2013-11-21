package com.grenadelawnchair.games.tbb.model;

/**
 * Singleton class for handling all types of combat
 */
public class CombatManager {

	private static CombatManager cM = null;
	
	private CombatManager(){
	}
	
	/**
	 * A normal strike with a weapon.
	 * @param attacker The attacking Character
	 * @param defender The defending Character
	 */
	public static void strike(GameCharacter attacker, GameCharacter defender){
		defender.affectHealth(attacker.getDamage());
		attacker.incrementCombo();
		System.out.println("Health: " + defender.getHealth());
	}
	
	public static CombatManager getInstance(){
		if(cM == null){
			cM = new CombatManager();
		}
		return cM;
	}
}

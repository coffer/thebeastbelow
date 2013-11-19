package com.grenadelawnchair.games.tbb.model;

import java.util.Random;

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
	 * @return The amount of damage inflicted on the defender
	 */
	public static double strike(Character attacker, Character defender){
		Random rand = new Random();
		// Defender manages to parry
		if(rand.nextDouble() <= (defender.getPryMultiplyer()*defender.getWeapon().getParry())){
			return 0;
		}
		// The defender manages to evade
		if(rand.nextDouble() <= defender.getEvasion()){
			return 0;
		}
		
		return Math.round(attacker.getAtkMultiplyer()*attacker.getWeapon().getDamage(1));
	}
	
	public static CombatManager getInstance(){
		if(cM == null){
			cM = new CombatManager();
		}
		return cM;
	}
}

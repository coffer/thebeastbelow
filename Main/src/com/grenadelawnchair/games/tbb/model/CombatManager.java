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
	 */
	public static void strike(GameCharacter attacker, GameCharacter defender){
		Random rand = new Random();
		if(!(defender.isParrying()) && rand.nextDouble() > defender.getWeapon().getParry()){
			defender.affectHealth(attacker.getDamage());
			attacker.incrementCombo();
			System.out.println(defender.getHealth());
		}else{
			System.out.println("Parry!");
			attacker.setCombo(0);
		}
	}
	
	public static CombatManager getInstance(){
		if(cM == null){
			cM = new CombatManager();
		}
		return cM;
	}
}

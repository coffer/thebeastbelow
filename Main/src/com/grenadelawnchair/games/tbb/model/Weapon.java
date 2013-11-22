package com.grenadelawnchair.games.tbb.model;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.grenadelawnchair.com.games.tbb.utils.XmlRoot;

public class Weapon extends Item {
	
	// Index 0 is base, the following are combos (double, triple, quad etc.)
	private int[] damage;
	// 0..1
	private double atkSpeed;
	// 0 = can't parry, 1 = excellent parrying 
	private double parry;
	
	private Effect effect;
	
	private double range;
	
	public Weapon(String name){
		super(name, "data/weapons.xml");
		damage = new int[5];
		try {
			Element root = XmlRoot.getInstance().parse(Gdx.files.internal("data/weapons.xml"));
			damage[0] = (int) root.getChildByName(name).getChildByName("damage").getFloat("first");
			damage[1] = (int) root.getChildByName(name).getChildByName("damage").getFloat("second");
			damage[2] = (int) root.getChildByName(name).getChildByName("damage").getFloat("third");
			damage[3] = (int) root.getChildByName(name).getChildByName("damage").getFloat("forth");
			damage[4] = (int) root.getChildByName(name).getChildByName("damage").getFloat("fifth");
			atkSpeed = root.getChildByName(name).getFloat("attackSpeed");
			effect = new Effect(root.getChildByName(name).get("effect"));
			range = root.getChildByName(name).getFloat("range");
		} catch (IOException e) {
			e.printStackTrace();
		}
//		// Debug
//		System.out.println(getName());
//		System.out.println(damage[0]);
//		System.out.println(damage[1]);
//		System.out.println(damage[2]);
//		System.out.println(damage[3]);
//		System.out.println(damage[4]);
//		System.out.println(atkSpeed);
//		System.out.println(effect.getName());
//		System.out.println(range);
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

	public double getRange(){
		return range;
	}
}

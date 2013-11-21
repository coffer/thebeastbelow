package com.grenadelawnchair.games.tbb.model;

import java.util.ArrayList;
import java.util.List;

public class Player extends GameCharacter {

	private List<Item> inventory;

	
	public Player(String name) {
		super(name);
		setMovementSpeed(200f);
		setWeapon(new Weapon("Malarn"));
		inventory = new ArrayList<Item>();
	}
	
	public void addItem(Item i){
		inventory.add(i);
	}
	
	public void removeItem(Item i){
		if(inventory.contains(i)){
			inventory.remove(i);
		}else{
			throw new IllegalArgumentException("Item is not in inventory");
		}
	}
	
	@Override
	public int getDamage(){
		return getWeapon().getDamage(getCombo())+getStrenght();
	}

	
}

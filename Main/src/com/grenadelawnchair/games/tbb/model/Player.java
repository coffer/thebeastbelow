package com.grenadelawnchair.games.tbb.model;

import java.util.ArrayList;
import java.util.List;

public class Player extends Character {

	private List<Item> inventory;
	
	public Player(String name) {
		super(name);
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


}

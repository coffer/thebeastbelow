package com.grenadelawnchair.games.tbb.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class Player extends GameCharacter {

	private List<Item> inventory;

	
	public Player(String name) {
		super(name);
		inventory = new ArrayList<Item>(3);
		
		try {
			Element root = new XmlReader().parse(Gdx.files.internal("data/characters.xml"));
			inventory.add(new Item(root.getChildByName(name).getChildByName("inventory").get("first"), "data/items.xml"));
			inventory.add(new Item(root.getChildByName(name).getChildByName("inventory").get("second"), "data/items.xml"));
			inventory.add(new Item(root.getChildByName(name).getChildByName("inventory").get("third"), "data/items.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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

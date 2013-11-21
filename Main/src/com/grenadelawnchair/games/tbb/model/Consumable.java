package com.grenadelawnchair.games.tbb.model;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class Consumable extends Item {

	Effect effect;
	
	public Consumable(String name) {
		super(name, "data/items.xml");
		try {
			Element root = new XmlReader().parse(Gdx.files.internal("data/items.xml"));
			effect = new Effect(root.getChildByName(name).get("effect"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Effect getEffect(){
		return effect;
	}
	

}

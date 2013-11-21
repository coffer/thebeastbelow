package com.grenadelawnchair.games.tbb.model;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

/**
 * General Item class for items used by the player
 */
public class Item {

	private String name;
	
	public Item(String name, String filepath){
		if(!name.equals("none")){
			try {
				Element root = new XmlReader().parse(Gdx.files.internal(filepath));
				this.name = root.getChildByName(name).get("name");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}
}

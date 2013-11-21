package com.grenadelawnchair.games.tbb.model;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class Ability {

	private Effect effect;
	private String name;
	private String desc;
	private boolean protective;
	private double cooldown;
	private boolean ready;
	
	public Ability(String name){
		if(!name.equals("none")){
			try {
				Element root = new XmlReader().parse(Gdx.files.internal("data/abilities.xml"));
				this.name = root.getChildByName(name).get("name");
				desc = root.getChildByName(name).get("describtion");
				if(root.getChildByName(name).get("protective").equals("true")){
					protective = true;
				}
				cooldown = root.getChildByName(name).getFloat("cooldown");
			} catch (IOException e) {
				e.printStackTrace();
			}
			ready = true;
		}
	}
	
	public Effect getEffect() {
		return effect;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return desc;
	}

	public boolean isProtective() {
		return protective;
	}
	
	public double getCooldown(){
		return cooldown;
	}
	
	public boolean isReady(){
		return ready;
	}
	
	public void setReady(boolean b){
		ready = b;
	}

}

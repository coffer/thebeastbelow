package com.grenadelawnchair.games.tbb.model;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class Effect {
	public enum Affecting {HEALTH, SPEED, STRENGTH, AGILITY, INTELLIGENCE}
	
	private boolean passive;	
	private String name;
	private String desc;
	private int timeLeft;
	private Affecting type;
	private double amount;
	
	public Effect(String name){
		if(!name.equals("none")){
			try {
				Element root = new XmlReader().parse(Gdx.files.internal("data/characters.xml"));
				this.name = root.getChildByName(name).get("name");
				desc = root.getChildByName(name).get("description");
				timeLeft = (int) root.getChildByName(name).getFloat("duration");
				
				String typeString = root.getChildByName(name).get("affecting");
				switch(typeString){
				case "health":
					type = Affecting.HEALTH;
					break;
				case "agility":
					type = Affecting.AGILITY;
					break;
				case "intelligence":
					type = Affecting.INTELLIGENCE;
					break;
				case "strength":
					type = Affecting.STRENGTH;
					break;
				case "speed":
					type = Affecting.SPEED;
				}
				amount = root.getChildByName(name).getFloat("amount");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void update(){
		if(!isPassive()){
			timeLeft--;
		}
	}
	
	public double getAmount(){
		return amount;
	}
	
	public Affecting getAffecting(){
		return type;
	}

	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return desc;
	}
	
	public int getTimeLeft(){
		return timeLeft;
	}
	
	public boolean isPassive(){
		return passive;
	}
}

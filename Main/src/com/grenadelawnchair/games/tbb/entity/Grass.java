package com.grenadelawnchair.games.tbb.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Grass {

	private TextureAtlas atlas;
	private Sprite grassSprite;
	
	public Grass(float xPos, float yPos){

		atlas = new TextureAtlas("graphics/world/grass.pack");
		 
	    grassSprite = atlas.createSprite("grass");
	    grassSprite.setSize(10, .5f);
	    grassSprite.setPosition(xPos, yPos);
	}
	

	
	public Sprite getSprite(){
		return grassSprite;
	}
}

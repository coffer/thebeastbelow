package com.grenadelawnchair.games.tbb.entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.grenadelawnchair.com.games.tbb.utils.Direction;
import com.grenadelawnchair.games.tbb.model.GameCharacter;

public interface Entity {

	public void update();
	
	public Body getBody();
	
	public Direction getDirection();
	
	public GameCharacter getGameCharacter();
}

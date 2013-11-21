package com.grenadelawnchair.games.tbb.entity;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.grenadelawnchair.com.games.tbb.utils.Direction;
import com.grenadelawnchair.games.tbb.model.GameCharacter;

public class NPCEntity implements Entity{

	private Body body;
	private float movementSpeed;
	private final float MAX_SPEED = 9f;
	private final float JUMPING_FORCE = 4000f;
	private Vector2 velocity = new Vector2(0, 0);
	private Vector2 currentVelocity = new Vector2(0, 0);
	private Direction direction;
	private GameCharacter npc;
	
	public NPCEntity(World world, FixtureDef fixDef, float xPos, float yPos){
		npc = new GameCharacter("Creep");
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(xPos, yPos);
		
		fixDef.density = 5;
		fixDef.friction = 1;
		fixDef.restitution = .2f;
		
		// Body
		PolygonShape bodyShape = new PolygonShape();
		bodyShape.setAsBox(.5f, 1);
		fixDef.shape = bodyShape;
		body = world.createBody(bodyDef);
		body.createFixture(fixDef);
		body.setFixedRotation(true);
	}
	
	public Body getBody(){
		return body;
	}
	
	public Direction getDirection(){
		return direction;
	}
	
	public GameCharacter getGameCharacter(){
		return npc;
	}
}

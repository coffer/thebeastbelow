package com.grenadelawnchair.games.tbb.entity;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class PlayerEntity extends InputAdapter {

	private Body body;
	private float movementSpeed = 200f;
	private final float JUMPING_FORCE = 4000f;
	private Vector2 velocity = new Vector2(0, 0);
	private Vector2 currentVelocity = new Vector2(0, 0);
	
	public PlayerEntity(World world, FixtureDef fixDef, float xPos, float yPos){
//		Player p = new Player("Player");
//		movementSpeed = p.getMovementSpeed();
		
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
	
	public void update(){
		currentVelocity = body.getLinearVelocity();
		body.applyForceToCenter(velocity, true);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode){
		case Keys.UP:
			if(currentVelocity.y == 0){
				body.applyForce(0, JUMPING_FORCE, 0, 0, true);
			}
			break;
		case Keys.LEFT:
			velocity.x = -movementSpeed;
			break;
		case Keys.RIGHT:
			velocity.x = movementSpeed;
			break;
		case Keys.SPACE:
			// TODO
			
		}
		return true;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		switch(keycode){
		case Keys.UP:
//			velocity.y = 0;
			break;
		case Keys.LEFT:
		case Keys.RIGHT:
			velocity.x = 0;
			break;
		case Keys.SPACE:
			
		}
		return true;
	}
	
	public Body getBody(){
		return body;
	}
}

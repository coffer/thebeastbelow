package com.grenadelawnchair.games.tbb.entity;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.grenadelawnchair.com.games.tbb.utils.Direction;
import com.grenadelawnchair.games.tbb.model.GameCharacter;
import com.grenadelawnchair.games.tbb.model.Player;

public class PlayerEntity extends InputAdapter implements Entity {

	private Body body;
	private float movementSpeed;
	private final float MAX_SPEED = 10f;
	private final float JUMPING_FORCE = 4000f;
	private Vector2 velocity = new Vector2(0, 0);
	private Vector2 currentVelocity = new Vector2(0, 0);
	private Direction direction;
	private Player player;
	private float time;
	private boolean attackOnCooldown;
	
	/**
	 * Constructor for the Player Entity
	 */
	public PlayerEntity(World world, FixtureDef fixDef, float xPos, float yPos){
		player = new Player("Player");
		movementSpeed = player.getMovementSpeed();
		
		direction = Direction.RIGHT;
		
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
	
	@Override
	public void update(){
		getGameCharacter().update();
		
		currentVelocity = body.getLinearVelocity();
		if(currentVelocity.x < MAX_SPEED && currentVelocity.x > -MAX_SPEED)
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
			direction = Direction.LEFT;
			velocity.x = -movementSpeed;
			break;
		case Keys.RIGHT:
			direction = Direction.RIGHT;
			velocity.x = movementSpeed;
			break;
		case Keys.ALT_LEFT:
			getGameCharacter().setParry(true);
		}
		return true;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		switch(keycode){
		case Keys.LEFT:
		case Keys.RIGHT:
			velocity.x = 0;
			break;
		case Keys.ALT_LEFT:
			getGameCharacter().setParry(false);
		}
		return true;
	}
	
	@Override
	public Body getBody(){
		return body;
	}
	
	@Override
	public Direction getDirection(){
		return direction;
	}
	
	@Override
	public GameCharacter getGameCharacter(){
		return player;
	}

	private void handleAttackCooldown(){
		if(attackOnCooldown){
			time += Gdx.app.getGraphics().getDeltaTime();
			if(time >= getGameCharacter().getWeapon().getAtkSpeed()){
			    attackOnCooldown = false;
			    time = 0; //reset
			}
		}
	}
	
	public void setAttackOnCooldown(boolean b){
		attackOnCooldown = b;
	}
	
	public boolean isAttackOnCooldown(){
		return attackOnCooldown;
	}
}

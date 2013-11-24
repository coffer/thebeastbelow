package com.grenadelawnchair.games.tbb.entity;

import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	private Sprite[] sprites;
	
	private AnimatedSprite strikeRight, strikeLeft, runRight, runLeft, idleRight, idleLeft;
	private boolean striking;
	private float animationCooldown;
	
	/**
	 * Constructor for the Player Entity
	 */
	public PlayerEntity(World world, FixtureDef fixDef, float xPos, float yPos){
		player = new Player("Player");
		movementSpeed = player.getMovementSpeed();
		direction = Direction.RIGHT;
		
		sprites = new Sprite[8];
		initializeSprites();
		
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
		body.setUserData(sprites[0]);
	}
	
	@Override
	public void update(){
		getGameCharacter().update();
		handleAttackCooldown();
		
		currentVelocity = body.getLinearVelocity();
		if(currentVelocity.x < MAX_SPEED && currentVelocity.x > -MAX_SPEED)
			body.applyForceToCenter(velocity, true);
		
		playAnimation();
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

	private void initializeSprites(){
		sprites[0] = new Sprite(new Texture(Gdx.files.internal("graphics/charactersprites/player/right/idle.png")));
		sprites[1] = new Sprite(new Texture(Gdx.files.internal("graphics/charactersprites/player/right/idlechop.png")));
		sprites[2] = new Sprite(new Texture(Gdx.files.internal("graphics/charactersprites/player/right/run1.png")));
		sprites[3] = new Sprite(new Texture(Gdx.files.internal("graphics/charactersprites/player/right/run2.png")));
		sprites[4] = new Sprite(new Texture(Gdx.files.internal("graphics/charactersprites/player/left/idle.png")));
		sprites[5] = new Sprite(new Texture(Gdx.files.internal("graphics/charactersprites/player/left/idlechop.png")));
		sprites[6] = new Sprite(new Texture(Gdx.files.internal("graphics/charactersprites/player/left/run1.png")));
		sprites[7] = new Sprite(new Texture(Gdx.files.internal("graphics/charactersprites/player/left/run2.png")));
		for(int i = 0; i < sprites.length; i++){
			sprites[i].setSize(2, 2); // Size of body
		}
		
		// ANIMATIONS
		// Strike right
		Animation animation = new Animation(1 / 4f, new TextureRegion(sprites[1]), new TextureRegion(sprites[0]));
		animation.setPlayMode(Animation.LOOP);
		strikeRight = new AnimatedSprite(animation, true);
		strikeRight.setSize(2, 2);
		
		// Strike left
		animation = new Animation(1 / 4f, new TextureRegion(sprites[5]), new TextureRegion(sprites[4]));
		animation.setPlayMode(Animation.LOOP);
		strikeLeft = new AnimatedSprite(animation, true);
		strikeLeft.setSize(2, 2);
	
		// Run right
		animation = new Animation(1 / 4f, new TextureRegion(sprites[2]), new TextureRegion(sprites[3]));
		animation.setPlayMode(Animation.LOOP);
		runRight = new AnimatedSprite(animation, true);
		runRight.setSize(2, 2);
		
		// Run left
		animation = new Animation(1 / 4f, new TextureRegion(sprites[6]), new TextureRegion(sprites[7]));
		animation.setPlayMode(Animation.LOOP);
		runLeft = new AnimatedSprite(animation, true);
		runLeft.setSize(2, 2);
		
		// Idle right
		animation = new Animation(1, new TextureRegion(sprites[0]));
		animation.setPlayMode(Animation.LOOP);
		idleRight = new AnimatedSprite(animation, true);
		idleRight.setSize(2, 2);
		
		// Idle left
		animation = new Animation(1, new TextureRegion(sprites[4]));
		animation.setPlayMode(Animation.LOOP);
		idleLeft = new AnimatedSprite(animation, true);
		idleLeft.setSize(2, 2);
	}

	@Override
	public void dispose(){
		for(int i = 0; i < sprites.length; i++){
			sprites[0].getTexture().dispose();
		}
	}

	private void playAnimation(){
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && !isAttackOnCooldown()){
			striking = true;
			setAttackOnCooldown(true);
		}
		// Set the timer for the attack animation
		if(striking){
			animationCooldown += Gdx.app.getGraphics().getDeltaTime();
			if(animationCooldown >= 1/2f){
			    striking = false;
			    animationCooldown = 0; //reset
			}
		}
		
		if(striking){
			if(direction == Direction.LEFT){
				body.setUserData(strikeLeft);
			}else{
				body.setUserData(strikeRight);
			}
		}else{
			if(body.getLinearVelocity().x != 0){
				if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
					body.setUserData(runLeft);
				}
				else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
					body.setUserData(runRight);
				}
			}else{
				if(direction == Direction.LEFT){
					body.setUserData(idleLeft);
				}else{
					body.setUserData(idleRight);
				}
			}
		}
	}
}

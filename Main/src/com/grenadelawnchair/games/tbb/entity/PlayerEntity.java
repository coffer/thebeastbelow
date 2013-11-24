package com.grenadelawnchair.games.tbb.entity;

import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;

import com.badlogic.gdx.Gdx;
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
	private int currentSprite = 0;
	
	private AnimatedSprite strikeRight, strikeLeft, runRight, runLeft;
	private float animationStartTime;
	
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
		
		body.setUserData(sprites[currentSprite]);
		
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
			if(direction != Direction.LEFT){
				currentSprite = (currentSprite + 4) % 8;
				direction = Direction.LEFT;
			}
			velocity.x = -movementSpeed;
			break;
		case Keys.RIGHT:
			if(direction != Direction.RIGHT){
				currentSprite = (currentSprite + 4) % 8;
				direction = Direction.RIGHT;
			}
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
		
		// Animations
		Animation animation = new Animation(1 / 3f, new TextureRegion(sprites[0]), new TextureRegion(sprites[1]), new TextureRegion(sprites[0]));
		animation.setPlayMode(Animation.NORMAL);
		strikeRight = new AnimatedSprite(animation, true);
		strikeRight.setSize(2, 2);
		
		animation = new Animation(1 / 3f, new TextureRegion(sprites[4]), new TextureRegion(sprites[5]), new TextureRegion(sprites[4]));
		animation.setPlayMode(Animation.NORMAL);
		strikeLeft = new AnimatedSprite(animation, true);
		strikeLeft.setSize(2, 2);
		
		animation = new Animation(1 / 20f, new TextureRegion(sprites[2]), new TextureRegion(sprites[3]));
		animation.setPlayMode(Animation.LOOP);
		runRight = new AnimatedSprite(animation, true);
		runRight.setSize(2, 2);
		
		animation = new Animation(1 / 20f, new TextureRegion(sprites[6]), new TextureRegion(sprites[7]));
		animation.setPlayMode(Animation.NORMAL);
		runLeft = new AnimatedSprite(animation, true);
		runLeft.setSize(2, 2);
	}

	@Override
	public void setSprite(int spriteindex){
		body.setUserData(sprites[spriteindex]);
		currentSprite = spriteindex;
	}
	
	@Override
	public void dispose(){
		for(int i = 0; i < sprites.length; i++){
			sprites[0].getTexture().dispose();
		}
	}

	public void playStrikeAnimation(){
		animationStartTime = Gdx.graphics.getDeltaTime();
		if(direction == Direction.LEFT){
			body.setUserData(strikeLeft);
		}else{
			body.setUserData(strikeRight);
		}
	}
	
	private boolean isAnimating(){
		return (strikeLeft.getAnimation().isAnimationFinished(animationStartTime) && strikeRight.getAnimation().isAnimationFinished(animationStartTime));
	}
}

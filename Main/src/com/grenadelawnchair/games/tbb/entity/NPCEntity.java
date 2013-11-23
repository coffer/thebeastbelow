package com.grenadelawnchair.games.tbb.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.grenadelawnchair.com.games.tbb.utils.Direction;
import com.grenadelawnchair.games.tbb.model.CombatManager;
import com.grenadelawnchair.games.tbb.model.GameCharacter;

public class NPCEntity implements Entity{

	private Body body;
	private float movementSpeed;
	private final float MAX_SPEED = 9f;
	private Vector2 velocity = new Vector2(0, 0);
	private Vector2 currentVelocity = new Vector2(0, 0);
	private Direction direction;
	private GameCharacter npc;
	private Entity target;
	private boolean agressive = true;
	private float aggroDistance;
	private boolean chase;
	private boolean attackOnCooldown;
	private float time;
	
	private boolean patroling;
	private float patrolDistance;
	private float originX;
	
	public NPCEntity(String name, boolean aggressive, World world, FixtureDef fixDef, float xPos, float yPos){
		npc = new GameCharacter(name);
		originX = xPos;
		this.agressive = aggressive;
		aggroDistance = 7f;
		
		direction = Direction.LEFT;
		movementSpeed = npc.getMovementSpeed();
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
		
		handleAttackCooldown();
		
		if(patroling){
			patrol();
		}
		if(agressive && target != null && (target.getBody().getPosition().dst2(getBody().getPosition()) <= aggroDistance)){
			stopPatrolling();
			chase = true;
		}
		if(chase){
			aggrivate();
		}
		currentVelocity = body.getLinearVelocity();
		if(currentVelocity.x < MAX_SPEED && currentVelocity.x > -MAX_SPEED)
			body.applyForceToCenter(velocity, true);
	}
	
	/**
	 * The NPC will start chasing the given entity and try to kill it
	 */
	public void aggrivate(){
		// NPC will not chase if the target is on another floor
		if(!(target.getBody().getPosition().y > getBody().getPosition().y + 2
				&& target.getBody().getPosition().y < getBody().getPosition().y - 2)){
			
			// Find the direction to the target and run towards it
			if(target.getBody().getPosition().x < getBody().getPosition().x){
				direction = Direction.LEFT;
				if(getBody().getPosition().dst2(target.getBody().getPosition()) >= getGameCharacter().getWeapon().getRange()){
					velocity.x = -movementSpeed;
				}else{
					velocity.x = 0;
				}
			}else{
				direction = Direction.RIGHT;
				if(getBody().getPosition().dst2(target.getBody().getPosition()) >= getGameCharacter().getWeapon().getRange()){
					velocity.x = movementSpeed;
				}else{
					velocity.x = 0;
				}
			}
			// Strike the target if in range
			if((getBody().getPosition().dst2(target.getBody().getPosition()) <= getGameCharacter().getWeapon().getRange())){
				if(!attackOnCooldown){
					CombatManager.strike(getGameCharacter(), target.getGameCharacter());
					attackOnCooldown = true;
				}
			}
		}
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
		return npc;
	}

	public void patrol(){
		// NPC is heading left
		if(direction == Direction.LEFT){
			if(getBody().getPosition().x >= originX - patrolDistance){
				velocity.x = -movementSpeed;
			}else{
				direction = Direction.RIGHT;
				velocity.x = movementSpeed;
			}
		}
		// NPC is heading right
		if(getBody().getPosition().x >= (originX + patrolDistance)){
			direction = Direction.LEFT;
			velocity.x = -movementSpeed;
		}
	}
	
	public void setPatrol(float distance){
		patroling = true;
		patrolDistance = distance;
	}
	
	public void stopPatrolling(){
		patroling = false;
	}
	
	public boolean isPatroling(){
		return patroling;
	}

	public Entity getTarget(){
		return target;
	}
	
	public void setTarget(Entity target){
		this.target = target;
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
}

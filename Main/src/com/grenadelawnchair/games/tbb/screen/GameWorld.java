package com.grenadelawnchair.games.tbb.screen;

import java.util.ArrayList;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Sine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.grenadelawnchair.com.games.tbb.utils.Constants;
import com.grenadelawnchair.com.games.tbb.utils.Direction;
import com.grenadelawnchair.com.games.tbb.utils.GrassSpriteAccessor;
import com.grenadelawnchair.games.tbb.entity.Entity;
import com.grenadelawnchair.games.tbb.entity.NPCEntity;
import com.grenadelawnchair.games.tbb.entity.PlayerEntity;
import com.grenadelawnchair.games.tbb.model.CombatManager;

public class GameWorld implements Screen{

	private World world;
	private Box2DDebugRenderer debugRenderer;
	private float gravity = -9.81f;
	private OrthographicCamera camera;
	private final int ZOOM = 100;
	
	private final float TIMESTEP = 1 / 60f;
	private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3; // Can be set to higher if higher quality is desired
	
	private SpriteBatch batch;
	
	private PlayerEntity player;
	private ArrayList<NPCEntity> npcList;
	
	private Array<Body> tmpBodies = new Array<Body>();
	
	// GRASS
	private TweenManager tweenManager;
	private TextureAtlas atlas;
	private Sprite grassSprite1;
	private Sprite grassSprite2;
	private Sprite grassSprite3;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		
		player.update();
		
		// Check if any NPC's are dead and update those who ain't
		for(NPCEntity npc : npcList){
			npc.update();
		}
		
		camera.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);
		camera.update();
		
		// Draw spites
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		world.getBodies(tmpBodies);
		for(Body body : tmpBodies){
			if(body.getUserData() != null && body.getUserData() instanceof Sprite){
				Sprite sprite = (Sprite) body.getUserData();
				sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2); // Draw sprite in bottom left
				sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
				sprite.draw(batch);
			}
		}

		grassSprite1.draw(batch);
		grassSprite2.draw(batch);
		grassSprite3.draw(batch);
		
		batch.end();
		
		debugRenderer.render(world, camera.combined);
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width / ZOOM;
		camera.viewportHeight = height / ZOOM;
		camera.update();
		
	}

	@Override
	public void show() {
		npcList = new ArrayList<NPCEntity>();
		
		tweenManager = new TweenManager();
		
		world  = new World(new Vector2(0, gravity), true);
		debugRenderer = new Box2DDebugRenderer();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();

		// Setup World Objects
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixDef = new FixtureDef();
		
		player = new PlayerEntity(world, fixDef, 0, 3);
		
		// Input handling
		Gdx.input.setInputProcessor(new InputMultiplexer(new InputAdapter() {
				@Override
				public boolean keyDown(int keycode) {
					switch(keycode) {
					case Keys.ESCAPE:
						((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
						break;
					case Keys.SPACE:
						if(!(player.isAttackOnCooldown() || player.getGameCharacter().isParrying())){
							for(NPCEntity npc : npcList){
								if(validHit(player, npc) && npc.getBody().isActive()){
									CombatManager.strike(player.getGameCharacter(), npc.getGameCharacter());
								}
							}
						}
					}
					return false;
				}
				@Override
				public boolean scrolled(int amount) {
					camera.zoom += amount /25f;
					return true;
				}
		}, player));
		
		NPCEntity npcEntity1 = new NPCEntity(Constants.Characters.Creep.toString(), true, world, fixDef, 10, 3);
		NPCEntity npcEntity2 = new NPCEntity(Constants.Characters.Creep.toString(), true, world, fixDef, -20, 3);
		npcList.add(npcEntity1);
		npcList.add(npcEntity2);
		
		npcEntity2.setPatrol(5f);
		npcEntity2.setTarget(player);

		// GROUND
		// Body definition
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(0, 0);
		
		// Ground shape
		ChainShape groundShape = new ChainShape();
		groundShape.createChain(new Vector2[] {new Vector2(-100, 0), new Vector2(100, 0)});
		
		// Fixture definition
		fixDef.shape = groundShape;
		fixDef.friction = 1;
		fixDef.restitution = 0;
		
		world.createBody(bodyDef).createFixture(fixDef);
		
		// GRASS
		atlas = new TextureAtlas("graphics/world/grass.pack");
		
		grassSprite1 = atlas.createSprite("grass1");
		grassSprite1.setSize(5, .4f);
		grassSprite1.setPosition(0,0);
		
		grassSprite2 = atlas.createSprite("grass2");
		grassSprite2.setSize(5, .4f);
		grassSprite2.setPosition(0, 0);
		
		grassSprite3 = atlas.createSprite("grass3");
		grassSprite3.setSize(5, .4f);
		grassSprite3.setPosition(0, 0);
		
		Tween.registerAccessor(Sprite.class, new GrassSpriteAccessor());
		Tween.call(windCallback).start(tweenManager);
		//---------- GRASS
		
		groundShape.dispose();
	}

	@Override
	public void hide() {
		dispose();
		
	}

	@Override
	public void pause() {
		// Nothing
		
	}

	@Override
	public void resume() {
		// Nothing
		
	}

	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
		player.dispose();
		for(NPCEntity e : npcList){
			e.dispose();
		}
	}

	private static boolean validHit(Entity attacker, Entity target){
		if(!inRange(attacker, target)){
			return false;
		}
		if(attacker.getDirection() == Direction.RIGHT && attacker.getBody().getPosition().x 
				< target.getBody().getPosition().x){
			return true;
		}
		else if(attacker.getDirection() == Direction.LEFT && attacker.getBody().getPosition().x 
				> target.getBody().getPosition().x){
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the attacker is in range of his subject
	 */
	public static boolean inRange(Entity attacker, Entity subject){
		if(attacker.getBody().getPosition().dst(subject.getBody().getPosition()) < attacker.getGameCharacter().getWeapon().getRange()){
			return true;
		}
		return false;
	}

	private final TweenCallback windCallback = new TweenCallback() {
		@Override
		public void onEvent(int type, BaseTween<?> source) {
			float d = MathUtils.random() * 0.5f + 0.5f;
			float t = -0.2f * grassSprite1.getHeight(); // Amount of wind
			
			Timeline.createParallel()
				.push(Tween.to(grassSprite1, GrassSpriteAccessor.SKEW_X2X3, d).target(t, t).ease(Sine.INOUT).repeatYoyo(1, 0).setCallback(windCallback))
				.push(Tween.to(grassSprite2, GrassSpriteAccessor.SKEW_X2X3, d).target(t, t).ease(Sine.INOUT).delay(d/3).repeatYoyo(1, 0))
				.push(Tween.to(grassSprite3, GrassSpriteAccessor.SKEW_X2X3, d).target(t, t).ease(Sine.INOUT).delay(d/3*2).repeatYoyo(1, 0))
				.start(tweenManager);
		}
	};
}

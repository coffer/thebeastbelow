package com.grenadelawnchair.games.tbb.screen;

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
import com.grenadelawnchair.games.tbb.entity.PlayerEntity;

public class GameWorld implements Screen{

	private World world;
	private Box2DDebugRenderer debugRenderer;
	private float gravity = -9.81f;
	private OrthographicCamera camera;
	private final int ZOOM = 25;
	
	private final float TIMESTEP = 1 / 60f;
	private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3; // Can be set to higher if higher quality is desired
	
	private SpriteBatch batch;
	
	private PlayerEntity player;
	
	private Array<Body> tmpBodies = new Array<Body>();
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		
		player.update(); 
		
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
					}
					return false;
				}
				@Override
				public boolean scrolled(int amount) {
					camera.zoom += amount /25f;
					return true;
				}
		}, player));
		
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
		
		groundShape.dispose();
	}

	@Override
	public void hide() {
		dispose();
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
		
	}

}

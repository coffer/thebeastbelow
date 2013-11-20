package com.grenadelawnchair.games.tbb.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.grenadelawnchair.games.tbb.controller.InputController;

public class GameWorld implements Screen{

	private World world;
	private Box2DDebugRenderer debugRenderer;
	private float gravity = -9.81f;
	private OrthographicCamera camera;
	private final int ZOOM = 25;
	
	private final float TIMESTEP = 1 / 60f;
	private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3; // Can be set to higher if higher quality is desired
	
	private Body box;
	private float speed = 300;
	private Vector2 movement = new Vector2(0, 0);
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		box.applyForceToCenter(movement, true);
		
		camera.position.set(box.getPosition().x, box.getPosition().y, 0);
		camera.update();
		
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
		
		camera = new OrthographicCamera();

		// Input handler // TODO move this
		Gdx.input.setInputProcessor(new InputController(){
			@Override
			public boolean keyDown(int keycode) {
				switch(keycode){
				case Keys.ESCAPE:
					((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
					break;
				case Keys.UP:
					movement.y = speed;
					break;
				case Keys.LEFT:
					movement.x = -speed;
					break;
				case Keys.RIGHT:
					movement.x = speed;
					break;
				case Keys.DOWN:
					//TODO duck					
				}
				return true;
			}
			@Override
			public boolean keyUp(int keycode) {
				switch(keycode){
				case Keys.UP:
					movement.y = 0;
					break;
				case Keys.LEFT:
				case Keys.RIGHT:
					movement.x = 0;
					break;
				case Keys.DOWN:
					//TODO stand					
				}
				return true;
			}
		});
		
		// BALL
		// Body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(0, 10);
		
		// Shape
		CircleShape ballShape = new CircleShape();
		ballShape.setRadius(.5f);
		
		// Fixture definition
		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = ballShape;
		fixDef.density = 2.5f; 	// 2.5 kg/m^3
		fixDef.friction = .25f;	// Scale 0..1 
		fixDef.restitution = .5f;	// Bounce, scale 0..1
		
		// Create body and attach the fixture
		world.createBody(bodyDef).createFixture(fixDef);
		
		ballShape.dispose();
		
		// GROUND
		// Body definition
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(0, 0);
		
		// Ground shape
		ChainShape groundShape = new ChainShape();
		groundShape.createChain(new Vector2[] {new Vector2(-50, 0), new Vector2(50, 0)});
		
		// Fixture definition
		fixDef.shape = groundShape;
		fixDef.friction = .5f;
		fixDef.restitution = 0;
		
		world.createBody(bodyDef).createFixture(fixDef);
		
		groundShape.dispose();
		
		// BOX
		// Body definition
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(2.25f, 10);
		
		// Box shape
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(.5f, 1);
		
		// Fixture definition
		fixDef.shape = boxShape;
		fixDef.friction = .75f;
		fixDef.restitution = .1f;
		fixDef.density = 5;
		
		box = world.createBody(bodyDef);
		box.createFixture(fixDef);
		
		boxShape.dispose();
		
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

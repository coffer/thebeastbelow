package com.grenadelawnchair.games.tbb.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class GameWorld implements Screen{

	private World world;
	private Box2DDebugRenderer debugRenderer;
	private float gravity = -9.81f;
	private OrthographicCamera camera;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		debugRenderer.render(world, camera.combined);		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		world  = new World(new Vector2(0, gravity), true);
		debugRenderer = new Box2DDebugRenderer();
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		// Body definition
		BodyDef ballDef = new BodyDef();
		ballDef.type = BodyType.DynamicBody;
		ballDef.position.set(0, 1);
		
		// Shape
		CircleShape shape = new CircleShape();
		shape.setRadius(.25f);
		
		// Fixture definition
		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = null;
		fixDef.density = 2.5f; 	// 2.5 kg/m^3
		fixDef.friction = .25f;	// Scale 0..1 
		fixDef.restitution = .5f;	// Bounce, scale 0..1
		
		
		world.createBody(ballDef).createFixture(fixDef);
		
		shape.dispose();
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

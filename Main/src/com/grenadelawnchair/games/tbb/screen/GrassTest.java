package com.grenadelawnchair.games.tbb.screen;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Sine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.grenadelawnchair.com.games.tbb.utils.GrassSpriteAccessor;

public class GrassTest implements Screen{
	private TweenManager tweenManager = new TweenManager();
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private TextureAtlas atlas;
	private Sprite grassSprite1;
	private Sprite grassSprite2;
	private Sprite grassSprite3;

	@Override
	public void render(float delta) {
		tweenManager.update(Gdx.graphics.getDeltaTime());
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		grassSprite3.draw(batch);
		grassSprite2.draw(batch);
		grassSprite1.draw(batch);
		batch.end();		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		float screenW = Gdx.graphics.getWidth();
		float screenH = Gdx.graphics.getHeight();
		float w = 1;
		float h = w * screenH / screenW;
		
		camera = new OrthographicCamera(w, h);
		batch = new SpriteBatch();
		
		atlas = new TextureAtlas("graphics/world/grass.pack");
		
		grassSprite1 = atlas.createSprite("grass1");
		grassSprite1.setSize(w, w * grassSprite1.getHeight() / grassSprite1.getWidth());
		grassSprite1.setPosition(-w/2, -h/2);
		
		grassSprite2 = atlas.createSprite("grass2");
		grassSprite2.setSize(w, w * grassSprite2.getHeight() / grassSprite2.getWidth());
		grassSprite2.setPosition(-w/2, -h/2);
		
		grassSprite3 = atlas.createSprite("grass3");
		grassSprite3.setSize(w, w * grassSprite3.getHeight() / grassSprite3.getWidth());
		grassSprite3.setPosition(-w/2, -h/2);
		
		Tween.registerAccessor(Sprite.class, new GrassSpriteAccessor());
		Tween.call(windCallback).start(tweenManager);
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	private final TweenCallback windCallback = new TweenCallback() {
		@Override
		public void onEvent(int type, BaseTween<?> source) {
			float d = MathUtils.random() * 0.5f + 0.5f;
			float t = -0.5f * grassSprite1.getHeight();
			
			Timeline.createParallel()
				.push(Tween.to(grassSprite1, GrassSpriteAccessor.SKEW_X2X3, d).target(t, t).ease(Sine.INOUT).repeatYoyo(1, 0).setCallback(windCallback))
				.push(Tween.to(grassSprite2, GrassSpriteAccessor.SKEW_X2X3, d).target(t, t).ease(Sine.INOUT).delay(d/3).repeatYoyo(1, 0))
				.push(Tween.to(grassSprite3, GrassSpriteAccessor.SKEW_X2X3, d).target(t, t).ease(Sine.INOUT).delay(d/3*2).repeatYoyo(1, 0))
				.start(tweenManager);
		}
	};
}

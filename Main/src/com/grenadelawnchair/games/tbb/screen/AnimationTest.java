package com.grenadelawnchair.games.tbb.screen;

import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationTest implements Screen {
	
	AnimatedSprite animatedSprite;
	SpriteBatch batch;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.begin();
		animatedSprite.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// Nothing
		
	}

	@Override
	public void show() {
		batch = new SpriteBatch();

		Animation animation = new Animation(1 / 3f, new TextureRegion(new Texture(Gdx.files.internal("graphics/charactersprites/player/idle.png"))), new TextureRegion(new Texture(Gdx.files.internal("graphics/charactersprites/player/idlechop.png"))), new TextureRegion(new Texture(Gdx.files.internal("graphics/charactersprites/player/idle.png"))));
		animation.setPlayMode(Animation.NORMAL);
		animatedSprite = new AnimatedSprite(animation);
	}

	@Override
	public void hide() {
		// Nothing
		
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
		batch.dispose();
		
	}

}

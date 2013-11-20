package com.grenadelawnchair.games.tbb.controller;

import com.badlogic.gdx.Game;
import com.grenadelawnchair.games.tbb.view.GameWorld;
import com.grenadelawnchair.games.tbb.view.SplashScreen;

public class GDXGame extends Game {

	@Override
	public void create() {
		// TODO set to splash
		setScreen(new GameWorld());
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

}

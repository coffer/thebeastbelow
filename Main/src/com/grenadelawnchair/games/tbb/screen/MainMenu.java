package com.grenadelawnchair.games.tbb.screen;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.grenadelawnchair.com.games.tbb.utils.ActorAccessor;
import com.grenadelawnchair.com.games.tbb.utils.Constants;

public class MainMenu implements Screen {

	// UI Elements
	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton exitButton, playButton, settingsButton;
	private BitmapFont white, black;
	private Label heading;
	private TweenManager tweenManager;
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
		tweenManager.update(delta);
		
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
		table.invalidateHierarchy();
		table.setSize(width, height);
	}

	@Override
	public void show() {
		//TODO menuSkin.json (Ep9)
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		atlas = new TextureAtlas("ui/button.pack");
		skin = new Skin(atlas);
		
		table = new Table(skin);
		table.setFillParent(true);
		
		//Creative Fonts
		white = new BitmapFont(Gdx.files.internal("fonts/arialwhite.fnt"), false);
		black = new BitmapFont(Gdx.files.internal("fonts/arialblack.fnt"), false);
		
		// Set texture behavior for the buttons
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button.up");
		textButtonStyle.down = skin.getDrawable("button.down");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = black;
		
		
		// Creating button and put into table
		playButton = new TextButton("Play", textButtonStyle);
		playButton.pad(15);
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new GameWorld());//TODO
			}
		});
		
		settingsButton = new TextButton("Settings", textButtonStyle);
		settingsButton.pad(15);
		settingsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//((Game) Gdx.app.getApplicationListener()).setScreen(new LevelScreen());
			}
		});
		
		exitButton = new TextButton("Exit", textButtonStyle);
		exitButton.pad(20);
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		
		// Creating Label
		LabelStyle headingStyle = new LabelStyle(white, Color.WHITE);
		heading = new Label(Constants.TITLE, headingStyle);
		heading.setFontScale(2);
		
		// Add to table
		table.add(heading);
		table.getCell(heading).spaceBottom(100);
		table.row();
		table.add(playButton);
		table.getCell(playButton).spaceBottom(10);
		table.row();
		table.add(settingsButton);
		table.getCell(settingsButton).spaceBottom(10);
		table.row();
		table.add(exitButton);
		stage.addActor(table);
		
		// Animations
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());
		
		// Heading and buttons fade in
		Timeline.createSequence().beginSequence()
			.push(Tween.set(playButton, ActorAccessor.ALPHA).target(0))
			.push(Tween.set(settingsButton, ActorAccessor.ALPHA).target(0))
			.push(Tween.set(exitButton, ActorAccessor.ALPHA).target(0))
			.push(Tween.from(heading, ActorAccessor.ALPHA, .5f).target(0))
			.push(Tween.to(playButton, ActorAccessor.ALPHA, .5f).target(1))
			.push(Tween.to(settingsButton, ActorAccessor.ALPHA, .5f).target(1))
			.push(Tween.to(exitButton, ActorAccessor.ALPHA, .5f).target(1))
			.end().start(tweenManager);
		
		// Table Fade in
		Tween.from(table, ActorAccessor.Y, .5f).target(0).start(tweenManager);
		Tween.from(table, ActorAccessor.Y, .5f).target(Gdx.graphics.getHeight() / 8).start(tweenManager);
	
		tweenManager.update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		atlas.dispose();
		white.dispose();
		black.dispose();
		skin.dispose();
	}

}

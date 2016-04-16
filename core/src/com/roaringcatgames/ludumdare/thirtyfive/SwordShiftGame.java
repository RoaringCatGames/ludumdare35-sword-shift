package com.roaringcatgames.ludumdare.thirtyfive;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.roaringcatgames.ludumdare.thirtyfive.screens.MenuScreen;

public class SwordShiftGame extends Game implements IGameProcessor {

	public InputMultiplexer multiplexer = new InputMultiplexer();
	public AssetManager am;

	private SpriteBatch batch;

	Screen menuScreen;
	Screen gameScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();
		menuScreen = new MenuScreen(this);

		//NOTE: We force finishLoading of the Loading Frames
		//  so we can count on it.
		am = Assets.load();
		am.finishLoading();
		setScreen(menuScreen);

		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void render () {
		float r = 29/255f;
		float g = 29/255f;
		float b = 27/255f;
		Gdx.gl.glClearColor(r, g, b, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


//		Screen nextScreen = screenDispatcher.getNextScreen();
//		if(nextScreen != getScreen()){
//			setScreen(nextScreen);
//		}

		super.render();
	}

	@Override
	public SpriteBatch getBatch() {
		return batch;
	}

	@Override
	public void switchScreens(String screenName) {
		setScreen(menuScreen);
	}

	@Override
	public void addInputProcessor(InputProcessor processor) {
		multiplexer.addProcessor(processor);
	}

	@Override
	public void removeInputProcessor(InputProcessor processor) {
 		multiplexer.removeProcessor(processor);
	}
}

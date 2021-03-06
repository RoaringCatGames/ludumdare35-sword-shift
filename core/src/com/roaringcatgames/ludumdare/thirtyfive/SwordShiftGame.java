package com.roaringcatgames.ludumdare.thirtyfive;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.roaringcatgames.ludumdare.thirtyfive.screens.GameScreen;
import com.roaringcatgames.ludumdare.thirtyfive.screens.LoadingScreen;
import com.roaringcatgames.ludumdare.thirtyfive.screens.MenuScreen;

public class SwordShiftGame extends Game implements IGameProcessor {

	public InputMultiplexer multiplexer = new InputMultiplexer();
	public AssetManager am;

	private SpriteBatch batch;
	private OrthographicCamera cam;
	private OrthographicCamera guiCam;

    Screen loadingScreen;
	Screen menuScreen;
	Screen gameScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();
		cam = new OrthographicCamera(App.W, App.H);
		guiCam = new OrthographicCamera(App.PixelW, App.PixelW);

        loadingScreen = new LoadingScreen(this);
		menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);

		//NOTE: We force finishLoading of the Loading Frames
		//  so we can count on it.
		am = Assets.load();

		setScreen(loadingScreen);

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
		switch(screenName){
			case "MENU":
				setScreen(menuScreen);
				break;
			case "GAME":
				setScreen(gameScreen);
				break;
		}

	}

	@Override
	public void addInputProcessor(InputProcessor processor) {
		multiplexer.addProcessor(processor);
	}

	@Override
	public void removeInputProcessor(InputProcessor processor) {
 		multiplexer.removeProcessor(processor);
	}

	@Override
	public OrthographicCamera getCamera() {
		return cam;
	}

	@Override
	public OrthographicCamera getGUICam() {
		return guiCam;
	}
}

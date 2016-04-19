package com.roaringcatgames.ludumdare.thirtyfive.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.roaringcatgames.kitten2d.ashley.components.*;
import com.roaringcatgames.kitten2d.ashley.systems.*;
import com.roaringcatgames.ludumdare.thirtyfive.Animations;
import com.roaringcatgames.ludumdare.thirtyfive.App;
import com.roaringcatgames.ludumdare.thirtyfive.Assets;
import com.roaringcatgames.ludumdare.thirtyfive.IGameProcessor;

/**
 * Created by barry on 4/16/16 @ 12:06 PM.
 */
public class MenuScreen extends LazyInitScreen implements InputProcessor{

    private PooledEngine engine;
    private IGameProcessor game;
    private Music bgMusic;

    public MenuScreen(IGameProcessor gp){
        this.game = gp;
    }

    private boolean isActive = true;
    private Entity title;
    @Override
    protected void init() {
        bgMusic = Assets.getBGMusic();
        bgMusic.setVolume(1f);
        bgMusic.setLooping(true);
        bgMusic.play();

        engine = new PooledEngine();

        RenderingSystem renderer = new RenderingSystem(game.getBatch(), App.PPM);

        engine.addSystem(new MovementSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(renderer);
        engine.addSystem(new DebugSystem(renderer.getCamera(), Color.CYAN, Color.PINK, Input.Keys.TAB));

        Entity entity = engine.createEntity();
        entity.add(TextureComponent.create(engine)
                .setRegion(Assets.getMenuScreen()));

        entity.add(TransformComponent.create(engine)
                .setPosition(App.W / 2f, App.H / 2f, 1f));

        engine.addEntity(entity);

        title = engine.createEntity();
        title.add(TextureComponent.create(engine));
        title.add(AnimationComponent.create(engine)
            .addAnimation("DEFAULT", Animations.getTitle())
            .setPaused(true));
        title.add(StateComponent.create(engine)
            .set("DEFAULT")
            .setLooping(false));
        title.add(TransformComponent.create(engine)
                .setPosition(App.W / 2f, 22f));
        engine.addEntity(title);

        Entity loading = engine.createEntity();
        loading.add(TextureComponent.create(engine));
        loading.add(AnimationComponent.create(engine)
                .addAnimation("DEFAULT", new Animation(1f / 16f, Assets.getLoadingFrames())));
        loading.add(StateComponent.create(engine)
                .set("DEFAULT")
                .setLooping(true));
        loading.add(TransformComponent.create(engine)
                .setPosition(-5f, 28f)
                .setRotation(-90f));
        loading.add(VelocityComponent.create(engine)
                .setSpeed(30f, 0f));
        engine.addEntity(loading);

        game.addInputProcessor(this);
        //engine.addSystem(new RenderSystem);
        //Do Systems initialization here
    }

    float elapsedTime = 0f;
    @Override
    protected void update(float deltaChange) {
        //Do any update work here
        engine.update(deltaChange);
        elapsedTime += deltaChange;

        if(elapsedTime > 1.4f){
            title.getComponent(AnimationComponent.class).setPaused(false);
        }
    }

    private void startGame(){
        if(isActive){
            isActive = false;
            game.switchScreens("GAME");
        }
    }

    @Override
    public boolean keyDown(int keycode) {

        startGame();
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        startGame();
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

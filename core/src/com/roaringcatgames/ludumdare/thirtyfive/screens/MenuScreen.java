package com.roaringcatgames.ludumdare.thirtyfive.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.roaringcatgames.kitten2d.ashley.components.KinematicComponent;
import com.roaringcatgames.kitten2d.ashley.components.TextureComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.components.VelocityComponent;
import com.roaringcatgames.kitten2d.ashley.systems.DebugSystem;
import com.roaringcatgames.kitten2d.ashley.systems.GravitySystem;
import com.roaringcatgames.kitten2d.ashley.systems.MovementSystem;
import com.roaringcatgames.kitten2d.ashley.systems.RenderingSystem;
import com.roaringcatgames.ludumdare.thirtyfive.App;
import com.roaringcatgames.ludumdare.thirtyfive.Assets;
import com.roaringcatgames.ludumdare.thirtyfive.IGameProcessor;

/**
 * Created by barry on 4/16/16 @ 12:06 PM.
 */
public class MenuScreen extends LazyInitScreen implements InputProcessor{

    private PooledEngine engine;
    private IGameProcessor game;

    public MenuScreen(IGameProcessor gp){
        this.game = gp;
    }

    @Override
    protected void init() {
        engine = new PooledEngine();

        engine.addSystem(new RenderingSystem(game.getBatch(), App.PPM));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new GravitySystem(new Vector2(-0.5f, -2.5f)));

        //engine.addSystem(new DebugSystem();

        Entity entity = engine.createEntity();
        entity.add(TextureComponent.create(engine)
            .setRegion(Assets.getBadGuyTexture()));

        entity.add(TransformComponent.create(engine)
            .setPosition(10f, 10f, 1f)
            .setRotation(30f));

        entity.add(VelocityComponent.create(engine)
            .setSpeed(2f, 3f));

        engine.addEntity(entity);

        game.addInputProcessor(this);
        //engine.addSystem(new RenderSystem);
        //Do Systems initialization here
    }

    @Override
    protected void update(float deltaChange) {
        //Do any update work here
        engine.update(deltaChange);
    }


    @Override
    public boolean keyDown(int keycode) {
        Gdx.app.log("Menu Screen", "Key pressed: " + keycode);
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
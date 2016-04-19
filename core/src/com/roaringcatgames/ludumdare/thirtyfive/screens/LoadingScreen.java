package com.roaringcatgames.ludumdare.thirtyfive.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.roaringcatgames.kitten2d.ashley.components.*;
import com.roaringcatgames.kitten2d.ashley.systems.AnimationSystem;
import com.roaringcatgames.kitten2d.ashley.systems.MovementSystem;
import com.roaringcatgames.kitten2d.ashley.systems.RenderingSystem;
import com.roaringcatgames.ludumdare.thirtyfive.Animations;
import com.roaringcatgames.ludumdare.thirtyfive.App;
import com.roaringcatgames.ludumdare.thirtyfive.Assets;
import com.roaringcatgames.ludumdare.thirtyfive.IGameProcessor;

/**
 * Created by barry on 4/16/16 @ 1:27 PM.
 */
public class LoadingScreen extends LazyInitScreen {

    private float elapsedTime = 0f;
    private float minSplashSeconds = 3f;
    IGameProcessor game;
    PooledEngine engine;

    public LoadingScreen(IGameProcessor game){
        this.game = game;
    }

    @Override
    protected void init() {
        engine = new PooledEngine();

        engine.addSystem(new MovementSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new RenderingSystem(game.getBatch(), App.PPM));

        Entity loading = engine.createEntity();
        loading.add(TextureComponent.create(engine));
        loading.add(AnimationComponent.create(engine)
            .addAnimation("DEFAULT", new Animation(1f / 16f, Assets.getLoadingFrames())));
        loading.add(StateComponent.create(engine)
            .set("DEFAULT")
            .setLooping(true));
        loading.add(TransformComponent.create(engine)
                .setPosition(-5f, 25f)
                .setRotation(-90f));
        loading.add(VelocityComponent.create(engine)
            .setSpeed(30f, 0f));
        engine.addEntity(loading);
    }

    @Override
    protected void update(float delta) {
        Gdx.gl.glClearColor(255f, 255f, 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        elapsedTime += delta;

        if(Assets.am.update() && elapsedTime >= minSplashSeconds){
            Gdx.app.log("Splash Screen", "Assets are Loaded!");
            Animations.init();
            game.switchScreens("MENU");
        }else {
            engine.update(delta);
        }
    }
}

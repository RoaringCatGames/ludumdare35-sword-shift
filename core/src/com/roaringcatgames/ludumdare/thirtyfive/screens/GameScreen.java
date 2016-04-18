package com.roaringcatgames.ludumdare.thirtyfive.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.systems.*;
import com.roaringcatgames.ludumdare.thirtyfive.App;
import com.roaringcatgames.ludumdare.thirtyfive.Assets;
import com.roaringcatgames.ludumdare.thirtyfive.IGameProcessor;
import com.roaringcatgames.ludumdare.thirtyfive.systems.*;


/**
 * Created by barry on 4/16/16 @ 3:36 PM.
 */
public class GameScreen extends LazyInitScreen{

    private IGameProcessor game;
    private PooledEngine engine;

    private Vector2 minBounds = new Vector2(0f, 0f);
    private Vector2 maxBounds = new Vector2(40f*App.TileCount, 15f);
    private Vector3 playerPosition = new Vector3(3f, 3f, 1f);
    private float initialScale = 1f;
    public GameScreen(IGameProcessor game){
        this.game = game;
    }

    @Override
    protected void init() {
        engine = new PooledEngine();

        RenderingSystem renderer = new RenderingSystem(game.getBatch(), App.PPM);

        engine.addSystem(new MovementSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new MultiBoundsSystem());
        engine.addSystem(new RemainInBoundsSystem(minBounds, maxBounds));
        engine.addSystem(new FadingSystem());
        engine.addSystem(new ParticleSystem());
        engine.addSystem(new BackgroundSystem());
        engine.addSystem(new EnemySpawnerSystem(renderer.getCamera()));
        engine.addSystem(new PlayerSystem(playerPosition, initialScale, game));
        engine.addSystem(new CameraSystem(renderer.getCamera(), 2f));
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new MoveToSystem());
        engine.addSystem(new EnemyAiSystem());
        engine.addSystem(new RotateToSystem());
        engine.addSystem(new TriggerAreaSystem());

        engine.addSystem(new AttackResolutionSystem());
        engine.addSystem(new FPSSystem(Assets.getFont32(), new Vector2(0.5f, 2f)));


        engine.addSystem(renderer);
        engine.addSystem(new HealthRenderSystem(renderer.getCamera()));
        engine.addSystem(new TextRenderingSystem(game.getBatch(),game.getGUICam(), game.getCamera()));

        engine.addSystem(new TriggerAreaDebugSystem(renderer.getCamera(), Color.ORANGE, Input.Keys.TAB));
        engine.addSystem(new DebugSystem(renderer.getCamera(), Color.CYAN, Color.PINK, Input.Keys.TAB));

        //FOR DEBUG PURPOSES ONLY
        Entity playBounds = engine.createEntity();
        playBounds.add(BoundsComponent.create(engine)
            .setBounds(minBounds.x, minBounds.y, maxBounds.x-minBounds.x, maxBounds.y - minBounds.y));
        playBounds.add(TransformComponent.create(engine)
            .setPosition((maxBounds.x - minBounds.x) / 2, (maxBounds.y - minBounds.y) / 2f));
        engine.addEntity(playBounds);
        //END DEBUG PURPOSES
    }

    @Override
    protected void update(float deltaChange) {
         engine.update(deltaChange);
    }
}

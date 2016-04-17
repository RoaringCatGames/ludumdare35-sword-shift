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
import com.roaringcatgames.ludumdare.thirtyfive.systems.BackgroundSystem;
import com.roaringcatgames.ludumdare.thirtyfive.systems.CameraSystem;
import com.roaringcatgames.ludumdare.thirtyfive.systems.EnemySpawnerSystem;
import com.roaringcatgames.ludumdare.thirtyfive.systems.PlayerSystem;


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
        //engine.addSystem(new FPSSystem(Assets.getFont32(), new Vector2(0.5f, 2f)));


        engine.addSystem(renderer);
        engine.addSystem(new DebugSystem(renderer.getCamera(), Color.CYAN, Color.PINK, Input.Keys.TAB));
        //engine.addSystem(new TextRenderingSystem(game.getBatch(),game.getGUICam(), game.getCamera()));

        //FOR DEBUG PURPOSES ONLY
        Entity playBounds = engine.createEntity();
        playBounds.add(BoundsComponent.create(engine)
            .setBounds(minBounds.x, minBounds.y, maxBounds.x-minBounds.x, maxBounds.y - minBounds.y));
        playBounds.add(TransformComponent.create(engine)
            .setPosition((maxBounds.x - minBounds.x) / 2, (maxBounds.y - minBounds.y) / 2f));
        engine.addEntity(playBounds);
        //END DEBUG PURPOSES

//        Entity entity = engine.createEntity();
//        entity.add(TextureComponent.create(engine)
//                .setRegion(Assets.getBadGuyTexture()));
//
//        entity.add(AnimationComponent.create(engine)
//                .addAnimation("DEFAULT", Animations.getTestAnimation()));
//        entity.add(StateComponent.create(engine).set("DEFAULT"));
//
//        entity.add(TransformComponent.create(engine)
//                .setPosition(10f, 10f, 1f)
//                .setRotation(30f));
//
//        entity.add(BoundsComponent.create(engine)
//            .setBounds(0f, 0f, 8f, 8f));
//        entity.add(RemainInBoundsComponent.create(engine)
//            .setMode(BoundMode.CENTER));
//        entity.add(MultiBoundsComponent.create(engine)
//                .addBound(new Bound(new Circle(0f, 0f, 0.5f), 0.5f, 0.5f))
//                .addBound(new Bound(new Circle(0f, 0f, 0.5f), 0.5f, -0.5f))
//                .addBound(new Bound(new Circle(0f, 0f, 0.5f), -0.5f, 0.5f))
//                .addBound(new Bound(new Circle(0f, 0f, 0.5f), -0.5f, -0.5f)));
//
//        entity.add(VelocityComponent.create(engine)
//                .setSpeed(2f, 3f));
//
////        entity.add(ParticleEmitterComponent.create(engine)
////            .setAngleRange(0f, 360f)
////            .setDuration(10f)
////            .setShouldLoop(true)
////            .setParticleImages(Assets.getTestFrames())
////            .setParticleLifespans(0.5f, 1f)
////            .setShouldFade(true)
////            .setSpawnRate(200f)
////            .setSpeed(2f, 10f)
////            .setZIndex(0f));
//
//        engine.addEntity(entity);
    }

    @Override
    protected void update(float deltaChange) {
         engine.update(deltaChange);
    }
}

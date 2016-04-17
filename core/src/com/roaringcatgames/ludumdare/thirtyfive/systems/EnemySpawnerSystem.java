package com.roaringcatgames.ludumdare.thirtyfive.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.roaringcatgames.kitten2d.ashley.K2MathUtil;
import com.roaringcatgames.kitten2d.ashley.components.*;
import com.roaringcatgames.ludumdare.thirtyfive.Animations;
import com.roaringcatgames.ludumdare.thirtyfive.components.EnemyComponent;
import com.roaringcatgames.ludumdare.thirtyfive.data.EnemyDefinition;
import com.roaringcatgames.ludumdare.thirtyfive.data.SpawnBatch;
import com.roaringcatgames.ludumdare.thirtyfive.data.SpawnGroup;

/**
 * Created by barry on 4/16/16 @ 5:00 PM.
 */
public class EnemySpawnerSystem extends IteratingSystem {

    private OrthographicCamera cam;

    private class EnemyGroup{
        public float xTrigger;
        public Array<Entity> entities = new Array<Entity>();
        public EnemyGroup(float x){
            this.xTrigger = x;
        }
    }

    private Array<EnemyGroup> groups = new Array<>();

    public EnemySpawnerSystem(OrthographicCamera cam){
        super(Family.all(EnemyComponent.class).get());
        this.cam = cam;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        PooledEngine eg = (PooledEngine)engine;
        //GENERATE ENEMIES READY TO BE ACTIVATED/ADDED
        for(SpawnBatch batch:SpawnGroup.getSpawns()){
            EnemyGroup group = new EnemyGroup(batch.triggerXPosition);
            for(EnemyDefinition def:batch.enemyDefs){
                Entity e = eg.createEntity();
                e.add(TransformComponent.create(eg)
                    .setPosition(def.initialOffset.x, def.initialOffset.y));
                e.add(TextureComponent.create(eg));
                e.add(StateComponent.create(eg)
                    .set("DEFAULT")
                    .setLooping(true));

                switch(def.enemyType){
                    case RAT:
                         e.add(AnimationComponent.create(eg)
                                .addAnimation("DEFAULT", Animations.getRatWalking())
                                .addAnimation("ATTACKING", Animations.getRatAttacking())
                                .addAnimation("DYING", Animations.getRatDying()));
                        break;
                    case BEAR:
                        e.add(AnimationComponent.create(eg)
                                .addAnimation("DEFAULT", Animations.getBearWalking())
                                .addAnimation("ATTACKING", Animations.getBearAttacking())
                                .addAnimation("DYING", Animations.getBearDying()));
                        break;
                    case TROLL:
                        e.add(AnimationComponent.create(eg)
                                .addAnimation("DEFAULT", Animations.getTrollWalking())
                                .addAnimation("ATTACKING", Animations.getTrollAttacking())
                                .addAnimation("DYING", Animations.getTrollDying()));
                        break;
                }

                //Add Particle System based on Enemy Type
//                e.add(ParticleEmitterComponent.create(eg)
//                    .setShouldLoop(true)
//                    .setSpeed(1f, 3f)
//                    .setSpawnRate(200f)
//                    .setAngleRange(-15f, 15f)
//                    .setShouldFade(true)
//                    .setParticleLifespans(2f, 5f)
//                    .setParticleImages(Assets.getAuraParticles(def.auraType)))
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

//        if(cam.position.x + (cam.viewportWidth/2f) >=)

//        Entity enemy = engine.createEntity();
//
//        float x = cam.position.x + (cam.viewportWidth/2f) + 1f;
//        float y = K2MathUtil.getRandomInRange(1f, 19f);
//        enemy.add(TransformComponent.create(engine)
//                .setPosition(x, y)
//                .setScale(2f, 2f));
//        enemy.add(TextureComponent.create(engine));
//        enemy.add(StateComponent.create(engine)
//                .setLooping(true)
//                .set("DEFAULT"));
//        enemy.add(AnimationComponent.create(engine)
//                .addAnimation("DEFAULT", Animations.getTestAnimation()));
//        enemy.add(VelocityComponent.create(engine)
//                .setSpeed(-4, 0f));
//
//        engine.addEntity(enemy);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}

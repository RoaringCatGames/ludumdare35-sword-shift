package com.roaringcatgames.ludumdare.thirtyfive.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.kitten2d.ashley.components.*;
import com.roaringcatgames.ludumdare.thirtyfive.*;
import com.roaringcatgames.ludumdare.thirtyfive.components.AuraType;
import com.roaringcatgames.ludumdare.thirtyfive.components.EnemyComponent;
import com.roaringcatgames.ludumdare.thirtyfive.data.EnemyDefinition;
import com.roaringcatgames.ludumdare.thirtyfive.data.SpawnBatch;
import com.roaringcatgames.ludumdare.thirtyfive.data.SpawnGroup;

import java.util.Comparator;

/**
 * Created by barry on 4/16/16 @ 5:00 PM.
 */
public class EnemySpawnerSystem extends IteratingSystem {

    private OrthographicCamera cam;

    private ComponentMapper<TransformComponent> tm;

    private class EnemyGroup{
        public boolean hasSpawned = false;
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
        this.tm = ComponentMapper.getFor(TransformComponent.class);
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
                    .setPosition(def.initialOffset.x, def.initialOffset.y, Z.enemy));
                e.add(TextureComponent.create(eg));
                e.add(StateComponent.create(eg)
                    .set("DEFAULT")
                    .setLooping(true));

                float xVelocity = -1f;
                float width = 2f;
                float height = 4f;
                float health = Health.rat;
                float dmg = Damage.rat;
                switch(def.enemyType){
                    case RAT:
                         if(def.auraType == AuraType.YELLOW) {
                             e.add(AnimationComponent.create(eg)
                                     .addAnimation("DEFAULT", Animations.getRatWalking())
                                     .addAnimation("ATTACKING", Animations.getRatAttacking())
                                     .addAnimation("DYING", Animations.getRatDying()));
                         }else{
                             e.add(AnimationComponent.create(eg)
                                     .addAnimation("DEFAULT", Animations.getRatAltWalking())
                                     .addAnimation("ATTACKING", Animations.getRatAltAttacking())
                                     .addAnimation("DYING", Animations.getRatAltDying()));
                         }
                        break;
                    case BEAR:
                        e.add(AnimationComponent.create(eg)
                                .addAnimation("DEFAULT", Animations.getBearWalking())
                                .addAnimation("ATTACKING", Animations.getBearAttacking())
                                .addAnimation("DYING", Animations.getBearDying()));
                        xVelocity = -0.5f;
                        width = 3f;
                        height = 2.5f;
                        health = Health.bear;
                        dmg = Damage.bear;
                        break;
                    case TROLL:
                        e.add(AnimationComponent.create(eg)
                                .addAnimation("DEFAULT", Animations.getTrollWalking())
                                .addAnimation("ATTACKING", Animations.getTrollAttacking())
                                .addAnimation("DYING", Animations.getTrollDying()));
                        xVelocity = -0.25f;
                        width = 4f;
                        height = 4f;
                        health = Health.troll;
                        dmg = Damage.troll;
                        break;
                }
                e.add(HealthComponent.create(engine)
                    .setHealth(health).setMaxHealth(health));
                e.add(DamageComponent.create(engine)
                    .setDPS(dmg));

                e.add(VelocityComponent.create(engine)
                    .setSpeed(xVelocity, 0f));

                e.add(BoundsComponent.create(engine)
                    .setBounds(0f, 0f, width, height));

                Array<TextureAtlas.AtlasRegion> regions;

                if(def.auraType == AuraType.YELLOW) {
                    regions = Assets.getYellowParticles();
                }else{
                    regions = Assets.getPurpleParticles();
                }
                e.add(EnemyComponent.create(engine)
                    .setAura(def.auraType)
                    .setEnemyType(def.enemyType));
                //Add Particle System based on Enemy Type
                e.add(ParticleEmitterComponent.create(eg)
                        .setShouldLoop(true)
                        .setZIndex(Z.aura)
                        .setSpeed(2f, 10f)
                        .setSpawnRate(30f)
                        .setAngleRange(0f, 360f)
                        .setShouldFade(true)
                        .setParticleLifespans(1f, 2f)
                        .setParticleImages(regions));

                group.entities.add(e);

            }
            groups.add(group);
        }

        groups.sort(new Comparator<EnemyGroup>() {
            @Override
            public int compare(EnemyGroup o1, EnemyGroup o2) {
                return Float.compare(o1.xTrigger, o2.xTrigger);
            }
        });
    }

    Array<EnemyGroup> groupsToRemove = new Array<>();
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);


        groupsToRemove.clear();
        for(EnemyGroup group:groups){

            if(!group.hasSpawned && cam.position.x + (cam.viewportWidth/2f) >= group.xTrigger){
                float x = cam.position.x + (cam.viewportWidth/2f) + 1f;
                for(Entity e:group.entities){
                    TransformComponent tc = tm.get(e);
                    tc.position.set(tc.position.x + x, tc.position.y, tc.position.z);

                    getEngine().addEntity(e);
                }

                group.hasSpawned = true;
                groupsToRemove.add(group);
            }
        }

        groups.removeAll(groupsToRemove, true);

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

package com.roaringcatgames.ludumdare.thirtyfive.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.kitten2d.ashley.K2MathUtil;
import com.roaringcatgames.kitten2d.ashley.components.*;
import com.roaringcatgames.ludumdare.thirtyfive.Speed;
import com.roaringcatgames.ludumdare.thirtyfive.components.EnemyComponent;
import com.roaringcatgames.ludumdare.thirtyfive.components.PlayerComponent;
import com.roaringcatgames.ludumdare.thirtyfive.components.TriggerAreaComponent;

/**
 * Created by barry on 4/17/16 @ 4:55 PM.
 */
public class EnemyAiSystem extends IteratingSystem {

    private Array<Entity> enemies;
    private Entity player;

    private ComponentMapper<PlayerComponent> pm;
    private ComponentMapper<EnemyComponent> em;
    private ComponentMapper<TransformComponent> tm;
    private ComponentMapper<MoveToComponent> mtm;
    private ComponentMapper<StateComponent> sm;
    private ComponentMapper<VelocityComponent> vm;
    private ComponentMapper<BoundsComponent> bm;
    private ComponentMapper<TriggerAreaComponent> tam;

    public EnemyAiSystem(){
        super(Family.one(PlayerComponent.class, EnemyComponent.class).get());

        enemies = new Array<>();
        pm = ComponentMapper.getFor(PlayerComponent.class);
        em = ComponentMapper.getFor(EnemyComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
        mtm = ComponentMapper.getFor(MoveToComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
        vm = ComponentMapper.getFor(VelocityComponent.class);
        bm = ComponentMapper.getFor(BoundsComponent.class);
        tam = ComponentMapper.getFor(TriggerAreaComponent.class);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        PooledEngine engine = (PooledEngine)getEngine();

        TransformComponent playerPos = tm.get(player);
        BoundsComponent playerBounds = bm.get(player);
        float playerLeft = playerBounds.bounds.x;
        float playerRight = playerBounds.bounds.x + playerBounds.bounds.width;

        for(Entity enemy:enemies){
            EnemyComponent ec = em.get(enemy);
            MoveToComponent mtc = mtm.get(enemy);
            TransformComponent enemyPos = tm.get(enemy);
            StateComponent enemyState = sm.get(enemy);
            VelocityComponent enemyVel = vm.get(enemy);
            TriggerAreaComponent trigger = tam.get(enemy);

            float triggerBoxLeft = trigger.triggerBox.x;
            float triggerBoxRight = trigger.triggerBox.x + trigger.triggerBox.width;

            if(enemyState.get() != "DYING") {
                if (trigger.triggerBox.overlaps(playerBounds.bounds)) {
                    enemyVel.setSpeed(0f, enemyVel.speed.y);
                    if (enemyState.get() != "ATTACKING") {
                        enemyState.set("ATTACKING");
                    }
                } else if(enemyState.get() != "DEFAULT"){
                    enemyState.set("DEFAULT");
                }

                if(enemyState.get() != "ATTACKING") {
                    if ((enemyVel.speed.x == 0f || trigger.offset.x < 0f) && playerLeft > triggerBoxRight) {
                        //}else if (playerPos.position.x > enemyPos.position.x) {
                        if (enemyState.get() != "DEFAULT") {
                            enemyState.set("DEFAULT");
                        }
                        enemyPos.setScale(-1f * Math.abs(enemyPos.scale.x), enemyPos.scale.y);
                        enemyVel.setSpeed(2f, enemyVel.speed.y);
                        trigger.setOffset((-1f * trigger.offset.x) - trigger.triggerBox.width, trigger.offset.y);
                    } else if ((enemyVel.speed.x == 0f || trigger.offset.x > 0f) && playerRight < triggerBoxLeft) {
                        if (enemyState.get() != "DEFAULT") {
                            enemyState.set("DEFAULT");
                        }
                        enemyPos.setScale(Math.abs(enemyPos.scale.x), enemyPos.scale.y);
                        enemyVel.setSpeed(-2f, enemyVel.speed.y);
                        trigger.setOffset((-1f * trigger.offset.x) - trigger.triggerBox.width, trigger.offset.y);
                    }
                }
            }
        }

        enemies.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if(em.has(entity)){
            enemies.add(entity);
        }else{
            player = entity;
        }
    }
}

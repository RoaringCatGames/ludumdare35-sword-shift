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

    public EnemyAiSystem(){
        super(Family.one(PlayerComponent.class, EnemyComponent.class).get());

        enemies = new Array<>();
        pm = ComponentMapper.getFor(PlayerComponent.class);
        em = ComponentMapper.getFor(EnemyComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
        mtm = ComponentMapper.getFor(MoveToComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        PooledEngine engine = (PooledEngine)getEngine();

        TransformComponent playerPos = tm.get(player);
        for(Entity enemy:enemies){
            EnemyComponent ec = em.get(enemy);
            MoveToComponent mtc = mtm.get(enemy);
            TransformComponent enemyPos = tm.get(enemy);
            StateComponent enemyState = sm.get(enemy);

            if(enemyState.get() != "DYING") {
                float xAdjust = K2MathUtil.getRandomInRange(-2f, 2f);
                float yAdjust = 0f; //K2MathUtil.getRandomInRange(-2f, 2f);
                float x = playerPos.position.x + xAdjust;
                float y = playerPos.position.y + yAdjust;

                if (x >= enemyPos.position.x) {
                    enemyPos.setScale(-1f * Math.abs(enemyPos.scale.x), enemyPos.scale.y);
                } else {
                    enemyPos.setScale(Math.abs(enemyPos.scale.x), enemyPos.scale.y);
                }

                if (mtc != null) {
                    mtc.setTarget(x, y);
                } else {
                    enemy.add(MoveToComponent.create(engine)
                            .setTarget(x, y)
                            .setSpeed(Speed.getSpeed(ec.enemyType)));
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

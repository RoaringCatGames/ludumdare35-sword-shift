package com.roaringcatgames.ludumdare.thirtyfive.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.kitten2d.ashley.components.*;
import com.roaringcatgames.ludumdare.thirtyfive.components.EnemyComponent;
import com.roaringcatgames.ludumdare.thirtyfive.components.PlayerComponent;

/**
 * Created by barry on 4/17/16 @ 3:37 PM.
 */
public class AttackResolutionSystem extends IteratingSystem {

    private Array<Entity> enemies;
    private Entity player;

    private ComponentMapper<PlayerComponent> pm;
    private ComponentMapper<EnemyComponent> em;
    private ComponentMapper<BoundsComponent> bm;
    private ComponentMapper<MultiBoundsComponent> mbm;
    private ComponentMapper<HealthComponent> hm;
    private ComponentMapper<DamageComponent> dm;
    private ComponentMapper<StateComponent> sm;

    public AttackResolutionSystem(){
        super(Family.one(PlayerComponent.class, EnemyComponent.class).get());

        enemies = new Array<>();
        pm = ComponentMapper.getFor(PlayerComponent.class);
        em = ComponentMapper.getFor(EnemyComponent.class);
        bm = ComponentMapper.getFor(BoundsComponent.class);
        mbm = ComponentMapper.getFor(MultiBoundsComponent.class);
        hm = ComponentMapper.getFor(HealthComponent.class);
        dm = ComponentMapper.getFor(DamageComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        PlayerComponent pc = pm.get(player);
        DamageComponent playerDamage = dm.get(player);
        MultiBoundsComponent playerBounds = mbm.get(player);
        BoundsComponent playerOuterBounds = bm.get(player);
        if(pc.isAttacking) {
            for (Entity e : enemies) {
                HealthComponent enemyHealth = hm.get(e);
                BoundsComponent enemyBounds = bm.get(e);
                int hitCount = 0;

                //See iff hitting
                if (enemyHealth.health > 0f && enemyBounds.bounds.overlaps(playerOuterBounds.bounds)) {
                    for (Bound b : playerBounds.bounds) {
                        if (b.isCircle) {
                            if (Intersector.overlaps(b.circle, enemyBounds.bounds)) {
                                hitCount += 1;
                            }
                        } else if (b.rect.overlaps(enemyBounds.bounds)) {
                            hitCount += 1;
                        }
                    }

                    enemyHealth.health = Math.max(0f, enemyHealth.health - (hitCount * playerDamage.dps * deltaTime));

                    if (enemyHealth.health == 0f) {
                        StateComponent es = sm.get(e);
                        es.set("DYING").setLooping(false);
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

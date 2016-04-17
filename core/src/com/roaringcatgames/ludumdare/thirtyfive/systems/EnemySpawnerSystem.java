package com.roaringcatgames.ludumdare.thirtyfive.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.roaringcatgames.kitten2d.ashley.K2MathUtil;
import com.roaringcatgames.kitten2d.ashley.components.*;
import com.roaringcatgames.ludumdare.thirtyfive.Animations;

/**
 * Created by barry on 4/16/16 @ 5:00 PM.
 */
public class EnemySpawnerSystem extends IntervalSystem {

    private OrthographicCamera cam;

    public EnemySpawnerSystem(OrthographicCamera cam, float minInterval){
        super(minInterval);
        this.cam = cam;
    }

    @Override
    protected void updateInterval() {
        Gdx.app.log("SPAWNER SYSTEM", "Updated");
        PooledEngine engine = (PooledEngine)getEngine();

        Entity enemy = engine.createEntity();

        float x = cam.position.x + (cam.viewportWidth/2f) + 1f;
         float y = K2MathUtil.getRandomInRange(1f, 19f);
        enemy.add(TransformComponent.create(engine)
            .setPosition(x, y)
            .setScale(2f, 2f));
        enemy.add(TextureComponent.create(engine));
        enemy.add(StateComponent.create(engine)
            .setLooping(true)
            .set("DEFAULT"));
        enemy.add(AnimationComponent.create(engine)
            .addAnimation("DEFAULT", Animations.getTestAnimation()));
        enemy.add(VelocityComponent.create(engine)
            .setSpeed(-4, 0f));

        engine.addEntity(enemy);
    }
}

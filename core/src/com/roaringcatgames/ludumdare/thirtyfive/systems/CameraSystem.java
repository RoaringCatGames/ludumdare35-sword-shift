package com.roaringcatgames.ludumdare.thirtyfive.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.ludumdare.thirtyfive.components.PlayerComponent;

/**
 * Created by barry on 4/17/16 @ 12:16 PM.
 */
public class CameraSystem extends IteratingSystem {

    private OrthographicCamera camera;
    private Entity player;
    private float moveThreshold;

    private ComponentMapper<TransformComponent> tm;

    public CameraSystem(OrthographicCamera cam, float moveCameraThreshold) {
        super(Family.all(PlayerComponent.class, TransformComponent.class).get());
        this.tm = ComponentMapper.getFor(TransformComponent.class);
        this.camera = cam;
        this.moveThreshold = moveCameraThreshold;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(player != null){
            TransformComponent playerTransform = tm.get(player);

            if(playerTransform.position.x >= (camera.position.x + moveThreshold)){
                camera.position.set(playerTransform.position.x - moveThreshold, camera.position.y, camera.position.z);
            }
        }

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        this.player = entity;
    }
}

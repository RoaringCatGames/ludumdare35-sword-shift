package com.roaringcatgames.ludumdare.thirtyfive.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.ludumdare.thirtyfive.components.PlayerComponent;
import com.roaringcatgames.ludumdare.thirtyfive.components.RotateTo;
import com.roaringcatgames.ludumdare.thirtyfive.components.RotateToComponent;

/**
 * Created by rexsoriano on 4/17/16.
 */
public class RotateToSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> tm;
    private ComponentMapper<RotateToComponent> rm;


    public RotateToSystem(){
        super(Family.all(RotateToComponent.class, TransformComponent.class).get());
        this.tm = ComponentMapper.getFor(TransformComponent.class);
        this.rm = ComponentMapper.getFor(RotateToComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent tc = tm.get(entity);
        RotateToComponent rc = rm.get(entity);

        RotateTo rotation = null;
        for(RotateTo r: rc.Rotations){
            if(!r.isFinished){
                rotation = r;
                break;
            }
        }

        if(rotation != null){
            //do the rotation
            boolean isAtTarget = false;
            float newRotation = tc.rotation + rotation.rotationSpeed * deltaTime;
            if (rotation.rotationSpeed < 0f && newRotation <= rotation.targetRotation) {
                tc.rotation=(Math.max(newRotation, rotation.targetRotation));
                rotation.isFinished = true;
            } else if (rotation.rotationSpeed > 0f && newRotation >= rotation.targetRotation) {
                tc.rotation = (Math.min(newRotation, rotation.targetRotation));
                rotation.isFinished = true;
            } else {
                tc.rotation = (newRotation);
            }
        }
        else{
            entity.remove(RotateToComponent.class);
        }
    }


}

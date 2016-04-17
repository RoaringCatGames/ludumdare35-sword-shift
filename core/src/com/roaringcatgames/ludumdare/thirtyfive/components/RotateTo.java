package com.roaringcatgames.ludumdare.thirtyfive.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by rexsoriano on 4/17/16.
 */
public class RotateTo{

    public float targetRotation;
    public float rotationSpeed;
    public boolean isFinished;

    public RotateTo(float target, float speed) {
        targetRotation = target;
        rotationSpeed = speed;
        isFinished = false;
    }
}

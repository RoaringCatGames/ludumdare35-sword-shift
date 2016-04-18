package com.roaringcatgames.ludumdare.thirtyfive.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.kitten2d.ashley.VectorUtils;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.ludumdare.thirtyfive.components.TriggerAreaComponent;

/**
 * Created by barry on 4/18/16 @ 6:45 PM.
 */
public class TriggerAreaDebugSystem extends IteratingSystem {

    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private ComponentMapper<TriggerAreaComponent> tam;
    private Array<Entity> triggers = new Array<>();
    private OrthographicCamera cam;
    private int[] debugKeys;
    private Color triggerBoxColor = Color.PURPLE;
    private boolean isDebugMode = false;

    public TriggerAreaDebugSystem(OrthographicCamera cam, Color color, int... debugKeys) {
        super(Family.all(TriggerAreaComponent.class).get());
        tam = ComponentMapper.getFor(TriggerAreaComponent.class);
        this.cam = cam;
        if (debugKeys != null && debugKeys.length > 0) {
            this.debugKeys = debugKeys;
        } else {
            this.debugKeys = new int[]{Input.Keys.TAB};
        }
        triggerBoxColor = color;
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        triggers.add(entity);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for (int key : debugKeys) {
            if (Gdx.input.isKeyJustPressed(key)) {
                isDebugMode = !isDebugMode;
                break;
            }
        }

        if (isDebugMode) {
            Gdx.gl20.glLineWidth(1f);
            shapeRenderer.setProjectionMatrix(cam.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            for (Entity e : triggers) {
                shapeRenderer.setColor(triggerBoxColor);
                TriggerAreaComponent triggerArea = tam.get(e);
                shapeRenderer.rect(triggerArea.triggerBox.x, triggerArea.triggerBox.y, triggerArea.triggerBox.width, triggerArea.triggerBox.height);
                shapeRenderer.setColor(Color.RED);
                float boundsCenterX = triggerArea.triggerBox.x + (triggerArea.triggerBox.width / 2f);
                float boundsCenterY = triggerArea.triggerBox.y + (triggerArea.triggerBox.height / 2f);
                shapeRenderer.circle(boundsCenterX, boundsCenterY, 0.2f);
            }
            shapeRenderer.end();

        }

        triggers.clear();
    }
}
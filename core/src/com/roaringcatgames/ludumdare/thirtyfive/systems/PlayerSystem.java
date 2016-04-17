package com.roaringcatgames.ludumdare.thirtyfive.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.kitten2d.ashley.components.*;
import com.roaringcatgames.ludumdare.thirtyfive.Animations;
import com.roaringcatgames.ludumdare.thirtyfive.IGameProcessor;
import com.roaringcatgames.ludumdare.thirtyfive.Z;
import com.roaringcatgames.ludumdare.thirtyfive.components.PlayerComponent;
import com.roaringcatgames.ludumdare.thirtyfive.components.RotateTo;
import com.roaringcatgames.ludumdare.thirtyfive.components.RotateToComponent;
import com.sun.glass.ui.EventLoop;

import java.util.ArrayList;

/**
 * Created by rexsoriano on 4/16/16.
 */
public class PlayerSystem extends IteratingSystem implements InputProcessor {
    private boolean isInitialized = false;
    private Entity player;
    private Vector3 initialPosition;
    private float initialScale;
    private ComponentMapper<PlayerComponent> pm;
    private IGameProcessor game;
    private ComponentMapper<VelocityComponent> vm;
    private ComponentMapper<StateComponent> sm;
    private ComponentMapper<TransformComponent> tm;
    private boolean isAttacking;
    private ComponentMapper<RotateToComponent> rtm;

    private ArrayList<Integer> isPressed = new ArrayList();



    public PlayerSystem(Vector3 initialPosition, float initialScale, IGameProcessor game){
        super(Family.all(PlayerComponent.class).get());
        this.initialPosition = initialPosition;
        this.initialScale = initialScale;
        this.pm = ComponentMapper.getFor(PlayerComponent.class);
        this.vm = ComponentMapper.getFor(VelocityComponent.class);
        this.sm = ComponentMapper.getFor(StateComponent.class);
        this.tm = ComponentMapper.getFor(TransformComponent.class);
        this.rtm = ComponentMapper.getFor(RotateToComponent.class);
        this.game = game;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        game.addInputProcessor(this);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        game.removeInputProcessor(this);
    }

    private void init(){
        if(player == null) {
            PooledEngine engine = (PooledEngine)getEngine();

            player = engine.createEntity();

            player.add(PlayerComponent.create(engine));
            player.add(RemainInBoundsComponent.create(engine));
            player.add(VelocityComponent.create(engine)
                .setSpeed(0f,0f));
            player.add(TransformComponent.create(engine)
                    .setOpacity(1f)
                    .setPosition(10f, 10f, Z.player));
            player.add(BoundsComponent.create(engine)
                    .setBounds(3f, 3f, 1f, 1f));
            player.add(StateComponent.create(engine)
                    .set("DEFAULT")
                    .setLooping(true));
            player.add(TextureComponent.create(engine));
            player.add(AnimationComponent.create(engine)
                    .addAnimation("DEFAULT", Animations.getTestAnimation())
                    .addAnimation("DAGGER_IDLE", Animations.getDaggerIdleAnimation()));
            player.add(RotateToComponent.create(engine)
                    .addRotateTo(90f, 5f)
                    .addRotateTo(-90f, -10f));
            engine.addEntity(player);

        }
        isInitialized = true;
    }

    public void update(float deltaTime) {
        super.update(deltaTime);

        if(!isInitialized){
            init();
        }

        VelocityComponent vc = vm.get(player);
        TransformComponent tc = tm.get(player);
        RotateToComponent rtc = rtm.get(player);
        if(isPressed.contains(new Integer(Input.Keys.LEFT))) {
            vc.setSpeed(-10f, vc.speed.y);
        }
        if(isPressed.contains(new Integer(Input.Keys.RIGHT))) {
            vc.setSpeed(10f, vc.speed.y);
        }
        if(isPressed.contains(new Integer(Input.Keys.UP))) {
            vc.setSpeed(vc.speed.x, 10f);
        }
        if(isPressed.contains(new Integer(Input.Keys.DOWN))) {
            vc.setSpeed(vc.speed.x, -10f);
        }

        if(isPressed.contains(new Integer(Input.Keys.SPACE))) {
            //swingDagger();


        }

        //attack state
        if(isAttacking){

            //get un-finished rotation
            float initRotation = tc.rotation;
            boolean doneAnimating = true;
            RotateTo rotation = null;
            for(int i =0; i < rtc.Rotations.size; i++){
                if(!rtc.Rotations.get(i).isFinished) {
                    rotation = rtc.Rotations.get(i);
                    doneAnimating = false;
                    break;
                }
            }


            if(doneAnimating) {
                //return to idle
                isAttacking = false;
                swingDagger(0.0f);
            }else {
                //do the rotation
                boolean isAtTarget = false;
                float newRotation = initRotation + rotation.rotationSpeed;
                if (rotation.rotationSpeed < 0f && newRotation <= rotation.targetRotation) {
                    swingDagger(Math.max(newRotation, rotation.targetRotation));
                    rotation.isFinished = true;
                } else if (rotation.rotationSpeed > 0f && newRotation >= rotation.targetRotation) {
                    swingDagger(Math.min(newRotation, rotation.targetRotation));
                    rotation.isFinished = true;
                } else {
                    swingDagger(initRotation + rotation.rotationSpeed);
                }
            }
        }

        //decelerate
        Vector2 speed = player.getComponent(VelocityComponent.class).speed;
        if(speed.x > 0){
            player.getComponent(VelocityComponent.class).setSpeed((float) Math.floor(speed.x - (speed.x * 0.15f)), speed.y);
        }
        else {
            player.getComponent(VelocityComponent.class).setSpeed((float) Math.ceil(speed.x + (speed.x * -0.15f)), speed.y);
        }

        if(speed.y > 0) {
            player.getComponent(VelocityComponent.class).setSpeed(speed.x, (float) Math.floor(speed.y - (speed.y * 0.15f)));
        }
        else{
            player.getComponent(VelocityComponent.class).setSpeed(speed.x, (float) Math.ceil(speed.y + (speed.y * -0.15f)));
        }



    }

    @Override
    public boolean keyDown(int keycode) {
        isPressed.add(keycode);
        if(Input.Keys.SHIFT_LEFT == keycode) {
            player.getComponent(StateComponent.class).set("DAGGER_IDLE");


            StateComponent sc = player.getComponent(StateComponent.class);
            AnimationComponent ac = player.getComponent(AnimationComponent.class);
            ac.animations.get(sc.get()).isAnimationFinished(sc.time);
        }

        if(Input.Keys.SPACE == keycode) {
           isAttacking = true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        isPressed.remove(new Integer(keycode));
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
    }
    /*HELPER FUNCTIONS*/
    public void swingDagger(float degree){
        player.getComponent(TransformComponent.class).setRotation(degree);
    }
}
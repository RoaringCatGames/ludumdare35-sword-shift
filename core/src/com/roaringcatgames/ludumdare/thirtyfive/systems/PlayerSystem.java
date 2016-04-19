package com.roaringcatgames.ludumdare.thirtyfive.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.roaringcatgames.kitten2d.ashley.components.*;
import com.roaringcatgames.ludumdare.thirtyfive.*;
import com.roaringcatgames.ludumdare.thirtyfive.components.*;

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
    private ComponentMapper<ParticleEmitterComponent> pem;
    private ComponentMapper<AnimationComponent> am;
    private ComponentMapper<MultiBoundsComponent> mbm;
    private ComponentMapper<BoundsComponent> bm;
    private int currentEnergy = 0;

    //private boolean isAttacking;
    private ComponentMapper<RotateToComponent> rtm;

    private Sound daggerSfx;
    private Sound hatchetSfx;
    private Sound katanaSfx;
    private Sound busterSfx;

    //private Sound

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
        this.am = ComponentMapper.getFor(AnimationComponent.class);
        this.mbm = ComponentMapper.getFor(MultiBoundsComponent.class);
        this.bm = ComponentMapper.getFor(BoundsComponent.class);

        daggerSfx = Assets.getDaggerSfx();
        hatchetSfx = Assets.getHatchetSfx();
        katanaSfx = Assets.getKatanaSfx();
        busterSfx = Assets.getBusterSfx();

        this.pem = ComponentMapper.getFor(ParticleEmitterComponent.class);
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
            player.add(RemainInBoundsComponent.create(engine).setMode(BoundMode.CENTER));
            player.add(VelocityComponent.create(engine)
                .setSpeed(0f, 0f));
            player.add(TransformComponent.create(engine)
                    .setOpacity(1f)
                    .setPosition(10f, 10f, Z.player));
            player.add(BoundsComponent.create(engine)
                    .setBounds(3f, 3f, 4.375f, 3.4f));
            player.add(StateComponent.create(engine)
                    .set("DEFAULT")
                    .setLooping(true));
            player.add(TextureComponent.create(engine));
            player.add(AnimationComponent.create(engine)
                    .addAnimation("DEFAULT", Animations.getDaggerIdleAnimation())
                    .addAnimation("DAGGER_IDLE", Animations.getDaggerIdleAnimation())
                    .addAnimation("HATCHET_IDLE", Animations.getHatchetIdleAnimation())
                    .addAnimation("HAMMER_IDLE", Animations.getHammerIdleAnimation())
                    .addAnimation("KATANA_IDLE", Animations.getSwordIdleAnimation())
                    .addAnimation("BUSTER_IDLE", Animations.getBusterIdleAnimation()));

            player.add(HealthComponent.create(engine)
                .setHealth(Health.player).setMaxHealth(Health.player));
            player.add(DamageComponent.create(engine).setDPS(Damage.player_dagger));
            player.add(MultiBoundsComponent.create(engine)
                .addBound(new Bound(new Circle(0f, 0f, 1f), 0f, 0f))
                .addBound(new Bound(new Circle(0f, 0f, 1f), 1f, 0f))
                .addBound(new Bound(new Circle(0f, 0f, 0.5f), 2f, 0f)));
            player.add(ParticleEmitterComponent.create(engine)
                .setParticleImages(Assets.getPurpleParticles())
                .setParticleLifespans(0.3f, 0.5f)
                .setSpawnRate(50f)
                .setShouldFade(true)
                .setAngleRange(0f, 360f)
                .setDuration(10f)
                .setShouldLoop(true)
                .setSpeed(4f, 5f)
                .setZIndex(Z.aura));

            engine.addEntity(player);

        }
        isInitialized = true;
    }

    public void update(float deltaTime) {
        super.update(deltaTime);

        if(!isInitialized){
            init();
        }

        PlayerComponent pc = pm.get(player);
        VelocityComponent vc = vm.get(player);
        TransformComponent tc = tm.get(player);
        RotateToComponent rtc = rtm.get(player);

        //Check level up
        if(currentEnergy > 30){
            levelUp();
            pc.energyLevel = 0;
            currentEnergy = 0;
        }
        else if(currentEnergy < 0){
            levelDown();
            pc.energyLevel = 0;
            currentEnergy = 0;
        }
        else{
            currentEnergy = pc.energyLevel;
        }

        if(isPressed.contains(new Integer(Input.Keys.LEFT)) || isPressed.contains(new Integer(Input.Keys.A))) {
            vc.setSpeed(-10f, vc.speed.y);
        }
        if(isPressed.contains(new Integer(Input.Keys.RIGHT)) || isPressed.contains(new Integer(Input.Keys.D)) ) {
            vc.setSpeed(10f, vc.speed.y);
        }
        if(isPressed.contains(new Integer(Input.Keys.UP)) || isPressed.contains(new Integer(Input.Keys.W))) {
            vc.setSpeed(vc.speed.x, 10f);
        }
        if(isPressed.contains(new Integer(Input.Keys.DOWN)) || isPressed.contains(new Integer(Input.Keys.S))) {
            vc.setSpeed(vc.speed.x, -10f);
        }

        if(isPressed.contains(new Integer(Input.Keys.SPACE))) {

        }

        //attack state
        if(pc.isAttacking){
            if(!rtm.has(player)){
                pc.isAttacking = false;

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
        if(Input.Keys.E == keycode || Input.Keys.SHIFT_LEFT == keycode || Input.Keys.SHIFT_RIGHT == keycode) {
            PlayerComponent pc = pm.get(player);
            ParticleEmitterComponent pec = pem.get(player);
            if(pc.auraType == AuraType.YELLOW){
                pc.auraType = AuraType.PURPLE;
                pec.setParticleImages(Assets.getPurpleParticles());
            }else{
                pc.auraType = AuraType.YELLOW;
                pec.setParticleImages(Assets.getYellowParticles());
            }
        }
        PlayerComponent pc = pm.get(player);
        if(Input.Keys.SPACE == keycode && !pc.isAttacking) {

            pc.isAttacking = true;
            switch(pc.weaponType) {
                case DAGGER :
                    player.add(RotateToComponent.create(getEngine())
                        .addRotateTo(90f, 600f)
                        .addRotateTo(-120f, -900f)
                        .addRotateTo(0f, 3000f));
                    break;
                case HATCHET:
                    player.add(RotateToComponent.create(getEngine())
                            .addRotateTo(90f, 600f)
                            .addRotateTo(-120f, -900f)
                            .addRotateTo(0f, 3000f));
                    break;
                case HAMMER:
                    player.add(RotateToComponent.create(getEngine())
                            .addRotateTo(90f, 600f)
                            .addRotateTo(-200f, -900f)
                            .addRotateTo(0f, 3000f));
                    break;
                case KATANA:
                    player.add(RotateToComponent.create(getEngine())
                            .addRotateTo(90f, 600f)
                            .addRotateTo(-120f, -900f)
                            .addRotateTo(0f, 3000f));
                    break;
                case BUSTER:
                    player.add(RotateToComponent.create(getEngine())
                            .addRotateTo(90f, 600f)
                            .addRotateTo(-120f, -900f)
                            .addRotateTo(0f, 3000f));
                    break;
            }

        }

        if(Input.Keys.NUM_1 == keycode) {
            daggerSfx.play(.5f);
        }
        if(Input.Keys.NUM_2 == keycode) {
            hatchetSfx.play(.5f);
        }
        if(Input.Keys.NUM_3 == keycode) {
            katanaSfx.play(.5f);
        }
        if(Input.Keys.NUM_4 == keycode) {
            busterSfx.play(.5f);
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

    /* Helper functions*/
    public void levelUp() {
        PlayerComponent pc = pm.get(player);
        StateComponent sc = sm.get(player);

        //dagger hatchet katana hammer buster
        switch(pc.weaponType) {
            case DAGGER :
                pc.weaponType = WeaponType.HATCHET;
                sc.set("HATCHET_IDLE");
                updateBounds();
                break;
            case HATCHET:
                pc.weaponType = WeaponType.KATANA;
                sc.set("KATANA_IDLE");
                updateBounds();
                break;
            case KATANA:
                pc.weaponType = WeaponType.HAMMER;
                sc.set("HAMMER_IDLE");
                updateBounds();
                break;
            case HAMMER:
                pc.weaponType = WeaponType.BUSTER;
                sc.set("BUSTER_IDLE");
                updateBounds();
                break;
            case BUSTER:
                //Do nothing YOUR AWESOME
                break;
            default :
                pc.weaponType = WeaponType.HATCHET;
                sc.set("HATCHET_IDLE");
                updateBounds();
                break;
        }
    }

    public void levelDown() {
        PlayerComponent pc = pm.get(player);
        StateComponent sc = sm.get(player);
        //dagger hatchet katana hammer buster
        switch(pc.weaponType) {
            case DAGGER:
                //do nothing or lose condition
                break;
            case HATCHET:
                pc.weaponType = WeaponType.DAGGER;
                sc.set("DAGGER_IDLE");
                updateBounds();
                break;
            case KATANA:
                pc.weaponType = WeaponType.HATCHET;
                sc.set("HATCHET_IDLE");
                updateBounds();
                break;
            case HAMMER:
                pc.weaponType = WeaponType.KATANA;
                sc.set("KATANA_IDLE");
                updateBounds();
                break;
            case BUSTER:
                pc.weaponType = WeaponType.HAMMER;
                sc.set("HAMMER_IDLE");
                updateBounds();
                break;
            default :
                pc.weaponType = WeaponType.HATCHET;
                sc.set("DEFAULT");
                updateBounds();
                break;
        }
    }

    public void updateBounds(){
        PlayerComponent pc = pm.get(player);
        MultiBoundsComponent mbc = mbm.get(player);
        BoundsComponent bc = bm.get(player);
        mbc.bounds.clear();
        switch(pc.weaponType) {
            case DAGGER:
                bc.setBounds(3f, 3f, 4.375f, 3.4f);
                mbc.addBound(new Bound(new Circle(0f, 0f, 1f), 0f, 0f))
                    .addBound(new Bound(new Circle(0f, 0f, 1f), 1f, 0f))
                    .addBound(new Bound(new Circle(0f, 0f, 0.5f), 2f, 0f));
                break;
            case HATCHET:
                bc.setBounds(0f, 0f, 7.6875f,  8.71875f);
                mbc.addBound(new Bound(new Circle(0f, 0f, 1f), 2.50f, 2.75f))
                        .addBound(new Bound(new Circle(0f, 0f, 1f), 2.50f, 2.5f))
                         .addBound(new Bound(new Circle(0f, 0f, 1f), 2.50f, 2.25f));
                break;
            case KATANA:
                bc.setBounds(0f, 0f, 8f, 8f);
                mbc.addBound(new Bound(new Circle(0f, 0f, .8f), 3f, 2.5f))
                        .addBound(new Bound(new Circle(0f, 0f, 1f), 2.5f, 1.8f))
                        .addBound(new Bound(new Circle(0f, 0f, 1f), 2f, 1.2f));
                break;
            case HAMMER:
                bc.setBounds(0f, 0f, 12.03125f, 12.03125f);
                mbc.addBound(new Bound(new Circle(0f, 0f, 1.2f), 2.2f, 4.2f))
                        .addBound(new Bound(new Circle(0f, 0f, 1.2f), 2.2f, 3.2f));
                break;
            case BUSTER:
                bc.setBounds(0f, 0f, 18.6878f, 18.6875f);
                mbc.addBound(new Bound(new Circle(0f, 0f, 1.1f), 4.8f, 2.2f))
                        .addBound(new Bound(new Circle(0f, 0f, 1.1f), 4.8f, 3.2f))
                        .addBound(new Bound(new Circle(0f, 0f, 1.1f), 4.8f, 4.2f))
                        .addBound(new Bound(new Circle(0f, 0f, 1.1f), 4.8f, 5.2f))
                        .addBound(new Bound(new Circle(0f, 0f, 1.1f), 4.8f, 6.2f))
                        .addBound(new Bound(new Circle(0f, 0f, 1.1f), 4.8f, 7.2f))
                        .addBound(new Bound(new Circle(0f, 0f, 1.1f), 4.8f, 8.2f))
                        .addBound(new Bound(new Circle(0f, 0f, 1.1f), 4.8f, 9.2f));
                break;
            default :
                mbc.addBound(new Bound(new Circle(0f, 0f, 1f), 0f, 0f))
                    .addBound(new Bound(new Circle(0f, 0f, 1f), 1f, 0f))
                    .addBound(new Bound(new Circle(0f, 0f, 0.5f), 2f, 0f));
                break;
        }
    }
}

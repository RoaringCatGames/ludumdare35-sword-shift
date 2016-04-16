package com.roaringcatgames.ludumdare.thirtyfive.screens;

import com.badlogic.gdx.ScreenAdapter;

/**
 * Created by barry on 4/16/16 @ 12:07 PM.
 */
public abstract class LazyInitScreen extends ScreenAdapter {

    protected boolean isInitialized = false;

    protected abstract void init();
    protected abstract void update(float deltaChange);

    @Override
    public void render(float delta) {
        super.render(delta);

        if(!isInitialized) {
            init();
            isInitialized = true;
        }

        update(delta);
    }
}
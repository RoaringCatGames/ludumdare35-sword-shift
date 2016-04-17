package com.roaringcatgames.ludumdare.thirtyfive.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.roaringcatgames.ludumdare.thirtyfive.App;

/**
 * Created by barry on 4/16/16 @ 12:07 PM.
 */
public abstract class LazyInitScreen extends ScreenAdapter {

    protected boolean isInitialized = false;

    protected abstract void init();
    protected abstract void update(float deltaChange);

    @Override
    public void render(float delta) {

        float throttledDelta = Math.min(delta, App.MAX_DELTA);

        super.render(throttledDelta);

        if(!isInitialized) {
            init();
            isInitialized = true;
        }

        update(throttledDelta);
    }
}
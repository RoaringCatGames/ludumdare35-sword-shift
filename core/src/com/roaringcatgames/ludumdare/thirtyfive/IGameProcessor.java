package com.roaringcatgames.ludumdare.thirtyfive;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by barry on 4/16/16 @ 12:24 PM.
 */
public interface IGameProcessor {

    SpriteBatch getBatch();
    void switchScreens(String screenName);
    void addInputProcessor(InputProcessor processor);
    void removeInputProcessor(InputProcessor processor);
}

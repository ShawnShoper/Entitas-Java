package com.ilargia.games.egdx.transitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.ilargia.games.egdx.EGEngine;
import com.ilargia.games.egdx.base.BaseTransition;


public abstract class RenderTransition extends BaseTransition{
    protected final EGEngine engine;
    private FrameBuffer currFbo;
    private FrameBuffer nextFbo;
    Texture current, next;

    public RenderTransition(float duration, EGEngine engine) {
        super(duration);
        this.engine = engine;
    }

    @Override
    public void loadResources() {
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        nextFbo = new FrameBuffer(Pixmap.Format.RGBA8888, w, h, true);
        currFbo = new FrameBuffer(Pixmap.Format.RGBA8888, w, h, true);
    }

    @Override
    public void init() {
        currFbo.begin();
        oldState.render();
        currFbo.end();
        oldState.onPause();
        oldState.dispose();

        nextFbo.begin();
        newState.update(0);
        newState.render();
        nextFbo.end();

        current = currFbo.getColorBufferTexture();
        next = nextFbo.getColorBufferTexture();
    }

    @Override
    public void dispose() {
        currFbo.dispose();
        nextFbo.dispose();
        currFbo = null;
        nextFbo = null;

    }
}

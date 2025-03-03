package io.alliancetable.main.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.alliancetable.main.Main;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericScreen implements Screen {

    protected Main myGame;
    protected List<Viewport> viewports;
    protected SpriteBatch batch;
    protected int lastWindowWidth;
    protected int lastWindowHeight;
    protected int worldWidth = Main.WORLD_WIDTH;
    protected int worldHeight = Main.WORLD_HEIGHT;
    protected int windowWidth = Main.WORLD_WIDTH;
    protected int windowHeight = Main.WORLD_HEIGHT;
    protected boolean isFullscreen = false;

    public GenericScreen(Main game) {
        this.myGame = game;
        this.viewports = new ArrayList<Viewport>();
        this.batch = game.batch;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
    }

    @Override
    public void resize(int width, int height) {

        windowWidth = width;
        windowHeight = height;
        if(!Gdx.graphics.isFullscreen()) {
            lastWindowWidth = width;
            lastWindowHeight = height;
        }
        for(Viewport viewport : viewports) {
            viewport.update(windowWidth, windowHeight, true);
        }
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {
        myGame.inputMultiplexer.addProcessor(new InputProcessor());
    }

    public boolean isScreenSet() {
        return myGame.getScreen().equals(this);
    }

    private void toggleFullscreen() {
        if (isFullscreen) {
            // Passa a modalità finestra
            Gdx.graphics.setWindowedMode(lastWindowWidth, lastWindowHeight); // Imposta le dimensioni della finestra
        } else {
            // Passa a modalità schermo intero
            Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
            Gdx.graphics.setFullscreenMode(displayMode);
        }
        isFullscreen = !isFullscreen;
    }

    private class InputProcessor extends InputAdapter {
        @Override
        public boolean keyDown(int keycode) {
            if (keycode == Input.Keys.ENTER && Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT)) {
                toggleFullscreen();
                return true; // Evento gestito
            }
            return false; // Evento non gestito
        }
    }
}

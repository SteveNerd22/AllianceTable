package io.alliancetable.main.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.alliancetable.main.Main;

public abstract class GenericScreen implements Screen {

    protected Main myGame;
    protected FitViewport viewport;
    protected int lastWindowWidth;
    protected int lastWindowHeight;
    protected int worldWidth = Main.WORLD_WIDTH;
    protected int worldHeight = Main.WORLD_HEIGHT;
    protected int windowWidth = Main.WORLD_WIDTH;
    protected int windowHeight = Main.WORLD_HEIGHT;
    protected boolean isFullscreen = false;

    public GenericScreen(Main game) {
        this.myGame = game;
        viewport = game.viewport;
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
        myGame.viewport.update(width, height, true);
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
            if(keycode == Input.Keys.C) {
                if(Gdx.graphics.getWidth() == 1280) {
                    Gdx.graphics.setWindowedMode(400, 180);
                    myGame.viewport.update(400, 180, true);
                    myGame.viewport.setWorldSize(Main.WORLD_WIDTH, Main.WORLD_HEIGHT);
                }
                else if(Gdx.graphics.getWidth() == 400) {
                    Gdx.graphics.setWindowedMode(600, 400);
                    myGame.viewport.update(600, 400, true);
                    myGame.viewport.setWorldSize(Main.WORLD_WIDTH, Main.WORLD_HEIGHT);
                } else if(Gdx.graphics.getWidth() == 600) {
                    Gdx.graphics.setWindowedMode(800, 600);
                    myGame.viewport.update(800, 600, true);
                    myGame.viewport.setWorldSize(Main.WORLD_WIDTH, Main.WORLD_HEIGHT);
                } else  {
                    Gdx.graphics.setWindowedMode(1280, 720);
                    myGame.viewport.update(1280, 720, true);
                    myGame.viewport.setWorldSize(Main.WORLD_WIDTH, Main.WORLD_HEIGHT);
                }

                return true;
            }
            if(keycode == Input.Keys.X){
                System.out.println("Gdx:      " + Gdx.graphics.getWidth()+"x"+Gdx.graphics.getHeight());
                System.out.println("Viewport: "+ myGame.viewport.getScreenWidth());
                System.out.println("world:    "+ myGame.viewport.getWorldWidth());
            }

            return false; // Evento non gestito
        }
    }
}

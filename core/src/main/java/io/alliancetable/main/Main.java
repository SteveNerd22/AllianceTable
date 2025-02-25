package io.alliancetable.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.alliancetable.main.screens.MainScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    public final static int WORLD_WIDTH = 1280, WORLD_HEIGHT = 720;

    public SpriteBatch batch;
    public AssetsHandler assetsHandler;
    public ShapeRenderer shapeRenderer;
    public EventDispatcher eventDispatcher;
    public Network network;
    public InputMultiplexer inputMultiplexer = new InputMultiplexer();

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        eventDispatcher = new EventDispatcher();
        network = new Network();
        assetsHandler = new AssetsHandler(this);
        Gdx.input.setInputProcessor(inputMultiplexer);
        setScreen(new MainScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }
}

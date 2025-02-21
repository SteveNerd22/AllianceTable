package io.alliancetable.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.alliancetable.main.screens.MainMenu;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    public final static int WORLD_WIDTH = 1280, WORLD_HEIGHT = 720;

    public OrthographicCamera camera;
    public FitViewport viewport;
    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public EventDispatcher eventDispatcher;
    public AssetsHandler assetsHandler;
    public InputMultiplexer inputMultiplexer = new InputMultiplexer();

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        eventDispatcher = new EventDispatcher();
        assetsHandler = new AssetsHandler();
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        batch.setProjectionMatrix(camera.combined);
        //shapeRenderer.setProjectionMatrix(camera.combined);
        Gdx.input.setInputProcessor(inputMultiplexer);
        setScreen(new MainMenu(this));
    }


    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }
}

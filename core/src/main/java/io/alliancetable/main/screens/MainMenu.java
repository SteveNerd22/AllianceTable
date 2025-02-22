package io.alliancetable.main.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import io.alliancetable.main.Main;

public class MainMenu extends GenericScreen {

    private ExtendViewport viewport;
    private OrthographicCamera camera;
    private Texture background;

    public MainMenu(Main game) {
        super(game);
        background = game.assetsHandler.hostPool.getTexture("logo.png");
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(worldWidth, worldHeight, camera);
        viewports.add(viewport);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();
    }
}



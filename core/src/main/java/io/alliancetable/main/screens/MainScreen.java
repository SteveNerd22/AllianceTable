package io.alliancetable.main.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.alliancetable.main.Main;
import io.alliancetable.main.scenes.UI;

public class MainScreen extends GenericScreen {

    private ScreenViewport viewport;
    private OrthographicCamera camera;
    private Texture background;
    private UI ui;

    public MainScreen(Main game) {
        super(game);
        background = game.assetsHandler.hostPool.getTexture("logo.png");
        ui = new UI(batch);
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        viewports.add(viewport);
        viewports.add(ui.viewport);
        game.inputMultiplexer.addProcessor(ui.stage);
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
        batch.setProjectionMatrix(ui.stage.getViewport().getCamera().combined);
        ui.stage.act(delta);
        ui.stage.draw();
    }
}



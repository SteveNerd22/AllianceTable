package io.alliancetable.main.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class UI {
    public Stage stage;
    public Viewport viewport;

    private Table layersTable;
    private Table viewsTable;
    private Table menuTable;

    public UI(SpriteBatch batch) {
        viewport = new ScreenViewport(new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        Container<Label> menu = generateContainer(new Color(0.9f, 0.5f, 0.2f, 1), "Menu", skin);
        Container<Label> views = generateContainer(new Color(0.12f, 0.34f, 0.56f, 1), "Views", skin);
        Container<Label> layers = generateContainer(new Color(0.24f, 0.68f, 0.54f, 1), "Layers", skin);

        viewsTable = new Table();
        menuTable = new Table();
        layersTable = new Table();

        SplitPane viewsSplit = new SplitPane(views, null, true, skin);
        SplitPane layersSplit = new SplitPane(null, layers, true, skin);
        SplitPane menuSplit = new SplitPane(null, menu, false, skin);

        viewsSplit.getStyle().handle.setMinHeight(5f);
        menuSplit.getStyle().handle.setMinWidth(5f);
        layersSplit.getStyle().handle.setMinHeight(5f);

        viewsTable.top();
        viewsTable.add(viewsSplit).height(viewport.getScreenHeight() / 4.5454f).expandX().fill();
        menuTable.right();
        menuTable.add(menuSplit).width(viewport.getScreenWidth() / 4f).expandY().fill();
        layersTable.bottom();
        layersTable.add(layersSplit).height(viewport.getScreenHeight() / 4.5454f).expandX().fill();

        layersSplit.setSplitAmount(0.82f);

        viewsSplit.setSplitAmount(0.18f);

        menuSplit.setSplitAmount(0.82f);

        stage.addActor(viewsTable);
        stage.addActor(layersTable);
        stage.addActor(menuTable);

        viewsTable.setFillParent(true);
        menuTable.setFillParent(true);
        layersTable.setFillParent(true);
    }

    private Container<Label> generateContainer(Color color, String name, Skin skin) {
        Container<Label> container = new Container<>(new Label(name, skin));
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        container.setBackground(drawable);
        return container;
    }
}

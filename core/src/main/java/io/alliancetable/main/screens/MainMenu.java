package io.alliancetable.main.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import io.alliancetable.main.Main;

public class MainMenu extends GenericScreen {

    private Stage mainStage;
    private Stage actualStage;
    TextureRegionDrawable startDrawable;
    TextureRegionDrawable optionsDrawable;
    TextureRegionDrawable startDrawable2;
    TextureRegionDrawable optionsDrawable2;

    ClickListener startListener;
    ClickListener optionsListener;

    public MainMenu(Main game) {
        super(game);

        mainStage = new Stage(viewport);

        Texture startTexture = new Texture("start.png");
        Texture optionsTexture = new Texture("options.png");
        Texture startTexture2 = new Texture("startOver.png");
        Texture optionsTexture2 = new Texture("optionsOver.png");
        startDrawable = new TextureRegionDrawable(startTexture);
        optionsDrawable = new TextureRegionDrawable(optionsTexture);
        startDrawable2 = new TextureRegionDrawable(startTexture2);
        optionsDrawable2 = new TextureRegionDrawable(optionsTexture2);

        ImageButton start = new ImageButton(startDrawable, startDrawable2);
        ImageButton options = new ImageButton(optionsDrawable, optionsDrawable2);

        start.setSize(startTexture.getWidth(), startTexture.getHeight());

        start.setName("start");
        options.setName("options");
        start.setWidth(startTexture.getWidth());
        start.setHeight(startTexture.getHeight());
        start.setPosition(((float) worldWidth / 2f) - start.getWidth() / 2f, (((float) worldHeight) / 2f - start.getHeight() / 2f) - 100f);
        options.setWidth(optionsTexture.getWidth());
        options.setHeight(optionsTexture.getHeight());
        options.setPosition(((float) worldWidth / 2f) - options.getWidth() / 2f, (((float) worldHeight) / 2f - options.getHeight() / 2f) + 100f);

        startListener = new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                //myGame.setScreen(new GameScreen(myGame));
            }
        };

        optionsListener = new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        };

        start.addListener(startListener);
        options.addListener(optionsListener);

        mainStage.addActor(start);
        mainStage.addActor(options);
        actualStage = mainStage;
        myGame.inputMultiplexer.addProcessor(actualStage);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        SpriteBatch batch = myGame.batch;
        for(Actor actor: mainStage.getActors()) {
            if(actor instanceof ImageButton button) {
                if(button.getName().equals("start")) {
                    if(button.isOver()) {
                        button.setBackground(startDrawable2);
                    }else {
                        button.setBackground(startDrawable);
                    }
                }
            }
        }
        batch.begin();
        mainStage.draw();
        //batch.draw(options, (float) windowWidth / 2f - (((float)options.getWidth() / 2f)*scaleX), (float) windowHeight / 2f - (((float) options.getHeight() / 2f -100f)*scaleY),options.getWidth()*scaleX, options.getHeight()*scaleY);
        batch.end();
    }
}



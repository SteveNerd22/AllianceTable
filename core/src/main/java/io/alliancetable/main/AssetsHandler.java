package io.alliancetable.main;

import com.badlogic.gdx.graphics.Texture;

import java.nio.ByteBuffer;
import java.util.List;

public class AssetsHandler {
    public TexturePool defaultPool;
    public TexturePool hostPool;
    private Main game;

    public AssetsHandler(Main game) {
        this.game = game;

        defaultPool = new TexturePool(100);
        hostPool = new TexturePool(100, false);

        List<DirectoryItem> items = game.network.getPublicDir();
        if(items != null) {
            for (DirectoryItem item : items) {
                if (item.getType().equals("cartella") && item.getName().equals("images")) {
                    for (DirectoryItem image : item.getContents()) {
                        if (!image.getType().equals("cartella")) {
                            ByteBuffer b = game.network.getFile("images/" + image.getName());
                            if (b != null) {
                                hostPool.addTexture(image.getName(), b);
                            }
                        }
                    }
                }
            }
        }
    }
}

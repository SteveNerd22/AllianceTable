package io.alliancetable.main;

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
        for (DirectoryItem item : items) {
            if(item.getType().equals("cartella") && item.getName().equals("images")) {
                for(DirectoryItem image : item.getContents()) {
                    if(!image.getType().equals("cartella"))
                        hostPool.addTexture(image.getName(), game.network.getFile("images/"+image.getName(), image.getName()));
                }
            }
        }
    }
}

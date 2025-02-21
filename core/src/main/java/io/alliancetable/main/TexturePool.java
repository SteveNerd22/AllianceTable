package io.alliancetable.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TexturePool {

    private final long MAX_MEMORY = Runtime.getRuntime().maxMemory() * 2 / 3;
    private long maxMemoryBytes;
    private long actualMemoryBytes;

    private final LinkedHashMap<String, TextureInfo> textures;

    public TexturePool(long maxMemoryMegaBytes) {
        if(maxMemoryMegaBytes > MAX_MEMORY)
            maxMemoryBytes = MAX_MEMORY;
        else
            this.maxMemoryBytes = maxMemoryMegaBytes * 1024 * 1024;
        this.actualMemoryBytes = 0;
        this.textures = new LinkedHashMap<>(16, 0.75f, true);
        fillPool();
    }

    public void addTexture(String textureId) {
        FileHandle file = Gdx.files.internal(textureId);
        long textureSizeBytes = file.length();

        if (actualMemoryBytes + textureSizeBytes > maxMemoryBytes) {
            freeMemory(textureSizeBytes);
        }

        Texture texture = new Texture(textureId);

        textures.put(textureId, new TextureInfo(texture, textureSizeBytes));

        actualMemoryBytes += textureSizeBytes;
    }

    public Texture getTexture(String textureId) {
        if (textures.containsKey(textureId)) {
            return textures.get(textureId).texture;
        }
        return null;
    }

    public void resizePool(long newMaxMemoryMegaBytes) {
        if(newMaxMemoryMegaBytes > MAX_MEMORY)
            maxMemoryBytes = MAX_MEMORY;
        else
            this.maxMemoryBytes = newMaxMemoryMegaBytes * 1024 * 1024;

        while (actualMemoryBytes > maxMemoryBytes) {
            freeOldestTexture();
        }
    }

    private void freeMemory(long requiredMemoryBytes) {
        while (actualMemoryBytes + requiredMemoryBytes > maxMemoryBytes) {
            freeOldestTexture();
        }
    }

    private void freeOldestTexture() {
        Iterator<Map.Entry<String, TextureInfo>> iterator = textures.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<String, TextureInfo> oldestEntry = iterator.next();
            String oldestTextureId = oldestEntry.getKey();
            TextureInfo oldestTexture = oldestEntry.getValue();
            actualMemoryBytes -= oldestTexture.bytes;
            oldestTexture.texture.dispose();
            iterator.remove();
        }
    }

    // Metodo per ottenere la memoria massima in megabyte (per visualizzazione)
    public double getMaxMemoryInMegaBytes() {
        return (double) maxMemoryBytes / (1024 * 1024);
    }

    // Metodo per ottenere la memoria utilizzata in megabyte (per visualizzazione)
    public double getActualMemoryInMegaBytes() {
        return (double) actualMemoryBytes / (1024 * 1024);
    }

    // Metodo per ottenere la memoria utilizzata in kilobyte (per visualizzazione)
    public double getActualMemoryInKiloBytes() {
        return (double) actualMemoryBytes / (1024);
    }

    // Metodo per ottenere la memoria disponibile in megabyte
    public double getAvailableMemoryInMegaBytes() {
        return (double) (maxMemoryBytes - actualMemoryBytes) / (1024 * 1024);
    }

    private boolean canFit(long requiredMemoryBytes) {
        return requiredMemoryBytes + actualMemoryBytes < maxMemoryBytes;
    }

    // Metodo per riempire il pool di texture fino a quando c'è spazio
    private void fillPool() {
        boolean stillSpace = true;
        FileHandle assetsDir = Gdx.files.local("assets");
        List<FileHandle> assets = List.of(assetsDir.list((dir, name) -> name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".gif") || name.endsWith(".bmp") || name.endsWith(".tga")));
        Iterator<FileHandle> iterator = assets.iterator();
        do {
            FileHandle file;
            if (iterator.hasNext())
                file = iterator.next();
            else {
                stillSpace = false;
                continue;
            }
            if (canFit(file.length())) {
                System.out.println(file.name());
                addTexture(file.name());
            } else {
                stillSpace = false;
            }
        } while (stillSpace);

        String actualMemoryRoundedString;

        if (actualMemoryBytes > 1024 * 1024) {
            BigDecimal actualMemoryRounded = BigDecimal.valueOf(getActualMemoryInMegaBytes()).setScale(2, RoundingMode.HALF_UP);
            actualMemoryRoundedString = actualMemoryRounded + "MB";
        } else if (actualMemoryBytes > 1024) {
            BigDecimal actualMemoryRounded = BigDecimal.valueOf(getActualMemoryInKiloBytes()).setScale(2, RoundingMode.HALF_UP);
            actualMemoryRoundedString = actualMemoryRounded + "KB";
        } else {
            actualMemoryRoundedString = actualMemoryBytes + "B";
        }

        BigDecimal actualMemoryRounded = BigDecimal.valueOf(getActualMemoryInMegaBytes()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal maxMemoryRounded = BigDecimal.valueOf(getMaxMemoryInMegaBytes()).setScale(2, RoundingMode.HALF_UP);
        System.out.println("DONE: pool filled\n" + actualMemoryRoundedString + "/" + maxMemoryRounded + "MB");
    }

    // Metodo per pulire tutte le risorse nel pool
    public void disposeAll() {
        for (TextureInfo textureInfo : textures.values()) {
            textureInfo.texture.dispose();
        }
        textures.clear();
        actualMemoryBytes = 0;
    }

    public void printTexturesByAccessOrder() {
        System.out.println("Textures in access order:");
        for (Map.Entry<String, TextureInfo> entry : textures.entrySet()) {
            System.out.println(entry.getKey() + " - Size: " + entry.getValue().bytes + " bytes");
        }
    }

    private class TextureInfo {
        protected Texture texture;
        protected long bytes;

        public TextureInfo(Texture texture, long bytes) {
            this.texture = texture;
            this.bytes = bytes;
        }
    }
}

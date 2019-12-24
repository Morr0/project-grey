package rami.project.grey.ui.resources;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public final class ResourcesManager {
    private AssetManager assetManager;

    private HashMap<String, Texture> textures;

    // TODO use name registery
    public ResourcesManager(){
        assetManager = new AssetManager();
        textures = new HashMap<>();

        // List to be loaded
        assetManager.load("Background-Testing.jpg", Texture.class);
        assetManager.load("Chika/Chika1-1.png", Texture.class);

        assetManager.update();
    }

    // Assigns the textures to be stored in the list of textures so that they can be used later on
    public void assignReferences(){
        // ASSETS TO BE LOADED
        // Textures
        textures.put("Background", assetManager.get("Background-Testing.jpg", Texture.class));
        textures.put("Chika1-1", assetManager.get("Chika/Chika1-1.png", Texture.class));
    }

    public Texture getBackground(){
        return textures.get("Background");
    }

    public Texture getBigChika() { return textures.get("Chika1-1"); }

    public Texture getTowedChika() { return textures.get("Chika1-1"); }

    public Texture getChika1(){
        return textures.get("Chika1-1");
    }

    public void dispose(){
        textures.clear();
        assetManager.dispose();
    }

    public boolean finishedLoadingEverything(){
        assetManager.update();
        return assetManager.isFinished();
    }


}

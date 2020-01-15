package rami.project.grey.ui.resources;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

import rami.project.grey.core.entity.consumable.attachables.weaponery.WeaponType;

public final class ResourcesManager {
    private AssetManager assetManager;

    private HashMap<String, Texture> textures;

    // TODO use name registery
    public ResourcesManager(){
        assetManager = new AssetManager();
        textures = new HashMap<>();

        // List to be loaded
        assetManager.load("Background-Testing.jpg", Texture.class);
        assetManager.load("Chika/Chika1-1.jpg", Texture.class);
        assetManager.load("Loot/Coin/Standard_Coin.jpeg", Texture.class);
//        assetManager.load("Weaponery/Weapons/Standard_Gun.png", Texture.class);

        assetManager.update();
    }

    // Assigns the textures to be stored in the list of textures so that they can be used later on
    public void assignReferences(){
        // ASSETS TO BE LOADED
        // Textures
        textures.put("Background", assetManager.get("Background-Testing.jpg", Texture.class));
        textures.put("Chika1-1", assetManager.get("Chika/Chika1-1.jpg", Texture.class));
        textures.put("Standard_Coin", assetManager.get("Loot/Coin/Standard_Coin.jpeg", Texture.class));
//        textures.put("StandardGun", assetManager.get("Weaponery/Weapons/Standard_Gun.png", Texture.class));

    }

    public Texture getBackground(){
        return textures.get("Background");
    }

    public Texture getBigChika() { return textures.get("Chika1-1"); }

    public Texture getTowedChika() { return textures.get("Chika1-1"); }

    public Texture getChika1(){
        return textures.get("Chika1-1");
    }

    public Texture getCoin(){
        return textures.get("Standard_Coin");
    }

//    public Texture getGun(){
//        return textures.get("StandardGun");
//    }


//    public Texture getTexture(ITypeHasTexture type){
//        if (type instanceof WeaponType){
//
//        }
//
//    }

    public void dispose(){
        textures.clear();
        assetManager.dispose();
    }

    public boolean finishedLoadingEverything(){
        assetManager.update();
        return assetManager.isFinished();
    }


}

package rami.project.grey.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import rami.project.grey.core.NameRegistery;

// TODO use name registery
public final class Player {
    private Preferences prefs;

    Player() {
        prefs = Gdx.app.getPreferences(NameRegistery.PPLAYER);
    }

    public String getName(){
        return prefs.getString(NameRegistery.PPLAYER_NAME, NameRegistery.PPLAYER_NAME_DEFAULT);
    }

    public void setName(String name){
        prefs.putString(NameRegistery.PPLAYER_NAME, name);
    }

    public float getCash(){
        return prefs.getFloat(NameRegistery.PPLAYER_CASH, NameRegistery.PLAYER_CASH_STARTING);
    }

    /**
     * Increments or decrements by the amount given
     */
    public void changeCash(float offset){
        float currentCash = getCash();
        currentCash += offset;
        prefs.putFloat(NameRegistery.PPLAYER_CASH, currentCash);
    }
}

package rami.project.grey.gameplay;

import rami.project.grey.core.NameRegistery;
import rami.project.grey.core.item.ItemInventory;

// TODO use name registery
public final class Player {
    CommonPreferences prefs;

    // CONSTANS
    private static final String GENERAL_PREFS_HAS_INVENTORY = "general-has-inventory";

    Player() {
        prefs = new CommonPreferences();
    }

    public String getName(){
        return prefs.getString(CommonPreferences.GENERAL_PREFERENCES, NameRegistery.PPLAYER_NAME, NameRegistery.PPLAYER_NAME_DEFAULT);
    }

    public void setName(String name){
        prefs.putString(CommonPreferences.GENERAL_PREFERENCES, NameRegistery.PPLAYER_NAME, name);
    }

    public float getCash(){
        return prefs.getFloat(CommonPreferences.GENERAL_PREFERENCES, NameRegistery.PPLAYER_CASH, NameRegistery.PLAYER_CASH_STARTING);
    }

    public int getCurrentXPLevel(){
        double factor = -Math.log10(0.0000000002d)/10;
        double level = factor * prefs.getLong(CommonPreferences.GENERAL_PREFERENCES,
                XPHandler.XP, 0);
        return (int) Math.floor(level);
    }

    /**
     * Increments or decrements by the amount given
     */
    public void changeCash(float offset){
        float currentCash = getCash();
        currentCash += offset;
        prefs.putFloat(CommonPreferences.GENERAL_PREFERENCES, NameRegistery.PPLAYER_CASH, currentCash);
    }

    byte maxAllowableTowes(){
        return 2;
    }

    byte maxAllowableAttachments(){ return 2; }

    ItemInventory getPlayerInventory(){
        if (!prefs.getBoolean(CommonPreferences.GENERAL_PREFERENCES, GENERAL_PREFS_HAS_INVENTORY, false)){
            // Tell inventory it has one
            prefs.putBoolean(CommonPreferences.GENERAL_PREFERENCES, GENERAL_PREFS_HAS_INVENTORY, true);

            return new ItemInventory();
        } else {
            // TODO retrieve the inventory from storage
            return new ItemInventory();
        }
    }
}

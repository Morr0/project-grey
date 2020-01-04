package rami.project.grey.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.HashMap;
import java.util.Map;

final class CommonPreferences {
    // Pref Categories
    static final String GENERAL_PREFERENCES = "prefs_general";

    // Holds all preferences in a map for easy access
    private HashMap<String, Preferences> prefs;

    CommonPreferences(){
        this.prefs = new HashMap<>();
        this.prefs.put(GENERAL_PREFERENCES, Gdx.app.getPreferences(GENERAL_PREFERENCES));
    }

    void putString(String prefCategory, String name, String value) {
        Preferences preferences = this.prefs.get(prefCategory);
        preferences.putString(name, value);
    }

    void putBoolean(String prefCategory, String name, boolean value) {
        Preferences preferences = this.prefs.get(prefCategory);
        preferences.putBoolean(name, value);
    }

    void putByte(String prefCategory, String name, byte value) {
        Preferences preferences = this.prefs.get(prefCategory);
        preferences.putInteger(name, value);
    }

    void putShort(String prefCategory, String name, short value){
        Preferences preferences = this.prefs.get(prefCategory);
        preferences.putInteger(name, value);
    }

    void putInt(String prefCategory, String name, int value){
        Preferences preferences = this.prefs.get(prefCategory);
        preferences.putInteger(name, value);
    }

    void putLong(String prefCategory, String name, long value){
        Preferences preferences = this.prefs.get(prefCategory);
        preferences.putLong(name, value);
    }

    void putFloat(String prefCategory, String name, float value){
        Preferences preferences = this.prefs.get(prefCategory);
        preferences.putFloat(name, value);
    }

    String getString(String prefCategory, String name, String defaultValueIfDoesNotExist){
        Preferences preferences = this.prefs.get(prefCategory);
        return preferences.getString(name, defaultValueIfDoesNotExist);
    }

    boolean getBoolean(String prefCategory, String name, boolean defaultValueIfDoesNotExist){
        Preferences preferences = this.prefs.get(prefCategory);
        return preferences.getBoolean(name, defaultValueIfDoesNotExist);
    }

    byte getByte(String prefCategory, String name, byte defaultValueIfDoesNotExist){
        Preferences preferences = this.prefs.get(prefCategory);
        return (byte) preferences.getInteger(name, defaultValueIfDoesNotExist);
    }

    short getShort(String prefCategory, String name, short defaultValueIfDoesNotExist){
        Preferences preferences = this.prefs.get(prefCategory);
        return (short) preferences.getInteger(name, defaultValueIfDoesNotExist);
    }

    int getInt(String prefCategory, String name, int defaultValueIfDoesNotExist){
        Preferences preferences = this.prefs.get(prefCategory);
        return preferences.getInteger(name, defaultValueIfDoesNotExist);
    }

    long getLong(String prefCategory, String name, long defaultValueIfDoesNotExist){
        Preferences preferences = this.prefs.get(prefCategory);
        return preferences.getLong(name, defaultValueIfDoesNotExist);
    }

    float getFloat(String prefCategory, String name, float defaultValueIfDoesNotExist){
        Preferences preferences = this.prefs.get(prefCategory);
        return preferences.getFloat(name, defaultValueIfDoesNotExist);
    }

    // Saves data
    void flush(){
        for (Map.Entry<String, Preferences> entry: prefs.entrySet()){
            Preferences preferences = entry.getValue();
            preferences.flush();
        }
    }

}

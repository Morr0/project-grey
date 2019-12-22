package rami.project.grey.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import rami.project.grey.Game;

public abstract class BaseScreen implements InputProcessor {
    protected Game game;
    Screen screenType;
    protected int gWidth;
    protected int gHeight;

    // To be used for initialising as well
    BaseScreen(Game game, Screen screenType){
        this.game = game;
        this.screenType = screenType;

        updateResolution();
        Gdx.input.setInputProcessor(this);
    }

    public abstract void update(float dt);
    public abstract void draw(float dt);
    public void dispose(){
        Gdx.input.setInputProcessor(null);
    }
    // A callback so that the current screen can update it's stuff
    protected abstract void screenResolutionUpdated();

    // This must be always be called from the GAME class
    public void updateResolution(){
        // TODO Make synchronised width and height
        this.gWidth = Gdx.graphics.getWidth();
        this.gHeight = Gdx.graphics.getHeight();

        screenResolutionUpdated();
    }

    // Logging conveniences
    public void log(int message){
        String m = Integer.toString(message);
        log(m);
    }

    public void log(float message){
        String m = Float.toString(message);
        log(m);
    }

    public void log(String message){
        Gdx.app.log("Game", message);
    }

    public enum Screen{
        SPLASH,
        MENU,
        SPLASH_EXTRA, // This maybe useful for loading between menu and play and vice versa
        PLAY
    }

    // INPUT PROCESSOR that are not used by inheritances ... KEEP IT LIKE SO


    @Override
    // So it can be used on windows
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public final boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public final boolean keyTyped(char character) {
        return false;
    }

    @Override
    public final boolean touchDown(int screenX, int screenY, int pointer, int button) {
        initialTime = System.currentTimeMillis();
        touchedOn(screenX, gHeight - screenY);
        return false;
    }

    @Override
    public final boolean touchUp(int screenX, int screenY, int pointer, int button) {
        int screeenY = gHeight - screenY;
        touchedOff(screenX, screeenY);

        // Clicked() method handling
        clicked(screenX, screeenY, (System.currentTimeMillis() - initialTime) / 1000f);
        return false;
    }

    @Override
    public final boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public final boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public final boolean scrolled(int amount) {
        return false;
    }

    // Input handler timing
    // whenever a new touchDown occurs the time gets reset
    private long initialTime;

    abstract protected void touchedOn(int screenX, int screenY);
    abstract protected void touchedOff(int screenX, int screenY);
    /**
     * Called after touchedOn and touchedOff.
     * */
    abstract protected void clicked(int screenX, int screenY, float elaspedMillisSeconds);

    // TODO implement touch dragging
}

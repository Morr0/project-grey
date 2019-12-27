package rami.project.grey.ui.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import rami.project.grey.Game;

/**
 *
 * Synchronises the movement of the background to appear as though the player is moving whereas the background really is moving.
 *
 */

// TODO implement acceleration
public final class ScreenBackground {
    private Game game;
    private BaseScreen screen;
    private Texture background;
    private SpriteBatch batch;

    // MOTION Constants
    public static final int INITIAL_SPEED = 100;

    // MOTION , only moves vertically
    // INTERNAL
    private float height1, height2;

    ScreenBackground(Game game, BaseScreen screen, SpriteBatch batch) {
        this.game = game;
        this.screen = screen;
        this.batch = batch;

        switch (screen.screenType){
            case PLAY:
                background = game.res.getBackground();
                break;
        }

        height1 = 0;
        height2 = background.getHeight();
    }

    /**
     * Internal updating only. i.e. doing it's job
     * */
    public void update(float dt, float currentSpeed){
        // INTERNAL UPDATING ONLY
        float decrementBy =  dt * currentSpeed;
        height1 = height1 - decrementBy;
        height2 = height2 - decrementBy;

        // Put the background back on top of the other
        if ((height1 + background.getHeight()) <= 0){
            height1 = height2 + background.getHeight();
        }
        if ((height2 + background.getHeight()) <= 0){
            height2 = height1 + background.getHeight();
        }
    }

    void draw(){
        batch.draw(background, 0, height1, screen.gWidth, screen.gHeight);
        batch.draw(background, 0, height2, screen.gWidth, screen.gHeight);
    }

}

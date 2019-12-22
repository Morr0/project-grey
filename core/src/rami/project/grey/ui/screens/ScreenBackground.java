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
final class ScreenBackground {
    private Game game;
    private BaseScreen screen;
    private Texture background;
    private SpriteBatch batch;

    // MOTION Constants
    public static final int INITIAL_SPEED = 100;

    // MOTION , only moves vertically
    private float height1, height2;
    private float currentSpeed = 100;
    private float acceleration = 0;


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

    void update(float dt){
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

    // INTERACTION METHODS

    public void toggleStop(boolean immediateStopn){
        if (currentSpeed == 0)
            currentSpeed = INITIAL_SPEED;
        else
            currentSpeed = 0;
    }

    public float getSpeed(){
        return currentSpeed;
    }

    public float getAcceleration(){
        return 0;
    }

    public void setAcceleration(float acceleration){
        this.acceleration = acceleration;
    }

}

package rami.project.grey.ui.screens;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public final class PlayerHud {
    private BitmapFont font;

    private int gWidth, gHeight;

    // Every how many frames to update the hud
    byte FRAMES_TO_UPDATE = 2;
    private byte updateRate = FRAMES_TO_UPDATE;

    // FPS
    boolean showFps = false;

    // Score
    boolean showScore = true;

    PlayerHud(int gWidth, int gHeight){
        font = new BitmapFont();

        onResize(gWidth, gHeight);
    }

    void onResize(int gWidth, int gHeight){
        this.gWidth = gWidth;
        this.gHeight = gHeight;
        setHudLocations();
    }

    // HUD positions
    private Vector2 fpsPos = new Vector2();
    private Vector2 scorePos = new Vector2();

    private void setHudLocations(){
        int propsX = gWidth / 20, propsY = gHeight / 20;

        fpsPos.x = propsX * 2;
        fpsPos.y = propsY * 19;

        scorePos.x = propsX * 4;
        scorePos.y = propsY * 19;
    }

    void update(){
        // Update controlling logic
        if (updateRate == (byte) 5)
            updateRate = 0;

        updateRate++;
    }

    void draw(SpriteBatch batch, float score, int fps){
        if (updateRate == FRAMES_TO_UPDATE) {
            if (showFps)
                font.draw(batch, "FPS: " + fps, fpsPos.x, fpsPos.y);
            if (showScore)
                font.draw(batch, "Score: " + score, scorePos.x, scorePos.y);
        }
    }
}

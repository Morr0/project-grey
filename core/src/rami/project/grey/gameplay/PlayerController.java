package rami.project.grey.gameplay;

import com.badlogic.gdx.math.Vector2;

import rami.project.grey.core.entity.chika.BigChika;
import rami.project.grey.core.gridsystem.GridManager;
import rami.project.grey.core.gridsystem.GridSubscriber;
import rami.project.grey.ui.screens.PlayerHud;
import rami.project.grey.ui.screens.ScreenBackground;

/**
 * Is what controls the player where it coordinates between the logic and view of the game so each thing can do its own.
 * This class is what controls every movable thing on the screen.
 * */
public final class PlayerController implements GridSubscriber {
    private ScreenBackground background;
    private PlayerHud hud;
    private Player player;
    public BigChika view;
    private GridManager gridManager;

    // CONSTANTS
    public static final float CONSTANT_MOTION_SCORE_RATE = 1.15f;
    public static final float NON_CONSTANT_MOTION_SCORE_RATE = 1.56f;
    public static final float STOPPED_SCORE_RATE = -0.86f;

    // Properties of a game
    private float scoreGain = 0;
    private float score = 0;


    public float getScore() { return score; }

    // States
    private boolean stopped;

    // Coords
    public Vector2 gridPos;

    public PlayerController(ScreenBackground background, PlayerHud hud, int gridColumns, int gridRows, GridManager gridManager){
        this.background = background;
        this.hud = hud;
        this.player = new Player();
        this.view = new BigChika(player.maxAllowableTowes());

        this.gridManager = gridManager;
        this.gridManager.setPlayerAt(gridColumns / 2, gridColumns / 2, view);

        this.gridPos = new Vector2(gridColumns / 2, gridRows / 2);

        this.gridManager.addSubscriber(this);
    }

    public void update(float dt, float speed, float acceleration){
        background.update(dt);

        if (speed == 0 && acceleration == 0)
            stopped = true;
        else if (speed != 0)
            stopped = false;

        if (stopped)
            scoreGain = STOPPED_SCORE_RATE;
        else {
            if (acceleration == 0){
                scoreGain = CONSTANT_MOTION_SCORE_RATE;
            } else
                scoreGain = NON_CONSTANT_MOTION_SCORE_RATE;
        }

        score += scoreGain;

        // Leave it till last
        hud.update(dt);
    }

    // TODO configure this so as the game lasts more the more able to spawn
    public byte getMaximumAllowableSpawns() {
        return 5;
    }

    // MOTION
    public void moveUp(){
        gridManager.moveTo((int) gridPos.x, (int) gridPos.y, 0, 1);
    }

    public void moveDown(){
        gridManager.moveTo((int) gridPos.x, (int) gridPos.y, 0, -1);
    }

    public void moveRight(){
        gridManager.moveTo((int) gridPos.x, (int) gridPos.y, 1, 0);
    }

    public void moveLeft(){
        gridManager.moveTo((int) gridPos.x, (int) gridPos.y, -1, 0);
    }

    // GRID SUBSCRIBER

    @Override
    public void playerPosChanged(int newGridX, int newGridY) {
        gridPos.x = newGridX;
        gridPos.y = newGridY;
    }

    @Override
    public void jumpedLevel() {

    }
}

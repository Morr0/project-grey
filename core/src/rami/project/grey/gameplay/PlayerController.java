package rami.project.grey.gameplay;

import com.badlogic.gdx.math.Vector2;

import rami.project.grey.Game;
import rami.project.grey.core.entity.chika.BigChika;
import rami.project.grey.core.gridsystem.GridManager;
import rami.project.grey.core.gridsystem.GridSubscriber;
import rami.project.grey.ui.screens.PlayerHud;
import rami.project.grey.ui.screens.ScreenBackground;

import java.util.Random;

/**
 * Is what controls the player where it coordinates between the logic and view of the game so each thing can do its own.
 * This class is what controls every movable thing on the screen.
 * */
public final class PlayerController implements GridSubscriber {
    private ScreenBackground bg;
    private PlayerHud hud;
    private Player player;
    public BigChika view;
    private GridManager gridManager;

    // CONSTANTS
    public static final float STOPPED_SCORE_RATE = -0.86f;

    private static final float INITIAL_STARTING_VELOCITY = 125f;
    private static final float INITIAL_STARTING_DECELERATION = 4.3f;

    //
    private float difficultyAccumulator = 0.0001f;

    // MOTION
    private float currentSpeed;
    private float currentAccel;

    // MOTION ALTERNATIVE // In terms of force
    private static final short gravityConstant = -10;
    private float netForce;

    // Properties of a game
    private float scoreGain = 0;
    private float score = 0;

    public float getScore() { return score; }

    // States
    private boolean stopped = false;

    // Coords
    public Vector2 gridPos;

    public PlayerController(ScreenBackground bg, PlayerHud hud, int gridColumns, int gridRows, GridManager gridManager){
        this.bg = bg;
        this.hud = hud;
        this.player = new Player();
        this.view = new BigChika(player.maxAllowableTowes(), player.maxAllowableAttachments());

        this.gridManager = gridManager;
        this.gridManager.setPlayerAt(gridColumns / 2, gridColumns / 2, view);

        this.gridPos = new Vector2(gridColumns / 2, gridRows / 2);

        this.gridManager.addSubscriber(this);

        // Defaults
        this.currentSpeed = INITIAL_STARTING_VELOCITY;
        this.currentAccel = INITIAL_STARTING_DECELERATION;
    }

    public void update(float dt){
        if (netForce != 0){
            currentAccel = netForce / view.getWeight();
        }

        currentSpeed += currentAccel * dt;

        // Score regulation  --- BEGIN ---

        // Applying multipliers
        scoreGain = stopped? STOPPED_SCORE_RATE: 1;
        scoreGain *= dt;
        scoreGain *= (float) (view.getTotalHealth()/view.getCurrentHealth());
        scoreGain /= view.getNoTows() * view.getCurrentHealth() + 1;

        score += scoreGain;
        // Score regulation  --- END ---

        // Leave it till last
        bg.update(dt, currentSpeed);
        hud.update(dt);
    }

    public void thrust(float amount){
        netForce += amount;
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

    public void toggleStop(){
        stopped = !stopped;
        currentAccel *= stopped? -1: 1;
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

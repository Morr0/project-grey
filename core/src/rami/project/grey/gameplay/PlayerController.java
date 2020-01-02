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
    private ScreenBackground bg;
    private PlayerHud hud;
    private Player player;
    public BigChika view;
    private GridManager gridManager;

    // CONSTANTS
    public static final float STOPPED_SCORE_RATE = -0.86f;

    private static final float DEFAULT_VELOCITY = 125f;

    // UP Time in milliseconds
    private long startingTime;

    // MOTION
    // States
    boolean stopped = false;
    // The desired speed changes dynamically
    float desiredSpeed;
    float currentSpeed;
    float currentAccel;

    private ThrustingController thruster;

    // Properties of a game
    private ScoreManager score;

    public float getScore() { return score.get(); }

    // Coords
    public Vector2 gridPos;

    public PlayerController(ScreenBackground bg, PlayerHud hud, int gridColumns, int gridRows, GridManager gridManager){
        this.bg = bg;
        this.hud = hud;
        this.player = new Player();
        this.view = new BigChika(player.maxAllowableTowes(), player.maxAllowableAttachments());

        this.startingTime = System.currentTimeMillis();

        this.gridManager = gridManager;
        this.gridPos = new Vector2(gridColumns / 2, gridRows / 2);
        this.gridManager.put((int) this.gridPos.x, (int) this.gridPos.y, view);

        this.gridManager.addSubscriber(this);

        // Defaults
        this.desiredSpeed = DEFAULT_VELOCITY;
        this.currentSpeed = DEFAULT_VELOCITY;

        this.thruster = new ThrustingController(this);

        this.score = new ScoreManager(this);
    }

    public void update(float dt){
        // SPEED REGULATION --- BEGIN ---
        desiredSpeed = stopped? 0: DEFAULT_VELOCITY;

        // Updates speed and acceleration depending on the thruster
        thruster.updateThrusting(dt);

        currentAccel = 1 / view.getWeight();
        currentAccel += thruster.thrustAcceleration;

        currentSpeed += currentAccel * dt;

        // SPEED REGULATION --- END ---

        // Score regulation
        score.update();

        // Leave it till last
        bg.update(dt, currentSpeed);
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

    public void toggleStop(){
        thruster.toggleStop();
    }

    public void toggleThruster(){
        thruster.toggleThruster();
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

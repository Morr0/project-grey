package rami.project.grey.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import rami.project.grey.core.entity.chika.BigChika;
import rami.project.grey.core.entity.consumable.AttachmentStructure;
import rami.project.grey.core.entity.consumable.thruster.Thruster;
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
    // The desired speed changes dynamically
    private float desiredSpeed;
    private float currentSpeed;
    private float currentAccel;

    // THRUSTING
    // So that the thruster runs for the required time only
    private long targetBurstTimeEnd;
    // So that there is a cooldown
    private long targetBurstCooldownEnd;
    private boolean currentlyThrusting = false;
    private float thrustAcceleration = 0f;

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

        this.startingTime = System.currentTimeMillis();

        this.gridManager = gridManager;
        this.gridManager.setPlayerAt(gridColumns / 2, gridColumns / 2, view);

        this.gridPos = new Vector2(gridColumns / 2, gridRows / 2);

        this.gridManager.addSubscriber(this);

        // Defaults
        this.desiredSpeed = DEFAULT_VELOCITY;
        this.currentSpeed = DEFAULT_VELOCITY;
    }

    public void update(float dt){
        // SPEED REGULATION --- BEGIN ---
        desiredSpeed = DEFAULT_VELOCITY;

        if (currentlyThrusting){
            if (stopped)
                stopped = false;

            Thruster thruster = (Thruster) view.attachments.get(AttachmentStructure.THRUSTER_SLOT);

            desiredSpeed *= thruster.getSpeedMultiplier();

            thrustAcceleration = thruster.getAccelerationMultiplier() * dt;

            // Thrust timing regulator
            if (System.currentTimeMillis() >= targetBurstTimeEnd){
                currentlyThrusting = false;

                // Starting cooldown
                targetBurstCooldownEnd = System.currentTimeMillis() + thruster.getBurstCooldown();
            }

        } else {
            // To decelerate from a fast thrust
            if (currentSpeed > desiredSpeed){
                float diffSpeeds = currentSpeed - desiredSpeed;
                // Don't divide by dt so that it decelerates smoothly
                thrustAcceleration = -diffSpeeds;
            } else // Just in case
                currentSpeed = desiredSpeed;
        }

        currentAccel = 1 / view.getWeight();
        currentAccel += thrustAcceleration;

        currentSpeed += currentAccel * dt;

        // To stop immediately
        if (stopped)
            currentSpeed = 0;

        // SPEED REGULATION --- END ---

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

    public void toggleThruster(){
        // Just in case
        if (view.attachments.get(AttachmentStructure.THRUSTER_SLOT) == null)
            return;

        Thruster thruster = (Thruster) view.attachments.get(AttachmentStructure.THRUSTER_SLOT);

        // Make sure to retoggle on only after cooldown
        if (System.currentTimeMillis() > targetBurstCooldownEnd)
            currentlyThrusting = !currentlyThrusting;

        if (currentlyThrusting){
            targetBurstTimeEnd = System.currentTimeMillis() + thruster.getBurstTime();
        }
    }

    // TODO configure this so as the game lasts more the more able to spawn
    public byte getMaximumAllowableSpawns() {
        return 0;
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

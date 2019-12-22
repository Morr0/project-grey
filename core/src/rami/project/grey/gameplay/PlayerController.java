package rami.project.grey.gameplay;

import rami.project.grey.core.entity.chika.BigChika;
import rami.project.grey.core.spawning.Spawner;

/**
 * Is what controls the player where it coordinates between the logic and view of the game so each thing can do its own.
 * This class THINKS that the player actually moves.
 * */
public final class PlayerController {
    private Player player;
    private BigChika view;

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


    public PlayerController(BigChika view){
        this.player = new Player();
        this.view = view;
    }

    public void update(float speed, float acceleration){

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
    }

    // TODO configure this so as the game lasts more the more able to spawn
    public byte getMaximumAllowableSpawns() {
        return 5;
    }
}

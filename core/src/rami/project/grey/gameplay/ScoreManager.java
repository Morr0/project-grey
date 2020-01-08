package rami.project.grey.gameplay;

final class ScoreManager {
    private PlayerController c;

    final float STOPPED_SCORE_RATE = -0.58f;

    private float currentScore = 0;
    private float scoreGain;

    ScoreManager(PlayerController c) {
        this.c = c;
    }

    // Don't take in dt as it will make scoring more on better devices
    void update(){
        scoreGain = c.stopped? STOPPED_SCORE_RATE: 1;
        scoreGain *= (float) (c.bigChika.getTotalHealth()/c.bigChika.getCurrentHealth());
        scoreGain /= c.bigChika.getNoTows() * c.bigChika.getCurrentHealth() + 1;

        currentScore += scoreGain;

        // Make sure to always have non negative score
        if (currentScore < 0)
            currentScore = 0;
    }

    float get(){
        return currentScore;
    }

    enum Penalty {
        PLAYER_WALKED_IN_CHIKA;
    }

    void deduct(Penalty penalty, byte number){
        switch (penalty){
            case PLAYER_WALKED_IN_CHIKA:
                currentScore -= 100 * number;
                break;
        }
    }

    public void add(short amount){
        currentScore += amount;
    }
}

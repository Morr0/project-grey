package rami.project.grey.gameplay;

/**
 * Takes care of the XP system once in game session however it does not store the entire XP
 * */
final class XPHandler {
    static final String XP = "player-xp";

    private long accumulatedXP;

    private Player player;

    XPHandler(Player player){
        this.player = player;
    }

    void rewardXp(ActionReward reward){
        accumulatedXP += reward.reward;
    }

    enum ActionReward{
        ENEMY_KILL((short) 50)
        ;
        short reward;

        ActionReward(short reward){
            this.reward = reward;
        }
    }

    // To add all accumulated xp of this session to the data
    void flush(){
        long currentXp = player.prefs.getLong(CommonPreferences.GENERAL_PREFERENCES, XP, 0);
        currentXp += accumulatedXP;
        player.prefs.putLong(CommonPreferences.GENERAL_PREFERENCES, XP, currentXp);
    }
}

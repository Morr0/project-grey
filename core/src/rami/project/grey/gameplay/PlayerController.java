package rami.project.grey.gameplay;

import com.badlogic.gdx.math.Vector2;

import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.entity.chika.BigChika;
import rami.project.grey.core.entity.chika.Chika;
import rami.project.grey.core.entity.consumable.attachables.weaponery.Weapon;
import rami.project.grey.core.entity.consumable.attachables.weaponery.WeaponType;
import rami.project.grey.core.entity.consumable.attachables.weaponery.ammo.Ammo;
import rami.project.grey.core.entity.consumable.attachables.weaponery.ammo.Bullet;
import rami.project.grey.core.entity.consumable.loot.Coin;
import rami.project.grey.core.gridsystem.GridManager;
import rami.project.grey.core.gridsystem.GridSubscriber;
import rami.project.grey.core.gridsystem.Spawner;
import rami.project.grey.core.item.ItemHolder;
import rami.project.grey.core.item.ItemInventory;
import rami.project.grey.ui.screens.PlayerHud;
import rami.project.grey.ui.screens.ScreenBackground;

/**
 * Is what controls the player where it coordinates between the logic and bigChika of the game so each thing can do its own.
 * This class is what controls every movable thing on the screen.
 * */
public final class PlayerController implements GridSubscriber {
    private ScreenBackground bg;
    private PlayerHud hud;
    public Player player;
    public ItemInventory inventory;
    public BigChika bigChika;
    private GridManager gridManager;
    Spawner spawner;
    XPHandler xp;

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

    // GAME PROPERTIES
    private ScoreManager score;

    public float getScore() { return score.get(); }

    // Player coordinates
    public Vector2 gridPos;

    public PlayerController(ScreenBackground bg, PlayerHud hud){
        this.bg = bg;
        this.hud = hud;
        this.player = new Player();
        this.inventory = player.getPlayerInventory();
        this.bigChika = new BigChika(inventory, player.maxAllowableTowes(), player.maxAllowableAttachments());
        this.bigChika.addController(this);

        this.startingTime = System.currentTimeMillis();

        this.gridManager = GridManager.get();
        this.gridPos = new Vector2(0, 0);
        this.gridManager.put((int) this.gridPos.x, (int) this.gridPos.y, bigChika);

        this.gridManager.addSubscriber(this);

        this.xp = new XPHandler(player);

        // Defaults
        this.desiredSpeed = DEFAULT_VELOCITY;
        this.currentSpeed = DEFAULT_VELOCITY;

        this.thruster = new ThrustingController(this);

        this.score = new ScoreManager(this);

        Weapon weapon = new Weapon(WeaponType.STANDARD, WeaponType.STANDARD.durability);
        Bullet bullet = new Bullet();
        this.inventory.setInUseItem(this.inventory.addItem(weapon));
        this.inventory.setInUseItem(this.inventory.addItem(bullet, 10));
        this.bigChika.attach(weapon);
    }

    // BECAUSE OF THE NEED TO INIT THIS CLASS FIRST THEN SPAWNER
    // TO BE CALLED FROM THE PLAYSCREEN
    public void addSpawner(Spawner spawner){
        this.spawner = spawner;
    }

    public void update(float dt){
        xp.rewardXp(XPHandler.ActionReward.ENEMY_KILL);
        // SPEED REGULATION --- BEGIN ---
        desiredSpeed = stopped? 0: DEFAULT_VELOCITY;

        // Updates speed and acceleration depending on the thruster
        thruster.updateThrusting(dt);

        currentAccel = 1 / bigChika.getWeight();
        currentAccel += thruster.thrustAcceleration;

        currentSpeed += currentAccel * dt;

        // SPEED REGULATION --- END ---

        // Score regulation
        score.update();

        // Entity management
        if (thruster.currentlyThrusting) // TODO make it so that entities move by instead of suddenly disappearing and when he comes back from thrusting as well
            gridManager.removeCurrentNonPlayers();

        // SPAWNER --- BEGIN ---
        if (stopped)
            spawner.stopped();
        else if (thruster.currentlyThrusting)
            spawner.thrusting();
        else
            spawner.moving();

        spawner.update();
        // SPAWNER --- END ---

        // Leave it till last
        bg.update(dt, currentSpeed);
        hud.update(dt);
    }

    public void endGame(){

        // Clear up all stuff
        xp.flush();

        // TODO take care of storing the items

        // this is the last to be called
        player.prefs.flush();
    }

    // PUBLIC METHODS

    public <T extends IEntity> void interact(T interactor){
        if (interactor instanceof Coin){
            score.add(((Coin) interactor).getAward());
        }
    }

    //

    // TODO configure this so as the game lasts more the more able to spawn
    public byte getMaximumAllowableSpawns() {
        return 5;
    }

    // MOTION
    public void moveUp(){
        if (!thruster.currentlyThrusting)
            gridManager.moveTo((int) gridPos.x, (int) gridPos.y, 0, 1);
    }

    public void moveDown(){
        if (!thruster.currentlyThrusting)
            gridManager.moveTo((int) gridPos.x, (int) gridPos.y, 0, -1);
    }

    public void moveRight(){
        if (!thruster.currentlyThrusting)
            gridManager.moveTo((int) gridPos.x, (int) gridPos.y, 1, 0);
    }

    public void moveLeft(){
        if (!thruster.currentlyThrusting)
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

    @Override
    public void removedEntity() {

    }

    // NOTIFIER FOR CHANGE OF PLAYER STATE FOR SPAWNER
    public interface PlayerMotionState{
        void stopped();
        void moving();
        void thrusting();
    }

    // BigChika callbacks
    public void walkedIn(IEntity victimE){
        if (victimE instanceof Chika){ // Deducts score as a result of walking into a friendly Chika
            Chika victim = (Chika) victimE;
            score.deduct(ScoreManager.Penalty.PLAYER_WALKED_IN_CHIKA, victim.getSize().number);
        }
    }
}

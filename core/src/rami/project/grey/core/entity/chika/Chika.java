package rami.project.grey.core.entity.chika;

import rami.project.grey.core.entity.EntitySize;
import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.entity.ILiveable;

// The good entity that the player controls
public class Chika implements ILiveable {

    protected byte BASE_HEALTH = 4;

    public boolean hasParent = false;
    BigChika parent = null;

    @Override
    public float getWeight() {
        return size.number;
    }

    // Size is set once
    protected EntitySize size;

    public Chika(EntitySize size) {
        this.size = size;

        this.totalHealth = (short) (BASE_HEALTH * size.number);
        this.currentHealth = totalHealth;
    }

    public EntitySize getSize() {
        return size;
    }

    // IEntity

    @Override
    public String getName() {
        return "Chika";
    }

    @Override
    public boolean isItem() {
        return false;
    }

    // ILiveable
    protected short totalHealth;
    protected short currentHealth;

    @Override
    public int getTotalHealth() {
        return totalHealth;
    }

    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    @Override
    public int getDamageDealt() {
        return size.number;
    }

    // EVENT CALLBACKS

    @Override
    public void touched(IEntity toucher) {

    }

    @Override
    public boolean walkedIn(IEntity walker) {
        if (walker instanceof BigChika) {
            BigChika player = (BigChika) walker;
            player.controller.walkedIn(this);
        }

        return true;
    }

    @Override
    public boolean walkedInBehind(IEntity walker) {
        if (walker instanceof BigChika) {
            BigChika bigChika = (BigChika) walker;
//            bigChika.tow(this);
        }

        return true;
    }

}
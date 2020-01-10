package rami.project.grey.core.entity.chika;

import rami.project.grey.core.entity.EntitySize;
import rami.project.grey.core.entity.EntityType;
import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.entity.LiveableEntity;

// The good entity that the player controls
public class Chika extends LiveableEntity {

    protected byte BASE_HEALTH = 4;

    public boolean hasParent = false;
    BigChika parent = null;

    @Override
    public float getWeight() {
        return size.number;
    }

    public Chika(EntitySize size) {
        super(EntityType.CHIKA, size);
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
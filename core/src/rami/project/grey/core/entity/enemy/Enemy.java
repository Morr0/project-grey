package rami.project.grey.core.entity.enemy;

import rami.project.grey.core.entity.EntitySize;
import rami.project.grey.core.entity.EntityType;
import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.entity.LiveableEntity;
import rami.project.grey.core.entity.chika.BigChika;

public abstract class Enemy extends LiveableEntity {
    Enemy(EntityType type, EntitySize size){
        super(type, size);
    }

    @Override
    public boolean isItem() {
        return false;
    }

    @Override
    public float getWeight() {
        return 0;
    }

    @Override
    public void touched(IEntity toucher) {
        if (toucher instanceof BigChika){
            hit(((BigChika) toucher).getDamageDealt());
        }
    }

    @Override
    public boolean walkedIn(IEntity walker) {
        if (walker instanceof LiveableEntity){
            hit(((LiveableEntity) walker).getDamageDealt());
        }

        return true;
    }

    @Override
    public boolean walkedInBehind(IEntity walker) {
        return DISALLOW;
    }

    @Override
    public int getDamageDealt() {
        return 0;
    }
}

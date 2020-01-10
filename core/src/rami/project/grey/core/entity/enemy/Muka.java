package rami.project.grey.core.entity.enemy;

import rami.project.grey.core.entity.EntityDeathReceiver;
import rami.project.grey.core.entity.EntitySize;

import static rami.project.grey.core.entity.EntityType.MUKA;

public class Muka extends Enemy {

    public Muka(EntityDeathReceiver receiver, EntitySize size) {
        super(MUKA, size);
    }


}

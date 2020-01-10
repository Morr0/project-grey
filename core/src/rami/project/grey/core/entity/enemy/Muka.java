package rami.project.grey.core.entity.enemy;

import rami.project.grey.core.entity.EntityDeathReceiver;
import rami.project.grey.core.entity.EntitySize;

public class Muka extends Enemy {

    public Muka(EntityDeathReceiver receiver, EntitySize size) {
        super(receiver, size);
    }

    @Override
    public String getName() {
        return "Muka";
    }

}

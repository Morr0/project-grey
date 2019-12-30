package rami.project.grey.core.entity.consumable.thruster;

import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.entity.chika.BigChika;
import rami.project.grey.core.entity.consumable.IAttachable;

public class Thruster implements IAttachable {
    private static byte HEALTH_PENALTY = -2;
    private static byte WEIGHT = 2;

    public boolean isAttached = false;

    public short getSpeedMultiplier(){
        return 125;
    }

    public short getAccelerationMultiplier(){
        return 2000;
    }

    /**
     * Gives the maximum time a thruster can go for in milliseconds
     * */
    public short getBurstTime(){
        return 3000;
    }

    public short getBurstCooldown(){
        return 5000;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public float getWeight() {
        return WEIGHT;
    }

    @Override
    public byte getHealthPenalty() {
        return HEALTH_PENALTY;
    }

    @Override
    public void touched(IEntity toucher) {

    }

    @Override
    public void walkedIn(IEntity walker) {
        if (walker instanceof BigChika){
            BigChika chika = (BigChika) walker;

            chika.attach(this);
            isAttached = true;
        }
    }

    @Override
    public void walkedInBehind(IEntity walker) {

    }
}

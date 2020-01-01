package rami.project.grey.core.entity.consumable.thruster;

import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.entity.chika.BigChika;
import rami.project.grey.core.entity.consumable.IAttachable;

public final class Thruster implements IAttachable {

    ThrusterType type;

    public Thruster(ThrusterType type){
        this.type = type;
    }

    public boolean isAttached = false;

    public short getSpeedMultiplier(){
        return type.SPEED_MULTIPLIER;
    }

    public short getAccelerationMultiplier(){
        return type.ACCEL_MULTIPLIER;
    }

    /**
     * Gives the maximum time a thruster can go for in milliseconds
     * */
    public short getBurstTime(){
        return type.BURST_TIME;
    }

    public short getBurstCooldown(){
        return type.BURST_COOLDOWN;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public float getWeight() {
        return type.WEIGHT;
    }

    @Override
    public short getHealthPenalty() {
        return type.HEALTH_PENALTY;
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

package rami.project.grey.core.entity.consumable.attachables.thruster;

import rami.project.grey.core.entity.EntitySize;
import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.entity.chika.BigChika;
import rami.project.grey.core.entity.consumable.attachables.IAttachable;
import rami.project.grey.core.item.IItem;
import rami.project.grey.core.item.ItemGrade;

public final class Thruster implements IItem, IAttachable {

    private ItemGrade grade;
    ThrusterType type;

    public Thruster(ThrusterType type){
        this.type = type;
        this.grade = type.grade();
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
        return type.toString();
    }

    @Override
    public boolean isItem() {
        return true;
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
    public boolean walkedIn(IEntity walker) {
        if (walker instanceof BigChika){
            BigChika chika = (BigChika) walker;

            chika.attach(this);
            isAttached = true;

            chika.controller.inventory.addItem(this);
        }

        return true;
    }

    @Override
    public EntitySize getSize() {
        return null;
    }

    @Override
    public boolean walkedInBehind(IEntity walker) {
        return true;
    }

    @Override
    public ItemType getType() {
        return ItemType.THRUSTER;
    }

    @Override
    public boolean isStackable() {
        return true;
    }

    @Override
    public boolean canBeConsumed() {
        return true;
    }

    @Override
    public void consume() {

    }

    @Override
    public int stackingLimit() {
        return 1;
    }
}

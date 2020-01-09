package rami.project.grey.core.entity.consumable.attachables.thruster;

import rami.project.grey.core.item.ItemGrade;

// Time is in milliseconds
public enum ThrusterType {
    BASIC((short) -2, (byte) 2, (short) 12500, (short) 20000, (short) 3000, (short) 15000),
    TURBO((short) -2, (byte) 3, (short) 18000, (short) 24000, (short) 2000, (short) 12000)
    ;


    short HEALTH_PENALTY;
    byte WEIGHT;
    short SPEED_MULTIPLIER;
    short ACCEL_MULTIPLIER;
    short BURST_TIME;
    short BURST_COOLDOWN;

    ThrusterType(short HEALTH_PENALTY, byte WEIGHT, short SPEED_MULTIPLIER, short ACCEL_MULTIPLIER
            , short BURST_TIME, short BURST_COOLDOWN) {
        this.HEALTH_PENALTY = HEALTH_PENALTY;
        this.WEIGHT = WEIGHT;
        this.SPEED_MULTIPLIER = SPEED_MULTIPLIER;
        this.ACCEL_MULTIPLIER = ACCEL_MULTIPLIER;
        this.BURST_TIME = BURST_TIME;
        this.BURST_COOLDOWN = BURST_COOLDOWN;
    }

    ItemGrade grade(){
        switch (this){
            default: case BASIC:
                return ItemGrade.STANDARD;
            case TURBO:
                return ItemGrade.UPGRADED;
        }
    }
}

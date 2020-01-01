package rami.project.grey.core.entity.consumable.thruster;

// Time is in milliseconds
public enum ThrusterType {
    BASIC((short) -2, (byte) 2, (short) 12500, (short) 20000, (short) 3000, (short) 15000);

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
}

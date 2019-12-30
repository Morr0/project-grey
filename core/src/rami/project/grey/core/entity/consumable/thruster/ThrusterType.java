package rami.project.grey.core.entity.consumable.thruster;

// Time is in milliseconds
public enum ThrusterType {
    BASIC;

    final byte HEALTH_PENALTY = -2;
    final byte WEIGHT = 2;
    final short SPEED_MULTIPLIER = 125;
    final short ACCEL_MULTIPLIER = 2000;
    final short BURST_TIME = 3000;
    final short BURST_COOLDOWN = 15000;
}

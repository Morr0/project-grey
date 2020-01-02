package rami.project.grey.gameplay;

import rami.project.grey.core.entity.consumable.AttachmentStructure;
import rami.project.grey.core.entity.consumable.thruster.Thruster;

// This is to control the thrusting code in order to relieve some pain of the PlayerController
class ThrustingController {
    private PlayerController c;

    // So that the thruster runs for the required time only
    private long targetBurstTimeEnd;
    // So that there is a cooldown
    private long targetBurstCooldownEnd;
    boolean currentlyThrusting = false;
    float thrustAcceleration = 0f;

    ThrustingController(PlayerController c){
        this.c = c;
    }

    void toggleThruster(){
        // KEEP IT
        if (c.view.attachments.get(AttachmentStructure.THRUSTER_SLOT) == null)
            return;

        Thruster thruster = (Thruster) c.view.attachments.get(AttachmentStructure.THRUSTER_SLOT);

        // Make sure to not stop midway thrusting if a thrust toggle was issued
        if (System.currentTimeMillis() < targetBurstTimeEnd)
            return;

        // Make sure to retoggle on only after cooldown
        if (System.currentTimeMillis() > targetBurstCooldownEnd)
            currentlyThrusting = !currentlyThrusting;

        if (currentlyThrusting){ // Sets timer if is thrusting
            targetBurstTimeEnd = System.currentTimeMillis() + thruster.getBurstTime();
        }
    }

    void toggleStop(){
        // To make sure there exists a thruster to work
        if (c.view.attachments.get(AttachmentStructure.THRUSTER_SLOT) != null){
            c.stopped = !c.stopped;
        }
    }

    void updateThrusting(float dt){
        if (currentlyThrusting) {
            if (c.stopped)
                c.stopped = false;

            Thruster thruster = (Thruster) c.view.attachments.get(AttachmentStructure.THRUSTER_SLOT);

            c.desiredSpeed *= thruster.getSpeedMultiplier();

            thrustAcceleration = thruster.getAccelerationMultiplier() * dt;

            // Thrust timing regulator
            if (System.currentTimeMillis() >= targetBurstTimeEnd){
                currentlyThrusting = false;

                // Starting cooldown
                targetBurstCooldownEnd = System.currentTimeMillis() + thruster.getBurstCooldown();
            }
        } else if (!currentlyThrusting || c.stopped) {
            // To decelerate/accelerate
            if (c.currentSpeed > c.desiredSpeed)
                thrustAcceleration = -(c.currentSpeed - c.desiredSpeed); // Don't divide by dt so that it decelerates smoothly
            else // Just in case
                c.currentSpeed = c.desiredSpeed;
        }
    }
}

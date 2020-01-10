package rami.project.grey.core.entity.consumable.attachables;

import rami.project.grey.core.entity.consumable.attachables.thruster.Thruster;
import rami.project.grey.core.entity.consumable.attachables.weaponery.Weapon;

import java.util.ArrayList;

// TODO implement a mechanism to not override taken slots
public class AttachmentStructure {

    // Slots
    private Weapon weaponSpot;
    private IAttachable accessorySpot;
    private Thruster thrusterSpot;

    public void put(IAttachable attachment){
        if (attachment instanceof Weapon)
            weaponSpot = (Weapon) attachment;
        else if (attachment instanceof Thruster)
            thrusterSpot = (Thruster) attachment;
        else
            accessorySpot = attachment;

    }

    public Weapon getWeapon() {
        return weaponSpot;
    }

    public IAttachable getAccessory() {
        return accessorySpot;
    }

    public Thruster getThruster() {
        return thrusterSpot;
    }

    public ArrayList<IAttachable> getUsedAttachments(){
        ArrayList<IAttachable> attachables = new ArrayList<>(3);
        if (weaponSpot != null)
            attachables.add(weaponSpot);
        if (accessorySpot != null)
            attachables.add(accessorySpot);
        if (thrusterSpot != null)
            attachables.add(thrusterSpot);

        return attachables;
    }

    public boolean hasWeapon(){
        return weaponSpot != null;
    }

    public boolean hasThruster(){
        return thrusterSpot != null;
    }

    public boolean hasAccessory(){
        return accessorySpot != null;
    }
}

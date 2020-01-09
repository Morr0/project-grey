package rami.project.grey.core.entity.consumable.attachables;

import rami.project.grey.core.entity.consumable.attachables.thruster.Thruster;
import rami.project.grey.core.entity.consumable.attachables.weaponery.Weapon;

import java.util.ArrayList;

/**
 * Holds the attachments in the attacher
 * */
// TODO implement a mechanism to not override taken slots
public class AttachmentStructure {
    public static final byte WEAPON_SLOT = 1;
    public static final byte ACCESSORY_SLOT = 2;
    public static final byte THRUSTER_SLOT = 3;

    public byte maxAllowed;
    public byte used;

    // Slots
    private Weapon weaponSpot;
    private IAttachable accesorySpot;
    private Thruster thrusterSpot;

    public AttachmentStructure(byte maxAllowed){
        this.maxAllowed = maxAllowed;
    }

    public void put(byte slot, IAttachable attachment){
        if (slot == WEAPON_SLOT)
            weaponSpot = (Weapon) attachment;
        else if (slot == ACCESSORY_SLOT)
            accesorySpot = attachment;
        else if (slot == THRUSTER_SLOT)
            thrusterSpot = (Thruster) attachment;

        updateVariables();
    }

    private void updateVariables(){
        used = 0;
        if (weaponSpot != null)
            used++;
        if (accesorySpot != null)
            used++;
        if (thrusterSpot != null)
            used++;
    }

    public void remove(byte slot){
        if (slot == WEAPON_SLOT)
            weaponSpot = null;
        else if (slot == ACCESSORY_SLOT)
            accesorySpot = null;
        else if (slot == THRUSTER_SLOT)
            thrusterSpot = null;

        updateVariables();
    }

    public IAttachable get(byte slot){
        switch (slot){
            case WEAPON_SLOT:
                return weaponSpot;
            case ACCESSORY_SLOT:
                return accesorySpot;
            case THRUSTER_SLOT:
                return thrusterSpot;
            default:
                return null;
        }
    }


    public ArrayList<IAttachable> getUsedAttachments(){
        ArrayList<IAttachable> attachables = new ArrayList<>(maxAllowed);
        if (weaponSpot != null)
            attachables.add(weaponSpot);
        if (accesorySpot != null)
            attachables.add(accesorySpot);
        if (thrusterSpot != null)
            attachables.add(thrusterSpot);

        return attachables;
    }

    public byte getNoUsed(){
        return used;
    }

    public byte getSpot(IAttachable attachable){
        if (attachable instanceof Thruster)
            return THRUSTER_SLOT;

        return -1;
    }

    public boolean hasWeapon(){
        return weaponSpot != null;
    }
}

package rami.project.grey.core.entity.consumable;

import java.util.ArrayList;

/**
 * Holds the attachments in the attacher
 * */
// TODO implement a mechanism to not override taken slots
public class AttachmentStructure {
    public static final byte RIGHT_SLOT = 1;
    public static final byte LEFT_SLOT = 2;
    public static final byte BOTTOM_SLOT = 3;

    public byte maxAllowed;
    public byte used;

    // Slots
    private IAttachable right;
    private IAttachable left;
    private IAttachable bottom;

    public AttachmentStructure(byte maxAllowed){
        this.maxAllowed = maxAllowed;
    }

    public void put(byte slot, IAttachable attachment){
        if (slot == RIGHT_SLOT)
            right = attachment;
        else if (slot == LEFT_SLOT)
            left = attachment;
        else if (slot == BOTTOM_SLOT)
            bottom = attachment;

        updateVariables();
    }

    private void updateVariables(){
        used = 0;
        if (right != null)
            used++;
        if (left != null)
            used++;
        if (bottom != null)
            used++;
    }

    public void remove(byte slot){
        if (slot == RIGHT_SLOT)
            right = null;
        else if (slot == LEFT_SLOT)
            left = null;
        else if (slot == BOTTOM_SLOT)
            bottom = null;

        updateVariables();
    }

    public IAttachable get(byte slot){
        switch (slot){
            case RIGHT_SLOT:
                return right;
            case LEFT_SLOT:
                return left;
            case BOTTOM_SLOT:
                return bottom;
            default:
                return null;
        }
    }


    public ArrayList<IAttachable> getUsedAttachments(){
        ArrayList<IAttachable> attachables = new ArrayList<>(maxAllowed);
        if (right != null)
            attachables.add(right);
        if (left != null)
            attachables.add(left);
        if (bottom != null)
            attachables.add(bottom);

        return attachables;
    }

    public byte getNoUsed(){
        return used;
    }

    public byte getSpot(IAttachable attachable){
        if (attachable instanceof Thruster)
            return BOTTOM_SLOT;

        return -1;
    }
}

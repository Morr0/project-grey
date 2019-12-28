package rami.project.grey.core.entity.chika;

import com.badlogic.gdx.Gdx;
import rami.project.grey.core.entity.consumable.AttachmentStructure;
import rami.project.grey.core.entity.consumable.IAttachable;

import java.util.ArrayList;
import java.util.Stack;

public class BigChika extends Chika {

    private float totalWeight;

    // Towing properties:
    private byte maxTows;
    // -Can only tow and untow from the front
    private boolean isTowed;
    private Stack<Chika> towes;

    // Attachments
    AttachmentStructure attachments;

    public BigChika(byte maxAllowableTowes, byte maxAllowableAttachments) {
        super(ChikaSize.XLARGE);
        this.maxTows = maxAllowableTowes;

        towes = new Stack<>();
        attachments = new AttachmentStructure(maxAllowableAttachments);

        // KEEP IT CALLED AFTER THE ATTACHMENTS CODE
        updateTotalWeight();
    }

    public int getNoTows(){
        return towes.size();
    }

    public byte getMaxAllowedAttachments(){
        return attachments.maxAllowed;
    }

    public Stack<Chika> getTowes() {
        return towes;
    }

    private boolean hasTowes(){
        return isTowed;
    }

    void tow(Chika child){
        // Only same or smaller size of the front tower can be towed
        // The front tower maybe BigChika itself if none other is towed

        if (!isTowed){ // First tow doesnt care about size of new tow
            child.hasParent = true;
            child.parent = this;
            towes.push(child);
            isTowed = true;
        } else {
            if (towes.size() < maxTows){
                if (canBeTowen(child)){
                    child.hasParent = true;
                    child.parent = this;
                    towes.push(child);
                } else
                    unsuccessfulTow(child, TowingReason.LIMITED_CAPACITY);
            }
        }

        // To update
        if (child.hasParent)
            updateConfiguration();
    }

    public void untow(){
        // Untows the first Chika into the front
        if (isTowed){
            towes.peek().hasParent = false;
            towes.peek().parent = null;
            towes.pop();
            if (towes.size() == 0)
                isTowed = false;

            updateConfiguration();
        }
    }

    // Determines whether can be towed or not
    private boolean canBeTowen(Chika potentialChild){
        if (towes.peek().getSize().towWeight >= potentialChild.getSize().towWeight)
            return true;

        unsuccessfulTow(potentialChild, TowingReason.BIG_SIZE);
        return false;
    }

    // This must be called whenever a tow couldn't occur
    private void unsuccessfulTow(Chika failedApplicant, TowingReason reason){
        Gdx.app.log("Game", "Unsuccessful tow due to " + reason.toString());
    }

    private void updateConfiguration(){
        updateTotalHealth();
        updateTotalWeight();
    }

    //  To be used whenever the
    private void updateTotalHealth(){
        totalHealth = (short) (BASE_HEALTH * size.towWeight);
        totalHealth *= (short) ((3 * getNoTows()/2) * BASE_HEALTH/(attachments.getNoUsed() + 1));

        for (IAttachable attachment: attachments.getUsedAttachments())
            totalHealth += attachment.getHealthPenalty();

        // To make sure no extra health exists
        if (currentHealth > totalHealth)
            currentHealth = totalHealth;
    }

    private void updateTotalWeight(){
        totalWeight = size.towWeight;
        for (Chika chika: towes)
            totalWeight += chika.getWeight();

        for (IAttachable attachment: attachments.getUsedAttachments())
            totalWeight += attachment.getWeight();
    }

    @Override
    public float getWeight() {
        return totalWeight;
    }

    // ATTACHING
    public void attach(IAttachable attachment){
        if (attachments.maxAllowed > attachments.used){
            attachments.put(attachments.getSpot(attachment), attachment);

            updateConfiguration();

            Gdx.app.log("Game", "Attached to thruster");
        }
    }
}

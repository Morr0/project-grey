package rami.project.grey.core.entity.chika;

import java.util.Stack;

import rami.project.grey.core.entity.EntitySize;
import rami.project.grey.core.entity.consumable.attachables.AttachmentStructure;
import rami.project.grey.core.entity.consumable.attachables.IAttachable;
import rami.project.grey.core.entity.consumable.attachables.weaponery.Weapon;
import rami.project.grey.core.entity.stacking.IHoldable;
import rami.project.grey.core.entity.stacking.IStackable;
import rami.project.grey.core.item.ItemInventory;
import rami.project.grey.gameplay.PlayerController;

public class BigChika extends Chika implements IStackable {

    public PlayerController controller;

    private float totalWeight;

    // Attachments
    public AttachmentStructure attachments;

    public BigChika(ItemInventory inventory, int maxAllowableStack, int maxAllowableAttachments) {
        super(EntitySize.XLARGE);

        attachments = new AttachmentStructure();

        // KEEP IT CALLED AFTER THE ATTACHMENTS CODE
        updateTotalWeight();

        // Stacking
        this.stack = new Stack<>();
        this.stackLimit = maxAllowableStack;
    }

    public void addController(PlayerController controller){
        this.controller = controller;
    }

    private void updateConfiguration(){
        updateTotalHealth();
        updateTotalWeight();
    }

    //  To be used whenever the
    private void updateTotalHealth(){
        totalHealth = (short) (BASE_HEALTH * size.number * 3/2);

        for (IAttachable attachment: attachments.getUsedAttachments())
            totalHealth += attachment.getHealthPenalty();

        // To make sure no extra health exists
        if (currentHealth > totalHealth)
            currentHealth = totalHealth;
    }

    private void updateTotalWeight(){
        totalWeight = size.number;
//        for (Chika chika: towes)
//            totalWeight += chika.getWeight();

        for (IAttachable attachment: attachments.getUsedAttachments())
            totalWeight += attachment.getWeight();
    }

    @Override
    public float getWeight() {
        return totalWeight;
    }

    // ATTACHING
    public void attach(IAttachable attachment){
        attachments.put(attachment);
        updateConfiguration();

    }

    @Override
    public int getDamageDealt() {
        if (attachments.hasWeapon()){
            Weapon weapon = attachments.getWeapon();
            if (weapon.canBeConsumed()){
                int damage = size.number;
                damage *= weapon.getWeaponType().multiplier;
                weapon.consume();
                return damage;
            }
        }

        if (stack.size() > 0){

        }

        return 0;
    }

    // Stacking
    private int stackLimit;
    private Stack<IHoldable> stack;

    @Override
    public boolean canTow(IHoldable holdable) {
        if (stack.empty())
            return true;

        // Checks whether can hold the size and exists a spot
        return (stack.peek().getSize().number >= holdable.getSize().number) && (stack.size() < stackLimit);
    }

    @Override
    public void tow(IHoldable holdable) {
        stack.push(holdable);
        holdable.setMasterHolder(this);
        System.out.println("Stacked");
    }

    @Override
    public void release() {
        stack.peek().setMasterHolder(null);
        stack.pop();
    }

    public Stack<IHoldable> getStack() {
        return stack;
    }
}

package rami.project.grey.core.item;

import java.util.HashSet;
import java.util.Iterator;

public final class ItemInventory {
    private HashSet<ItemHolder> totalItemHolders;

    public ItemInventory(){
        this.totalItemHolders = new HashSet<>();
    }

    public HashSet<ItemHolder> getItems(){
            return totalItemHolders;
    }

    public int totalItems(){
        return totalItemHolders.size();
    }

    public void addItem(Item item){
        // Searches if exists else will add a new ItemHolder downstairs
        for (ItemHolder holder: totalItemHolders){
            if (holder.getItem().getType() == item.getType()){
                if (item.isStackable() )
                holder.count++;
                return;
            }
        }

        totalItemHolders.add(new ItemHolder(item, 1));
    }

    // ASSUMES THE ITEM EXISTS
    public void consumeItem(Item item){
        Iterator<ItemHolder> iterator = totalItemHolders.iterator();
        while (iterator.hasNext()){
            ItemHolder holder = iterator.next();
            if (holder.getItem().getType() == item.getType()){
                holder.count--;

                // In case there is not any left, remove the item holder
                if (holder.count <= 0){
                    iterator.remove();
                    return;
                }
            }
        }
    }

}

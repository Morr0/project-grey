package rami.project.grey.core.item;

public class ItemHolder<T extends Item> {
    Item item;
    int count;

    public ItemHolder(T item, int count){
        this.item = item;
        this.count = count;
    }

    public Item getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }

    public boolean canBeConsumed(){
        return count > 0;
    }

    public void consume(int amount){
        count -= amount;
    }
}

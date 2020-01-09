package rami.project.grey.core.item;

public class ItemHolder {
    Item item;
    int count;

    ItemHolder(Item item, int count){
        this.item = item;
        this.count = count;
    }

    public Item getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }

    public void consume(int amount){
        count -= amount;
    }
}

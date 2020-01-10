package rami.project.grey.core.item;

public class ItemHolder<T extends IItem> {
    IItem IItem;
    int count;

    public ItemHolder(T item, int count){
        this.IItem = item;
        this.count = count;
    }

    public IItem getIItem() {
        return IItem;
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

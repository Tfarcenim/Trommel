package tfar.trommel.invetory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import tfar.trommel.TrommelInventory;

public class ContainerWrapper implements Container {

    private final TrommelInventory inventory;

    public ContainerWrapper(TrommelInventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public int getContainerSize() {
        return inventory.getSlotCount();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int i) {
        return inventory.getStack(i);
    }

    @Override
    public ItemStack removeItem(int i, int i1) {
        return inventory.extractStack(i,i1,false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        return null;
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {
        inventory.setStack(i,itemStack);
    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }

    @Override
    public void clearContent() {

    }
}

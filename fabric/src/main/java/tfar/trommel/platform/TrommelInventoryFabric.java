package tfar.trommel.platform;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import tfar.trommel.TrommelBlockEntity;
import tfar.trommel.TrommelInventory;

public class TrommelInventoryFabric implements TrommelInventory {

    protected final NonNullList<ItemStack> stacks;
    private final TrommelBlockEntity trommelBlockEntity;

    public TrommelInventoryFabric(TrommelBlockEntity trommelBlockEntity) {
        this.trommelBlockEntity = trommelBlockEntity;
        stacks = NonNullList.withSize(5, ItemStack.EMPTY);
    }

    @Override
    public NonNullList<ItemStack> getStacks() {
        return stacks;
    }
    
    @Override
    public void setChanged() {
        if (trommelBlockEntity != null) trommelBlockEntity.setChanged();
    }

}

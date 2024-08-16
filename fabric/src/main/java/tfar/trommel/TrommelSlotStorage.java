package tfar.trommel;

import net.fabricmc.fabric.api.transfer.v1.item.base.SingleStackStorage;
import net.minecraft.world.item.ItemStack;

public class TrommelSlotStorage extends SingleStackStorage {

    private final TrommelInventory trommelInventory;
    private final int slot;

    public TrommelSlotStorage(TrommelInventory trommelInventory, int slot) {

        this.trommelInventory = trommelInventory;
        this.slot = slot;
    }

    @Override
    protected ItemStack getStack() {
        return trommelInventory.getStack(slot);
    }

    @Override
    protected void setStack(ItemStack stack) {
        trommelInventory.setStack(slot,stack);
    }
}

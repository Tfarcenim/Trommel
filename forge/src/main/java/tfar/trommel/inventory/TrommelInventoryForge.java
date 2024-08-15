package tfar.trommel.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;
import tfar.trommel.TrommelBlockEntity;
import tfar.trommel.TrommelInventory;

public class TrommelInventoryForge implements TrommelInventory, IItemHandlerModifiable {

    protected final NonNullList<ItemStack> stacks;
    private final TrommelBlockEntity trommelBlockEntity;

    public TrommelInventoryForge(TrommelBlockEntity trommelBlockEntity) {
        this.trommelBlockEntity = trommelBlockEntity;
        stacks = NonNullList.withSize(5, ItemStack.EMPTY);
    }

    @Override
    public NonNullList<ItemStack> getStacks() {
        return stacks;
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        setStack(slot, stack);
    }

    @Override
    public int getSlots() {
        return getSlotCount();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return getStack(slot);
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return insertStack(slot, stack, simulate);
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        return extractStack(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return getSlotStackSize(slot);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return isStackValid(slot, stack);
    }

    @Override
    public void setChanged() {
        if (trommelBlockEntity != null) {
            trommelBlockEntity.setChanged();
        }
    }
}

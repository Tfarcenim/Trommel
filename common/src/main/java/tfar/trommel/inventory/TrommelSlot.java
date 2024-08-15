package tfar.trommel.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import tfar.trommel.TrommelInventory;

//SlotIItemHandler adapted to common
public class TrommelSlot extends Slot {
    private static Container emptyInventory = new SimpleContainer(0);
    private final TrommelInventory itemHandler;
    private final int index;

    public TrommelSlot(TrommelInventory itemHandler, int index, int xPosition, int yPosition) {
        super(emptyInventory, index, xPosition, yPosition);
        this.itemHandler = itemHandler;
        this.index = index;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        if (stack.isEmpty())
            return false;
        return itemHandler.isStackValid(index, stack);
    }

    @Override
    @NotNull
    public ItemStack getItem() {
        return this.getItemHandler().getStack(index);
    }

    @Override
    public void set(@NotNull ItemStack stack) {
        this.getItemHandler().setStack(index, stack);
        this.setChanged();
    }

    @Override
    public void onQuickCraft(@NotNull ItemStack oldStackIn, @NotNull ItemStack newStackIn) {

    }

    @Override
    public int getMaxStackSize() {
        return this.itemHandler.getSlotStackSize(this.index);
    }

    @Override
    public int getMaxStackSize(@NotNull ItemStack stack) {
        return stack.getMaxStackSize();
    }

    @Override
    public boolean mayPickup(Player playerIn) {
        return !this.getItemHandler().extractStack(index, 1, true).isEmpty();
    }

    @Override
    @NotNull
    public ItemStack remove(int amount) {
        return this.getItemHandler().extractStack(index, amount, false);
    }

    public TrommelInventory getItemHandler() {
        return itemHandler;
    }

}

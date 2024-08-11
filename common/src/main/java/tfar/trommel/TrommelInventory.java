package tfar.trommel;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
//Similar to IItemhandler, but adapted for common
public interface TrommelInventory {
    /**
     * Returns the number of slots available
     *
     * @return The number of slots available
     **/
    int getSlotCount();

    @NotNull
    ItemStack getStack(int slot);

    @NotNull
    ItemStack insertStack(int slot, @NotNull ItemStack stack, boolean simulate);


    @NotNull
    ItemStack extractStack(int slot, int amount, boolean simulate);


    int getSlotStackSize(int slot);


    boolean isStackValid(int slot, @NotNull ItemStack stack);

    void setStack(int slot, @NotNull ItemStack stack);

    CompoundTag serialize();

    void deserialize(CompoundTag nbt);

    void setChanged();

}

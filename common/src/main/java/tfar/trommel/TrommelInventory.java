package tfar.trommel;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import tfar.trommel.platform.Services;

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


    default boolean isStackValid(int slot, @NotNull ItemStack stack) {
        if (slot == TrommelBlockEntity.FUEL) {
            int i = Services.PLATFORM.getBurnTime(stack, ModRecipeTypes.TROMMEL);
            return i > 0;
        }
        return true;
    }

    void setStack(int slot, @NotNull ItemStack stack);

    CompoundTag serialize();

    void deserialize(CompoundTag nbt);

    void setChanged();

}

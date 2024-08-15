package tfar.trommel;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import tfar.trommel.platform.Services;

import java.util.List;

//Similar to IItemhandler, but adapted for common
public interface TrommelInventory {
    /**
     * Returns the number of slots available
     *
     * @return The number of slots available
     **/
    default int getSlotCount() {
        return getStacks().size();
    }

    List<ItemStack> getStacks();

    default @NotNull ItemStack getStack(int slot) {
        return getStacks().get(slot);
    }

    default void setStack(int slot, @NotNull ItemStack stack) {
        getStacks().set(slot,stack);
        setChanged();
    }

    default @NotNull ItemStack insertStack(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        if (!isStackValid(slot, stack))
            return stack;

        ItemStack existing = this.getStacks().get(slot);

        int limit = getSlotStackSize(slot);

        if (!existing.isEmpty()) {
            if (!ItemStack.isSameItemSameTags(stack, existing))
                return stack;

            limit -= existing.getCount();
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate) {
            if (existing.isEmpty()) {
                this.getStacks().set(slot, reachedLimit ? stack.copyWithCount(limit) : stack);
            } else {
                existing.grow(reachedLimit ? limit : stack.getCount());
            }
            setChanged();
        }

        return reachedLimit ? stack.copyWithCount(stack.getCount() - limit) : ItemStack.EMPTY;
    }

    default @NotNull ItemStack extractStack(int slot, int amount, boolean simulate) {
        if (amount == 0)
            return ItemStack.EMPTY;


        ItemStack existing = this.getStacks().get(slot);

        if (existing.isEmpty())
            return ItemStack.EMPTY;

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (existing.getCount() <= toExtract)
        {
            if (!simulate)
            {
                this.getStacks().set(slot, ItemStack.EMPTY);
                setChanged();
                return existing;
            }
            else
            {
                return existing.copy();
            }
        }
        else
        {
            if (!simulate)
            {
                this.getStacks().set(slot, existing.copyWithCount(existing.getCount() - toExtract));
                setChanged();
            }

            return existing.copyWithCount(toExtract);
        }
    }

    default int getSlotStackSize(int slot) {
        return 64;
    }

    default boolean isStackValid(int slot, @NotNull ItemStack stack) {
        if (slot == TrommelBlockEntity.FUEL) {
            int i = Services.PLATFORM.getBurnTime(stack, ModRecipeTypes.TROMMEL);
            return i > 0;
        }
        return true;
    }

    default CompoundTag serialize() {
        ListTag nbtTagList = new ListTag();
        for (int i = 0; i < getStacks().size(); i++)
        {
            if (!getStacks().get(i).isEmpty())
            {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                getStacks().get(i).save(itemTag);
                nbtTagList.add(itemTag);
            }
        }
        CompoundTag nbt = new CompoundTag();
        nbt.put("Items", nbtTagList);
        nbt.putInt("Size", getStacks().size());
        return nbt;
    }

    default void deserialize(CompoundTag nbt) {
        ListTag tagList = nbt.getList("Items", Tag.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++)
        {
            CompoundTag itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt("Slot");

            if (slot >= 0 && slot < getStacks().size())
            {
                getStacks().set(slot, ItemStack.of(itemTags));
            }
        }
    }

    void setChanged();

}

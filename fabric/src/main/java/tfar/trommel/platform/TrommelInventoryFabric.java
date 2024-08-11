package tfar.trommel.platform;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
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
    public int getSlotCount() {
        return stacks.size();
    }

    @Override
    public @NotNull ItemStack getStack(int slot) {
        return stacks.get(slot);
    }

    @Override
    public @NotNull ItemStack insertStack(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        if (!isStackValid(slot, stack))
            return stack;

        ItemStack existing = this.stacks.get(slot);

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
                this.stacks.set(slot, reachedLimit ? stack.copyWithCount(limit) : stack);
            } else {
                existing.grow(reachedLimit ? limit : stack.getCount());
            }
            setChanged();
        }

        return reachedLimit ? stack.copyWithCount(stack.getCount() - limit) : ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack extractStack(int slot, int amount, boolean simulate) {
        if (amount == 0)
            return ItemStack.EMPTY;


        ItemStack existing = this.stacks.get(slot);

        if (existing.isEmpty())
            return ItemStack.EMPTY;

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (existing.getCount() <= toExtract)
        {
            if (!simulate)
            {
                this.stacks.set(slot, ItemStack.EMPTY);
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
                this.stacks.set(slot, existing.copyWithCount(existing.getCount() - toExtract));
               setChanged();
            }

            return existing.copyWithCount(toExtract);
        }
    }

    @Override
    public int getSlotStackSize(int slot) {
        return 64;
    }



    @Override
    public void setStack(int slot, @NotNull ItemStack stack) {
        stacks.set(slot,stack);
        setChanged();
    }


    @Override
    public CompoundTag serialize() {
        ListTag nbtTagList = new ListTag();
        for (int i = 0; i < stacks.size(); i++)
        {
            if (!stacks.get(i).isEmpty())
            {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                stacks.get(i).save(itemTag);
                nbtTagList.add(itemTag);
            }
        }
        CompoundTag nbt = new CompoundTag();
        nbt.put("Items", nbtTagList);
        nbt.putInt("Size", stacks.size());
        return nbt;
    }

    @Override
    public void deserialize(CompoundTag nbt) {
        ListTag tagList = nbt.getList("Items", Tag.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++)
        {
            CompoundTag itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt("Slot");

            if (slot >= 0 && slot < stacks.size())
            {
                stacks.set(slot, ItemStack.of(itemTags));
            }
        }
    }

    @Override
    public void setChanged() {
        if (trommelBlockEntity != null) trommelBlockEntity.setChanged();
    }

}

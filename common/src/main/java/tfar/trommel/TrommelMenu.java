package tfar.trommel;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import tfar.trommel.inventory.TrommelSlot;
import tfar.trommel.platform.Services;

public class TrommelMenu extends AbstractContainerMenu {

    private final ContainerLevelAccess access;
    private final Player player;
    private final TrommelInventory trommelInventory;
    private final ContainerData dataAccess;

    public TrommelMenu(int id, Inventory $$1) {
        this(id, $$1, ContainerLevelAccess.NULL, Services.PLATFORM.create(null), new SimpleContainerData(4));
    }


    protected TrommelMenu(int id, Inventory inventory, ContainerLevelAccess access, TrommelInventory trommelInventory, ContainerData dataAccess) {
        super(Init.MENU_TYPE, id);
        this.access = access;
        this.player = inventory.player;
        this.trommelInventory = trommelInventory;
        this.dataAccess = dataAccess;

        addSlot(new TrommelSlot(trommelInventory,0,33,50));

        addSlot(new TrommelSlot(trommelInventory,1, 105,30));
        addSlot(new TrommelSlot(trommelInventory,2, 125,50));
        addSlot(new TrommelSlot(trommelInventory,3, 105,70));
        addSlot(new TrommelSlot(trommelInventory,4, 85,50));

        int y = 110;

        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(inventory, i1 + k * 9 + 9, 8 + i1 * 18, y + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(inventory, l, 8 + l * 18, y+58));
        }

        addDataSlots(dataAccess);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack $$2 = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack $$4 = slot.getItem();
            $$2 = $$4.copy();
            if (index < this.trommelInventory.getSlotCount()) {
                if (!this.moveItemStackTo($$4, this.trommelInventory.getSlotCount(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo($$4, 0, this.trommelInventory.getSlotCount(), false)) {
                return ItemStack.EMPTY;
            }

            if ($$4.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return $$2;
    }

    public int getBurnProgress() {
        int i = this.dataAccess.get(2);
        int j = this.dataAccess.get(3);
        return j != 0 && i != 0 ? i * 24 / j : 0;
    }

    public int getLitProgress() {
        int i = this.dataAccess.get(1);
        if (i == 0) {
            i = 200;
        }

        return this.dataAccess.get(0) * 13 / i;
    }

    public boolean isLit() {
        return this.dataAccess.get(0) > 0;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, Init.BLOCK);
    }
}

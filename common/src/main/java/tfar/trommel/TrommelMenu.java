package tfar.trommel;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class TrommelMenu extends AbstractContainerMenu {

    private final ContainerLevelAccess access;
    private final Player player;

    public TrommelMenu(int id, Inventory $$1) {
        this(id, $$1, ContainerLevelAccess.NULL);
    }


    protected TrommelMenu(int id, Inventory inventory, ContainerLevelAccess access) {
        super(Init.MENU_TYPE, id);
        this.access = access;
        this.player = inventory.player;



        int y = 110;

        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(inventory, i1 + k * 9 + 9, 8 + i1 * 18, y + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(inventory, l, 8 + l * 18, y+58));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}

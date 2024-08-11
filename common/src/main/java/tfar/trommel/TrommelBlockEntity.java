package tfar.trommel;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tfar.trommel.platform.Services;

public class TrommelBlockEntity extends BlockEntity implements MenuProvider, Nameable {

    protected TrommelInventory trommelInventory = Services.PLATFORM.create(this);

    public TrommelBlockEntity(BlockPos $$1, BlockState $$2) {
        this(Init.BLOCK_ENTITY_TYPE, $$1, $$2);
    }

    public static final MutableComponent DEFAULT_NAME = Component.translatable("container.trommel.trommel");

    @Nullable
    private Component name;

    public TrommelBlockEntity(BlockEntityType<?> $$0, BlockPos $$1, BlockState $$2) {
        super($$0, $$1, $$2);
    }

    @Override
    public Component getName() {
        return this.name != null ? this.name : DEFAULT_NAME;
    }

    @Override
    public Component getDisplayName() {
        return getName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new TrommelMenu(i,inventory, ContainerLevelAccess.create(level,worldPosition),trommelInventory);
    }

    public void load(CompoundTag tag) {
        super.load(tag);

        trommelInventory.deserialize(tag.getCompound("inv"));
        if (tag.contains("CustomName", 8)) {
            this.name = Component.Serializer.fromJson(tag.getString("CustomName"));
        }
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inv",trommelInventory.serialize());
        if (this.name != null) {
            tag.putString("CustomName", Component.Serializer.toJson(this.name));
        }
    }
}

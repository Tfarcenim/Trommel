package tfar.trommel;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tfar.trommel.inventory.ContainerWrapper;
import tfar.trommel.platform.Services;
import tfar.trommel.recipe.RangedEntry;
import tfar.trommel.recipe.TrommelRecipe;

public class TrommelBlockEntity extends BlockEntity implements MenuProvider, Nameable {

    protected TrommelInventory trommelInventory = Services.PLATFORM.create(this);

    protected static final int FUEL = 0;

    int litTime;
    int litDuration;
    int cookingProgress;
    int cookingTotalTime;
    protected final ContainerData dataAccess = new ContainerData() {
        public int get(int index) {
            return switch (index) {
                case 0 -> litTime;
                case 1 -> litDuration;
                case 2 -> cookingProgress;
                case 3 -> cookingTotalTime;
                default -> 0;
            };
        }

        public void set(int index, int value) {
            switch (index) {
                case 0 -> litTime = value;
                case 1 -> litDuration = value;
                case 2 -> cookingProgress = value;
                case 3 -> cookingTotalTime = value;
            }
        }

        public int getCount() {
            return 4;
        }
    };

    private boolean checkRecipes;

    public TrommelBlockEntity(BlockPos $$1, BlockState $$2) {
        this(Init.BLOCK_ENTITY_TYPE, $$1, $$2);
    }

    protected final ContainerWrapper wrapper = new ContainerWrapper(trommelInventory);
    private final RecipeManager.CachedCheck<ContainerWrapper, TrommelRecipe> quickCheck = RecipeManager.createCheck(ModRecipeTypes.TROMMEL);
    private TrommelRecipe match;

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

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, TrommelBlockEntity blockEntity) {
        boolean flag = blockEntity.isLit();
        boolean isDirty = false;
        if (blockEntity.isLit()) {
            --blockEntity.litTime;
        }

        blockEntity.match = blockEntity.quickCheck.getRecipeFor(blockEntity.wrapper, pLevel).orElse(null);

        if (!blockEntity.isLit() && blockEntity.match != null) {
            ItemStack itemstack = blockEntity.trommelInventory.getStack(FUEL);
            if (!itemstack.isEmpty()) {
                blockEntity.litTime = Services.PLATFORM.getBurnTime(itemstack, ModRecipeTypes.TROMMEL);
                blockEntity.litDuration = blockEntity.litTime;

                isDirty = true;
                if (!Services.PLATFORM.getCraftRemainder(itemstack).isEmpty())
                    blockEntity.trommelInventory.setStack(FUEL, Services.PLATFORM.getCraftRemainder(itemstack));
                else {
                    itemstack.shrink(1);
                    if (itemstack.isEmpty()) {
                        blockEntity.trommelInventory.setStack(FUEL, Services.PLATFORM.getCraftRemainder(itemstack));
                    }
                }
            }
        }

        if (blockEntity.isLit()) {
            if (blockEntity.match != null) {
                ++blockEntity.cookingProgress;
                if (blockEntity.cookingProgress >= blockEntity.cookingTotalTime) {
                    blockEntity.cookingProgress = 0;
                    blockEntity.cookingTotalTime = getTotalCookTime(pLevel, blockEntity);
                    blockEntity.process();
                    isDirty = true;
                }
            }
        }

        if (blockEntity.match == null || !blockEntity.isLit()) {
            blockEntity.cookingProgress = 0;
        }

        if (flag != blockEntity.isLit()) {
            isDirty = true;
            pState = pState.setValue(TrommelBlock.LIT, blockEntity.isLit());
            pLevel.setBlock(pPos, pState, 3);
        }

        if (isDirty) {
            setChanged(pLevel, pPos, pState);
        }

    }

    private static int getTotalCookTime(Level $$0, TrommelBlockEntity $$1) {
        return $$1.quickCheck.getRecipeFor($$1.wrapper, $$0).map(TrommelRecipe::processingTime).orElse(50);
    }

    protected void process() {
        if (match != null) {
            int slot = match.findInput(wrapper);
            trommelInventory.extractStack(slot,1,false);
            if (match.outputChance() >= level.random.nextDouble()) {
                RangedEntry rangedEntry = match.get(level.random);
                ItemStack stack = rangedEntry.getItem(level.random);
                Direction direction = getBlockState().getValue(TrommelBlock.FACING).getCounterClockWise();
                BlockPos containerPos = getBlockPos().relative(direction);
                BlockEntity blockEntity = level.getBlockEntity(containerPos);
                ItemStack rejected = Services.PLATFORM.addToNearbyInventory(level, blockEntity, containerPos,stack,direction);
                if (!rejected.isEmpty()) {
                    Containers.dropItemStack(level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), rejected);
                }
            }
        }
    }

    private boolean isLit() {
        return this.litTime > 0;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new TrommelMenu(i, inventory, ContainerLevelAccess.create(level, worldPosition), trommelInventory, dataAccess);
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
        tag.put("inv", trommelInventory.serialize());
        if (this.name != null) {
            tag.putString("CustomName", Component.Serializer.toJson(this.name));
        }
    }
}

package tfar.trommel;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tfar.trommel.invetory.ContainerWrapper;
import tfar.trommel.platform.Services;
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
    private final RecipeManager.CachedCheck<Container, TrommelRecipe> quickCheck = RecipeManager.createCheck(ModRecipeTypes.TROMMEL);
    private TrommelRecipe cache;

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


        if (blockEntity.checkRecipes) {
            blockEntity.cache = blockEntity.quickCheck.getRecipeFor(blockEntity.wrapper, pLevel).orElse(null);
            blockEntity.checkRecipes = false;
        }

        if (!blockEntity.isLit()) {
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

        ItemStack itemstack = blockEntity.trommelInventory.getStack(FUEL);
        boolean flag3 = !itemstack.isEmpty();
        if (blockEntity.isLit()) {
            Recipe<?> recipe;

            if (blockEntity.isLit()) {
                ++blockEntity.cookingProgress;
                if (blockEntity.cookingProgress == blockEntity.cookingTotalTime) {
                    blockEntity.cookingProgress = 0;
                    blockEntity.cookingTotalTime = 99;//getTotalCookTime(pLevel, blockEntity);
                //    if (blockEntity.burn(pLevel.registryAccess(), recipe, blockEntity.items, i)) {
                 //       blockEntity.setRecipeUsed(recipe);
                 //   }

                    isDirty = true;
                }
            } else {
                blockEntity.cookingProgress = 0;
            }
        } else if (!blockEntity.isLit() && blockEntity.cookingProgress > 0) {
            blockEntity.cookingProgress = Mth.clamp(blockEntity.cookingProgress - 2, 0, blockEntity.cookingTotalTime);
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

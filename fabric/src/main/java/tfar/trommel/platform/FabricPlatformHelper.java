package tfar.trommel.platform;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import tfar.trommel.TrommelBlockEntity;
import tfar.trommel.TrommelInventory;
import tfar.trommel.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public TrommelInventory create(TrommelBlockEntity trommelBlockEntity) {
        return new TrommelInventoryFabric(trommelBlockEntity);
    }

    @Override
    public int getBurnTime(ItemStack stack, RecipeType<?> type) {
        return FuelRegistry.INSTANCE.get(stack.getItem());
    }

    @Override
    public ItemStack getCraftRemainder(ItemStack stack) {
        return stack.getRecipeRemainder();
    }

    @Override
    public ItemStack addToNearbyInventory(Level level, BlockEntity blockEntity, BlockPos pos, ItemStack stack, Direction direction) {
        Storage<ItemVariant> storage = ItemStorage.SIDED.find(level,pos, level.getBlockState(pos), blockEntity,direction);
        Transaction transaction = Transaction.openOuter();
        long moved = StorageUtil.tryInsertStacking(
                storage,
                ItemVariant.of(stack),
                stack.getCount(), transaction
        );
        transaction.commit();
        if (moved > 0) {
            return stack.copyWithCount(stack.getCount() - (int)moved);
        } else {
            return stack;
        }
    }
}

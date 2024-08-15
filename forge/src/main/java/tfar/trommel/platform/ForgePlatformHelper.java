package tfar.trommel.platform;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.ForgeHooks;
import tfar.trommel.TrommelBlockEntity;
import tfar.trommel.TrommelInventory;
import tfar.trommel.inventory.TrommelInventoryForge;
import tfar.trommel.platform.services.IPlatformHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public TrommelInventory create(TrommelBlockEntity trommelBlockEntity) {
        return new TrommelInventoryForge(trommelBlockEntity);
    }

    @Override
    public int getBurnTime(ItemStack stack, RecipeType<?> type) {
        return ForgeHooks.getBurnTime(stack,type);
    }

    @Override
    public ItemStack getCraftRemainder(ItemStack stack) {
        return stack.getCraftingRemainingItem();
    }

    @Override
    public ItemStack addToNearbyInventory(Level level, BlockEntity blockEntity, BlockPos pos, ItemStack stack, Direction direction) {
        return stack;
    }
}
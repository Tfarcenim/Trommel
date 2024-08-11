package tfar.trommel.platform;

import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
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
}

package tfar.trommel.platform.services;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import tfar.trommel.TrommelBlockEntity;
import tfar.trommel.TrommelInventory;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {

        return isDevelopmentEnvironment() ? "development" : "production";
    }


    TrommelInventory create(TrommelBlockEntity trommelBlockEntity);

    int getBurnTime(ItemStack stack, RecipeType<?> type);

    ItemStack getCraftRemainder(ItemStack stack);

    ItemStack addToNearbyInventory(Level level, BlockEntity blockEntity, BlockPos pos, ItemStack stack, Direction direction);


}
package tfar.trommel;

import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipeTypes {
    public static final RecipeType<CraftingRecipe> TROMMEL = new RecipeType<>() {
        @Override
        public String toString() {
            return Trommel.MOD_ID+":trommel";
        }
    };
}
